import { useEffect, useState } from "react";
import SideBar from "../components/SideBar";
import { useNavigate } from "react-router-dom";

function Dashboard(){

     const [user, setUser] = useState<any>(null);
    const navigate = useNavigate();
    const [inncorectMessage,setIncorrectMessage] = useState("");
    const [loading ,setLoading] = useState(true);

    useEffect(() => {
    const getUser = async() => {
        try{
            const response = await fetch("http://localhost:8080/api/auth/account-information",{
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
   return (
    <>
        {loading ? null : user ? (
            <div>
            <SideBar />
            <h1>{user.firstName}</h1>
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