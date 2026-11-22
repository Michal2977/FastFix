import { useEffect, useState } from "react";
import { API_URL } from "../../config";
import { useNavigate } from "react-router-dom";


function AllOrders(){

    const [ordersReceived,setOrdersReceived] = useState<any>([]);
    const [loading ,setLoading] = useState(true);
    const navigate = useNavigate();


    useEffect(() => {
        const findOrders =  async() =>{
            try{
                const response = await fetch(`${API_URL}/api/all-orders`,{
                    credentials : "include"
                });

                if(response.status === 401){
                   navigate("/login");
                   return;
                }

                const data = await response.json();
                setOrdersReceived(data);

            }catch(error){
                console.log(error);
            }finally{
                setLoading(false);
            }
        }
        findOrders();
    },[navigate])
    return(
        <div>
            {loading  ? <p>Loading ...</p> : ordersReceived ? (
                <>
                {ordersReceived.map((order : any) => (
                    <div key={order.id}>
                   <h1>{order?.id}</h1>
                   <h1>{ordersReceived?.deviceStatus}</h1>
                    </div>
                ))}
               
                </>
            ) : (
                <>
                <p>Unable to load account information.</p>
                <button onClick={() => window.location.reload()}>Refresh Page</button>
                </>
            )}
        </div>
    )
}

export default AllOrders;