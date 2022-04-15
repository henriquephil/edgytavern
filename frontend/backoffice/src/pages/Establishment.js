import { Box, Flex } from '@chakra-ui/react'
import Menubar from '../layout/Menubar';
import Dashboard from './establishment/Dashboard';
import Manage from './establishment/Manage';
import bg from '../assets/pub-bg1.jpeg';
import style from './Establishment.module.css';
import { ModalProvider } from '../components/modal/ModalContext';
import { Route, Routes } from 'react-router-dom';
import CategoriesAssets from './establishment/manage/category-asset/CategoriesAssets';
import Spots from './establishment/manage/spots/Spots';

function Establishment() {
  return (
    <Flex direction="column" justifyContent="start" align="center" w="100%" h="100vh" maxH="100vh" minW="768px" className={style.Establishment}
        bgColor="#222" color="#f0f0f0"> {/* backgroundImage={`url(${bg})`} */}
      <ModalProvider>
        <Flex direction="column" w="100%" h="100%">
          <Menubar/>
          <Flex w="100%" h="100%" alignItems="start" justifyContent="space-around">
            <Routes>
              <Route path="dashboard" element={<Dashboard/>} index />
              <Route path="manage" element={<Manage/>}>
                <Route path="categories-assets" element={<CategoriesAssets/>} />
                <Route path="spots" element={<Spots/>} />
              </Route>
            </Routes>
          </Flex>
        </Flex>
      </ModalProvider>
    </Flex>
  );
}

export default Establishment;
