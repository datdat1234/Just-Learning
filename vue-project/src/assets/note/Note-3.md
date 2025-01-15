- Here we have declared a computed property publishedBooksMessage. The computed() function expects to be passed a getter function, and the returned value is a computed ref. Similar to normal refs, you can access the computed result as publishedBooksMessage.value. Computed refs are also auto-unwrapped in templates so you can reference them without .value in template expressions.

- A computed property automatically tracks its reactive dependencies. Vue is aware that the computation of publishedBooksMessage depends on author.books, so it will update any bindings that depend on publishedBooksMessage when author.books changes.

- Instead of a computed property, we can define the same function as a method. For the end result, the two approaches are indeed exactly the same. However, the difference is that computed properties are cached based on their reactive dependencies. A computed property will only re-evaluate when some of its reactive dependencies have changed. This means as long as author.books has not changed, multiple access to publishedBooksMessage will immediately return the previously computed result without having to run the getter function again.

- This also means the following computed property will never update, because Date.now() is not a reactive dependency:
`EX: const now = computed(() => Date.now())`
. In comparison, a method invocation will always run the function whenever a re-render happens.

- Why do we need caching? Imagine we have an expensive computed property list, which requires looping through a huge array and doing a lot of computations. Then we may have other computed properties that in turn depend on list. Without caching, we would be executing list’s getter many more times than necessary! In cases where you do not want caching, use a method call instead.

- In case you need it, you can get the previous value returned by the computed property accessing the first argument of the getter

- Computed
1. Getters should be side-effect free: It is important to remember that computed getter functions should only perform pure computation and be free of side effects. For example, don't mutate other state, make async requests, or mutate the DOM inside a computed getter! To perform side effects in reaction to state changes, we use watchers.
2. Avoid mutating computed value: The returned value from a computed property is derived state. Think of it as a temporary snapshot - every time the source state changes, a new snapshot is created. It does not make sense to mutate a snapshot, so a computed return value should be treated as read-only and never be mutated - instead, update the source state it depends on to trigger new computations.