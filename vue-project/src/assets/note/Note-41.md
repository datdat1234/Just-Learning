# Performance

## Overview

Vue is designed to be performant for most common use cases without much need for manual optimizations. However, there are always challenging scenarios where extra fine-tuning is needed. In this section, we will discuss what you should pay attention to when it comes to performance in a Vue application.

First, let's discuss the two major aspects of web performance:
   - __Page Load Performance__: how fast the application shows content and becomes interactive on the initial visit. This is usually measured using web vital metrics like Largest Contentful Paint (LCP) and Interaction to Next Paint.
   - __Update Performance__: how fast the application updates in response to user input. For example, how fast a list updates when the user types in a search box, or how fast the page switches when the user clicks a navigation link in a Single-Page Application (SPA).

While it would be ideal to maximize both, different frontend architectures tend to affect how easy it is to attain desired performance in these aspects. In addition, the type of application you are building greatly influences what you should prioritize in terms of performance. Therefore, the first step of ensuring optimal performance is picking the right architecture for the type of application you are building:
   - Consult Ways of Using Vue to see how you can leverage Vue in different ways.
   - Jason Miller discusses the types of web applications and their respective ideal implementation / delivery in Application Holotypes.

## Profiling Options

To improve performance, we need to first know how to measure it. There are a number of great tools that can help in this regard:

For profiling load performance of production deployments:
   - PageSpeed Insights
   - WebPageTest

For profiling performance during local development:
   - Chrome DevTools Performance Panel: app.config.performance enables Vue-specific performance markers in Chrome DevTools' performance timeline.
   - Vue DevTools Extension also provides a performance profiling feature.

## Page Load Optimizations

There are many framework-agnostic aspects for optimizing page load performance - check out this web.dev guide for a comprehensive round up. Here, we will primarily focus on techniques that are specific to Vue.

### Choosing the Right Architecture

If your use case is sensitive to page load performance, avoid shipping it as a pure client-side SPA. You want your server to be directly sending HTML containing the content the users want to see. Pure client-side rendering suffers from slow time-to-content. This can be mitigated with Server-Side Rendering (SSR) or Static Site Generation (SSG). Check out the SSR Guide to learn about performing SSR with Vue. If your app doesn't have rich interactivity requirements, you can also use a traditional backend server to render the HTML and enhance it with Vue on the client.

If your main application has to be an SPA, but has marketing pages (landing, about, blog), ship them separately! Your marketing pages should ideally be deployed as static HTML with minimal JS, by using SSG.

### Bundle Size and Tree-shaking

One of the most effective ways to improve page load performance is shipping smaller JavaScript bundles. Here are a few ways to reduce bundle size when using Vue:
   - Use a build step if possible.
      - Many of Vue's APIs are "tree-shakable" if bundled via a modern build tool. For example, if you don't use the built-in `<Transition>` component, it won't be included in the final production bundle. Tree-shaking can also remove other unused modules in your source code.
      - When using a build step, templates are pre-compiled so we don't need to ship the Vue compiler to the browser. This saves 14kb min+gzipped JavaScript and avoids the runtime compilation cost.
   - Be cautious of size when introducing new dependencies! In real-world applications, bloated bundles are most often a result of introducing heavy dependencies without realizing it.
      - If using a build step, prefer dependencies that offer ES module formats and are tree-shaking friendly. For example, prefer lodash-es over lodash.
      - Check a dependency's size and evaluate whether it is worth the functionality it provides. Note if the dependency is tree-shaking friendly, the actual size increase will depend on the APIs you actually import from it. Tools like bundlejs.com can be used for quick checks, but measuring with your actual build setup will always be the most accurate.
   - If you are using Vue primarily for progressive enhancement and prefer to avoid a build step, consider using petite-vue (only 6kb) instead.

### Code Splitting

Code splitting is where a build tool splits the application bundle into multiple smaller chunks, which can then be loaded on demand or in parallel. With proper code splitting, features required at page load can be downloaded immediately, with additional chunks being lazy loaded only when needed, thus improving performance.

Bundlers like Rollup (which Vite is based upon) or webpack can automatically create split chunks by detecting the ESM dynamic import syntax:

```js
// lazy.js and its dependencies will be split into a separate chunk
// and only loaded when `loadLazy()` is called.
function loadLazy() {
  return import('./lazy.js')
}
```

Lazy loading is best used on features that are not immediately needed after initial page load. In Vue applications, this can be used in combination with Vue's Async Component feature to create split chunks for component trees:

```js
import { defineAsyncComponent } from 'vue'

// a separate chunk is created for Foo.vue and its dependencies.
// it is only fetched on demand when the async component is
// rendered on the page.
const Foo = defineAsyncComponent(() => import('./Foo.vue'))
```

For applications using Vue Router, it is strongly recommended to use lazy loading for route components. Vue Router has explicit support for lazy loading, separate from defineAsyncComponent. See Lazy Loading Routes for more details.