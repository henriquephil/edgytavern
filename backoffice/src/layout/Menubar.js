import { Box, Flex, Grid } from "@chakra-ui/react"
import { NavLink } from "react-router-dom"
import topbar from '../assets/topbar.png'
import style from './Menubar.module.css'

function Menubar({establishment}) {
  return (
    <Flex w="100%" h="80px" className={style.Menubar}
        background={`url(${topbar})`} backgroundRepeat="no-repeat" backgroundPosition="center" color="#222" justify="center">
      <Flex maxW="1280px" w="100%" alignItems="baseline" justify="space-between">
        <Flex p="8px" className={style.links}>
          <Box>
            <NavLink to="/dashboard" activeClassName={style.linkActive}>Dashboard</NavLink>
          </Box>
          <Box>
            <NavLink to="/manage" activeClassName={style.linkActive}>Manage</NavLink>
          </Box>
        </Flex>
        <Box p="8px" color="black"><h1 className={style.establishmentName}>{establishment.name}</h1></Box>
        <Box p="8px">User</Box>
      </Flex>
    </Flex>);
}

export default Menubar;
