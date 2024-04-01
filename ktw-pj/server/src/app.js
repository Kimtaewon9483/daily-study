const express = require("express");
const app = express();
const cors = require("cors");
const mysql = require("mysql");
const PORT = 8080; // PORT번호는 .env로 옮기고싶음 (더욱 추후에는 도커로 실행)
// .env파일 사용을 위해 dotenv 모듈 설치 필요

app.use(express.json());
app.use(cors());

const dbConnInfo = {
  host: "127.0.0.1",
  user: "root",
  password: "1234",
  port: "3306",
  database: "house_keeping",
};
const db = mysql.createConnection(dbConnInfo);

// 추후 라우팅 설정 필요.
app.get("/api/v1/getCategoryList/:id", (req, res) => {
  const { categoryList } = require("./mock/categoryList");
  const id = req.params.id;

  const sql = `
  SELECT
    *
  FROM
    item
  WHERE
    categoryCode = ?
`;
  db.query(sql, [id], (error, results, fields) => {
    if (error) {
      throw new Error(error);
      res.status(500).send("Select Query Error");
    }

    res.status(200).json(results);
  });

  // const filteredList = categoryList.filter((item) => item.categoryCode == id);
  // console.log(filteredList);
  // res.json(filteredList);
});

app.post("/api/v1/regist-item", (req, res) => {
  const { categoryList } = require("./mock/categoryList");
  const { code, item } = req.body;

  const sql = `
    INSERT INTO 
      item (categoryCode, title, count, price)
    VALUES
      (?, ?, ?, ?)
    `;

  const getSql = `
  SELECT 
    *
  FROM
    item
  WHERE
    categoryCode = ?
`;

  db.query(sql, [code, item, 0, 0], (error, results, fields) => {
    console.log("error", error);
    console.log("results", results);
    console.log("fields", fields);
    if (error) {
      throw new Error(error);
      res.status(500).send("Insert Query Error");
    }

    console.log("Check");
    db.query(getSql, [code], (error, results, feilds) => {
      if (error) {
        throw new Error(error);
        res.status(500).send("Select Query Error");
      }
      res.status(200).json(results);
    });
  });

  // const filteredArray = categoryList.filter(
  //   (item) => item.categoryCode == code,
  // );
  // const newItem = {
  //   id: categoryList.length + 1,
  //   categoryCode: code,
  //   title: item,
  //   count: 1,
  //   price: 1000,
  // };
  // const newArray = [...filteredArray, newItem];
  // res.json(newArray);
});

app.listen(PORT, () => {
  console.log(`Server is running on ${PORT}`);
});
