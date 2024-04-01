import { CategoryItemType } from "../types/dashboard";

type Props = {
  code: string | string[];
  setList: React.Dispatch<React.SetStateAction<CategoryItemType[]>>;
};

const Input = ({ code, setList }: Props) => {
  const submitHandler = (e: any) => {
    e.preventDefault();
    const item: string = e.target.item.value;
    const price: number = e.target.price.value | 0;
    const count: number = e.target.count.value | 0;

    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        code,
        item,
        price,
        count,
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
      <input type="text" name="item" placeholder="Input item" required /> <br />
      <input type="number" name="count" placeholder="count" /> <br />
      <input type="number" name="price" placeholder="price" /> <br />
      <input type="submit" />
    </form>
  );
};

export default Input;
