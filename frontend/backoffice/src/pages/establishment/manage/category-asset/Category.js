import CategoryAsset from "./CategoryAsset"
import styles from './Category.module.css'
import EditAssetModal from "./modals/EditAssetModal"
import { useContext, useEffect, useState } from "react"
import { ModalContext } from "../../../../components/modal/ModalContext"
import api from '../../../../services/api'
import Button from "../../../../components/Button"

function Category({ category }) {
  const [loading, setLoading] = useState(true)
  const [assets, setAssets] = useState([])
  
  const { openModal } = useContext(ModalContext)

  function loadAssets() {
    setLoading(true)
    setAssets([])
    api.get('/api/establishment/managed/assets', { params: { categoryId: category.id } })
      .then(response => setAssets(response.data.assets))
      .finally(() => setLoading(false))
  }
  
  useEffect(() => loadAssets(), [])

  return (
    <div className={styles.category}>
      <div className={styles.categoryHeader}>
        <div className={styles.categoryName}>
          {category.name}
        </div>
        <Button primary onClick={() => openModal(<EditAssetModal asset={{ category }}/>, { onClose: loadAssets })}>
          New asset
        </Button>
      </div>
      {loading ? 'Loading' :
        assets.map(asset => {
          asset.category = category
          return <CategoryAsset key={asset.id} asset={asset} onCategoryEdited={loadAssets}/>
        })}
    </div>
  )
}

export default Category
