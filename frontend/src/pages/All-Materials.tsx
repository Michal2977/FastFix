import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { API_URL } from "../../config";


function AllMaterials(){

   const [materials,setMaterials] = useState<any>([]);
   const navigate = useNavigate();
   const [loading,setLoading] = useState(true);

   const getCsrfToken = () => {
    return document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN"))?.split("=")[1]
};
   
   useEffect(() => {
    const getMaterials = async() => {
        try{
      const response = await fetch(`${API_URL}/api/all-materials`,{
            credentials : "include"
        });

        if(response.status === 401){
            navigate("/login");
            return;
        }

        const data = await response.json();
        setMaterials(data);
        } catch(error){
            console.log(error);
        }finally{
            setLoading(false);
        }
    }
    getMaterials();
   })

    return(
        <>
         {loading ? <p>Loading...</p> : materials ? (
           <>
           {materials.map((material : any) => (
            <div key={material.id}>
            <h1>{material.id}</h1>
            {material?.picture && (
                <img src={`data:${material.pictureContentType};base64,${material.picture}`} width={"200px"} height={"200px"}/>
            )}
            <img src="`data`"/>
              {/* <img src={`data:${user.pictureContentType};base64,${user.picture}`} width={"200px"} height={"200px"}/>
                 )} */}
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

export default AllMaterials;