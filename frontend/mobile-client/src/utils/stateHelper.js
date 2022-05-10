export function updateStateArrayItem(collection, index, callback) {
  return collection.map((item, idx) => {
    if (idx === index) {
      const newItem = {
        ...item
      };
      callback(newItem, item);
      return newItem
    }
    return item;
  });
}