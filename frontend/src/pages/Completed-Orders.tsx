import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { API_URL } from "../../config";


function CompletedOrders(){

    const [orders,setOrders] = useState<any>([]);
    const [loading,setLoading]  = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
    const findOrders = async() => {
        try{
            const response  = await fetch(`${API_URL}/api/completed-orders`,{
                credentials : "include"
            });

            if(response.status === 401){
                navigate("/login");
                return;
            }

            const data = await response.json();
            setOrders(data);
        }catch(error){
            console.log(error);
        }finally{
            setLoading(false);
        }
    }
    findOrders();
    },[navigate])

    return(
        <>
        {loading ? <p>Loading...</p> : orders ? (

          <>
           {orders.map((order : any) => (
         <div key={order.id}>
          <h1>{order?.deviceStatus}</h1>
           </div>
           ))}
          </>
       
        
        ) : (
            <>
            <p>Unable to load account information.</p>
            <button onClick={() => window.location.reload()}>Refresh Page</button>
            </>
        )}
        </>
    )

}

export default CompletedOrders;