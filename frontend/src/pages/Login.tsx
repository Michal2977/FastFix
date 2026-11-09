import React, { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
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
      setTimeout(() => {
            setCorrectMessage("");
        }, 3000);
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

 setTimeout(() => {
        setCorrectMessage("");
    },3000);
   })

     
    const login = async(e : React.SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();
    try {
        const csrfResponse = await fetch(
            `${API_URL}/api/auth/csrf`,
            {
                credentials: "include"
            }
        );
        
        if (!csrfResponse.ok) {
             setIncorrectMessage("An error occurred during loginasda");
             setTimeout(() => {
             setIncorrectMessage("");
             },3000);
             return;
        }

        const cookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));

        if (!cookie) {
            setIncorrectMessage("An error occurred during login");
            setTimeout(() => {
                setIncorrectMessage("");
            },3000);
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
            setTimeout(() => {
            setIncorrectMessage("");
            },3000);
           
          
        }
    } catch (error) {
        setIncorrectMessage("An error occurred during login");
        setTimeout(() => {
           setIncorrectMessage("");
        },3000);
       
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
        <form onSubmit={login}>  
         <input type="email" placeholder="email" value={email}  onChange={(e) => setEmail(e.target.value)} required/>
         <br/>
         <input type="password" placeholder="password" value={password} onChange={(e) => setPassword(e.target.value)} required/>
         <br/>
         <button type="submit" className="btn btn-success">Sign In</button>
         <br/>
         <Link to={"/resend-email"}>Resend Email</Link>
         <Link to={"/forgot-password"}>Forgot Passowrd?</Link>
         </form>
        </div>
      
    )
}
export default Login;