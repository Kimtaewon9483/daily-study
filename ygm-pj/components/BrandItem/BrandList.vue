<template>
  <div class="mx-[500px]">
    <input class="m-5 p-1.5 w-128 rounded shadow" placeholder="Search" />
    <div class="mr-10">
      <div v-for="item in list" :key="item.id" :id="item.id">
        <div class="flex flex-col mt-4">
          <p class="font-bold">{{ item.id }}</p>
          <NuxtLink :to="item.url">{{ item.name }} </NuxtLink>
          <br />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
const list = [
  {
    id: "A",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "B",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "C",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "D",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "E",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "F",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "G",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "H",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "I",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "J",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "K",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "L",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "M",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "N",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "O",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "P",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "Q",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "R",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "S",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "T",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "U",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "V",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "W",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "X",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "Y",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
  {
    id: "Z",
    name: "ATMOS",
    url: "https://www.atmos-tokyo.com/",
  },
];
</script>

<style></style>
