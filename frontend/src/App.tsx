import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import AccountInfomation from "./pages/Account-Information";


function App() {
 

  return (
  
    <BrowserRouter>
     <Routes>
      <Route path="/login" element={<Login/>} />
      <Route path="/dashboard" element={<Dashboard/>} />
      <Route path="/account-information" element={<AccountInfomation/>} />
     </Routes>
    </BrowserRouter>
    
  )
}

export default App
