"use client";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { CategoryItemType } from "../types/dashboard";
import Link from "next/link";
import Input from "./Input";
import RegistItemModal from "./RegistItemModal";

export default function CategoryItemListComp() {
  const params = useParams();
  const categoryId = params.id;
  const [list, setList] = useState<CategoryItemType[]>([]);
  const [toggleModal, setToggleModal] = useState<boolean>(false);
  const [toggleInput, setToggleInput] = useState<boolean>(false);
  const [item, setItem] = useState<CategoryItemType | undefined>();

  useEffect(() => {
    fetch(`http://localhost:8080/api/v1/getCategoryList/${categoryId}`)
      .then((res) => res.json())
      .then((data) => setList(data))
      .catch((err) => {
        throw new Error(err);
      });
  }, []);

  const deleteHandler = (id: number) => {
    const options = {
      method: "delete",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id,
      }),
    };
    fetch("http://localhost:8080/api/v1/delete-item", options).then((res) => {
      if (res.ok) {
        fetch(`http://localhost:8080/api/v1/getCategoryList/${categoryId}`)
          .then((res) => res.json())
          .then((data) => {
            console.log(data);
            setList(data);
          });
      }
    });
  };

  if (list.length === 0)
    return (
      <>
        <div>
          <button onClick={() => setToggleInput((pre) => !pre)}>
            Add Item
          </button>
          {toggleInput ? (
            <Input
              code={categoryId}
              setList={setList}
              toggleInput={setToggleInput}
            />
          ) : null}
        </div>
        <div>해당 카테고리에 등록된 아이템이 없습니다.</div>
      </>
    );

  return (
    <>
      <div>
        <button onClick={() => setToggleInput((pre) => !pre)}>Add Item</button>
      </div>
      {toggleInput ? (
        <Input
          code={categoryId}
          setList={setList}
          toggleInput={setToggleInput}
        />
      ) : null}
      {list &&
        list.map((item) => (
          <div key={item.id}>
            <span
              onClick={() => {
                setItem(item);
                setToggleModal((pre) => !pre);
              }}
            >
              {item.title} {item.count} 개
            </span>
            <button onClick={() => deleteHandler(item.id)}>Delete</button>
          </div>
        ))}

      {toggleModal ? (
        <RegistItemModal
          item={item}
          setItem={setItem}
          setToggleModal={setToggleModal}
          setList={setList}
        />
      ) : null}
    </>
  );
}
