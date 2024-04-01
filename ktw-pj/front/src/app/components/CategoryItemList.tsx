"use client";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { CategoryItemType } from "../types/dashboard";
import Link from "next/link";
import Input from "./Input";
import RegistItemModal from "./RegistItemModal";

export default function CategoryItemListComp() {
  const params = useParams();
  const id = params.id;
  const [list, setList] = useState<CategoryItemType[]>([]);
  const [toggleModal, setToggleModal] = useState<boolean>(false);
  const [item, setItem] = useState<CategoryItemType | undefined>();

  useEffect(() => {
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
    );

  return (
    <>
      <Input code={id} setList={setList} />
      {list &&
        list.map((item) => (
          <div
            key={item.id}
            onClick={() => {
              setItem(item);
              setToggleModal((pre) => !pre);
            }}
          >
            {item.title} {item.count} 개
          </div>
        ))}

      {toggleModal ? (
        <RegistItemModal
          item={item}
          setItem={setItem}
          setToggleModal={setToggleModal}
        />
      ) : null}
    </>
  );
}
