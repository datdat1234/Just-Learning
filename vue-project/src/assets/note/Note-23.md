## Accepting Reactive State
​
- useFetch() takes a static URL string as input - so it performs the fetch only once and is then done. What if we want it to re-fetch whenever the URL changes? In order to achieve this, we need to pass reactive state into the composable function, and let the composable create watchers that perform actions using the passed state.

- For example, useFetch() should be able to accept a ref:
```
const url = ref('/initial-url')

const { data, error } = useFetch(url)

// this should trigger a re-fetch
url.value = '/new-url'
```

- Or, accept a getter function:
```
// re-fetch when props.id changes
const { data, error } = useFetch(() => `/posts/${props.id}`)
```

- We can refactor our existing implementation with the watchEffect() and toValue() APIs:
```js
// fetch.js
import { ref, watchEffect, toValue } from 'vue'

export function useFetch(url) {
  const data = ref(null)
  const error = ref(null)

  const fetchData = () => {
    // reset state before fetching..
    data.value = null
    error.value = null

    fetch(toValue(url))
      .then((res) => res.json())
      .then((json) => (data.value = json))
      .catch((err) => (error.value = err))
  }

  watchEffect(() => {
    fetchData()
  })

  return { data, error }
}
```

- toValue() is an API added in 3.3. It is designed to normalize refs or getters into values. If the argument is a ref, it returns the ref's value; if the argument is a function, it will call the function and return its return value. Otherwise, it returns the argument as-is. It works similarly to unref(), but with special treatment for functions.

- Notice that toValue(url) is called inside the watchEffect callback. This ensures that any reactive dependencies accessed during the toValue() normalization are tracked by the watcher.

- This version of useFetch() now accepts static URL strings, refs, and getters, making it much more flexible. The watch effect will run immediately, and will track any dependencies accessed during toValue(url). If no dependencies are tracked (e.g. url is already a string), the effect runs only once; otherwise, it will re-run whenever a tracked dependency changes.

# Conventions and Best Practices
​
## Naming

- It is a convention to name composable functions with camelCase names that start with "use".

## Input Arguments
​
- A composable can accept ref or getter arguments even if it doesn't rely on them for reactivity. If you are writing a composable that may be used by other developers, it's a good idea to handle the case of input arguments being refs or getters instead of raw values. The toValue() utility function will come in handy for this purpose:
```
import { toValue } from 'vue'

function useFeature(maybeRefOrGetter) {
  // If maybeRefOrGetter is a ref or a getter,
  // its normalized value will be returned.
  // Otherwise, it is returned as-is.
  const value = toValue(maybeRefOrGetter)
}
```

- If your composable creates reactive effects when the input is a ref or a getter, make sure to either explicitly watch the ref / getter with watch(), or call toValue() inside a watchEffect() so that it is properly tracked.

# Return Values
​
- You have probably noticed that we have been exclusively using ref() instead of reactive() in composables. The recommended convention is for composables to always return a plain, non-reactive object containing multiple refs. This allows it to be destructured in components while retaining reactivity:
```js
// x and y are refs
const { x, y } = useMouse()
```

- Returning a reactive object from a composable will cause such destructures to lose the reactivity connection to the state inside the composable, while the refs will retain that connection.

- If you prefer to use returned state from composables as object properties, you can wrap the returned object with reactive() so that the refs are unwrapped. For example:
```js
const mouse = reactive(useMouse())
// mouse.x is linked to original ref
console.log(mouse.x)
```

```template
Mouse position is at: {{ mouse.x }}, {{ mouse.y }}
```

## Side Effects
​
- It is OK to perform side effects (e.g. adding DOM event listeners or fetching data) in composables, but pay attention to the following rules:
   - If you are working on an application that uses Server-Side Rendering (SSR), make sure to perform DOM-specific side effects in post-mount lifecycle hooks, e.g. onMounted(). These hooks are only called in the browser, so you can be sure that code inside them has access to the DOM.
   - Remember to clean up side effects in onUnmounted(). For example, if a composable sets up a DOM event listener, it should remove that listener in onUnmounted() as we have seen in the useMouse() example. It can be a good idea to use a composable that automatically does this for you, like the useEventListener() example.

## Usage Restrictions
​
- Composables should only be called in `<script setup>` or the setup() hook. They should also be called synchronously in these contexts. In some cases, you can also call them in lifecycle hooks like onMounted().

- These restrictions are important because these are the contexts where Vue is able to determine the current active component instance. Access to an active component instance is necessary so that:
   1. Lifecycle hooks can be registered to it.
   2. Computed properties and watchers can be linked to it, so that they can be disposed when the instance is unmounted to prevent memory leaks.

- `<script setup>` is the only place where you can call composables after using await. The compiler automatically restores the active instance context for you after the async operation.

# Extracting Composables for Code Organization
​
- Composables can be extracted not only for reuse, but also for code organization. As the complexity of your components grow, you may end up with components that are too large to navigate and reason about. Composition API gives you the full flexibility to organize your component code into smaller functions based on logical concerns:
```vue
<script setup>
import { useFeatureA } from './featureA.js'
import { useFeatureB } from './featureB.js'
import { useFeatureC } from './featureC.js'

const { foo, bar } = useFeatureA()
const { baz } = useFeatureB(foo)
const { qux } = useFeatureC(baz)
</script>
```

- To some extent, you can think of these extracted composables as component-scoped services that can talk to one another.

# Comparisons with Other Techniques
​
## ​vs. Mixins
​
- Users coming from Vue 2 may be familiar with the mixins option, which also allows us to extract component logic into reusable units. There are three primary drawbacks to mixins:
   - __Unclear source of properties__: when using many mixins, it becomes unclear which instance property is injected by which mixin, making it difficult to trace the implementation and understand the component's behavior. This is also why we recommend using the refs + destructure pattern for composables: it makes the property source clear in consuming components.
   - __Namespace collisions__: multiple mixins from different authors can potentially register the same property keys, causing namespace collisions. With composables, you can rename the destructured variables if there are conflicting keys from different composables.
   - __Implicit cross-mixin communication__: multiple mixins that need to interact with one another have to rely on shared property keys, making them implicitly coupled. With composables, values returned from one composable can be passed into another as arguments, just like normal functions.

## vs. Renderless Components
​
- In the component slots chapter, we discussed the Renderless Component pattern based on scoped slots. We even implemented the same mouse tracking demo using renderless components.

- The main advantage of composables over renderless components is that composables do not incur the extra component instance overhead. When used across an entire application, the amount of extra component instances created by the renderless component pattern can become a noticeable performance overhead.

- The recommendation is to use composables when reusing pure logic, and use components when reusing both logic and visual layout.

## vs. React Hooks
​
- If you have experience with React, you may notice that this looks very similar to custom React hooks. Composition API was in part inspired by React hooks, and Vue composables are indeed similar to React hooks in terms of logic composition capabilities. However, Vue composables are based on Vue's fine-grained reactivity system, which is fundamentally different from React hooks' execution model. This is discussed in more detail in the Composition API FAQ.