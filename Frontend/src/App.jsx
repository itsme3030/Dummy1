import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useEffect, useState } from "react";

import GoogleAuthentication from './components/GoogleAuthentication';
import Logout from "./components/Logout";
import Header from "./components/Header";
import ProductDetail from "./components/ProductDetail";
import UserProfile from "./components/UserProfileComponents/UserProfile";

import Home from "./pages/Home";
import AdminHome from "./pages/AdminHome";  // Admin-specific Home page
import AddProduct from "./pages/AddProduct";
import Affiliate from "./pages/Affiliate";
import Payments from "./pages/Payments";




import { jwtDecode } from "jwt-decode";
import AboutUs from "./pages/AboutUs";
import Contact from "./pages/Contact";

function App() {
    const [role, setRole] = useState("Guest");
    // const [username, setUsername] = useState("");

    // Check for role or JWT token in sessionStorage 
    useEffect(() => {
        const token = sessionStorage.getItem("token");
        if (token) {
            const decodedToken = jwtDecode(token);
            setRole(decodedToken.role);
            console.log("role set as (Inside App.jsx):" + decodedToken.role);
        } else {
            setRole("Guest");
        }
    }, []);

    // Debugging: Check role value
    console.log("Current Role:", role);

    return (
        <BrowserRouter>
            <Header role={role} />
            <Routes>
                {/* For User Role */}
                {(role === "USER" || role === "Guest") ? (
                    <>
                        <Route path="/" element={<Home />} />
                        <Route path="/affiliate" element={<Affiliate />} />
                        <Route path="/product-detail" element={<ProductDetail/>} />
                        <Route path="/Authenticate" element={<GoogleAuthentication setRole={setRole} />} />
                        <Route path="/AboutUs" element={<AboutUs/>} />
                        <Route path="/Contact" element={<Contact/>} />
                        <Route path="/logout" element={<Logout setRole={setRole}/>} />
                        <Route path="/add-product" element={<AddProduct />} />
                        <Route path="/user-profile" element={<UserProfile />} />
                        <Route path="/payments" element={<Payments />} />
                        <Route path="/admin-home" element={<Navigate to="/" />} />
                        {/* Optional: A route for handling 404 (not found) */}
                        {/* <Route path="*" element={<NotFound />} /> */}
                    </>
                ) : 
                null
                }

                {/* For Admin Role */}
                {role === "ADMIN" ? (
                    <>
                        <Route path="/admin-home" element={<AdminHome />} />
                        <Route path="/logout" element={<Logout setRole={setRole}/>} />
                        <Route path="/" element={<Navigate to="/admin-home" />} />
                        <Route path="/user-profile" element={<Navigate to="/admin-home" />} />
                        <Route path="/add-product" element={<Navigate to="/admin-home" />} />
                        <Route path="/Authenticate" element={<Navigate to="/admin-home" />} />
                    </>
                ) : 
                null
                }

                {/* Redirect if no role is defined */}
                {/* <Route path="*" element={<Navigate to="/" />} /> */}

            </Routes>
        </BrowserRouter>
    );
}

export default App;
