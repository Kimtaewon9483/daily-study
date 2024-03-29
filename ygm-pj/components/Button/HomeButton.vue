<template>
  <button
    @click="$router.push('/')"
    class="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow"
  >
    <slot>Home</slot>
  </button>
</template>

<script lang="ts">
export default {
  setup() {
    return {};
  },
};
</script>

<style lang="scss" scoped></style>
