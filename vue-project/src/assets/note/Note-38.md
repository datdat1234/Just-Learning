# Server-Side Rendering (SSR)

## Overview

### What is SSR?

Vue.js is a framework for building client-side applications. By default, Vue components produce and manipulate DOM in the browser as output. However, it is also possible to render the same components into HTML strings on the server, send them directly to the browser, and finally "hydrate" the static markup into a fully interactive app on the client.

A server-rendered Vue.js app can also be considered "isomorphic" or "universal", in the sense that the majority of your app's code runs on both the server and the client.

### Why SSR?

Compared to a client-side Single-Page Application (SPA), the advantage of SSR primarily lies in:
   - __Faster time-to-content__: this is more prominent on slow internet or slow devices. Server-rendered markup doesn't need to wait until all JavaScript has been downloaded and executed to be displayed, so your user will see a fully-rendered page sooner. In addition, data fetching is done on the server-side for the initial visit, which likely has a faster connection to your database than the client. This generally results in improved Core Web Vitals metrics, better user experience, and can be critical for applications where time-to-content is directly associated with conversion rate.
   - __Unified mental model__: you get to use the same language and the same declarative, component-oriented mental model for developing your entire app, instead of jumping back and forth between a backend templating system and a frontend framework.
   - __Better SEO__: the search engine crawlers will directly see the fully rendered page.

As of now, Google and Bing can index synchronous JavaScript applications just fine. Synchronous being the key word there. If your app starts with a loading spinner, then fetches content via Ajax, the crawler will not wait for you to finish. This means if you have content fetched asynchronously on pages where SEO is important, SSR might be necessary.

There are also some trade-offs to consider when using SSR:
   - __Development constraints__. Browser-specific code can only be used inside certain lifecycle hooks; some external libraries may need special treatment to be able to run in a server-rendered app.
   - __More involved build setup and deployment requirements__. Unlike a fully static SPA that can be deployed on any static file server, a server-rendered app requires an environment where a Node.js server can run.
   - __More server-side load__. Rendering a full app in Node.js is going to be more CPU-intensive than just serving static files, so if you expect high traffic, be prepared for corresponding server load and wisely employ caching strategies.

Before using SSR for your app, the first question you should ask is whether you actually need it. It mostly depends on how important time-to-content is for your app. For example, if you are building an internal dashboard where an extra few hundred milliseconds on initial load doesn't matter that much, SSR would be an overkill. However, in cases where time-to-content is absolutely critical, SSR can help you achieve the best possible initial load performance.

### SSR vs. SSG

__Static Site Generation (SSG)__, also referred to as pre-rendering, is another popular technique for building fast websites. If the data needed to server-render a page is the same for every user, then instead of rendering the page every time a request comes in, we can render it only once, ahead of time, during the build process. Pre-rendered pages are generated and served as static HTML files.

SSG retains the same performance characteristics of SSR apps: it provides great time-to-content performance. At the same time, it is cheaper and easier to deploy than SSR apps because the output is static HTML and assets. The keyword here is static: SSG can only be applied to pages providing static data, i.e. data that is known at build time and can not change between requests. Every time the data changes, a new deployment is needed.

If you're only investigating SSR to improve the SEO of a handful of marketing pages (e.g. /, /about, /contact, etc.), then you probably want SSG instead of SSR. SSG is also great for content-based websites such as documentation sites or blogs. In fact, this website you are reading right now is statically generated using VitePress, a Vue-powered static site generator.

## Basic Tutorial

### Rendering an App

Let's take a look at the most bare-bones example of Vue SSR in action.
   - Create a new directory and cd into it
   - Run npm init -y
   - Add "type": "module" in package.json so that Node.js runs in ES modules mode.
   - Run npm install vue
   - Create an example.js file:

```js
// this runs in Node.js on the server.
import { createSSRApp } from 'vue'
// Vue's server-rendering API is exposed under `vue/server-renderer`.
import { renderToString } from 'vue/server-renderer'

const app = createSSRApp({
  data: () => ({ count: 1 }),
  template: `<button @click="count++">{{ count }}</button>`
})

renderToString(app).then((html) => {
  console.log(html)
})
```

Then run:

```sh
node example.js
```

It should print the following to the command line:

```
<button>1</button>
```

renderToString() takes a Vue app instance and returns a Promise that resolves to the rendered HTML of the app. It is also possible to stream rendering using the Node.js Stream API or Web Streams API. Check out the SSR API Reference for full details.

We can then move the Vue SSR code into a server request handler, which wraps the application markup with the full page HTML. We will be using express for the next steps:
   - Run npm install express
   - Create the following server.js file:

```js
import express from 'express'
import { createSSRApp } from 'vue'
import { renderToString } from 'vue/server-renderer'

const server = express()

server.get('/', (req, res) => {
  const app = createSSRApp({
    data: () => ({ count: 1 }),
    template: `<button @click="count++">{{ count }}</button>`
  })

  renderToString(app).then((html) => {
    res.send(`
    <!DOCTYPE html>
    <html>
      <head>
        <title>Vue SSR Example</title>
      </head>
      <body>
        <div id="app">${html}</div>
      </body>
    </html>
    `)
  })
})

server.listen(3000, () => {
  console.log('ready')
})
```

Finally, run node server.js and visit http://localhost:3000. You should see the page working with the button.

### Client Hydration

If you click the button, you'll notice the number doesn't change. The HTML is completely static on the client since we are not loading Vue in the browser.

To make the client-side app interactive, Vue needs to perform the hydration step. During hydration, it creates the same Vue application that was run on the server, matches each component to the DOM nodes it should control, and attaches DOM event listeners.

To mount an app in hydration mode, we need to use createSSRApp() instead of createApp():

```js
// this runs in the browser.
import { createSSRApp } from 'vue'

const app = createSSRApp({
  // ...same app as on server
})

// mounting an SSR app on the client assumes
// the HTML was pre-rendered and will perform
// hydration instead of mounting new DOM nodes.
app.mount('#app')
```

### Code Structure

Notice how we need to reuse the same app implementation as on the server. This is where we need to start thinking about code structure in an SSR app - how do we share the same application code between the server and the client?

Here we will demonstrate the most bare-bones setup. First, let's split the app creation logic into a dedicated file, app.js:

```js
// app.js (shared between server and client)
import { createSSRApp } from 'vue'

export function createApp() {
  return createSSRApp({
    data: () => ({ count: 1 }),
    template: `<button @click="count++">{{ count }}</button>`
  })
}
```

This file and its dependencies are shared between the server and the client - we call them __universal code__. There are a number of things you need to pay attention to when writing universal code, as we will discuss below.

Our client entry imports the universal code, creates the app, and performs the mount:

```js
// client.js
import { createApp } from './app.js'

createApp().mount('#app')
```

And the server uses the same app creation logic in the request handler:

```js
// server.js (irrelevant code omitted)
import { createApp } from './app.js'

server.get('/', (req, res) => {
  const app = createApp()
  renderToString(app).then(html => {
    // ...
  })
})
```

In addition, in order to load the client files in the browser, we also need to:
   - Serve client files by adding server.use(express.static('.')) in server.js.
   - Load the client entry by adding `<script type="module" src="/client.js"></script>` to the HTML shell.
   - Support usage like import * from 'vue' in the browser by adding an Import Map to the HTML shell.