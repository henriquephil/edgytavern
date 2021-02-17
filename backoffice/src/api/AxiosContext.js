import axios from "axios";
import { createContext } from "react";

const AxiosContext = createContext(axios)

export default AxiosContext;