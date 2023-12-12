const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function(app) {
  app.use('/api/security',
    createProxyMiddleware({
      target: 'http://192.168.16.101:8089',
      pathRewrite: { '^/api/security': '/', },
      changeOrigin: true,
    })
  )
  app.use('/api/establishment',
    createProxyMiddleware({
      target: 'http://192.168.16.101:8081',
      pathRewrite: { '^/api/establishment': '/', },
      changeOrigin: true,
    })
  )
  app.use('/api/counter',
    createProxyMiddleware({
      target: 'http://192.168.16.101:8082',
      pathRewrite: { '^/api/counter': '/', },
      changeOrigin: true,
    })
  )
}
