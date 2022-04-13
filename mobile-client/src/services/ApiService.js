const mock = [
  { id: 1, uid: 'yyyy', name: 'Mesa 1', establishment: {id: 1, uid: 'xxxx', name: 'Lanchonete' } }
];

function beginSessionAt(establishmentUid, localtionUid) {
  // there we should start a "session" in the new position
  // Must check if there is not an ongoing session for this user (backend)
  // And then return the location data to build the ui
  // Should also store the session id in storage to continue in case of reopening the app
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const filtered = mock.filter(l => l.uid === localtionUid);
      if (filtered.length >= 1) {
        resolve({
          uid: 'ad09a8Se21',
          location: filtered[0]
        });
      } else {
        reject('not found');
      }
    }, 3000);
  })
};

const bills = {
  findAll: localtionUid => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
          resolve([
            {
              uid: 'dsad009',
              customer: { uid: 'asdqe123', name: 'Henrique' },
              itens: [
                { uid: 'qd123d', description: 'asdasdasd', quantity: 1, value: 123.3},
                { uid: '12efrc', description: 'qweqweqw', quantity: 1, value: 123.3},
                { uid: '654dfe', description: 'zxczxczx', quantity: 1, value: 123.3}
              ]
            },
            {
              uid: '6z1xcv',
              customer: { uid: 't89e4r8', name: 'John' },
              itens: [
                { uid: 'sd5s4f', description: 'gdfgdfg', quantity: 1, value: 123.3},
                { uid: 'f9g8f9', description: 'erterter', quantity: 1, value: 123.3},
                { uid: 'e8w78w', description: 'cvbcvb', quantity: 1, value: 123.3}
              ]
            }
          ]);
      }, 3000);
    });
  }
}

const ApiService = {
  beginSessionAt,
  bills
}

export default ApiService;