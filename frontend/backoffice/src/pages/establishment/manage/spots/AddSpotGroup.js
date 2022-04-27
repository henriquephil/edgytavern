import { Box, Button, Flex, FormLabel } from "@chakra-ui/react"
import { AddIcon } from '@chakra-ui/icons'
import { useState } from "react";
import CutBorder from "../../../../components/CutBorder";


function AddSpotGroup({ addGroup }) {
  const [name, setName] = useState('');
  const [amount, setAmount] = useState('');

  return (
    <CutBorder borderAt='left'>
      <Flex flexDirection="column">
        <Flex grow="1" direction="column" className="form-input">
          <label>New group name</label>
          <input type="text" value={name} onChange={e => setName(e.target.value)}/>
        </Flex>
        <Flex shrink="0" direction="column" className="form-input">
          <label>Amount of spots</label>
          <input type="text" value={amount} onChange={e => setAmount(e.target.value)}/>
        </Flex>
        <Flex className="form-input">
          <Button disabled={!name || !amount} onClick={() => {
            addGroup({ name, amount });
            setName('');
            setAmount('');
          }}>
            <AddIcon w={3} h={3} />
          </Button>
        </Flex>
      </Flex>
    </CutBorder>
  );
}

export default AddSpotGroup;
