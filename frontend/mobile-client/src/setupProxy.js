const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function(app) {
  app.use('/api/security',
    createProxyMiddleware({
      target: 'http://localhost:8089',
      pathRewrite: { '^/api/security': '/', },
      changeOrigin: true,
    })
  )
  app.use('/api/establishment',
    createProxyMiddleware({
      target: 'http://localhost:8081',
      pathRewrite: { '^/api/establishment': '/', },
      changeOrigin: true,
    })
  )
  app.use('/api/bills',
    createProxyMiddleware({
      target: 'http://localhost:8082',
      pathRewrite: { '^/api/bills': '/', },
      changeOrigin: true,
    })
  )
}
