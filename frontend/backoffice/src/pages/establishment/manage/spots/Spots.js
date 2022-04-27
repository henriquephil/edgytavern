import { Box,  Grid } from "@chakra-ui/react"
import { Fragment, useEffect, useState } from "react";
import { establishmentApi } from "../../../../services/api";
import SpotGroup from './SpotGroup';
import AddSpotGroup from './AddSpotGroup';

function Spots() {
  const [loading, setLoading] = useState(true);
  const [spots, setSpots] = useState([]);

  const loadSpots = () => {
    setLoading(true);
    establishmentApi.get('/managed/spots')
      .then(({ data }) => {
        setSpots(data.groups);
      })
      .finally(() => setLoading(false))
  }

  useEffect(() => loadSpots(), []);

  const addGroup = group => {
    if (group.name && group.amount) {
      setLoading(true);
      establishmentApi.post('/managed/spots', group)
        .then(_ => {
          loadSpots();
        });
    }
  }

  const emptyMessage = (
    <Box w="100%">
      <span>Start by adding a Group (outside, balcony, pooltable, etc)</span>
    </Box>
  )
  return (
    <>
      {spots?.length === 0 ? emptyMessage : <Fragment/>}
      <Grid templateColumns="repeat(auto-fill, minmax(200px, 1fr))" autoColumns="dense" gap="3">
        { spots.map(spot => <SpotGroup key={spot.id} spot={spot}/> )}
        <AddSpotGroup addGroup={group => addGroup(group)}/>
      </Grid>
    </>
    );
}

export default Spots;
