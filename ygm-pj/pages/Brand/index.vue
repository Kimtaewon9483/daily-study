<style lang="scss" scoped></style>

<template>
  <div>
    <nav v-if="$route.path !== '/brand'" class="m-12">
      <NuxtLink class="m-2.5" to="/">HOME</NuxtLink>
      <NuxtLink class="m-2.5" to="/brand">BRAND</NuxtLink>
      <NuxtLink class="m-2.5" to="/category">CATEGORY</NuxtLink>
    </nav>
    <div>Brand list</div>
    <BrandList />

    <NuxtPage />
  </div>
</template>

<script lang="ts">
import BrandList from "~/components/BrandItem/BrandList.vue";

export default {
  components: { BrandList },
  setup() {
    return {};
  },
};
</script>
