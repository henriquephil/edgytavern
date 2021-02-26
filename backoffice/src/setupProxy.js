const createProxyMiddleware = require('http-proxy-middleware');

module.exports = function(app) {
  app.use('/api/establishment',
    createProxyMiddleware({
      target: 'http://localhost:8081',
      pathRewrite: { '^/api/establishment': '/', },
      changeOrigin: true,
    })
  );
};
