import { Box, Button, Flex, FormLabel } from "@chakra-ui/react"
import { AddIcon } from '@chakra-ui/icons'
import { useState } from "react";


function AddSpotGroup({ addGroup }) {
  const [name, setName] = useState('');

  return (
    <Flex w="100%" align="flex-end">
      <Flex grow="1" direction="column" className="form-input">
        <label>New group name</label>
        <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </Flex>
      <Flex className="form-input">
        <Button variant="ghost" disabled={!name} onClick={() => {
          addGroup(name);
          setName('');
        }}>
          <AddIcon w={3} h={3} />
        </Button>
      </Flex>
    </Flex>);
}

export default AddSpotGroup;
