import { Box, Flex } from "@chakra-ui/react"
import { NavLink, Redirect, Route, Switch } from "react-router-dom";
import MyScroll from "../../components/MyScroll";
import CategoriesAssets from "./manage/category-asset/CategoriesAssets";
import Seats from "./manage/Seats";
import style from "./Manage.module.css";

function Manage() {
  return (
    <>
      <Box flexBasis="300px" className={style.links}>
        <Box p="12px">
          <NavLink to="/manage/categories-assets" activeClassName={style.active}>Assets</NavLink>
        </Box>
        <Box p="12px">
          <NavLink to="/manage/seats" activeClassName={style.active}>Seats</NavLink>
        </Box>
      </Box>
      <Box flexBasis="auto" flexGrow="1" h="100%">
        <MyScroll>
          <Switch>
            <Route path="/manage/categories-assets" component={CategoriesAssets} />
            <Route path="/manage/seats" component={Seats} />
            <Redirect to="/manage/categories-assets" />
          </Switch>
        </MyScroll>
      </Box>
    </>);
}

export default Manage;
