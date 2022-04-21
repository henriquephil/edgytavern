import { Box, Button, Flex, Input } from '@chakra-ui/react'
import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom'
// import AxiosContext from '../api/AxiosContext';
import { establishmentApi } from '../services/api';

function CreateEstablishment() {
  const [ name, setName ] = useState('')
  const navigate = useNavigate();
  
  const nameEstablishment = () => {
    establishmentApi.post('/managed', { name })
        .then(res => {
          console.log(res);
          navigate('/')
        })
  }
  return (
    <Flex w="100%" h="100vh" maxH="100vh" bg="#222" justify="center" alignItems="center">
      <Box w="400px">
      <p>You haven't named your establishment yet</p>
      <Input type="text" value={name} onChange={e => setName(e.target.value)} />
      <Button w="100%" disabled={name.length < 1} onClick={nameEstablishment}>Submit</Button>
      </Box>
    </Flex>
  );
}

export default CreateEstablishment;
