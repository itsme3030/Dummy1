import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

import EarningYearlyLineChart from './EarningYearlyLineChart';

// Register the necessary ChartJS components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const EarningVisualization = ({ earnings, totalEarnings }) => {
  const [selectedMonth, setSelectedMonth] = useState(null);
  const [selectedTracker, setSelectedTracker] = useState(null);
  const [chartData, setChartData] = useState(null);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());

  // Filter earnings based on selected month and tracker
  useEffect(() => {
    let clickCount = 0;
    let buyCount = 0;
    let earningsSum = 0;

    const processEarnings = (earnings) => {
      let filteredEarnings = earnings;

      // Apply month filter if selected
      if (selectedMonth) {
        filteredEarnings = earnings.filter((earning) =>
          earning.monthlyTrackers.some((tracker) => tracker.month === selectedMonth)
        );
      }

      // Apply tracker filter if selected
      if (selectedTracker !== null) {
        filteredEarnings = filteredEarnings.filter((earning) => earning.tId === selectedTracker);
      }

      // Loop through the earnings and calculate the required data
      filteredEarnings.forEach((earning) => {
        if (selectedMonth) {
          earning.monthlyTrackers.forEach((tracker) => {
            if (tracker.month === selectedMonth) {
              clickCount += tracker.count;
              buyCount += tracker.buyCount;
              earningsSum += tracker.count * earning.perClickPrice + tracker.buyCount * earning.perBuyPrice;
            }
          });
        } else {
          // Default: Use the total counts and earnings
          clickCount += earning.count;
          buyCount += earning.buyCount;
          earningsSum += earning.count * earning.perClickPrice + earning.buyCount * earning.perBuyPrice;
        }
      });

      setChartData({
        labels: ['Click Count', 'Buy Count', 'Earnings'],
        datasets: [
          {
            label: 'Earnings Visualization',
            data: [clickCount, buyCount, earningsSum],
            backgroundColor: ['#4A90E2', '#50E3C2', '#F5A623'],
          },
        ],
      });
    };

    processEarnings(earnings);
  }, [earnings, selectedMonth, selectedTracker]);

  const handleMonthChange = (event) => {
    setSelectedMonth(event.target.value);
  };

  const handleTrackerChange = (event) => {
    setSelectedTracker(Number(event.target.value));
  };

  const handleYearChange = (e) => {
    setSelectedYear(e.target.value);
  };

  // Generate month options dynamically
  const getMonthOptions = () => {
    const months = Array.from(new Set(earnings.flatMap((earning) => earning.monthlyTrackers.map((tracker) => tracker.month))));
    return months.map((month, index) => (
      <option key={index} value={month}>
        {month}
      </option>
    ));
  };

  // Generate tracker options dynamically
  const getTrackerOptions = () => {
    const trackers = Array.from(new Set(earnings.map((earning) => earning.tId)));
    return trackers.map((trackerId) => {
      const tracker = earnings.find((earning) => earning.tId === trackerId);
      return (
        <option key={trackerId} value={trackerId}>
          {tracker?.productName || 'Unknown Product'}
        </option>
      );
    });
  };

  return (
    <>
      <div className="bg-gray-100 rounded-xl p-4 md:p-6 flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
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
              value={selectedTracker || ''}
              onChange={handleTrackerChange}
              className="border p-2 rounded w-full md:w-auto"
            >
              <option value="">Select Tracker</option>
              {getTrackerOptions()}
            </select>
          </div>

          <div className="mb-6">
            <h3 className="text-xl font-semibold">Total Earnings: ${totalEarnings}</h3>
          </div>

          {chartData && (
            <div className="bg-white p-6 shadow-lg rounded-md">
              <Bar data={chartData} options={{ responsive: true }} />
            </div>
          )}
        </div>

        <div className="container mx-auto p-6 bg-white shadow-lg rounded-lg w-full md:w-1/2">
          <h1 className="text-xl font-semibold mb-4">Earnings Visualization</h1>

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

          {/* Pass earnings and selectedYear to the child component */}
          <EarningYearlyLineChart earnings={earnings} selectedYear={selectedYear} />
        </div>
      </div>
    </>
  );
};

export default EarningVisualization;
