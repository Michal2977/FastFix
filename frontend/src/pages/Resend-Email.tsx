import type React from "react"
import { API_URL } from "../../config";
import { useState } from "react";




function ResendEmail(){

    const [email,setEmail] = useState("");
    const [successMessage,setSuccessMessage] = useState("");
    const [failedMessage,setFailedMessage] = useState("");
   
   
    const resendEmail = async(e : React.SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();
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
      
            
            const response = await fetch(`${API_URL}/api/auth/resend?email=${encodeURIComponent(email)}`,{
              method : "POST",
              credentials : "include",
              headers : {"X-XSRF-TOKEN" :  csrfToken ?? "" }
            });

           const text = await response.json();
           if(response.ok){
            setSuccessMessage(text.message);
            setTimeout(() => {
                setSuccessMessage("");
            },3000);
           }else{
            setFailedMessage(text.message);
            setTimeout(() => {
                setFailedMessage("");
            },3000);
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
            </form>

        </div>
    )
}

export default ResendEmail;