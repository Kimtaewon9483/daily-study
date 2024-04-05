"use client";

import { useEffect, useState } from "react";
import { CategoryItemType } from "../types/dashboard";

const Settings = () => {
  const [items, setItems] = useState<CategoryItemType[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/getAllItems")
      .then((res) => res.json())
      .then((data) => setItems(data));
  }, []);

  if (items.length === 0)
    return (
      <div>
        <h1>Settings</h1>
        <div>유저정보 블록</div>
        <div>여기엔 뭘 넣지...</div>
        No Data
      </div>
    );

  return (
    <div>
      <h1>Settings</h1>

      <div>유저정보 블록</div>
      <div>여기엔 뭘 넣지...</div>

      <ul>
        {items.map((item) => (
          <li key={item.id}>
            <div>제품명 : {item.title}</div>
            <div>가격 : {item.price}</div>
            <div>개수 : {item.count}</div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Settings;
