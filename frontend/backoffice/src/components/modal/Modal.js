import { useContext, useEffect, useRef } from "react"
import { createPortal } from "react-dom"
import { useKey } from "react-use"
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

  function backgroundClick(e) {
    console.log(e, 'looking for mouse down')
    if (e.target === bgRef.current) close()
  }

  useEffect(() => {
    if (visible) firstTabIdx.current.focus()
  }, [visible])

  if (visible) {
    return createPortal(
      <div ref={bgRef} className={style.Modal} display={visible ? 'flex' : 'none'} onClick={e => backgroundClick(e)}>
        <input ref={firstTabIdx} onKeyDown={e => blockTabShift(e)} className={style.hiddenTabInput}></input>
        <div className={style.box} maxH="100vh" maxW="768px">
          {modalContent}
        </div>
        <input onKeyDown={e => blockTab(e)} className={style.hiddenTabInput}></input>
      </div>,
      document.querySelector("#modal-root"))
  }
  return null
}

export default Modal