import { ChakraProvider } from '@chakra-ui/react'
import React from 'react'
import { createRoot } from 'react-dom/client'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { extendTheme } from "@chakra-ui/react"
import { Provider } from 'react-redux'
import store from './state/store'
import { BrowserRouter } from 'react-router-dom'

const theme = extendTheme({
  background: "#222222",
  colors: {
    gray: {
      50: "#222222",
      100: "#333333",
      200: "#4a4a4a",
      300: "#646464",
      400: "#7f7f7f",
      500: "#999999",
      600: "#b3b3b3",
      700: "#cccccc",
      800: "#e6e6e6",
      900: "#ffffff",
    }
  },
  shadows: {
    outline: "0 0 0 2px #ffa100",
  },
  radii: {
    sm: "1px",
    base: "2px",
    md: "3px",
    lg: "4px",
    xl: "5px",
  },
  variant: 'ghost',
})


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()

createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <ChakraProvider theme={theme}>
          <App />
        </ChakraProvider>
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
)
