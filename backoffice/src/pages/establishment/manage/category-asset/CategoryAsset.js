import { EditIcon } from "@chakra-ui/icons";
import { Box, Button, Flex, Grid, GridItem, Spacer } from "@chakra-ui/react";
import { useContext, useState } from "react";
import CutBorder from "../../../../components/CutBorder";
import { ModalContext } from "../../../../components/modal/ModalContext";
import EditAssetModal from "./modals/EditAssetModal";

function CategoryAsset({asset, onCategoryEdited}) {
  const [ expanded, setExpanded ] = useState(false)
  const { openModal } = useContext(ModalContext)
  const onClose = res => {
    console.log(res);
    if (res) onCategoryEdited();
  }

  return (
    <Box p="4px">
      <CutBorder borderAt='left'>
        <Flex>
          <Box flexBasis="auto" flexGrow="1" p="4px" borderLeft="3px solid #ffa100" background="#333" color="#fff"
              style={{opacity: asset.active ? 1 : 0.5}}>
            <Flex align="baseline" onClick={() => setExpanded(!expanded)}>
              <Box fontSize="1.1em" p="4px 8px">{asset.name}</Box>
              <Spacer/>
              <Box>{asset.price}</Box>
            </Flex>
            {expanded ?
            <Flex p="2px 8px">
              <Grid templateColumns="auto 1fr">
                {asset.description?.length > 0 ? <GridItem colSpan="2" p="2px">{asset.description}</GridItem> : null}

                <GridItem><span>Ingredients:</span></GridItem>
                <GridItem p="2px">{asset.ingredients?.length > 0 ? asset.ingredients.map(i => <span key={i.id}>{i.name}</span>) : null}</GridItem>

                <GridItem><span>Additionals:</span></GridItem>
                <GridItem p="2px">{asset.additionals?.length > 0 ? asset.additionals.map(i => <span key={i.id}>{i.name}</span>) : null}</GridItem>
              </Grid>
            </Flex> : null}
          </Box>
          <Flex p="0 4px" align="center" justify="flex-end">
            <Button variant="ghost" onClick={() => openModal(<EditAssetModal asset={asset}/>, { onClose })}>
              <EditIcon/>
            </Button>
          </Flex>
        </Flex>
      </CutBorder>
    </Box>
  );
}

export default CategoryAsset;
