<script setup>
import { ref } from 'vue'
const selected = ref('A')
const msg = ref('A')

const options = ref([
  { text: 'One', value: 'A' },
  { text: 'Two', value: 'B' },
  { text: 'Three', value: 'C' }
])
</script>
<template>
  <select v-model="selected">
    <option v-for="option in options" :value="option.value">
      {{ option.text }}
    </option>
  </select>

  <div>Selected: {{ selected }}</div>

  <input v-model.lazy="msg" />
  <h1>{{ msg }}</h1>
</template>
<style></style>

<!-- 
- In addition, v-model can be used on inputs of different types, <textarea>, and <select> elements. 
It automatically expands to different DOM property and event pairs based on the element it is used on:
+ <input> with text types and <textarea> elements use value property and input event;
+ <input type="checkbox"> and <input type="radio"> use checked property and change event;
+ <select> uses value as a prop and change as an event.

- For languages that require an IME (Chinese, Japanese, Korean etc.), you'll notice that v-model doesn't 
get updated during IME composition. If you want to respond to these updates as well, use your own input 
event listener and value binding instead of using v-model.

- If the initial value of your v-model expression does not match any of the options, the <select> element 
will render in an "unselected" state. On iOS this will cause the user not being able to select the first 
item because iOS does not fire a change event in this case. It is therefore recommended to provide a disabled 
option with an empty value, as demonstrated in the example above.

<div>Selected: {{ selected }}</div>

<select v-model="selected">
  <option disabled value="">Please select one</option>
  <option>A</option>
  <option>B</option>
  <option>C</option>
</select>

- If the initial value of your v-model expression does not match any of the options, the <select> element will 
render in an "unselected" state. On iOS this will cause the user not being able to select the first item because 
iOS does not fire a change event in this case. It is therefore recommended to provide a disabled option with an 
empty value, as demonstrated in the example above.

- Select options can be dynamically rendered with v-for:
EX:
const selected = ref('A')

const options = ref([
  { text: 'One', value: 'A' },
  { text: 'Two', value: 'B' },
  { text: 'Three', value: 'C' }
])

<select v-model="selected">
  <option v-for="option in options" :value="option.value">
    {{ option.text }}
  </option>
</select>

<div>Selected: {{ selected }}</div>

- For radio, checkbox and select options, the v-model binding values are usually static strings (or booleans for checkbox):
EX: `picked` is a string "a" when checked
<input type="radio" v-model="picked" value="a" />

`toggle` is either true or false
<input type="checkbox" v-model="toggle" />

`selected` is a string "abc" when the first option is selected
<select v-model="selected">
  <option value="abc">ABC</option>
</select>

- The true-value and false-value attributes don't affect the input's value attribute, because browsers don't include unchecked 
boxes in form submissions. To guarantee that one of two values is submitted in a form (e.g. "yes" or "no"), use radio inputs instead.

- v-model supports value bindings of non-string values as well! In the above example, when the option is selected, selected will 
be set to the object literal value of { number: 123 }.

<select v-model="selected">
  <option :value="{ number: 123 }">123</option>
</select>

- .lazy: By default, v-model syncs the input with the data after each input event (with the exception of IME composition as stated above). 
You can add the lazy modifier to instead sync after change events: onInput ~ when the value change, onChange ~ when the input lose focus.
EX: <input v-model.lazy="msg" />

- If you want user input to be automatically typecast as a number, you can add the number modifier to your v-model managed inputs:
EX: <input v-model.number="age" />

- If the value cannot be parsed with parseFloat(), then the original (string) value is used instead. In particular, if the input is 
empty (for instance after the user clearing the input field), an empty string is returned

- If you want whitespace from user input to be trimmed automatically, you can add the trim modifier to your v-model-managed inputs:
EX: <input v-model.trim="msg" />
-->
