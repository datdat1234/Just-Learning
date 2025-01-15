# https://vuejs.org/tutorial/

## Welcome to the Vue tutorial!

- The core feature of Vue is declarative rendering: using a template syntax that extends HTML, we can describe how the HTML should look based on JavaScript state. When the state changes, the HTML updates automatically.

- State that can trigger updates when changed is considered reactive. We can declare reactive state using Vue's reactive() API. Objects created from reactive() are JavaScript Proxies that work just like normal objects.
```js
import { reactive } from 'vue'

const counter = reactive({
  count: 0
})

console.log(counter.count) // 0
counter.count++
```

- reactive() only works on objects (including arrays and built-in types like Map and Set). ref(), on the other hand, can take any value type and create an object that exposes the inner value under a .value property.

- Reactive state declared in the component's `<script setup>` block can be used directly in the template. This is how we can render dynamic text based on the value of the counter object and message ref, using mustaches syntax.

- Notice how we did not need to use .value when accessing the message ref in templates: it is automatically unwrapped for more succinct usage.

- A directive is a special attribute that starts with the v- prefix. They are part of Vue's template syntax. Similar to text interpolations, directive values are JavaScript expressions that have access to the component's state. Because v-bind is used so frequently, it has a dedicated shorthand syntax:
```
<div :id="dynamicId"></div>
```

- __Event Listeners__: We can listen to DOM events using the v-on directive:
```
<button v-on:click="increment">{{ count }}</button>
```

- Event handlers can also use inline expressions, and can simplify common tasks with modifiers.