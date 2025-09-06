import React, { useState } from "react";
import { FaArrowDown, FaArrowUp, FaCheckCircle, FaTrash, FaCopy } from "react-icons/fa";

const TransactionHistory = ({ title, transactions }) => {
  const [showDetails, setShowDetails] = useState(false);

  const payments = transactions.filter((t) => t.transactionType === "PAYMENT");
  const withdrawals = transactions.filter((t) => t.transactionType === "WITHDRAWAL");

  const handleDetailsClick = () => {
    setShowDetails(!showDetails);
  };

  return (
    <div className="bg-white p-6 rounded-xl shadow-xl hover:shadow-2xl transition-all duration-300 ease-in-out transform hover:scale-105 mb-6">
      <div className="flex justify-between items-center mb-6">
        <h3 className="text-2xl font-semibold text-gray-800">{title || "Transaction History"}</h3>
      </div>

      {/* Show Details Toggle Button */}
      <button
        onClick={handleDetailsClick}
        className="flex items-center justify-center text-blue-600 hover:text-blue-800 transition duration-300 mb-4"
      >
        {showDetails ? (
          <>
            <FaArrowUp className="mr-2" />
            Hide Details
          </>
        ) : (
          <>
            <FaArrowDown className="mr-2" />
            Show Details
          </>
        )}
      </button>

      {/* Transaction Details */}
      {showDetails && (
        <div className="space-y-6">
          {/* Payments Section */}
          <div>
            <h4 className="text-lg font-semibold text-green-600 mb-4">Payments</h4>
            {payments.length > 0 ? (
              <div className="max-h-[300px] overflow-y-auto pr-4"> {/* Scrollable area */}
                <ul className="space-y-4">
                  {payments.map((t) => (
                    <li key={t.transactionId} className="flex justify-between items-center py-2 border-b">
                      <div>
                        <p className="text-gray-700 text-sm">Amount: ${t.amount.toFixed(2)}</p>
                        <p className="text-gray-500 text-sm">Date: {t.transactionDate}</p>
                        <p className="text-gray-500 text-sm">Status: {t.status}</p>
                      </div>
                    </li>
                  ))}
                </ul>
              </div>
            ) : (
              <p>No payments recorded.</p>
            )}
          </div>

          {/* Withdrawals Section */}
          <div>
            <h4 className="text-lg font-semibold text-red-600 mb-4">Withdrawals</h4>
            {withdrawals.length > 0 ? (
              <div className="max-h-[300px] overflow-y-auto pr-4"> {/* Scrollable area */}
                <ul className="space-y-4">
                  {withdrawals.map((t) => (
                    <li key={t.transactionId} className="flex justify-between items-center py-2 border-b">
                      <div>
                        <p className="text-gray-700 text-sm">Amount: ${t.amount.toFixed(2)}</p>
                        <p className="text-gray-500 text-sm">Date: {t.transactionDate}</p>
                        <p className="text-gray-500 text-sm">Status: {t.status}</p>
                      </div>
                    </li>
                  ))}
                </ul>
              </div>
            ) : (
              <p>No withdrawals recorded.</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default TransactionHistory;
