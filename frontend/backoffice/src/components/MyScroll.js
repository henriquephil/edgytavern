import { Box } from '@chakra-ui/react'
import { Scrollbars } from 'react-custom-scrollbars'

function MyScroll({children}) {
  return <Scrollbars renderThumbVertical={({style, ...props}) => <div {...props} style={{...style, backgroundColor: '#ffa100', }}></div>}>
      <Box paddingRight="10px">
        {children}
      </Box>
    </Scrollbars>
}
export default MyScroll
