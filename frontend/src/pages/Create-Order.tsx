import React, { useState } from "react";
import { API_URL } from "../../config";
import { useNavigate } from "react-router-dom";
import PhoneInput from "react-phone-number-input";
import "react-phone-number-input/style.css";


function CreateOrder () {

        const inicialOrder = () => ({
           customerFirstName : "", customerLastName: "" , customerPhoneNumber :  ""
            , customerEmail : "", customerPickupPerson : "", 
            devices : [{
                deviceType : "", model : "" ,serialNumber: "", amount: 1, manufacturer : ""
            }]
        })
        const [createOrder,setCreateOrder] = useState<any>(inicialOrder());
        const [successMessage,setSuccessMessage] = useState("");
        const [failedMessage,setFailedMessage] = useState("");
        const navigate = useNavigate();
        

     const addDevice = () => {
        setCreateOrder({...createOrder,devices : [...createOrder.devices,{
            deviceType : "",
            model : "",
            serialNumber : "",
            amount : 1,
            manufacturer : ""
        }]});
     };
      const getCsrfToken = () => {
        return document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="))?.split("=")[1];
      }

        const createOrderRequest = async(e : React.SyntheticEvent<HTMLFormElement>) => {
            e.preventDefault();
            try{

                const csrfToken = getCsrfToken();
                const response = await fetch(`${API_URL}/api/create-order`,{
                    method : "POST",
                    credentials : "include",
                    headers : {"Content-Type" : "application/json",
                        "X-XSRF-TOKEN" : csrfToken ?? "" },
                    body : JSON.stringify(createOrder)
                });

                if(response.status === 401){
                navigate("/login");
                return;
                }
                const text = await response.json();
                if(response.ok){
                    setSuccessMessage(text.message);
                    setCreateOrder(inicialOrder());
                    
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
          <form onSubmit={createOrderRequest}>
            <div>
                {successMessage && (
                    <h1>{successMessage}</h1>
                )}
                     {failedMessage && (
                    <h1>{failedMessage}</h1>
                )}
                <h1>Customer Information</h1>
                <input type="text" placeholder="customer First Name" value={createOrder.customerFirstName} required
                onChange={(e) => setCreateOrder({...createOrder,customerFirstName : e.target.value})}/>
                <br/>
                <input type="text" placeholder="customer Last Name" value={createOrder.customerLastName}
                onChange={(e) => setCreateOrder({...createOrder,customerLastName : e.target.value})}/>
                <br/>
                <PhoneInput defaultCountry="PL" value={createOrder.customerPhoneNumber}  placeholder="Enter Phone Number"
                onChange={(e) => setCreateOrder({...createOrder,customerPhoneNumber : e ?? ""})}/>
                <br/>
                <input type="email" placeholder="customer email" value={createOrder.customerEmail}
                onChange={(e) => setCreateOrder({...createOrder,customerEmail : e.target.value})}/>
                <br/>
                <input type="text" placeholder="customer  Pickup Person" value={createOrder.customerPickupPerson}
                onChange={(e) => setCreateOrder({...createOrder,customerPickupPerson : e.target.value})}/>
                <br/>
                <h1>Device Information</h1>
                {createOrder.devices.map((device : any,index : number) => (
                    <div key={index}>
                        <h1>Device : {index + 1}</h1>

                <input type="text" placeholder="device Type" value={device.deviceType} required
                onChange={(e) => {
                    const updateDevices = [...createOrder.devices];
                    updateDevices[index] = {...updateDevices[index],deviceType : e.target.value};
                    setCreateOrder({...createOrder,devices : updateDevices});
                }} />
                <br/>
                 <input type="text" placeholder="device model" value={device.model} 
                onChange={(e) => {
                    const updateDevices = [...createOrder.devices];
                    updateDevices[index] = {...updateDevices[index],model : e.target.value};
                    setCreateOrder({...createOrder,devices : updateDevices});
                }} />
                <br/>
                <input type="text" placeholder="device serialNumber" value={device.serialNumber} 
                onChange={(e) => {
                    const updateDevices = [...createOrder.devices];
                    updateDevices[index] = {...updateDevices[index],serialNumber : e.target.value};
                    setCreateOrder({...createOrder,devices : updateDevices});
                }} />
                <br/>
                <input type="text" placeholder="device manufacturer" value={device.manufacturer} 
                onChange={(e) => {
                    const updateDevices = [...createOrder.devices];
                    updateDevices[index] = {...updateDevices[index],manufacturer : e.target.value};
                    setCreateOrder({...createOrder,devices : updateDevices});
                }} />
  
                <br/>
                <input type="number" placeholder="device amount" value={device.amount}  min={1}
                onChange={(e) => {
                    const updateDevices = [...createOrder.devices];
                    updateDevices[index] = {...updateDevices[index],amount : Number(e.target.value)};
                    setCreateOrder({...createOrder,devices : updateDevices});
                }} />
                    </div>
                ))}
            </div>
            <button className="btn btn-success" type="submit">Create Order</button>
            <button type="button" onClick={addDevice}> + Add another device</button>
          </form>
        </div>
    )    
}

export default CreateOrder;