<template>
  <div class="mx-[500px]">
    <input class="m-5 p-1.5 w-128 rounded shadow" placeholder="Search" />
    <div class="mr-10">
      <div v-for="item in list" :key="item.name" :id="item.name">
        <div class="flex flex-col mt-4">
          <p class="font-bold">{{ item.name }}</p>
          <NuxtLink :to="item.url"> ATMOS </NuxtLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
const list = [
  {
    name: "A",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "B",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "C",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "D",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "E",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "F",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "G",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "H",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "I",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "J",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "K",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "L",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "M",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "N",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "O",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "P",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "Q",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "R",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "S",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "T",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "U",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "V",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "W",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "X",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "Y",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    name: "Z",
    url: "https://www.atmos-tokyo.com/",
  },
];
</script>

<style></style>
