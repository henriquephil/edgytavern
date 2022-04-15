import { Box, Flex } from "@chakra-ui/react"
import { useEffect, useState } from "react";
import { api } from "../../../../api";
import { groupByReducer } from "../../../../utils";
import SpotGroup from './SpotGroup';
import AddSpotGroup from './AddSpotGroup';

function Spots() {
  const [loading, setLoading] = useState(true);
  const [spots, setSpots] = useState({});

  const loadSpots = () => {
    setLoading(true);
    api.get('/managed/spots')
      .then(({ data }) => {
        setSpots(data.spots.reduce(groupByReducer('group'), {}));
      })
      .finally(() => setLoading(false))
  }

  useEffect(() => loadSpots(), []);

  const addGroup = groupName => {
    if (groupName) {
      const newState = {
        ...spots
      };
      newState[groupName] = [];
      setSpots(newState);
    }
  }

  const addSpot = (group, name) => {
    if (group && name) {
      api.post('/managed/spots', { group, name })
        .then(() => loadSpots());
    }
  }

  const emptyMessage = (
    <Box w="100%">
      <span>Start by adding a Group (outside, balcony, pooltable, etc)</span>
    </Box>
  )
  const groupNames = Object.keys(spots);
  return (
    <Box>
      {groupNames.length ? groupNames.map(groupName => 
        <SpotGroup key={groupName} groupName={groupName} spots={spots[groupName]} addSpot={spotName => addSpot(groupName, spotName)}/>
      ) : emptyMessage}
      <AddSpotGroup addGroup={groupName => addGroup(groupName)}/>
    </Box>);
}

export default Spots;
