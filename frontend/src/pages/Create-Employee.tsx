import React, { useState } from "react";
import { API_URL } from "../../config";
import { useNavigate } from "react-router-dom";


function CreateEmplyee(){

    const [newEmployee,setNewEmployee] = useState<any>({firstName : "",lastName : "",email : "",password : "",role : ""});
    const [successMessage,setSuccessMessage] = useState("");
    const [failedMessage,setFialedMessage] = useState("");
    const navigate = useNavigate();



     const getCsrfToken = () => {
        return document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN"))?.split("=")[1];
     }


    const createEmployee = async(e : React.SyntheticEvent<HTMLFormElement>) => {
     e.preventDefault();
     try{
        const csrfToken = getCsrfToken();
        const response = await fetch(`${API_URL}/api/auth/create-employee`,{
            method : "POST",
            credentials : "include",
            headers : {"Content-Type" : "application/json",
                "X-XSRF-TOKEN" : csrfToken ?? ""
            },
            body : JSON.stringify({...newEmployee, role: [newEmployee.role]})
        });

        if(response.status === 401){
            navigate("/login");
            return;
        }
        const text = await response.json();
        if(response.ok){
            setSuccessMessage(text.message);
             setTimeout(() => {
                setSuccessMessage("");
            },3000);
        }else{
            setFialedMessage(text.message);
            setTimeout(() => {
                setFialedMessage("");
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
            <form onSubmit={createEmployee}>
               
          <input type="text" placeholder="first name" value={newEmployee.firstName} required
          onChange={(e) => setNewEmployee({...newEmployee,firstName : e.target.value})}/>
          <br/>
          <input type="text" placeholder="last name" value={newEmployee.lastName} required
           onChange={(e) => setNewEmployee({...newEmployee,lastName : e.target.value})}/>
          <br/>

           <div className="form-field">
            <label htmlFor="exampleFormControlSelect2" className="form-label">Example select</label>
            <select className="form-control" id="exampleFormControlSelect2" value={newEmployee.role} 
            onChange={(e) => setNewEmployee({...newEmployee,role : e.target.value})} required>
                <option value="" disabled>Select Role</option>
                <option value="ADMIN">ADMIN</option>
                <option value="MANAGER">MANAGER</option>
                <option value="TECHNICIAN">TECHNICIAN</option>
                <option value="RECEPTIONIST">RECEPTIONIST</option>
            </select>
             </div>
          <br/>

          <input type="email" placeholder="email" value={newEmployee.email} required
          onChange={(e) => setNewEmployee({...newEmployee,email : e.target.value})}/>
          <br/>
          <input type="password" placeholder="password" value={newEmployee.password} required
          onChange={(e) => setNewEmployee({...newEmployee,password : e.target.value})}/>

          <br/>
          <button type="submit" className="btn btn-success">Create</button>
          </form>
        </div>
    );
}
export default CreateEmplyee;

