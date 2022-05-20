import { createContext } from "react"
import Modal from "./Modal"
import useModal from "./useModal"

export const ModalContext = createContext({})

export function ModalProvider({ children }) {
  const { visible, openModal, closeModal, modalContent } = useModal()
  return (
    <ModalContext.Provider value={{ visible, openModal, closeModal, modalContent }}>
      <Modal />
      {children}
    </ModalContext.Provider>
  )
}
