<script setup>
import { shuffle } from 'lodash'
import { ref } from 'vue'

const items = ref([
  { id: 1, label: 'first name' },
  { id: 2, label: 'last name' },
  { id: 3, label: 'email' },
  { id: 4, label: 'website' }
])
const shuffleItems = () => {
  items.value = shuffle(items.value)
}
</script>
<template>
  <ul>
    <li v-for="item in items">
      {{ item.label }} <input type="text" />
    </li>
  </ul>
  <button @click="shuffleItems">Shuffle Items</button>
</template>
<style>
ul {
  margin: 0;
  padding: 0;
  padding-right: 20px;
  margin-bottom: 10px;
  list-style-type: none;
}
ul li {
  display: block;
  margin-bottom: 20px;
}
ul li input {
  display: block;
}
</style>

<!-- 
- Without keys, Vue uses an algorithm that minimizes element movement and tries to patch/reuse elements 
of the same type in-place as much as possible. With keys, it will reorder elements based on the order 
change of keys, and elements with keys that are no longer present will always be removed / destroyed.

- Children of the same common parent must have unique keys. Duplicate keys will cause render errors.

Explaination:
-  Như giải thích ở trên, Vue có cơ chế render theo key. Nếu không có key, Vue sẽ cố gắng tái sử dụng
lại các phần tử cùng loại mà không cần phải render lại. Điều này giúp tăng hiệu suất render của Vue, tuy
nhiên khi các phần tử có thay đổi vị trí, Vue lại không thực hiện render đúng lại thứ tự, chỉ đổ data vào
gây ra sai sót. (Như ví dụ trên, chỉ render lại dữ liệu chứ ko render lại component).

- Còn với có key, Vue sẽ thực hiện sắp xếp lại thứ tự theo key, và các phần tử không còn key sẽ bị xóa đi.
Nếu truyền vào key là index, giả sử trong trường hợp shuffle, chiều dài mảng vẫn như cũ, index vẫn như vậy
(Trong ví dụ từ 1 tới 4), Vue sẽ cho rằng mảng không có thay đổi nên không thực hiện render lại các component
cho đúng mà chỉ render lại dữ liệu.
-->