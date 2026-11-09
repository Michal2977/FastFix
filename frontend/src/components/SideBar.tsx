import { Link } from "react-router-dom";


function SideBar({logout,user,admin} : any){
    return(
         <div className="d-flex">
  <nav className="bg-dark text-white vh-100 p-3" style={{ width: "250px" }}>
    <div className="container-fluid d-flex flex-column align-items-start">
      <a className="navbar-brand text-white mb-4" href="#">
        Navbar
      </a>
      <button className="navbar-toggler mb-3"type="button" data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
        aria-expanded="false" aria-label="Toggle navigation">
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse show w-100" id="navbarSupportedContent">
        <ul className="nav flex-column w-100">
          <li className="nav-item">
            <Link to={"/account-information"}>Account Information</Link>
          </li>
          {admin && (
            <div>
             <li className="nav-item">
           <Link to={"/create-employee"}>CreateEmployee</Link>
          </li>
            </div>
          )}
         

          <li className="nav-item dropdown">
            <a className="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Dropdown
            </a>
            <ul className="dropdown-menu">
              <li><button className="btn btn-danger" type="button" onClick={logout}>Logout</button></li>
              <li><h1>{user?.firstName}</h1></li>
              <li><a className="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>

          <li className="nav-item">
            <a className="nav-link disabled" aria-disabled="true">
              Disabled
            </a>
          </li>
        </ul>

        <form className="d-flex mt-4" role="search">
          <input className="form-control me-2"  type="search"  placeholder="Search" aria-label="Search"/>
          <button className="btn btn-outline-success" type="submit">
            Search
          </button>
        </form>
      </div>
    </div>
  </nav>
</div>
    )
}

export default SideBar;