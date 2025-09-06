const AdminSummaryCard = ({ type, title, data, totalAmount }) => {
  return (
    <>
      <div className="bg-white p-6 rounded-lg shadow-md border border-gray-200">
        <h3 className="text-xl font-semibold text-gray-700 mb-4">{title}</h3>
        <div className="text-3xl font-bold text-gray-800">
          {data ? (
            <span className="text-yellow-800">{data}</span>
          ) : (
            <span className={
              type === 'earnings' ? 'text-blue-600' :
              type === 'payable' ? 'text-blue-600' :
              type === 'website' ? 'text-green-800' :
              'text-gray-800' // Default color
          }>{totalAmount ? totalAmount.toFixed(2) : "N/A"}$</span>
          )}
        </div>
        {type === "earnings" && (
          <p className="text-sm text-gray-500 mt-2">Total Earnings for All Users</p>
        )}
        {type === "payable" && (
          <p className="text-sm text-gray-500 mt-2">Total Payable Amount for All Users</p>
        )}
        {type === "users" && (
          <p className="text-sm text-gray-500 mt-2">Total Number of Users in the Platform</p>
        )}
        {type === "website" && (
          <p className="text-sm text-gray-500 mt-2">Total Earnings from 50% commission</p>
        )}
      </div>
    </>
  );
};

export default AdminSummaryCard;
