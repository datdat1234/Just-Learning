## Update Optimizations

### Props Stability

In Vue, a child component only updates when at least one of its received props has changed. Consider the following example:

```template
<ListItem
  v-for="item in list"
  :id="item.id"
  :active-id="activeId" />
```

Inside the `<ListItem>` component, it uses its id and activeId props to determine whether it is the currently active item. While this works, the problem is that whenever activeId changes, every `<ListItem>` in the list has to update!

Ideally, only the items whose active status changed should update. We can achieve that by moving the active status computation into the parent, and make `<ListItem>` directly accept an active prop instead:

```template
<ListItem
  v-for="item in list"
  :id="item.id"
  :active="item.id === activeId" />
```

Now, for most components the active prop will remain the same when activeId changes, so they no longer need to update. In general, the idea is keeping the props passed to child components as stable as possible.

#### v-once

v-once is a built-in directive that can be used to render content that relies on runtime data but never needs to update. The entire sub-tree it is used on will be skipped for all future updates. Consult its API reference for more details.

#### v-memo

v-memo is a built-in directive that can be used to conditionally skip the update of large sub-trees or v-for lists. Consult its API reference for more details.

### Computed Stability

In Vue 3.4 and above, a computed property will only trigger effects when its computed value has changed from the previous one. For example, the following isEven computed only triggers effects if the returned value has changed from true to false, or vice-versa:

```js
const count = ref(0)
const isEven = computed(() => count.value % 2 === 0)

watchEffect(() => console.log(isEven.value)) // true

// will not trigger new logs because the computed value stays `true`
count.value = 2
count.value = 4
```

This reduces unnecessary effect triggers, but unfortunately doesn't work if the computed creates a new object on each compute:

```js
const computedObj = computed(() => {
  return {
    isEven: count.value % 2 === 0
  }
})
```

Because a new object is created each time, the new value is technically always different from the old value. Even if the isEven property remains the same, Vue won't be able to know unless it performs a deep comparison of the old value and the new value. Such comparison could be expensive and likely not worth it.

Instead, we can optimize this by manually comparing the new value with the old value, and conditionally returning the old value if we know nothing has changed:

```js
const computedObj = computed((oldValue) => {
  const newValue = {
    isEven: count.value % 2 === 0
  }
  if (oldValue && oldValue.isEven === newValue.isEven) {
    return oldValue
  }
  return newValue
})
```

## General Optimizations

### Virtualize Large Lists

One of the most common performance issues in all frontend applications is rendering large lists. No matter how performant a framework is, rendering a list with thousands of items will be slow due to the sheer number of DOM nodes that the browser needs to handle.

However, we don't necessarily have to render all these nodes upfront. In most cases, the user's screen size can display only a small subset of our large list. We can greatly improve the performance with list virtualization, the technique of only rendering the items that are currently in or close to the viewport in a large list.

Implementing list virtualization isn't easy, luckily there are existing community libraries that you can directly use:
   - vue-virtual-scroller
   - vue-virtual-scroll-grid
   - vueuc/VVirtualList

### Reduce Reactivity Overhead for Large Immutable Structures

Vue's reactivity system is deep by default. While this makes state management intuitive, it does create a certain level of overhead when the data size is large, because every property access triggers proxy traps that perform dependency tracking. This typically becomes noticeable when dealing with large arrays of deeply nested objects, where a single render needs to access 100,000+ properties, so it should only affect very specific use cases.

Vue does provide an escape hatch to opt-out of deep reactivity by using shallowRef() and shallowReactive(). Shallow APIs create state that is reactive only at the root level, and exposes all nested objects untouched. This keeps nested property access fast, with the trade-off being that we must now treat all nested objects as immutable, and updates can only be triggered by replacing the root state:

```js
const shallowArray = shallowRef([
  /* big list of deep objects */
])

// this won't trigger updates...
shallowArray.value.push(newObject)
// this does:
shallowArray.value = [...shallowArray.value, newObject]

// this won't trigger updates...
shallowArray.value[0].foo = 1
// this does:
shallowArray.value = [
  {
    ...shallowArray.value[0],
    foo: 1
  },
  ...shallowArray.value.slice(1)
]
```

### Avoid Unnecessary Component Abstractions

Sometimes we may create renderless components or higher-order components (i.e. components that render other components with extra props) for better abstraction or code organization. While there is nothing wrong with this, do keep in mind that component instances are much more expensive than plain DOM nodes, and creating too many of them due to abstraction patterns will incur performance costs.

Note that reducing only a few instances won't have noticeable effect, so don't sweat it if the component is rendered only a few times in the app. The best scenario to consider this optimization is again in large lists. Imagine a list of 100 items where each item component contains many child components. Removing one unnecessary component abstraction here could result in a reduction of hundreds of component instances.