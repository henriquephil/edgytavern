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
  app.use('/api/counter',
    createProxyMiddleware({
      target: 'http://localhost:8082',
      pathRewrite: { '^/api/counter': '/', },
      changeOrigin: true,
    })
  )
  app.use('/api/events',
    createProxyMiddleware({
      target: 'http://localhost:8088',
      pathRewrite: { '^/api/events': '/', },
      changeOrigin: true,
    })
  )
}
