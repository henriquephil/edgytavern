import { Box } from "@chakra-ui/react"
import { useEffect, useState } from "react"
import api from "../../../../services/api"
import AddCategory from "./AddCategory"
import Category from "./Category"

function CategoriesAssets() {
  const [loading, setLoading] = useState(true)
  const [categories, setCategories] = useState([])

  function loadCategories() {
    setLoading(true)
    api.get('/api/establishment/managed/categories')
      .then(response => {
        setCategories(response.data.categories)
      })
      .finally(() => setLoading(false))
  }

  useEffect(() => loadCategories(), [])

  function addCategory(category) {
    if (category.name) {
      api.post('/api/establishment/managed/categories', category)
        .then(res => loadCategories())
    }
  }
  
  if (loading) return 'Loading'
  return (
    <Box w="100%">
      {categories?.length ? categories.map(c => <Category key={c.id} category={c} />) :
      <Box w="100%">
        <span>Looks like you haven't added categories yet. Start by using the form below</span>
      </Box>}
      <AddCategory addCategory={category => addCategory(category)}/>
    </Box>)
}

export default CategoriesAssets
