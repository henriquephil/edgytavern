import { AddIcon, DeleteIcon, EditIcon } from "@chakra-ui/icons";
import { Box, Button, Flex, Grid } from "@chakra-ui/react";
import { useState } from "react";
import CutBorder from "../../../../components/CutBorder";
import style from './SpotGroup.module.css';

function SpotGroup({ groupName, spots, addSpot }) {
  const [name, setName] = useState('');
  
  return (
    <Box p="8px">
      <Box flexBasis="auto" flexGrow="1" fontSize="1.7em" p="8px 32px" className={style.categoryName}>
        {groupName}
      </Box>
      <Grid templateColumns="repeat(auto-fill, minmax(150px, 1fr))" autoColumns="dense" gap="2">
        {spots.map(spot => 
          <CutBorder borderAt='left'>
            <Flex flexDirection="column">
              <Flex basis="45px" grow="1">
                <Box fontSize="1.1em" p="4px 8px">{spot.name}</Box>
              </Flex>
              <Button variant="ghost" onClick={() => console.log(spot)}>
                <DeleteIcon/>
              </Button>
            </Flex>
          </CutBorder>
        )}
        <CutBorder borderAt='left'>
          <Flex flexDirection="column">
            <Flex basis="45px" grow="1" className="form-input">
              <input type="text" value={name} onChange={e => setName(e.target.value)} placeholder="Spot name"/>
            </Flex>
            <Button variant="ghost" disabled={!name} onClick={() => {
              addSpot(name);
              setName('');
            }}>
              <AddIcon/>
            </Button>
          </Flex>
        </CutBorder>
      </Grid>
    </Box>
  );
}

export default SpotGroup;
