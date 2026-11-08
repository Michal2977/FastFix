import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { API_URL } from "../../config";


function Login(){

    const navigate = useNavigate();
    const [correctMessage,setCorrectMessage] = useState("");
    const [inncorectMessage,setIncorrectMessage] = useState("");
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const location = useLocation();





  
useEffect(() => {
    if (location.state?.message) {
        setCorrectMessage(location.state.message);
        navigate(location.pathname, {
            replace: true,
            state: null
        });
       const timer = setTimeout(() => {
            setCorrectMessage("");
        }, 3000);
        return () => clearTimeout(timer);
    }
}, [location.state, navigate]);

   useEffect(() => {
    if(location.state?.message){
        setCorrectMessage(location.state.message);
        navigate(location.pathname,{
            replace: true,
            state: null
            
        });
    }
   },[location,navigate]);

   useEffect(() => {
    if(!correctMessage){return;}

    const timer = setTimeout(() => {
        setCorrectMessage("");
    },3000);
   })

    const login = async () => {
    try {
        const csrfResponse = await fetch(
            `${API_URL}/api/auth/csrf`,
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
        const response = await fetch(`${API_URL}/api/auth/login`,
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
           const timer =  setTimeout(() => {
            setIncorrectMessage("");
            },3000);
           
            return () => clearTimeout(timer);
        }
    } catch (error) {
        setIncorrectMessage("An error occurred during login");
       const timer =  setTimeout(() => {
           setIncorrectMessage("");
        },3000);
        return () => clearTimeout(timer);
    }
};

    return(
        <div>
            {inncorectMessage && (
                <h1>{inncorectMessage}</h1>
            )}
            {correctMessage && (
                <h1>{correctMessage}</h1>
            )} 
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