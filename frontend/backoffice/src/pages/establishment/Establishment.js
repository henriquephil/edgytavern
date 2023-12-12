import Menubar from '../../layout/Menubar'
import Dashboard from './dashboard/Dashboard'
import Manage from './manage/Manage'
import style from './Establishment.module.css'
import { ModalProvider } from '../../components/modal/ModalContext'
import { Route, Routes } from 'react-router-dom'
import CategoriesAssets from './manage/category-asset/CategoriesAssets'
import Spots from './manage/spots/Spots'

function Establishment() {
  return (
    <div className={style.Establishment}>
      <ModalProvider>
        <div className={style.container}>
          <Menubar/>
          <div className={style.content}>
            <Routes>
              <Route path="dashboard" element={<Dashboard/>} index />
              <Route path="manage" element={<Manage/>}>
                <Route path="categories-assets" element={<CategoriesAssets/>} />
                <Route path="spots" element={<Spots/>} />
              </Route>
            </Routes>
          </div>
        </div>
      </ModalProvider>
    </div>
  )
}

export default Establishment
