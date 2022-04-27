import { AddIcon, DeleteIcon, EditIcon } from "@chakra-ui/icons";
import { Box, Button, Flex, Grid } from "@chakra-ui/react";
import { useState } from "react";
import CutBorder from "../../../../components/CutBorder";

function SpotGroup({ spot }) {
  return (
    <CutBorder borderAt='left'>
      <Flex flexDirection="column">
        <Flex grow="1" direction="column" className="form-input">
          <label>Group name</label>
          <input type="text" value={spot.name} disabled/>
        </Flex>
        <Flex shrink="0" direction="column" className="form-input">
          <label>Amount of spots</label>
          <input type="text" value={spot.amount} disabled/>
        </Flex>
        <Flex className="form-input">
          <Button onClick={() => console.log(spot)}>
            <DeleteIcon/>
          </Button>
        </Flex>
      </Flex>
    </CutBorder>
  );
}

export default SpotGroup;
