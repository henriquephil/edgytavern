import { Scrollbars } from 'react-custom-scrollbars'
import styles from "./MyScroll.module.css"

function MyScroll({children}) {
  return <Scrollbars renderThumbVertical={({style, ...props}) => <div {...props} style={{...style, backgroundColor: '#ffa100', }}></div>}>
      <div className={styles.container}>
        {children}
      </div>
    </Scrollbars>
}
export default MyScroll
