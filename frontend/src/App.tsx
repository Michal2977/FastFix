import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import AccountInfomation from "./pages/Account-Information";
import CreateEmplyee from "./pages/Create-Employee";
import VerifyFailed from "./pages/Verify-Failed";
import ResendEmail from "./pages/Resend-Email";
import ForgotPassword from "./pages/Forgot-Password";
import ResetPassword from "./pages/Reset-Password";
import CreateOrder from "./pages/Create-Order";
import AllOrders from "./pages/All-Orders";
import AcceptedOrders from "./pages/Accepted-Orders";
import InProgressOrders from "./pages/In-Progress-Orders";
import CompletedOrders from "./pages/Completed-Orders";
import AddMaterial from "./pages/Add-Material";
import AllMaterials from "./pages/All-Materials";


function App() {
 

  return (
  
    <BrowserRouter>
     <Routes>
      <Route path="/login" element={<Login/>} />
      <Route path="/dashboard" element={<Dashboard/>} />
      <Route path="/account-information" element={<AccountInfomation/>} />
      <Route path="/dashboard/create-employee" element={<CreateEmplyee/>} />
      <Route path="/verify-failed" element={<VerifyFailed/>} />
      <Route path="/resend-email" element={<ResendEmail/>} />
      <Route path="/forgot-password" element={<ForgotPassword/>} />
      <Route path="/reset-password" element={<ResetPassword/>} />
      <Route path="/dashboard/create-order" element={<CreateOrder/>} />
      <Route path="/dashboard/all-orders" element={<AllOrders/>} />
      <Route path="/dashboard/accepted-orders" element={<AcceptedOrders/>} />
      <Route path="/dashboard/in-progress-orders" element={<InProgressOrders/>} />
      <Route path="/dashboard/completed-orders" element={<CompletedOrders/>} />
      <Route path="/dashboard/add-material" element={<AddMaterial/>} />
      <Route path="/dashboard/all-materials" element={<AllMaterials/>} />
     </Routes>
    </BrowserRouter>
    
  )
}

export default App
