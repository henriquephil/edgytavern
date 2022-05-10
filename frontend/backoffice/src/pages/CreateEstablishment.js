import { Box, Button, Flex, Input } from '@chakra-ui/react'
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { createEstablishment } from '../services/apiService';

function CreateEstablishment() {
  const [ name, setName ] = useState('');
  const dispatch = useDispatch();
  
  const nameEstablishment = () => {
    dispatch(createEstablishment(name));
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
