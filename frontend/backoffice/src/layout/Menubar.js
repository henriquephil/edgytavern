import { Box, Flex } from "@chakra-ui/react"
import { NavLink } from "react-router-dom"
import topbar from '../assets/topbar.png'
import style from './Menubar.module.css'
import { useSelector } from 'react-redux'
import { useLocalStorage } from "react-use"
import { auth } from 'tavern-commons'

function Menubar() {
  const establishment = useSelector(state => state.establishment.data)
  const [ userInfo ] = useLocalStorage('userInfo')

  return (
    <Flex w="100%" h="80px" className={style.Menubar} color="#222" justify="center"
        background={`url(${topbar})`} backgroundRepeat="no-repeat" backgroundPosition="center">
      <Flex maxW="1280px" w="100%" alignItems="baseline" justify="space-between">
        <Flex p="8px" className={style.links}>
          <Box>
            <NavLink to="/dashboard" className={({ isActive }) => isActive ? style.linkActive : ""}>Dashboard</NavLink>
          </Box>
          <Box>
            <NavLink to="/manage" className={({ isActive }) => isActive ? style.linkActive : ""}>Manage</NavLink>
          </Box>
        </Flex>
        <Box p="8px" color="black"><h1 className={style.establishmentName}>{establishment.name}</h1></Box>
        <Box p="8px" fontSize="1.2em" onClick={() => auth.signOut()} cursor="pointer">{JSON.stringify(userInfo)}</Box>
      </Flex>
    </Flex>)
}

export default Menubar
