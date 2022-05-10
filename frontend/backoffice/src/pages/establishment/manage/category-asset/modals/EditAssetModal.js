import { AddIcon, DeleteIcon, EditIcon, HamburgerIcon, LockIcon, UnlockIcon } from "@chakra-ui/icons";
import { Box, Button, Checkbox, Flex, Menu, MenuButton, MenuItem, MenuList } from "@chakra-ui/react";
import { useContext, useState } from "react";
import { useList } from "react-use";
import api from '../../../../../services/api';
import { ModalContext } from "../../../../../components/modal/ModalContext";

// TODO break into components
function EditAssetModal({asset}) {
  if (!asset.category) throw new Error("Asset must have a predetermined category");

  const [ name, setName ] = useState(asset.name);
  const [ active, setActive ] = useState(asset.active);
  const [ price, setPrice ] = useState(asset.price);
  const [ description, setDescription ] = useState(asset.description);
  const [ ingredients, ingredientsActions ] = useList(asset.ingredients);
  const [ ingredientName, setIngredientName ] = useState('');
  const [ ingredientRemovable, setIngredientRemovable ] = useState(false);
  const [ additionals, additionalsActions ] = useList(asset.additionals);
  const [ additionalName, setadditionalName ] = useState('');
  const [ additionalPrice, setAdditionalPrice ] = useState('');
  const { closeModal } = useContext(ModalContext)

  const [ saveLoading, setSaveLoading ] = useState(false);

  const save = () => {
    const body = {
      name, active, categoryId: asset.category.id, price, description, ingredients, additionals
    };
    if (body.name && body.categoryId && body.price) {
      setSaveLoading(true);
      const promise = asset.id ? 
        api.put(`/managed/assets/${asset.id}`, body) :
        api.post('/managed/assets', body);
      promise
        .then(() => closeModal(true))
        .finally(() => setSaveLoading(false));
    }
  }

  const renderIngredient = ingredient => {
    return <Flex key={ingredient.id}>
      <Flex basis="auto" grow="1" className="form-input">
        <span readOnly>{ingredient.name}</span>
      </Flex>
      <Flex shrink="0" p="0 20px" className="form-input" justify="center">
        {ingredient.removable ? <UnlockIcon/> : <LockIcon/>}
      </Flex>
      <Flex sharink="0" className="form-input">
        <Menu>
          <MenuButton as={Button} p="8px">
            <HamburgerIcon />
          </MenuButton>
          <MenuList>
            <MenuItem><EditIcon/> Edit</MenuItem>
            <MenuItem><DeleteIcon/> Delete</MenuItem>
          </MenuList>
        </Menu>
      </Flex>
    </Flex>;
  }

  const renderNewIngredient = () => {
    return (
      <Flex>
        <Flex basis="auto" grow="1" className="form-input">
          <input type="text" placeholder="Name" value={ingredientName} onChange={e => setIngredientName(e.target.value)}/>
        </Flex>
          <Box p="4px" flexShrink="0">
            <Button onClick={() => setIngredientRemovable(!ingredientRemovable)} variant="ghost">
              {ingredientRemovable ? <UnlockIcon/> : <LockIcon/>}
            </Button>
          </Box>
        <Box p="4px" flexShrink="0">
          <Button onClick={() => {
            if (ingredientName && (typeof ingredientRemovable === 'boolean'))
              ingredientsActions.push({ name: ingredientName, removable: ingredientRemovable })
          }}>
            <AddIcon/>
          </Button>
        </Box>
      </Flex>
    );
  }

  const renderAdditional = additional => {
    return <Flex key={additional.id}>
    <Flex basis="auto" grow="1" className="form-input">
        <span>{additional.name}</span>
      </Flex>
      <Flex basis="120px" flexShrink="0" className="form-input">
        <span>{additional.price}</span>
      </Flex>
      <Flex sharink="0" className="form-input">
        <Menu>
          <MenuButton as={Button} p="8px">
            <HamburgerIcon />
          </MenuButton>
          <MenuList>
            <MenuItem><EditIcon/> Edit</MenuItem>
            <MenuItem><DeleteIcon/> Delete</MenuItem>
          </MenuList>
        </Menu>
      </Flex>
    </Flex>;
  }

  const renderNewAdditional = () => {
    return (
    <Flex>
      <Flex basis="auto" grow="1" className="form-input">
        <input type="text" placeholder="Name" value={additionalName} onChange={e => setadditionalName(e.target.value)}/>
      </Flex>
      <Flex basis="120px" flexShrink="0" className="form-input">
        <input type="number" placeholder="Price" value={additionalPrice} onChange={e => setAdditionalPrice(e.target.value)}/>
      </Flex>
      <Box p="4px" flexShrink="0">
        <Button onClick={() => {
          if (additionalName && additionalPrice)
            additionalsActions.push({ name: additionalName, price: additionalPrice })
        }}>
          <AddIcon/>
        </Button>
      </Box>
    </Flex>
    )
  }

  return (
    <Flex p="8px" w="750px" wrap="wrap">
      <Flex basis="50%" grow="1" className="form-input">
          <label>Name</label>
          <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </Flex>
      {asset.id ? (
        <Flex shrink="1" className="form-input" paddingTop="32px">
          <Checkbox isChecked={active} onChange={e => setActive(e.target.checked)} colorScheme="whiteAlpha">Active</Checkbox>
        </Flex>) : null}
      <Flex basis="70%" className="form-input">
          <label>Category</label>
          <input type="text" value={asset.category.name} readOnly tabIndex="-1" />
      </Flex>
      <Flex basis="30%" className="form-input">
          <label>Price</label>
          <input type="number" value={price} onChange={e => setPrice(e.target.value)}/>
      </Flex>
      <Flex basis="100%" className="form-input">
          <label>Detailed description</label>
          <input type="text" value={description} onChange={e => setDescription(e.target.value)}/>
      </Flex>

      <Flex basis="50%" direction="column">
        <label>Ingredients</label>
        {ingredients.map(renderIngredient)}
        {renderNewIngredient()}
      </Flex>

      <Flex basis="50%" direction="column">
        <label>Additionals</label>
        {additionals.map(renderAdditional)}
        {renderNewAdditional()}
      </Flex>

      <Flex basis="100%" justify="flex-end" borderTop="1px solid #ffa100" marginTop="4px">
        <Box p="4px"><Button onClick={() => closeModal()}>Cancel</Button></Box>
        <Box p="4px"><Button onClick={() => save()} isLoading={saveLoading}>Save</Button></Box>
      </Flex>
    </Flex>
  )
}

export default EditAssetModal;