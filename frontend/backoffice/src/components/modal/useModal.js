import { useState } from "react";

function useModal() {
  const [ visible, setVisible ] = useState(false);
  const [ modalContent, setModalContent ] = useState(null);
  const [ events, setEvents ] = useState({ onClose: null });

  const openModal = (content, options = {}) => {
    setVisible(true);
    setModalContent(content);
    setEvents({ onClose: options.onClose });
  };
  const closeModal = res => {
    setVisible(false);
    setModalContent();
    if (events) {
      if (events.onClose) events.onClose(res);
    }
  }

  return { visible, openModal, closeModal, modalContent };
};

export default useModal;