import { Box } from "@chakra-ui/react";
import { useContext, useEffect, useState } from "react";
import AxiosContext from "../../../../api/AxiosContext";
import AddCategory from "./AddCategory";
import Category from "./Category";

function randomString() {
  return Math.random().toString(36).substring(7);
}

function CategoriesAssets() {
  const [loading, setLoading] = useState(true);
  const [categories, setCategories] = useState([]);
  const axios = useContext(AxiosContext);

  const loadCategories = () => {
    setLoading(true);
    axios.get('/api/establishment/management/categories')
      .then(response => {
        setCategories(response.data.categories);
      })
      .finally(() => setLoading(false))
  }

  useEffect(() => loadCategories(), []);

  const addCategory = category => {
    if (category.name) {
      axios.post('/api/establishment/management/categories', category)
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
