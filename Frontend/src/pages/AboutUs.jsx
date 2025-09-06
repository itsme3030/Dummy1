import React from 'react';

const AboutUs = () => {
  return (
    <div className="bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 py-16 px-8">
      <div className="container mx-auto text-center text-white">

        <div className="bg-white bg-opacity-50 flex flex-col items-center rounded-lg shadow-lg p-6 m-6 transform hover:scale-105 transition duration-300 ease-in-out text-gray-800">
          <div className="text-gray-800">
            <h1 className="text-2xl font-extrabold mb-6 animate__animated animate__fadeIn">About AffiliateAdda</h1>
            <p className="text-xl mb-8 max-w-3xl mx-auto animate__animated animate__fadeIn">
              Welcome to <span className="font-bold text-yellow-300">AffiliateAdda</span>, an innovative affiliate marketing platform that connects sellers and affiliates, providing a seamless way to promote and earn commissions from products. Whether you're a seller wanting to list your products or an affiliate looking to promote, AffiliateAdda offers an easy-to-use platform for both.
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 px-4">
          <div className="flex flex-col items-center rounded-lg shadow-lg p-6 transform hover:shadow-gray-800">
            <h2 className="text-3xl font-semibold text-white mb-4">Our Mission</h2>
            <p className="text-lg text-white">
              Our mission at AffiliateAdda is to simplify affiliate marketing for both sellers and affiliates. We aim to provide a platform where sellers can easily list their products and set commission rates, while affiliates can promote and earn commissions seamlessly. We prioritize transparency, easy tracking, and real-time statistics, making affiliate marketing more accessible and rewarding for everyone involved.
            </p>
          </div>

          <div className="flex flex-col items-center rounded-lg shadow-lg p-6 transform hover:shadow-gray-800">
            <h2 className="text-3xl font-semibold text-white mb-4">Our Features</h2>
            <ul className="list-disc list-inside text-lg text-white">
              <li>Product Promotion & Affiliate Program: Sellers can add products, set commission rates, and track affiliate performance.</li>
              <li>Easy and Fast Login: OAuth 2.0 & JWT for easy login and secure authentication.</li>
              <li>Product & Affiliate Link Deactivation: Both sellers and affiliates can deactivate products or affiliate links at any time.</li>
              <li>User Profile Analytics: Track your earnings, transactions, affiliate links, and monthly/yearly performance through interactive charts.</li>
            </ul>
          </div>
        </div>

        <div className="bg-white bg-opacity-50 flex flex-col items-center rounded-lg shadow-lg p-6 m-6 transform hover:shadow-gray-800">
          <div className="mt-16">
            <h3 className="text-3xl font-semibold mb-6 text-gray-800">Our Team</h3>
            <p className="text-lg text-gray-800">
              Our team at AffiliateAdda is composed of experts in affiliate marketing, technology, and customer support. We're passionate about making affiliate marketing accessible to everyone. Whether it's through enhancing our platform’s functionality, providing personalized support, or offering insightful resources, we are always working hard to bring you the best experience.
            </p>
          </div>
        </div>


        <div className="mt-16 p-6 rounded-lg transition duration-1000 hover:bg-gray-800 bg-opacity-50">
          <h3 className="text-3xl font-semibold mb-6 text-white">Join the AffiliateAdda Family</h3>
          <p className="text-lg text-gray-200">
            Are you ready to be a part of the affiliate revolution? Whether you're a seller looking to expand your reach or an affiliate eager to earn commissions, AffiliateAdda is the perfect platform for you. Sign up today and start your journey towards success!
          </p>
        </div>
      </div>
    </div>
  );
};

export default AboutUs;
