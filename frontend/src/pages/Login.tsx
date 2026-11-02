import { useState } from "react";
import { useNavigate } from "react-router-dom";


function Login(){

    const navigate = useNavigate();
    const [correctMessage,setCorrectMessage] = useState("");
    const [message,setIncorrectMessage] = useState("");
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");

    const login = async() => {
        const response = await fetch("http://localhost:8080/api/auth/login",{
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            credentials : "include",
            body : JSON.stringify({email,password})
        })

       
        if(response.ok){
            navigate("/dashboard");
        }else{
             const text = await response.json();
            setIncorrectMessage(text.message);
        }
    }

    return(
        <div>
        <form onSubmit={(e) => {e.preventDefault(); login();}}>  
         <input type="email" placeholder="email" value={email}  onChange={(e) => setEmail(e.target.value)} required/>
         <br/>
         <input type="password" placeholder="password" value={password} onChange={(e) => setPassword(e.target.value)} required/>
         <br/>
         <button type="submit">Sign In</button>
         </form>
        </div>
      
    )
}
export default Login;