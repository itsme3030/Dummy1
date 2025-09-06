import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { FaSpinner } from 'react-icons/fa'; // Loading spinner
import Particles from 'react-tsparticles'; // Particle animation

import EarningsSummaryCard from './EarningsSummaryCard';
import PayableAmountSummaryCard from './PayableAmountSummaryCard';
import TransactionHistory from './TransactionHistory';
import OverallSummary from './OverallSummary';
import ProfileCard from './ProfileCard';
import EarningVisualization from './EarningVisualization';
import PayableVisualization from './PayableVisualization';
import UserReviews from './UserReviews';
import ProfileVisualization from './ProfileVisualization ';

const UserProfile = () => {
  const [profileData, setProfileData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = sessionStorage.getItem("token");
    if (!token) {
      navigate("/Authenticate");
      return;
    }

    axios
      .get(`${import.meta.env.VITE_API}/user/profile`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setProfileData(response.data);
        setLoading(false);
      })
      .catch((error) => {
        setError('Error fetching profile data');
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

  const handleMakePaymentClick = () => {
    navigate("/payments", {
      state: {
        Allpayments: profileData
      }
    });
  };

  return (
    <div className="relative w-full mx-auto p-6 md:p-8 lg:p-12">
      {/* Particle Background */}
      <Particles
        id="tsparticles"
        options={{
          background: {
            color: {
              value: "#f0f0f0", // Light gray background
            },
          },
          particles: {
            number: {
              value: 60,
              density: {
                enable: true,
                value_area: 800,
              },
            },
            color: {
              value: "#000000", // Black particles
            },
            shape: {
              type: "circle",
            },
            opacity: {
              value: 0.5,
              random: true,
              anim: {
                enable: true,
                speed: 1,
                opacity_min: 0.1,
                sync: false,
              },
            },
            size: {
              value: 4,
              random: true,
              anim: {
                enable: true,
                speed: 2,
                size_min: 0.1,
                sync: false,
              },
            },
            links: {
              enable: true,
              distance: 150,
              color: "#000000",
              opacity: 0.4,
              width: 1,
            },
            move: {
              enable: true,
              speed: 1,
              direction: "none",
              random: true,
              straight: false,
              out_mode: "out",
              attract: {
                enable: false,
                rotateX: 600,
                rotateY: 1200,
              },
            },
          },
          interactivity: {
            detect_on: "canvas",
            events: {
              onhover: {
                enable: true,
                mode: "repulse",
              },
              onclick: {
                enable: true,
                mode: "push",
              },
              resize: true,
            },
            modes: {
              grab: {
                distance: 400,
                line_linked: {
                  opacity: 1,
                },
              },
              bubble: {
                distance: 400,
                size: 40,
                duration: 2,
                opacity: 8,
                speed: 3,
              },
              repulse: {
                distance: 200,
                duration: 0.4,
              },
              push: {
                particles_nb: 4,
              },
              remove: {
                particles_nb: 2,
              },
            },
          },
          retina_detect: true,
        }}
      />

      {/* ProfileCard & OverallSummary side by side */}
      <div className="flex flex-col md:flex-row gap-6 md:gap-8 w-full relative z-10">
        <div className="w-full md:w-1/3">
          <ProfileCard userDetail={profileData.userDetail} />
        </div>
        <div className="w-full md:w-2/3">
          <OverallSummary profileData={profileData} />
        </div>
      </div>

      {/* Profile Visualization Component */}
      <div className="mt-8 relative z-10">
        <ProfileVisualization data={profileData} />
      </div>
      
      {/* UserReviews */}
      <div className="mt-8 relative z-10">
        <UserReviews reviews={profileData.reviews} />
      </div>

      {/* EarningsSummaryCard & EarningVisualization in a vertical layout */}
      <div className="mt-8 relative z-10">
        <EarningsSummaryCard
          type="earnings"
          title="Earnings Summary"
          data={profileData.earnings}
          totalAmount={profileData.totalEarnings}
        />
        <EarningVisualization
          earnings={profileData.earnings}
          totalEarnings={profileData.totalEarnings}
        />
      </div>

      {/* PayableAmountSummaryCard & PayableVisualization in a vertical layout */}
      <div className="mt-8 relative z-10">
        <PayableAmountSummaryCard
          type="payable"
          title="Payable Amount Summary"
          data={profileData.payableAmounts}
          totalAmount={profileData.totalPayableAmount}
        />
        <PayableVisualization
          payableAmounts={profileData.payableAmounts}
          totalPayableAmount={profileData.totalPayableAmount}
        />
      </div>

      {/* TransactionHistory */}
      <div className="mt-8 relative z-10">
        <TransactionHistory transactions={profileData.payments} title="Transaction Summary" />
      </div>

      {/* Make Payment Button */}
      <div className="flex justify-center mt-6 relative z-10">
        <button
          onClick={handleMakePaymentClick}
          className="bg-blue-600 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-blue-800 transition"
        >
          Make Payment
        </button>
      </div>
    </div>
  );
};

export default UserProfile;
