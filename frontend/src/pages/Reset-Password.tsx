import React, { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { API_URL } from "../../config";


function ResetPassword(){

    const [successMessage,setSuccessMessage] = useState("");
    const [failedMessage,setFailedMessage] = useState("");
    const navigate = useNavigate();
    const [password,setPassword] = useState("");
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");

    const changePassword = async(e : React.SyntheticEvent<HTMLFormElement>) => {
     e.preventDefault();
        try{
            const csrfResponse = await fetch(`${API_URL}/api/auth/csrf`,{
                credentials : "include"
            });

            if(!csrfResponse.ok){
                setFailedMessage("An error occurred while processing your password reset request");
                setTimeout(() => {
                    setFailedMessage("");
                },3000);
                return;
            }

            const cookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));
            if(!cookie){
                setFailedMessage("An error occurred while processing your password reset request");
                setTimeout(() => {
                    setFailedMessage("");
                },3000);
                return;
            }
     
            const csrfToken = decodeURIComponent(cookie.substring("XSRF-TOKEN=".length));

            if(!token){
            setFailedMessage("Invalid or missing password reset link.");
            setTimeout(() => {
                setFailedMessage("");
            },3000);
           return;
            }

            const response = await fetch(`${API_URL}/api/auth/reset-password?token=${encodeURIComponent(token)}&password=${encodeURIComponent(password)}`,{
                method : "POST",
                credentials : "include",
                headers : {"X-XSRF-TOKEN" : csrfToken ?? ""}
            });

            const text = await response.json();
            if(response.ok){
                setSuccessMessage(text.message);
                setTimeout(() => {
                    setSuccessMessage("");
                    navigate("/login");
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

        <form onSubmit={changePassword}>
        <input type="password" placeholder="new Password" value={password} onChange={(e) => setPassword(e.target.value)} required/>
        <br/>
        <button type="submit" className="btn btn-success">Reset Password</button>
        </form>

        </div>
    )
}
export default ResetPassword;