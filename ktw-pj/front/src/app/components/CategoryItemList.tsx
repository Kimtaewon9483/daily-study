"use client";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { CategoryItemType } from "../types/dashboard";

export default function CategoryItemListComp() {
  const params = useParams();
  const id = params.id;
  const [list, setList] = useState<CategoryItemType[]>([]);
  useEffect(() => {
    console.log("Hello World");

    fetch(`http://localhost:8080/api/v1/getCategoryList/${id}`)
      .then((res) => res.json())
      .then((data) => setList(data))
      .catch((err) => {
        throw new Error(err);
      });
  }, []);

  if (list.length === 0)
    return <div>해당 카테고리에 등록된 아이템이 없습니다.</div>;

  return (
    <>
      <div>Item List</div>
      {list &&
        list.map((item) => (
          <div key={item.id}>
            {item.title} {item.count} 개
          </div>
        ))}
    </>
  );
}
