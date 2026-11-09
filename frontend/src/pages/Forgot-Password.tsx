import type React from "react";
import { API_URL } from "../../config";
import { useState } from "react";



function ForgotPassword(){

    const [failedMessage,setFailedMessage] = useState("");
    const [successMessage,setSuccessMessage] = useState("");
    const [email,setEmail] = useState("");

    const sendEmail = async(e : React.SyntheticEvent<HTMLFormElement>) => {
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

            const cookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));

            if(!cookie){
                setFailedMessage("An error occurred during resend email");
                setTimeout(() => {
                    setFailedMessage("");
                },3000);
                return;
            }

            const csrfToken = decodeURIComponent(cookie.substring("XSRF-TOKEN=".length));

            const response = await fetch(`${API_URL}/api/auth/forgot-password?email=${encodeURIComponent(email)}`,{
                credentials : "include",
                method : "POST",
                headers : {"X-XSRF-TOKEN" : csrfToken ?? ""}
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
            <form onSubmit={sendEmail}>
             {successMessage && (
                <h1>{successMessage}</h1>
            )}
              {failedMessage && (
                <h1>{failedMessage}</h1>
            )}

             <input type="email" placeholder="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
             <br/>
             <button type="submit" className="btn btn-success">Send </button>
            </form>

        </div>
    )
}

export default ForgotPassword;