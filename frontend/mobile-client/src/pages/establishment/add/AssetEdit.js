import styles from './AssetEdit.module.css';

function AssetEdit({asset, cancel}) {

  return (
    <div className={styles.AssetEdit} onClick={() => cancel()}>
      {JSON.stringify(asset)}
    </div>
  );
}

export default AssetEdit;
