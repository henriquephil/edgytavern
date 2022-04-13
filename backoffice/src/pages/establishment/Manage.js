import { Box } from "@chakra-ui/react"
import MyScroll from "../../components/MyScroll";
import style from "./Manage.module.css";
import { NavLink, Outlet } from "react-router-dom";

function Manage() {
  return (
    <>
      <Box flexBasis="300px" className={style.links}>
        <Box p="12px">
          <NavLink to="/manage/categories-assets" className={({ isActive }) => isActive ? style.active : ""}>Assets</NavLink>
        </Box>
        <Box p="12px">
          <NavLink to="/manage/spots" className={({ isActive }) => isActive ? style.active : ""}>Spots</NavLink>
        </Box>
      </Box>
      <Box flexBasis="auto" flexGrow="1" h="100%">
        <MyScroll>
          <Outlet />
        </MyScroll>
      </Box>
    </>);
}

export default Manage;
