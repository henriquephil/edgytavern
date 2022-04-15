import { Box, Button, Flex, FormLabel } from "@chakra-ui/react"
import { AddIcon } from '@chakra-ui/icons'
import { useState } from "react";


function AddCategory({ addCategory }) {
  const [name, setName] = useState('');

  return (
    <Flex w="100%" align="flex-end">
      <Flex grow="1" direction="column" className="form-input">
          <label>New category name</label>
          <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </Flex>
      <Flex shrink="1" className="form-input">
        <Button variant="ghost" disabled={!name} onClick={() => {
          addCategory({ name });
          setName('');
        }}>
          <AddIcon w={3} h={3} />
        </Button>
      </Flex>
    </Flex>);
}

export default AddCategory;
