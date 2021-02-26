import { Box, Button, Flex } from "@chakra-ui/react";
import CategoryAsset from "./CategoryAsset";
import style from './Category.module.css';
import EditAssetModal from "./modals/EditAssetModal";
import { useContext, useEffect, useState } from "react";
import AxiosContext from "../../../../api/AxiosContext";
import { ModalContext } from "../../../../components/modal/ModalContext";

function Category({ category }) {
  const [loading, setLoading] = useState(true);
  const [assets, setAssets] = useState([]);
  const axios = useContext(AxiosContext);
  const { openModal } = useContext(ModalContext)
  
  const onCloseEditAsset = closed => {
    console.log('closed: ', closed);
  }

  const loadAssets = () => {
    setLoading(true);
    setAssets([]);
    axios.get('/api/establishment/management/assets', { params: { categoryId: category.id } })
      .then(response => setAssets(response.data.assets))
      .finally(() => setLoading(false));
  }

  useEffect(() => loadAssets(), []);

  return (
    <Box w="100%" p="8px 0">
      <Flex w="100%" p="8px 43px 0 32px" align="baseline">
        <Box flexBasis="auto" flexGrow="1" fontSize="1.7em" className={style.categoryName}>
          {category.name}
        </Box>
        <Button variant="ghost" onClick={() => openModal(<EditAssetModal asset={{ category }}/>, { onClose: onCloseEditAsset })}>
          New asset
        </Button>
      </Flex>
      {loading ? 'Loading' :
        assets.map(asset => {
          asset.category = category;
          return <CategoryAsset key={asset.id} asset={asset} onCategoryEdited={loadAssets}/>
        })}
    </Box>
  );
}

export default Category;
