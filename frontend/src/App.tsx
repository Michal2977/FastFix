import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/login";
import Dashboard from "./pages/Dashboard";

function App() {
 

  return (
  
    <BrowserRouter>
     <Routes>
      <Route path="/login" element={<Login/>} />
      <Route path="/dashboard" element={<Dashboard/>} />
     </Routes>
    </BrowserRouter>
    
  )
}

export default App
