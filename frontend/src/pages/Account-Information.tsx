import { useEffect, useState } from "react";
import { API_URL } from "../../config";
import { useNavigate } from "react-router-dom";



function AccountInfomation(){

   const [user,setUser] = useState<any>(null);
   const navigate = useNavigate();
   const [loading,setLoading] = useState(true);
   
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
   
   return(
    <>
    {loading ? <p>Loading........</p> : user ? (
        <div>
            <h1>{user?.firstName}</h1>
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