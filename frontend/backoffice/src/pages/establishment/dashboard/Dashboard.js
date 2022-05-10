import { Box, Flex } from "@chakra-ui/react";
import MyScroll from "../../../components/MyScroll";
import RecentOrders from "./RecentOrders";
import SpotBills from './SpotBills';
import styles from './Dashboard.module.css';
import { useDispatch, useSelector } from "react-redux";
import OpenRegister from "./OpenRegister";
import { closeRegister } from "../../../services/apiService";

function Dashboard() {
  const { loading, data, error } = useSelector(state => state.register);
  const dispatch = useDispatch();
  
  function clickCloseRegister() {
    dispatch(closeRegister())
      .catch(err => console.log(err)) // TODO
  }

  if (loading) return 'Loading';
  if (error) return JSON.stringify(error);
  if (!data) return <OpenRegister/>

  return (
    <div className={styles.Dashboard}>
      <div className={styles.DashboardRegister}>
        Register running: {JSON.stringify(data)}
        <button onClick={() => clickCloseRegister()}>Close register</button>
      </div>
      <div className={styles.DashboardPanel}>
        <Flex flexBasis="auto" grow="1" h="100%" wrap="wrap" alignItems="start" justify="center" flexWrap="wrap">
          <MyScroll>
            <SpotBills />
          </MyScroll>
        </Flex>
        <Box flexShrink="0" h="100%" className={styles.RecentOrdersContainer}>
          <MyScroll>
            <RecentOrders/>
          </MyScroll>
        </Box>
      </div>
    </div>
  );
}

export default Dashboard;
