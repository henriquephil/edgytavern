import { useEffect, useRef } from "react"
import QrScanner from 'qr-scanner';

export default function QrVideo({ onResult }) {
  const videoRef = useRef(null)
  
  useEffect(() => {
    const qrScanner = new QrScanner(videoRef.current, result => {
      console.log(result)
      if (result) {
        qrScanner.stop()
        onResult(result.data)
      }
    }, {
      preferredCamera: 'environment'
    })
    qrScanner.start()
    return () => {
      qrScanner.stop()
    }
  }, [])

  return (
    <div style={{ width: '100vw', height: '40vh'}}>
      <video ref={videoRef} muted/>
    </div>
  )
}