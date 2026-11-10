import type React from "react"
import { API_URL } from "../../config";
import { useState } from "react";
import {Turnstile,  useTurnstile } from "react-turnstile";




function ResendEmail(){

    const [email,setEmail] = useState("");
    const [successMessage,setSuccessMessage] = useState("");
    const [failedMessage,setFailedMessage] = useState("");
    const [turnstileToken,setTurnstileToken] = useState("");
    const turnstile = useTurnstile();
   
   
    const resendEmail = async(e : React.SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();

        if(!turnstileToken){
                setFailedMessage("Complete the Turnstile verification.");
                setTimeout(() => {
                    setFailedMessage("");
                },3000);
                return;
            }
        try{
          
            const csrfResponse = await fetch(`${API_URL}/api/auth/csrf`,{
                credentials : "include"
            });

            if(!csrfResponse.ok){
                setFailedMessage("An error occurred during resend email");
                setTimeout(() => {
                    setFailedMessage("");
                },3000);
                return;
            }
            const getCookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));
             
             if(!getCookie){
                setFailedMessage("An error occurred during resend email");
                setTimeout(() => {
                    setFailedMessage("");
                },3000);
                return;
             }   

             const csrfToken = decodeURIComponent(getCookie.substring("XSRF-TOKEN=".length));
      
            
            const response = await fetch(`${API_URL}/api/auth/resend?email=${encodeURIComponent(email)}&turnstileToken=${encodeURIComponent(turnstileToken)}`,{
              method : "POST",
              credentials : "include",
              headers : {"X-XSRF-TOKEN" :  csrfToken ?? "" }
            });

           const text = await response.json();
           if(response.ok){
            setSuccessMessage(text.message);
            setEmail("");
            setTurnstileToken("");
            setTimeout(() => {
                setSuccessMessage("");
            },3000);
           }else{
            setFailedMessage(text.message);
            setTurnstileToken("");
            setTimeout(() => {
                setFailedMessage("");
            },3000);
           turnstile.reset();
           }
     
        }catch(error){
            console.log(error);
        }
    } 
    

    return(
        <div>
            {successMessage && (
                <h1>{successMessage}</h1>
            )}
              {failedMessage && (
                <h1>{failedMessage}</h1>
            )}

            <form onSubmit={resendEmail}>
                
                <input type="email" placeholder="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                <button type="submit" className="btn btn-success">Send</button>
                <Turnstile sitekey="0x4AAAAAADsdFGr1bVY65jeD" onSuccess={(token) => 
                    {setTurnstileToken(token);setFailedMessage("");}}/>
           
            </form>

        </div>
    )
}

export default ResendEmail;