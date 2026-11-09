import { useEffect, useState } from "react";
import SideBar from "../components/SideBar";
import { useNavigate } from "react-router-dom";
import { API_URL } from "../../config";

function Dashboard(){

     const [user, setUser] = useState<any>(null);
    const navigate = useNavigate();
    const [inncorectMessage,setIncorrectMessage] = useState("");
    const [correctMessage,setCorrectMessage] = useState("");
    const [loading ,setLoading] = useState(true);

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
    };
       getUser();
    },[navigate])


    const logout = async() => {
        try{
            const csrfResponse = await fetch(`${API_URL}/api/auth/csrf`,{
                credentials : "include"
            });

            if(!csrfResponse.ok){
                return;
            }

            const cookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));

            if(!cookie){
                return;
            }

            const csrfToken = decodeURIComponent(cookie.substring("XSRF-TOKEN=".length));

            const response = await fetch(`${API_URL}/api/auth/logout`,{
                method : "POST",
                credentials : "include",
                headers : {"X-XSRF-TOKEN" : csrfToken}
            });

            if(response.ok){
                const text = await response.json();
                navigate("/login",{state: {message: text.message}});
            }
        }catch(error){
            console.log(error);
        }
    }
         
    const admin = user?.roleDto?.some((role: {role :String}) => role.role === "ADMIN");


   return (
    <>
        {loading ? <p>Loading......</p> : user ?  (
            <div>
            <SideBar logout = {logout} user= {user} admin={admin}/>
           
            </div>
        ) : (
        <>
        <p>Unable to load account information.</p>
        <button onClick={() => window.location.reload()}> Refresh page </button>
            </>
        )}
    </>
);
}
export default Dashboard;

