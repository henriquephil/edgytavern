import { selectLocation } from '../../../state/sessionSlice';
import { useSelector } from 'react-redux';
import { Flex } from '@chakra-ui/react';

function LocationHeader({ sum }) {
  const location = useSelector(selectLocation);
  
  return (
    <Flex bg="black" w="100%" h="40px" align="center" justify="space-between" p="4px 8px" color="#f0f0f0" shrink="0">
      <span>{location?.name}</span>
      <span>$ {sum.toFixed(2)}</span>
    </Flex>
  );
}

export default LocationHeader;
