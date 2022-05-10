import { Box, Flex } from "@chakra-ui/react"
import MyScroll from "../../../components/MyScroll";
import style from "./Manage.module.css";
import { NavLink, Outlet } from "react-router-dom";

function Manage() {
  return (
    <Flex flexBasis="1200px" minW="900px" h="100%" alignItems="start">
      <Flex flexShrink="1" minWidth="100px" className={style.links} flexDirection="column">
        <Box p="12px" textAlign="right">
          <NavLink to="/manage/categories-assets" className={({ isActive }) => isActive ? style.active : ""}>Assets</NavLink>
        </Box>
        <Box p="12px" textAlign="right">
          <NavLink to="/manage/spots" className={({ isActive }) => isActive ? style.active : ""}>Spots</NavLink>
        </Box>
      </Flex>
      <Box flexBasis="auto" flexGrow="5" h="100%">
        <MyScroll>
          <Outlet />
        </MyScroll>
      </Box>
    </Flex>);
}

export default Manage;
