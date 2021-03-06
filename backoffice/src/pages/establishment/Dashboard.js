import { Box, Flex } from "@chakra-ui/react";
import MyScroll from "../../components/MyScroll";
import RecentOrders from "./dashboard/RecentOrders";
import SpotsBills from './dashboard/SpotBills';
import style from './Dashboard.module.css';

function Dashboard() {
  return (<>
      <Flex flexBasis="auto" grow="1" h="100%" wrap="wrap" alignItems="start" justify="center" flexWrap="wrap">
        <MyScroll>
          <SpotsBills />
        </MyScroll>
      </Flex>
      <Box flexShrink="0" h="100%" className={style.RecentOrdersContainer}>
        <MyScroll>
          <RecentOrders/>
        </MyScroll>
      </Box>
    </>);
}

export default Dashboard;
