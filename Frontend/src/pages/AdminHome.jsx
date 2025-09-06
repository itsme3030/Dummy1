import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import AdminSummaryCard from '../components/AdminSummaryCard';
import { FaSpinner, FaTrash, FaTimesCircle, FaCheckCircle } from 'react-icons/fa'; // Loading spinner

const AdminHome = () => {
  const [adminData, setAdminData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null); // New state for success messages
  const [token, setToken] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedToken = sessionStorage.getItem("token");
    console.log("sessionStorage token : ", storedToken);
    if (!storedToken) {
      navigate("/Authenticate");
      return;
    }
    setToken(storedToken);

    axios
      .get(`${import.meta.env.VITE_API}/admin/home`, {
        headers: {
          "Authorization": `Bearer ${storedToken}`,
        },
      })
      .then((response) => {
        setAdminData(response.data);
        setLoading(false);
      })
      .catch((error) => {
        setError('Error fetching admin data'); // Display error if data fetch fails
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="text-lg text-gray-600 flex items-center">
          <FaSpinner className="animate-spin mr-2 text-blue-500" /> Loading...
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="text-lg text-red-600">{error}</div>
      </div>
    );
  }

  // Handle Deactivate User
  const handleDeactivateUser = (userId) => {
    console.log("User Deactivated with ID:", userId);
    if (!token) {
      navigate("/Authenticate");
      return;
    }
    const url = `${import.meta.env.VITE_API}/admin/deactivateUser/${userId}`;
    axios
      .post(url, {}, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      })
      .then(response => {
        console.log('Deactivation response:', response.data);
        setSuccessMessage('User deactivated successfully!'); // Show success message
        setTimeout(() => window.location.reload(), 2000); // Refresh after 2 seconds
      })
      .catch(error => {
        console.error('Error deactivating:', error);
        setError('Failed to deactivate user. Please try again later.'); // Display error message
      });
  };

  // Handle Activate User
  const handleActivateUser = (userId) => {
    console.log("User Activated with ID:", userId);
    if (!token) {
      navigate("/Authenticate");
      return;
    }
    const url = `${import.meta.env.VITE_API}/admin/activateUser/${userId}`;
    axios
      .post(url, {}, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      })
      .then(response => {
        console.log('Activation response:', response.data);
        setSuccessMessage('User activated successfully!'); // Show success message
        setTimeout(() => window.location.reload(), 2000); // Refresh after 2 seconds
      })
      .catch(error => {
        console.error('Error activating:', error);
        setError('Failed to activate user. Please try again later.'); // Display error message
      });
  };

  return (
    <div className="container mx-auto p-6 md:p-8 lg:p-12">
      <h2 className="text-4xl font-semibold mb-8 text-center text-gray-800 tracking-wide">
        Admin Dashboard
      </h2>

      {/* Show Success Message if exists */}
      {successMessage && (
        <div className="flex justify-center items-center mb-4">
          <div className="text-lg text-green-600">{successMessage}</div>
        </div>
      )}

      {/* Show Error Message if exists */}
      {error && (
        <div className="flex justify-center items-center mb-4">
          <div className="text-lg text-red-600">{error}</div>
        </div>
      )}

      <div className="grid gap-6 mb-8">
        {/* Website Earnings */}
        <AdminSummaryCard
          type="website"
          title="Total Website Earnings"
          totalAmount={adminData.websiteEarnings}
        />
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 mb-8">
        {/* Total Users Card */}
        <AdminSummaryCard
          type="users"
          title="Total Users"
          data={adminData.totalUsers}
        />

        {/* Total Earnings Card */}
        <AdminSummaryCard
          type="earnings"
          title="Total Earnings"
          totalAmount={adminData.totalEarnings}
        />

        {/* Total Payable Amount Card */}
        <AdminSummaryCard
          type="payable"
          title="Total Payable Amount"
          totalAmount={adminData.totalPayableAmount}
        />
      </div>

      <h3 className="text-2xl font-semibold mb-4 text-center text-gray-800">
        Users Overview
      </h3>

      {/* Users List Section */}
      <div className="overflow-x-auto">
        <table className="min-w-full table-auto border-collapse">
          <thead>
            <tr className="bg-gray-200 text-gray-700">
              <th className="py-3 px-6 text-left">Username</th>
              <th className="py-3 px-6 text-left">Total Earnings</th>
              <th className="py-3 px-6 text-left">Total Payable Amount</th>
              <th className="py-3 px-6 text-left">Action</th>
            </tr>
          </thead>
          <tbody>
            {adminData.users.map((user) => (
              <tr key={user.userId} className="border-t border-gray-200">
                <td className="py-3 px-6">{user.username}</td>
                <td className="py-3 px-6">{user.totalEarnings.toFixed(2)}</td>
                <td className="py-3 px-6">{user.totalPayableAmount.toFixed(2)}</td>
                <td className="py-3 px-6 text-center">
                  {user.active ? (
                    <button
                      onClick={() => handleDeactivateUser(user.userId)}
                      className="text-red-600 hover:text-red-800"
                    >
                      <FaTrash /> Deactivate
                    </button>
                  ) : (
                    <button
                      onClick={() => handleActivateUser(user.userId)}
                      className="text-blue-600 hover:text-blue-800"
                    >
                      <FaCheckCircle /> Activate
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminHome;
