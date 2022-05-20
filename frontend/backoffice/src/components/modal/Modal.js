import { Box, Flex } from "@chakra-ui/react"
import { useContext, useEffect, useRef } from "react"
import { createPortal } from "react-dom"
import { useKey } from "react-use"
import CutBorder from "../CutBorder"
import style from "./Modal.module.css"
import { ModalContext } from "./ModalContext"

function Modal() {
  const { modalContent, closeModal, visible } = useContext(ModalContext)
  const bgRef = useRef(null)
  const firstTabIdx = useRef(null)

  const close = result => {
    if (visible) {
      closeModal(result)
    }
  }
  useKey('Escape', () => close())

  const blockTab = e => {
    if (!(e.key === 'Tab' && e.shiftKey)) {
      e.preventDefault()
    }
  }

  const blockTabShift = e => {
    if (!(e.key === 'Tab' && !e.shiftKey)) {
      e.preventDefault()
    }
  }

  useEffect(() => {
    if (visible) firstTabIdx.current.focus()
  }, [visible])

  if (visible) {
    return createPortal(
      <Flex ref={bgRef} className={style.Modal} display={visible ? 'flex' : 'none'} onClick={e => {
        if (e.target === bgRef.current) close()
      }}>
        <input ref={firstTabIdx} onKeyDown={e => blockTabShift(e)} className={style.hiddenTabInput}></input>
          <Box maxH="100vh" maxW="768px">
            <CutBorder borderAt='top'>
              <Box background="#222">
                {modalContent}
              </Box>
            </CutBorder>
          </Box>
          <input onKeyDown={e => blockTab(e)} className={style.hiddenTabInput}></input>
        </Flex>,
        document.querySelector("#modal-root"))
  }
  return null
}

export default Modal