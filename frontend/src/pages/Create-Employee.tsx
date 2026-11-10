import React, { useRef, useState } from "react";
import { API_URL } from "../../config";
import { useNavigate } from "react-router-dom";


function CreateEmplyee(){

    const [newEmployee,setNewEmployee] = useState<any>({firstName : "",lastName : "",email : "",password : "",role : ""});
    const [successMessage,setSuccessMessage] = useState("");
    const [failedMessage,setFialedMessage] = useState("");
    const navigate = useNavigate();
    const [file,setFile] = useState<File | null>(null);
    const fileInputRef = useRef<HTMLInputElement>(null);



     const getCsrfToken = () => {
        return document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN"))?.split("=")[1];
     }


    const createEmployee = async(e : React.SyntheticEvent<HTMLFormElement>) => {
     e.preventDefault();
     try{

        const request = {
            ...newEmployee,
            firstName : newEmployee.firstName,
            lastName : newEmployee.lastName,
            email : newEmployee.email,
            password : newEmployee.password,
            role : [newEmployee.role],
        };

        const formData = new FormData();

        formData.append("request", new Blob([
            JSON.stringify(request)
        ],{type : "application/json"}));

        if(file){
            formData.append("file",file);
        }
        const csrfToken = getCsrfToken();
        const response = await fetch(`${API_URL}/api/auth/create-employee`,{
            method : "POST",
            credentials : "include",
            headers : {
                "X-XSRF-TOKEN" : csrfToken ?? ""
            },
            body : formData
        });

        if(response.status === 401){
            navigate("/login");
            return;
        }
        const text = await response.json();
        if(response.ok){
            setSuccessMessage(text.message);
             setTimeout(() => {
                setNewEmployee({
                firstName : "",
                lastName : "",
                email : "",
                password : "",
                role : []

                })
                setSuccessMessage("");
            },3000);
            if(fileInputRef.current){
              fileInputRef.current.value = "";
            }
            setFile(null);
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
           <input type="file"  accept="image/jpeg,image/png,image/webp" onChange={(e : React.ChangeEvent<HTMLInputElement>) => {
            setFile(e.target.files?.[0] ?? null);
           }}   ref={fileInputRef}/>
           {file && (
            <img src={URL.createObjectURL(file)} alt="no Image" width={"200px"} height={"200px"}/>
           )}
          <br/>
          <button type="submit" className="btn btn-success">Create</button>
          </form>
        </div>
    );
}
export default CreateEmplyee;

