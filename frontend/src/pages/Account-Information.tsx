import React, { useEffect, useState } from "react";
import { API_URL } from "../../config";
import { useNavigate } from "react-router-dom";



function AccountInfomation(){

   const [user,setUser] = useState<any>(null);
   const navigate = useNavigate();
   const [loading,setLoading] = useState(true);
   const [newPassword,setNewPassowrd] = useState("");
   const [coorectMessage,setCorrectMessage] = useState("");
   const [inCorrectMessage,setInCorrectMessage] = useState("");
   
    const getCsrfToken = () => {
    return document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="))?.split("=")[1];
   }
   useEffect(() => {
    const getUser = async() => {
        try{
            const response = await fetch(`${API_URL}/api/auth/account-information`,{
                credentials : "include"
            });

            if(response.status === 401){
             navigate("/login");
             return;
            }

            const data = await response.json();
            setUser(data);
        }catch(error){
            console.log(error);
        }finally{
           setLoading(false);
        }
    }
    getUser();
   },[navigate])
  
   const updateUserAccountData = async(e: React.SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault();
    try{
    
        const csrfToken = getCsrfToken();
        const response = await fetch(`${API_URL}/api/auth/account-information/update`,{
            method : "PUT",
            credentials : "include",
            headers : {"Content-Type" : "application/json",
                "X-XSRF-TOKEN" : csrfToken ?? ""
            },
            body  : JSON.stringify({...user,password : newPassword.trim() || null})
        });

        if(response.status === 401){
          navigate("/login");
          return;
        }

           const data = await response.json();
        
        if(response.ok){
         setCorrectMessage(data.message);
         setTimeout(() => {
            setCorrectMessage("");
         },3000);
        }else{
            setInCorrectMessage(data.message);
            setTimeout(() => {
                setInCorrectMessage("");
            },3000);
        }
     
    }catch(error){
        console.log(error);
    }
   }
   return(
    <>
    {loading ? <p>Loading........</p> : user ? (
        <div>
            {coorectMessage && (
                <h1>{coorectMessage}</h1>
            )}
             {inCorrectMessage && (
                <h1>{inCorrectMessage}</h1>
            )}
            <form onSubmit={updateUserAccountData} autoComplete="off">
                <input type="text" placeholder="firstName" value={user?.firstName}
                onChange={(e) => setUser({...user,firstName : e.target.value})}/>
                <br/>
                <input type="text" placeholder="lastName" value={user?.lastName}
                onChange={(e) => setUser({...user,lastName : e.target.value})}/>
               <br/>
                <input type="email" placeholder="email"  autoComplete="off" value={user?.email}
                onChange={(e) => setUser({...user,email : e.target.value})}/>
                <br/>
                <input type="password"  placeholder="password"  autoComplete="new-password" value={newPassword}
                 onChange={(e) => setNewPassowrd(e.target.value)}/>
                <br/>
                <button type="submit" className="btn btn-success" >Update</button>
            </form>
        </div>
    ) : (
        <>
        <p>Unable to load account information.</p>
        <button onClick={() => window.location.reload()}>Refresh Page</button>
        </>
    )}
    </>
   )

}
 


export default AccountInfomation;