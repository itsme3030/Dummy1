import React, { useState } from "react";
import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import { FaSpinner } from "react-icons/fa"; // Import spinner icon for loading
import { jwtDecode } from "jwt-decode";

const GoogleAuthentication = ({ setRole }) => {
    const [loading, setLoading] = useState(false); // State for loading
    const [errorMessage, setErrorMessage] = useState(''); // State for error messages
    const navigate = useNavigate();

    // Success handler
    const handleGoogleSuccess = async (response) => {
        setLoading(true); // Show loading spinner
        setErrorMessage(''); // Clear any previous error message
        const token = response.credential;

        try {
            const res = await fetch(`${import.meta.env.VITE_API}/auth/google`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ token }),
            });

            if (!res.ok) {
                throw new Error("Failed to authenticate with Google");
            }

            const data = await res.json();
            // localStorage.setItem("token", data.token);
            sessionStorage.setItem("token", data.token);
            console.log("JWT Token:", data.token);

            // Decode the JWT token
            const decodedToken = jwtDecode(data.token);
            const role = decodedToken.role;
            console.log("JWT Token role :", role);

            const username = decodedToken.sub;
            console.log("username : ", username);
            sessionStorage.setItem("username", username);

            // navigate("/");

            // Update the role state in parent component
            setRole(role);

            // Redirect based on user role
            if (role === "ADMIN") {
                console.log("redirect to........admin-home");
                navigate("/admin-home");  // Redirect to admin view
            } else {
                console.log("redirect to........home");
                navigate("/");  // Redirect to user home page
            }

        } catch (error) {
            setErrorMessage('Authentication failed, please try again later.');
            console.error("Authentication error:", error);
        } finally {
            setLoading(false); // Hide loading spinner
        }
    };

    // Failure handler
    const handleGoogleFailure = (error) => {
        setErrorMessage('Google Login Failed, please try again.');
        console.error("Google Login Failed", error);
    };

    return (
        <GoogleOAuthProvider
            clientId={import.meta.env.VITE_CLIENTID || ""}
        >
            <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600">
                <div className="bg-white p-10 rounded-xl shadow-xl max-w-sm w-full">
                    <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">
                        Welcome to Affiliat Adda
                    </h2>
                    <div className="text-center">
                        {loading ? (
                            <button
                                disabled
                                className="bg-blue-500 text-white w-full py-2 rounded-lg flex justify-center items-center disabled:opacity-50"
                            >
                                <FaSpinner className="animate-spin w-5 h-5 mr-2" />
                                Authenticating...
                            </button>
                        ) : (
                            <GoogleLogin
                                onSuccess={handleGoogleSuccess}
                                onFailure={handleGoogleFailure}
                                buttonText="Login with Google"
                                theme="outline" // Optional: You can customize the button style further
                                shape="rectangular" // Optional: Make button rectangular for a cleaner look
                            />
                        )}

                        {errorMessage && (
                            <div className="mt-4 text-red-600 text-sm">{errorMessage}</div>
                        )}
                    </div>
                </div>
            </div>
        </GoogleOAuthProvider>
    );
};

export default GoogleAuthentication;
