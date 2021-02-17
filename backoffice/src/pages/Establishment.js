import { Box, Flex } from '@chakra-ui/react'
import { BrowserRouter, Redirect, Route, Switch } from 'react-router-dom';
import Menubar from '../layout/Menubar';
import Dashboard from './establishment/Dashboard';
import Manage from './establishment/Manage';
import bg from '../assets/pub-bg1.jpeg';
import style from './Establishment.module.css';
import { ModalProvider } from '../components/modal/ModalContext';

function Establishment({establishment}) {
  return (
    <Flex direction="column" justifyContent="start" align="center" w="100%" h="100vh" maxH="100vh" minW="768px" className={style.Establishment}
        bgColor="#222" color="#f0f0f0"> {/* backgroundImage={`url(${bg})`} */}
      <ModalProvider>
        <Flex direction="column" maxW="1350px" w="100%" h="100%">
          <BrowserRouter>
            <Menubar establishment={establishment}/>
            <Flex w="100%" h="100%" alignItems="start">
              <Switch>
                <Route path="/dashboard" component={Dashboard} />
                <Route path="/manage" component={Manage} />
                <Redirect to="/dashboard" />
              </Switch>
            </Flex>
          </BrowserRouter>
        </Flex>
      </ModalProvider>
    </Flex>
  );
}

export default Establishment;
