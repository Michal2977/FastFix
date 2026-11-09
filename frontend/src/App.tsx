import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import AccountInfomation from "./pages/Account-Information";
import CreateEmplyee from "./pages/Create-Employee";
import VerifyFailed from "./pages/Verify-Failed";
import ResendEmail from "./pages/Resend-Email";
import ForgotPassword from "./pages/Forgot-Password";
import ResetPassword from "./pages/Reset-Password";


function App() {
 

  return (
  
    <BrowserRouter>
     <Routes>
      <Route path="/login" element={<Login/>} />
      <Route path="/dashboard" element={<Dashboard/>} />
      <Route path="/account-information" element={<AccountInfomation/>} />
      <Route path="/create-employee" element={<CreateEmplyee/>} />
      <Route path="/verify-failed" element={<VerifyFailed/>} />
      <Route path="/resend-email" element={<ResendEmail/>} />
      <Route path="/forgot-password" element={<ForgotPassword/>} />
      <Route path="/reset-password" element={<ResetPassword/>} />
     </Routes>
    </BrowserRouter>
    
  )
}

export default App
