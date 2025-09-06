import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

import PayableYearlyLineChart from './PayableYearlyLineChart';

// Register the necessary ChartJS components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

function PayableVisualization({ payableAmounts, totalPayableAmount }) {
  const [selectedMonth, setSelectedMonth] = useState(null);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [chartData, setChartData] = useState(null);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());

  // console.log("Payable : ",payableAmounts);

  useEffect(() => {
    let clickCount = 0;
    let buyCount = 0;
    let payableAmountsSum = 0;

    const processPayableAmounts = (payableAmounts) => {
      let filteredPayableAmounts = payableAmounts;

      // Apply month filter if selected
      if (selectedMonth) {
        filteredPayableAmounts = payableAmounts.filter((payableAmount) =>
          payableAmount.monthlyTrackers.some((tracker) => tracker.month === selectedMonth)
        );
      }

      // Apply product filter if selected
      if (selectedProduct !== null) {
        filteredPayableAmounts = filteredPayableAmounts.filter((payableAmount) => payableAmount.productId === selectedProduct);
      }

      // Loop through the earnings and calculate the required data
      filteredPayableAmounts.forEach((payableAmount) => {
        if (selectedMonth) {
          payableAmount.monthlyTrackers.forEach((tracker) => {
            if (tracker.month === selectedMonth) {
              clickCount += tracker.count;
              buyCount += tracker.buyCount;
              payableAmountsSum += tracker.count * payableAmount.perClickPrice + tracker.buyCount * payableAmount.perBuyPrice;
            }
          });
        } else {
          // Default: Use the total counts and earnings
          clickCount += payableAmount.count;
          buyCount += payableAmount.buyCount;
          payableAmountsSum += payableAmount.count * payableAmount.perClickPrice + payableAmount.buyCount * payableAmount.perBuyPrice;
        }
      });

      setChartData({
        labels: ['Click Count', 'Buy Count', 'Payable Amount'],
        datasets: [
          {
            label: 'Payable Amount Visualization',
            data: [clickCount, buyCount, payableAmountsSum],
            backgroundColor: ['#4A90E2', '#50E3C2', '#F5A623'],
          },
        ],
      });
    };
    processPayableAmounts(payableAmounts);
  }, [payableAmounts, selectedMonth, selectedProduct]);

  const handleMonthChange = (event) => {
    setSelectedMonth(event.target.value);
  };

  const handleProductChange = (event) => {
    setSelectedProduct(Number(event.target.value));
  };

  const handleYearChange = (e) => {
    setSelectedYear(e.target.value);
  };

  // Generate month options dynamically
  const getMonthOptions = () => {
    if (!payableAmounts || payableAmounts.length === 0) {
      console.log("No payable amounts data available.");
      return [];
    }

    const months = Array.from(
      new Set(
        payableAmounts.flatMap((payableAmount) => {
          if (Array.isArray(payableAmount.monthlyTrackers)) {
            return payableAmount.monthlyTrackers.map((tracker) => tracker.month);
          } else {
            console.log("No monthly trackers found for", payableAmount.productName);
            return [];
          }
        })
      )
    );

    return months.map((month, index) => (
      <option key={index} value={month}>
        {month}
      </option>
    ));
  };

  // Generate product options dynamically
  const getProductOptions = () => {
    const products = Array.from(new Set(payableAmounts.map((payableAmount) => payableAmount.productId)));
    return products.map((pId) => {
      const product = payableAmounts.find((payableAmount) => payableAmount.productId === pId);
      return (
        <option key={pId} value={pId}>
          {product?.productName || 'Unknown Product'}
        </option>
      );
    });
  };

  return (
    <>
      <div className="bg-gray-100 rounded-xl p-4 md:p-6 flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
        <div className="container mx-auto p-6 bg-white shadow-lg rounded-lg w-full md:w-1/2">
          <h1 className="text-xl font-semibold mb-4">Payable Visualization</h1>
          
          {/* Year Selection Dropdown */}
          <div className="mb-4">
            <label htmlFor="year" className="text-sm">Select Year: </label>
            <select
              id="year"
              value={selectedYear}
              onChange={handleYearChange}
              className="border p-2 rounded-md ml-2 w-full md:w-auto"
            >
              {[2022, 2023, 2024, 2025].map((year) => (
                <option key={year} value={year}>{year}</option>
              ))}
            </select>
          </div>
          
          {/* Pass payableAmounts and selectedYear to the child component */}
          <PayableYearlyLineChart payableAmounts={payableAmounts} selectedYear={selectedYear} />
        </div>

        <div className="container mx-auto p-6 bg-white shadow-lg rounded-lg w-full md:w-1/2">
          <div className="mb-6 flex flex-col md:flex-row items-start md:items-center space-y-4 md:space-y-0 md:space-x-4 w-full">
            <select
              value={selectedMonth || ''}
              onChange={handleMonthChange}
              className="border p-2 rounded w-full md:w-auto"
            >
              <option value="">Select Month</option>
              {getMonthOptions()}
            </select>

            <select
              value={selectedProduct || ''}
              onChange={handleProductChange}
              className="border p-2 rounded w-full md:w-auto"
            >
              <option value="">Select Product</option>
              {getProductOptions()}
            </select>
          </div>

          <div className="mb-6">
            <h3 className="text-xl font-semibold">Total Payable Amount: ${totalPayableAmount}</h3>
          </div>

          {chartData && (
            <div className="bg-white p-6 shadow-lg rounded-md">
              <Bar data={chartData} options={{ responsive: true }} />
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default PayableVisualization;
