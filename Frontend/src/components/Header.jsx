import React, { useState } from "react";
import { Link } from "react-router-dom";
import { FaBars, FaTimes, FaHome, FaPlus, FaUserAlt, FaSignOutAlt, FaHandshake, FaSignInAlt, FaInfoCircle, FaEnvelope } from "react-icons/fa";

function Header({ role }) {
  const [menuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => setMenuOpen(!menuOpen);

  return (
    <header className="bg-gray-100 text-gray-800 py-4 shadow-xl sticky top-0 z-50 w-full">
      <div className="max-w-7xl mx-auto flex justify-between items-center px-6">
        {/* Left Side - Website Name */}
        <h1 className="text-xl font-bold tracking-wide hover:scale-105 transition-transform">
          AffiliateAdda
        </h1>

        {/* Mobile Menu Button */}
        <button className="md:hidden text-gray-800 text-2xl" onClick={toggleMenu}>
          {menuOpen ? <FaTimes /> : <FaBars />}
        </button>

        {/* Centered Navigation */}
        <nav className={`md:flex text-sm flex-grow justify-center absolute md:static top-16 left-0 w-full md:w-auto bg-gray-500 md:bg-transparent md:flex-row flex-col items-center md:items-center ${menuOpen ? "flex" : "hidden"}`}>
          {(role === "USER" || role === "Guest") && (
            [
              { to: "/", icon: <FaHome />, label: "Home" },
              { to: "/affiliate", icon: <FaHandshake />, label: "Affiliate" },
              { to: "/add-product", icon: <FaPlus />, label: "Promote" },
              { to: "/AboutUs", icon: <FaInfoCircle />, label: "AboutUs" },
              { to: "/Contact", icon: <FaEnvelope />, label: "Contact" }
            ].map(({ to, icon, label }) => (
              <Link
                key={to}
                to={to}
                className="flex items-center gap-1 px-3 py-2 text-sm rounded-lg md:bg-gray-100 hover:bg-gray-300 transition-all"
              >
                {icon} <span>{label}</span>
              </Link>
            ))
          )}
        </nav>

        {/* Right Side - Authentication/Profile/Logout */}
        <nav className="flex items-center">
          {role === "Guest" ? (
            <Link
              to="/Authenticate"
              className="flex items-center gap-1 px-3 py-2 text-sm rounded-lg bg-gray-100 hover:bg-gray-300 transition-all"
            >
              <FaSignInAlt /> <span>Login</span>
            </Link>
          ) : role === "USER" ? (
            [
              { to: "/user-profile", icon: <FaUserAlt /> },
              { to: "/logout", icon: <FaSignOutAlt />, label: "Logout" }
            ].map(({ to, icon, label }) => (
              <Link
                key={to}
                to={to}
                className="flex items-center gap-1 px-3 py-2 text-sm rounded-lg bg-gray-100 hover:bg-gray-300 transition-all"
              >
                {icon} <span>{label}</span>
              </Link>
            ))
          ) : (
            [
              { to: "/admin-home", icon: <FaHome />, label: "Home" },
              { to: "/logout", icon: <FaSignOutAlt />, label: "Logout" }
            ].map(({ to, icon, label }) => (
              <Link
                key={to}
                to={to}
                className="flex items-center gap-1 px-3 py-2 text-sm rounded-lg hover:bg-gray-100 transition-all"
              >
                {icon} <span>{label}</span>
              </Link>
            ))
          )}
        </nav>
      </div>
    </header>
  );
}

export default Header;
