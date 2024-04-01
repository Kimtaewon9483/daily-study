import CategoryItemListComp from "@/app/components/CategoryItemList";
import Link from "next/link";

export default function CategoryItemList() {
  return (
    <>
      <div>Item List</div>
      <h2>
        <Link href={"/"}>
          <span>HOME</span>
        </Link>
      </h2>
      <CategoryItemListComp />
    </>
  );
}
