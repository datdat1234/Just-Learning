<script setup>
import { ref } from 'vue'

const isActive = ref(true)
const hasError = ref(false)
</script>

<template>
  <div
    class="static"
    :class="{ active: isActive, 'text-danger': hasError }"
  ></div>
</template>

<!-- 
- We can pass an object to :class (short for v-bind:class) to dynamically toggle classes
EX: <div :class="{ active: isActive }"></div>

- The bound object doesn't have to be inline:
const classObject = reactive({
  active: true,
  'text-danger': false
})

<div :class="classObject"></div>

- We can also bind to a computed property that returns an object. This is a common and powerful pattern:
const isActive = ref(true)
const error = ref(null)

const classObject = computed(() => ({
  active: isActive.value && !error.value,
  'text-danger': error.value && error.value.type === 'fatal'
}))

<div :class="classObject"></div>

- We can bind :class to an array to apply a list of classes:
<div :class="[activeClass, errorClass]"></div>

- It's also possible to use the object syntax inside the array syntax:
<div :class="[{ [activeClass]: isActive }, errorClass]"></div>

- When you use the class attribute on a component with a single root element, 
those classes will be added to the component's root element and merged with any 
existing class already on it.
<p class="foo bar">Hi!</p>
                                  =>>>>  <p class="foo bar baz boo">Hi!</p>
<MyComponent class="baz boo" />

- If your component has multiple root elements, you would need to define which 
element will receive this class. You can do this using the $attrs component property:
<p :class="$attrs.class">Hi!</p>
                                          ////=> <MyComponent class="baz" />
<span>This is a child component</span>

- Although camelCase keys are recommended, :style also supports kebab-cased CSS property 
keys (corresponds to how they are used in actual CSS) - for example:
<div :style="{ 'font-size': fontSize + 'px' }"></div>

- It is often a good idea to bind to a style object directly so that the template is cleaner:
const styleObject = reactive({
  color: 'red',
  fontSize: '30px'
})

<div :style="styleObject"></div>

- We can bind :style to an array of multiple style objects. These objects will be merged and 
applied to the same element:
<div :style="[baseStyles, overridingStyles]"></div>

-->
