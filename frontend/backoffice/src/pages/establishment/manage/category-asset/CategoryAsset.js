import { useContext } from "react"
import { ModalContext } from "../../../../components/modal/ModalContext"
import EditAssetModal from "./modals/EditAssetModal"
import styles from "./CategoryAsset.module.css"
import Button from "../../../../components/Button"

function CategoryAsset({asset, onCategoryEdited}) {
  const { openModal } = useContext(ModalContext)
  const onClose = res => {
    console.log(res)
    if (res) onCategoryEdited()
  }

  return (
    <div className={styles.CategoryAsset}>
      <div className={styles.CategoryAssetList}>
        <div className={styles.Asset} style={{opacity: asset.active ? 1 : 0.5}}>
          <div className={styles.AssetInner}>
            <div className={styles.AssetName}>{asset.name}</div>
            <div className={styles.AssetPrice}>{asset.price}</div>
          </div>
        </div>
        <div className={styles.AssetControl}>
          <Button onClick={() => openModal(<EditAssetModal asset={asset}/>, { onClose })}>
            Edit
          </Button>
        </div>
      </div>
    </div>
  )
}

export default CategoryAsset
