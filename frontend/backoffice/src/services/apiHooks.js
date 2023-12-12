import { useEffect, useState } from "react"
import api from "./api"

export function useFetchBill(billId) {
  const [billLoading, setLoading] = useState(true)
  const [billData, setBill] = useState(null)
  const [billError, setError] = useState(null)

  useEffect(() => {
    if (!billId) {
      setError('invalid bill id')
    }
    setLoading(true)
    api.get(`/api/counter/admin/bills/${billId}`)
      .then(res => {
        setBill(res.data)
        setLoading(false)
      })
      .catch(setError)
  }, [billId])

  return { billLoading, billData, billError }
}