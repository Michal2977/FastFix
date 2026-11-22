import React, { useRef, useState, type HtmlHTMLAttributes } from "react";
import { useNavigate } from "react-router-dom";
import { API_URL } from "../../config";



function AddMaterial(){

   const [successMessage,setSuccessMessage] = useState("");
   const [failedMessage,setFialedMessage] = useState("");
   const [material,setMaterial] = useState<any>({
    name : "", description: "",amount : 1,packageSize : 0.01, 
    packageUnit : "", price : 0.01,currency : "", });
    const [file,setFile] = useState<File | null>(null);
    const fileInputRef = useRef<HTMLInputElement>(null);
    const navigate = useNavigate();

    const getCsrfToken = () => {return document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN"))?.split("=")[1]}
    const addNewMaterial = async(e : React.SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();

        try{
            const formData = new FormData();

            formData.append("request",new Blob([
                JSON.stringify(material)
            ], { type : "application/json"}));

            if(file){
                formData.append("file",file);
            }

            const csrfToken = getCsrfToken();

            const response = await fetch(`${API_URL}/api/add-material`,{
                credentials : "include",
                method : "POST",
                headers : {"X-XSRF-TOKEN" : csrfToken ?? ""},
                body : formData
            });

            if(response.status === 401){
                navigate("/login");
                return;
            }

            const text = await response.json();
            if(response.ok){
                setSuccessMessage(text.message);
                setMaterial({
                name: "",
                description: "",
                amount: 1,
                packageSize: 0.01,
                packageUnit: "",
                price: 0.01,
                currency: ""
                });
               
                setTimeout(() => {
                setSuccessMessage("");
                },3000);
                 if(fileInputRef.current){
                    fileInputRef.current.value="";
                    setFile(null);
                 }
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
            <form onSubmit={addNewMaterial}>
         <input type="text" placeholder="name of material" value={material.name} required minLength={2} maxLength={50}
            onChange={(e) => setMaterial({...material,name : e.target.value})}/>
            <br/>
            <textarea placeholder="description(optional)" value={material.description} maxLength={1000}
            onChange={(e) => setMaterial({...material,description : e.target.value})}/>
            <br/>
            <input type="number" placeholder="amount of material" value={material.amount} min={1} required
            onChange={(e) => setMaterial({...material,amount : e.target.value})}/>
            <br/>
            <input type="number" placeholder="pacage Size " value={material.packageSize} min={0.01} required step={"0.01"}
            onChange={(e) => setMaterial({...material,packageSize : e.target.value})}/>
             <br/>
            <select className="form-control" value={material.packageUnit} required
            onChange={(e) => setMaterial({...material,packageUnit : e.target.value})} >
            <option value="" disabled>Choose Unit</option>
            <option value="PIECE">PIECE</option>
            <option value="GRAM">GRAM</option>
            <option value="KILOGRAM">KILOGRAM</option>
            <option value="MILLILITER">MILLILITER</option>
            <option value="LITER">LITER</option>
            <option value="METER">METER</option>
            </select>
             <br/>
            <input type="text" placeholder="Matrial Price" value={material.price} min={0.01} step={"0.01"} required
            onChange={(e) => setMaterial({...material,price : e.target.value})}/>
            <br/>
            <select className="form-control" value={material.currency} required
            onChange={(e) => setMaterial({...material,currency : e.target.value})} >
            <option value="" disabled>Choose Currency</option>
            <option value="PLN">PLN</option>
            <option value="EUR">EUR</option>
            <option value="USD">USD</option>
            <option value="GBP">GBP</option>
            <option value="CHF">CHF</option>
            <option value="JPY">JPY</option>
            <option value="CNY">CNY</option>
            <option value="CAD">CAD</option>
            <option value="AUD">AUD</option>
            <option value="SEK">SEK</option>
            </select>

            <input type="file" accept="image/jpeg,image/png,image/webp" 
            onChange={(e : React.ChangeEvent<HTMLInputElement>) => {setFile(e.target.files?.[0] ?? null)}} ref={fileInputRef}/>
            <br/>
            {file && (
                <img src={URL.createObjectURL(file)} alt="No Image Selected" width={"200px"} height={"200px"}/>
            )}
             <button type="submit" className="btn btn-success">Add New Material</button>
            </form>
          
        </div>

    )
}

export default AddMaterial;