"use client";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { CategoryItemType } from "../types/dashboard";
import Link from "next/link";

type Props = {
  code: string | string[];
  setList: React.Dispatch<React.SetStateAction<CategoryItemType[]>>;
};

const Input = ({ code, setList }: Props) => {
  const submitHandler = (e: any) => {
    e.preventDefault();
    const item: string = e.target.item.value;
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        code,
        item,
      }),
    };

    fetch("http://localhost:8080/api/v1/regist-item", options)
      .then((res) => res.json())
      .then((data: CategoryItemType[]) => {
        setList(data);
        e.target.item.value = "";
      })
      .catch((err) => {
        throw new Error(err);
      });
  };
  return (
    <form onSubmit={submitHandler}>
      <input type="text" name="item" placeholder="Input item" />
      <input type="submit" />
    </form>
  );
};

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
    return (
      <>
        <Input code={id} setList={setList} />
        <div>해당 카테고리에 등록된 아이템이 없습니다.</div>
      </>
    )

  return (
    <>
      <Input code={id} setList={setList} />
      {list &&
        list.map((item) => (
          <Link key={item.id} href={"/"}>
            <div>
              {item.title} {item.count} 개
            </div>
          </Link>
        ))}
    </>
  );
}
