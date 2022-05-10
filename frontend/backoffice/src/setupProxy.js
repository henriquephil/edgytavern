const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use('/api/establishment',
    createProxyMiddleware({
      target: 'http://localhost:8081',
      pathRewrite: { '^/api/establishment': '/', },
      changeOrigin: true,
    })
  );
  app.use('/api/bills',
    createProxyMiddleware({
      target: 'http://localhost:8082',
      pathRewrite: { '^/api/bills': '/', },
      changeOrigin: true,
    })
  );
  app.use('/api/yeller',
    createProxyMiddleware({
      target: 'http://localhost:8088',
      pathRewrite: { '^/api/yeller': '/', },
      changeOrigin: true,
    })
  );
};
