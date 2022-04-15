import { Box } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { api } from "../../../../api";
import AddCategory from "./AddCategory";
import Category from "./Category";

function CategoriesAssets() {
  const [loading, setLoading] = useState(true);
  const [categories, setCategories] = useState([]);

  const loadCategories = () => {
    setLoading(true);
    api.get('/managed/categories')
      .then(response => {
        setCategories(response.data.categories);
      })
      .finally(() => setLoading(false))
  }

  useEffect(() => loadCategories(), []);

  const addCategory = category => {
    if (category.name) {
      api.post('/managed/categories', category)
        .then(res => loadCategories());
    }
  }
  
  if (loading) return 'Loading';
  return (
    <Box w="100%">
      {categories?.length ? categories.map(c => <Category key={c.id} category={c} />) :
      <Box w="100%">
        <span>Looks like you haven't added categories yet. Start by using the form below</span>
      </Box>}
      <AddCategory addCategory={category => addCategory(category)}/>
    </Box>);
}

export default CategoriesAssets;
