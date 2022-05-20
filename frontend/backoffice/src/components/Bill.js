import { Box, Flex, Grid } from "@chakra-ui/react"
import { Fragment } from "react"
import style from './Bill.module.css'

function Bill({bill}) {


  return (
    <Box p="8px 0">
      <Flex direction="column" p="6px" rounded="4px" bg="#ffa100">
        <Flex w="100%" className={style.header}>
          <Box flex="1">{bill.customer.name}</Box>
          <Box shrink="0">$ {bill.itens.map(i => i.value).reduce((prev, i) => prev += i, 0).toFixed(2)}</Box>
        </Flex>
        <Grid templateColumns="auto auto auto" className={style.itens}>
          {bill.itens.map(i => <Fragment key={i.uid}>
              <Box>{i.description}</Box>
              <Box className="text-right">{i.quantity}</Box>
              <Box className="text-right">{i.value}</Box>
            </Fragment>)}
        </Grid>
      </Flex>
    </Box>
  )
}

export default Bill
