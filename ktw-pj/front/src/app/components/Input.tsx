import { CategoryItemType } from "../types/dashboard";
import { defaultValue } from "../constants/item";
import { useState } from "react";

type Props = {
  code: string | string[];
  setList: React.Dispatch<React.SetStateAction<CategoryItemType[]>>;
  toggleInput: React.Dispatch<React.SetStateAction<boolean>>;
};

type ItemType = {
  categoryCode: number;
  title: string;
  count: number;
  price: number;
};

const Input = ({ code, setList, toggleInput }: Props) => {
  const [item, setItem] = useState<ItemType>({
    ...defaultValue,
    categoryCode: Number(code),
  });

  const submitHandler = (e: any) => {
    e.preventDefault();
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(item),
    };
    fetch("http://localhost:8080/api/v1/regist-item", options)
      .then((res) => res.json())
      .then((data: CategoryItemType[]) => {
        setList(data);
        setItem(defaultValue);
        toggleInput((pre) => !pre);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const changeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (name === "count" || name === "price") {
      setItem({
        ...item,
        [name]: Number(value),
      });
    } else {
      setItem({
        ...item,
        [name]: value,
      });
    }
  };

  return (
    <form onSubmit={submitHandler}>
      <input
        type="text"
        name="title"
        placeholder="Input item"
        value={item.title}
        onChange={changeHandler}
        required
      />{" "}
      <br />
      <input
        type="number"
        name="count"
        placeholder="count"
        onChange={changeHandler}
        value={item.count}
      />{" "}
      <br />
      <input
        type="number"
        name="price"
        placeholder="price"
        onChange={changeHandler}
        value={item.price}
      />{" "}
      <br />
      <input type="submit" />
    </form>
  );
};

export default Input;
