import { EditIcon } from "@chakra-ui/icons"
import { Box, Button, Flex, Spacer } from "@chakra-ui/react"
import { useContext } from "react"
import CutBorder from "../../../../components/CutBorder"
import { ModalContext } from "../../../../components/modal/ModalContext"
import EditAssetModal from "./modals/EditAssetModal"

function CategoryAsset({asset, onCategoryEdited}) {
  const { openModal } = useContext(ModalContext)
  const onClose = res => {
    console.log(res)
    if (res) onCategoryEdited()
  }

  return (
    <Box p="4px 0 4px 4px">
      <CutBorder borderAt='left'>
        <Flex>
          <Box flexBasis="auto" flexGrow="1" p="4px" background="#333" color="#fff"
              style={{opacity: asset.active ? 1 : 0.5}}>
            <Flex align="baseline">
              <Box fontSize="1.1em" p="4px 8px">{asset.name}</Box>
              <Spacer/>
              <Box>{asset.price}</Box>
            </Flex>
          </Box>
          <Flex p="0 4px" align="center" justify="flex-end">
            <Button variant="ghost" onClick={() => openModal(<EditAssetModal asset={asset}/>, { onClose })}>
              <EditIcon/>
            </Button>
          </Flex>
        </Flex>
      </CutBorder>
    </Box>
  )
}

export default CategoryAsset
