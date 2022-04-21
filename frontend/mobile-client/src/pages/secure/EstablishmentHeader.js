import { useSelector } from 'react-redux';
import { Flex } from '@chakra-ui/react';

function EstablishmentHeader() {
  const establishmentState = useSelector(state => state.establishment);
  const billState = useSelector(state => state.bill);

  const sum = billState.data?.orderedItems
    .map(b => b.finalPrice)
    .reduce((prev, i) => prev += i, 0);
  
  return (
    <Flex bg="black" w="100%" h="40px" align="center" justify="space-between" p="4px 8px" color="#f0f0f0" shrink="0">
      <span>{establishmentState.data?.name}</span>
      <span>$ {(sum || 0).toFixed(2)}</span>
    </Flex>
  );
}

export default EstablishmentHeader;
