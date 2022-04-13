import LocationHeader from './inloco/LocationHeader';
import { Flex } from "@chakra-ui/react"
import Bills from './inloco/Bills';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Choose from './inloco/Choose';
import IncludeProduct from './inloco/IncludeProduct';
import ApiService from '../../services/ApiService';
import { useState } from 'react';

function Located() {
  const [bills, setBills] = useState([]);
  ApiService.bills.findAll().then(bills => setBills(bills));
  return (
    <Flex direction="column" h="100%" grow="1">
      <LocationHeader sum={bills.flatMap(b => b.itens.map(i => i.value)).reduce((prev, i) => prev += i, 0)}/>
      <BrowserRouter>
        <Routes>
          <Route path="/choose" element={Choose}/>
          <Route path="/include" element={IncludeProduct}/>
          <Route path="/" render={() => <Bills bills={bills}/>}/>
          {/* <Redirect to="/" /> */}
        </Routes>
      </BrowserRouter>
    </Flex>
  );
}

export default Located;
