import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Located from './secure/Located';
import Load from './secure/Load';
import Locate from './secure/Locate';
import UserHeader from './secure/UserHeader';
import { Flex } from "@chakra-ui/react"

function Authenticated() {
  return (
    <Flex direction="column" w="100%">
      <UserHeader/>
      <BrowserRouter>
        <Routes>
          <Route path="/load" element={Load} />
          <Route path="/locate" element={Locate} />
          <Route path="/" element={Located} />
          {/* <Redirect to="/" /> */}
        </Routes>
      </BrowserRouter>
    </Flex>);
}

export default Authenticated;
