import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

function Logout({ setRole }) {
    const navigate = useNavigate();

    useEffect(()=>{
        handleLogout();
    },[]);

    const handleLogout = async () => {
        try {
            const response = await axios.post(`${import.meta.env.VITE_API}/auth/logout`);
            console.log(response.data); // Success message
            // Clear any stored tokens or user data
            // localStorage.removeItem("token");
            sessionStorage.removeItem("token");
            sessionStorage.clear();

            // Optional: Force a reload to clear cached pages
            //window.location.reload();

            setRole("Guest");

            // Redirect to login or home page
            navigate("/");

        } catch (error) {
            console.error("Logout failed:", error);
        }
    };

    return (
    //   <div className="flex justify-center items-center min-h-screen">
    //     <button
    //         onClick={handleLogout}
    //         className="p-3 bg-teal-600 text-white rounded-lg text-lg font-semibold hover:bg-teal-700 transition duration-300 focus:outline-none focus:ring-2 focus:ring-teal-500"
    //     >
    //         Logout
    //     </button>
    // </div>
    <></>
    );
}

export default Logout;
