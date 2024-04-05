import { CategoryItemType } from "../types/dashboard";

type Props = {
  item?: CategoryItemType;
  setItem?: React.Dispatch<React.SetStateAction<CategoryItemType | undefined>>;
  setToggleModal?: React.Dispatch<React.SetStateAction<boolean>>;
  setList?: React.Dispatch<React.SetStateAction<CategoryItemType[]>>;
};

const RegistItemModal = ({ item, setItem, setToggleModal, setList }: Props) => {
  const submitHandler = (e: React.SyntheticEvent) => {
    e.preventDefault();

    console.log(item);

    const options = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(item),
    };

    fetch("http://localhost:8080/api/v1/update-item", options).then((res) => {
      if (!setToggleModal) return;
      if (res.ok) {
        fetch(
          `http://localhost:8080/api/v1/getCategoryList/${item?.categoryCode}`,
        )
          .then((res) => res.json())
          .then((data) => {
            if (!setList) return;
            setList(data);
            setToggleModal((pre) => !pre);
          });
      }
    });
  };
  return (
    <>
      {item && (
        <>
          <div className="absolute justify-center top-0 left-0 w-full h-full bg-red-100">
            <div className="absolute top-1/2 left-1/2 w-1/2 h-1/2 text-center bg-sky-400 translate-x-[-50%] translate-y-[-50%]">
              <div className="flex justify-between">
                <div>title</div>
                <div
                  onClick={() => {
                    if (!setToggleModal) return;
                    setToggleModal((pre) => !pre);
                  }}
                >
                  X
                </div>
              </div>
              <div>
                <form onSubmit={submitHandler}>
                  <input
                    type="text"
                    name="title"
                    value={item.title}
                    placeholder="Title"
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                      if (!setItem) return;
                      setItem({
                        ...item,
                        title: e.target.value,
                      });
                    }}
                    required
                  />{" "}
                  <br />
                  <input
                    type="number"
                    name="price"
                    value={item.price}
                    placeholder="Price"
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                      if (!setItem) return;
                      setItem({
                        ...item,
                        price: Number(e.target.value),
                      });
                    }}
                    required
                  />{" "}
                  <br />
                  <input
                    type="number"
                    name="count"
                    value={item.count}
                    placeholder="Count"
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                      if (!setItem) return;
                      setItem({
                        ...item,
                        count: Number(e.target.value),
                      });
                    }}
                    required
                  />{" "}
                  <br />
                  <input type="submit" className="border border-zinc-600" />
                </form>
              </div>
            </div>
          </div>
        </>
      )}
    </>
  );
};

export default RegistItemModal;
