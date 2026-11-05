import { useState } from "react";
import { useNavigate } from "react-router-dom";


function Login(){

    const navigate = useNavigate();
    const [correctMessage,setCorrectMessage] = useState("");
    const [inncorectMessage,setIncorrectMessage] = useState("");
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");

    const login = async () => {
    try {
        const csrfResponse = await fetch(
            "http://localhost:8080/api/auth/csrf",
            {
                credentials: "include"
            }
        );
        if (!csrfResponse.ok) {
             setIncorrectMessage("An error occurred during login");
             return;
        }

        const cookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));

        if (!cookie) {
            setIncorrectMessage("An error occurred during login");
            return;
        }
        const csrfToken = decodeURIComponent(
            cookie.substring("XSRF-TOKEN=".length)
        );
        const headers = {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": csrfToken
        };
        const response = await fetch("http://localhost:8080/api/auth/login",
            {
                method: "POST",
                credentials: "include",
                headers,
                body: JSON.stringify({ email, password })
            }
        );
       
        if (response.ok) {
             await response.text();
            navigate("/dashboard");
        }else{
             const text = await response.json();
            setIncorrectMessage(text.message);
            return;
        }
    } catch (error) {
        setIncorrectMessage("An error occurred during login");
        return;
    }
};

    return(
        <div>
          {inncorectMessage && <h1>{inncorectMessage}</h1>}    
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