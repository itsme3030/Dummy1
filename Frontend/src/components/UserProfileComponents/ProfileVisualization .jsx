import React, { useState, useEffect, useMemo } from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { FaCalendarAlt } from 'react-icons/fa';

// Register Chart.js components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const ProfileVisualization = ({ data }) => {
  const [selectedMonth, setSelectedMonth] = useState(null);
  const [filteredData, setFilteredData] = useState(null);

  // Helper function to format the date as YYYY-MM
  const formatDate = (date) => {
    return date ? date.split('T')[0].slice(0, 7) : null; // "YYYY-MM"
  };

  // Memoize monthly data to avoid recalculating on every render
  const monthlyData = useMemo(() => {
    const dataMap = {
      reviews: {},
      productsAdded: {},
      linkGenerated: {},
      transactions: {},
    };

    // Process reviews
    data.reviews.forEach((review) => {
      const month = formatDate(review.reviewDate);
      dataMap.reviews[month] = (dataMap.reviews[month] || 0) + 1;
    });

    // Process earnings (link generated)
    data.earnings.forEach((earning) => {
      const month = formatDate(earning.createdAt);
      dataMap.linkGenerated[month] = (dataMap.linkGenerated[month] || 0) + 1;
    });

    // Process payable amounts (products added)
    data.payableAmounts.forEach((payable) => {
      const month = formatDate(payable.createdAt);
      dataMap.productsAdded[month] = (dataMap.productsAdded[month] || 0) + 1;
    });

    // Process payments (transactions)
    data.payments.forEach((payment) => {
      const month = formatDate(payment.transactionDate);
      dataMap.transactions[month] = (dataMap.transactions[month] || 0) + 1;
    });

    return dataMap;
  }, [data]);

  // Get the list of months from all data categories (reviews, link generated, etc.)
  const allMonths = useMemo(() => {
    const months = [
      ...Object.keys(monthlyData.reviews),
      ...Object.keys(monthlyData.linkGenerated),
      ...Object.keys(monthlyData.productsAdded),
      ...Object.keys(monthlyData.transactions),
    ];
    return [...new Set(months)].sort().reverse(); // Get unique and sorted months
  }, [monthlyData]);

  // Set the latest month by default
  const latestMonth = allMonths[0];

  useEffect(() => {
    setSelectedMonth(latestMonth);
  }, [latestMonth]);

  useEffect(() => {
    if (selectedMonth) {
      setFilteredData({
        reviews: monthlyData.reviews[selectedMonth] || 0,
        productsAdded: monthlyData.productsAdded[selectedMonth] || 0,
        linkGenerated: monthlyData.linkGenerated[selectedMonth] || 0,
        transactions: monthlyData.transactions[selectedMonth] || 0,
      });
    }
  }, [selectedMonth, monthlyData]);

  // Chart data for the bar chart
  const chartData = {
    labels: ['Reviews', 'Products Added', 'Link Generated', 'Transactions'],
    datasets: [
      {
        label: `Data for ${selectedMonth}`,
        data: [
          filteredData?.reviews || 0,
          filteredData?.productsAdded || 0,
          filteredData?.linkGenerated || 0,
          filteredData?.transactions || 0,
        ],
        backgroundColor: [
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)',
          'rgba(255, 99, 132, 0.2)',
        ],
        borderColor: [
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)',
          'rgba(255, 99, 132, 1)',
        ],
        borderWidth: 1,
      },
    ],
  };

  return (
    <div className="p-6 bg-white shadow-lg rounded-lg">
      <h3 className="text-2xl font-semibold mb-4">Profile Visualization</h3>

      {/* Month Selection */}
      <div className="flex items-center mb-6">
        <FaCalendarAlt className="text-xl mr-2 text-gray-500" />
        <select
          className="border rounded-lg p-2"
          value={selectedMonth}
          onChange={(e) => setSelectedMonth(e.target.value)}
        >
          {allMonths.map((month) => (
            <option key={month} value={month}>
              {month}
            </option>
          ))}
        </select>
      </div>

      {/* Bar Chart */}
      <div className="mb-6">
        <Bar data={chartData}
          options={{
            responsive: true,  // Make chart responsive
            maintainAspectRatio: false, // Allow aspect ratio to change for responsiveness
            plugins: {
              legend: {
                position: 'top',
              },
              tooltip: {
                enabled: true,
              },
            },
          }} />
      </div>
    </div>
  );
};

export default ProfileVisualization;
