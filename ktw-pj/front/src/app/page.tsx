import { DashboardMenuType } from "./types/dashboard";
import { dashboardMenus } from "./constants/Dashboard";
import Link from "next/link";
// 집에 자주 사용하는 물건 혹은 식재료 등 기록 가능한 웹사이트 작성
export default function Home() {
  return (
    <>
      <header>Header</header>
      <main>
        <div>Dashboard</div>
        <ul className="grid grid-cols-3 gap-4">
          {dashboardMenus.map((menu: DashboardMenuType) => (
            <Link key={menu.code} href={`/CategoryItemList/${menu.code}`}>
              <li className="border border-red-400">{menu.title}</li>
            </Link>
          ))}
        </ul>
      </main>
      <footer>Footer</footer>
    </>
  );
}
