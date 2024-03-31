const express = require("express");
const app = express();
const cors = require("cors");
const PORT = 8080;

app.use(cors());

app.get("/api/v1/getCategoryList/:id", (req, res) => {
  const { categoryList } = require("./mock/categoryList");
  const id = req.params.id;

  const filteredList = categoryList.filter((item) => item.categoryCode == id);
  console.log(filteredList);
  res.json(filteredList);
});

app.listen(PORT, () => {
  console.log(`Server is running on ${PORT}`);
});
