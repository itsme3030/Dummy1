import React from 'react';
import { FaHandshake, FaLink, FaDollarSign, FaStar, FaChartLine, FaUsers, FaMobileAlt } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import Footer from '../components/Footer';

const Home = () => {
  return (
    <div>
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 text-white text-center py-20">
        <h1 className="text-5xl font-extrabold mb-4">Unlock Passive Income with Top-Tier Products</h1>
        <p className="text-xl mb-6">Join our Affiliate Program and earn commissions by promoting products you love.</p>
        <Link to="/affiliate" className="bg-yellow-400 text-gray-900 py-3 px-8 rounded-full text-xl font-semibold hover:bg-yellow-500 transition duration-300">
          Get Started
        </Link>
      </section>

      {/* How It Works */}
      <section className="py-16 bg-gray-200">
        <h2 className="text-4xl font-bold text-center mb-12">How It Works</h2>
        <div className="max-w-6xl mx-auto grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 px-6">
          {/* Card 1: Register */}
          <div className="bg-white p-8 rounded-lg shadow-lg text-center transform transition duration-300 hover:scale-105">
            <FaUsers className="text-6xl text-indigo-600 mb-4" />
            <h3 className="text-2xl font-semibold mb-4">Register</h3>
            <p>Create your affiliate account in minutes and gain access to our dashboard.</p>
          </div>

          {/* Card 2: Promote */}
          <div className="bg-white p-8 rounded-lg shadow-lg text-center transform transition duration-300 hover:scale-105">
            <FaLink className="text-6xl text-green-600 mb-4" />
            <h3 className="text-2xl font-semibold mb-4">Promote</h3>
            <p>Share your unique links across blogs, social media, or your website.</p>
          </div>

          {/* Card 3: Earn */}
          <div className="bg-white p-8 rounded-lg shadow-lg text-center transform transition duration-300 hover:scale-105">
            <FaDollarSign className="text-6xl text-yellow-500 mb-4" />
            <h3 className="text-2xl font-semibold mb-4">Earn</h3>
            <p>Receive commissions for every sale made through your referrals.</p>
          </div>
        </div>
      </section>

      {/* Benefits Section */}
      <section className="py-16 bg-white">
        <h2 className="text-4xl font-bold text-center mb-12">Why Partner with Us?</h2>
        <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8 px-6">
          <div className="bg-gray-50 p-8 rounded-lg shadow-xl text-center">
            <FaChartLine className="text-6xl text-blue-600 mb-4" />
            <h3 className="text-2xl font-semibold mb-4">Competitive Commissions</h3>
            <p>Benefit from industry-leading commission rates that maximize your earnings.</p>
          </div>
          <div className="bg-gray-50 p-8 rounded-lg shadow-xl text-center">
            <FaMobileAlt className="text-6xl text-purple-600 mb-4" />
            <h3 className="text-2xl font-semibold mb-4">Real-Time Tracking</h3>
            <p>Monitor your performance with our state-of-the-art tracking tools.</p>
          </div>
          <div className="bg-gray-50 p-8 rounded-lg shadow-xl text-center">
            <FaStar className="text-6xl text-yellow-500 mb-4" />
            <h3 className="text-2xl font-semibold mb-4">Exclusive Products</h3>
            <p>Promote a curated selection of high-quality products that resonate with your audience.</p>
          </div>
        </div>
      </section>

      {/* FAQ Section */}
      <section className="py-20 bg-gray-200">
        <div className="max-w-7xl mx-auto px-6">
          <h2 className="text-4xl font-bold text-center mb-12">Frequently Asked Questions</h2>
          <div className="space-y-8">
            {[
              { question: "How do I get paid?", answer: "You will receive payments via bank transfer or RazorPay, depending on your preferences." },
              { question: "What are the commission rates?", answer: "Our rate is 50%." },
              { question: "Is there a minimum payout amount?", answer: "Yes, the minimum payout is $50." }
            ].map((faq, index) => (
              <div key={index} className="bg-gray-50 p-6 rounded-lg shadow-lg">
                <h3 className="text-2xl font-semibold">{faq.question}</h3>
                <p className="text-gray-600 mt-2">{faq.answer}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials Section */}
      <section className="py-16 bg-gray-50">
        <h2 className="text-4xl font-bold text-center mb-12">What Our Affiliates Say</h2>
        <div className="max-w-6xl mx-auto flex flex-wrap justify-center gap-8 px-6">
          <div className="bg-white p-8 rounded-lg shadow-lg w-80">
            <p className="text-xl mb-4">"This program has transformed my blogging income. The support and products are top-notch!"</p>
            <p className="font-semibold">Alex Johnson</p>
            <p className="text-gray-500">Professional Blogger</p>
          </div>
          <div className="bg-white p-8 rounded-lg shadow-lg w-80">
            <p className="text-xl mb-4">"The real-time tracking tools have made it easy to optimize my strategies and boost earnings."</p>
            <p className="font-semibold">Samantha Lee</p>
            <p className="text-gray-500">Digital Marketer</p>
          </div>
          <div className="bg-white p-8 rounded-lg shadow-lg w-80">
            <p className="text-xl mb-4">"Partnering with this program was the best decision for my online business."</p>
            <p className="font-semibold">Michael Chen</p>
            <p className="text-gray-500">E-commerce Entrepreneur</p>
          </div>
        </div>
      </section>

      {/* Call to Action Section */}
      <section className="bg-indigo-600 text-white py-20 text-center">
        <h2 className="text-4xl font-bold mb-6">Start Your Affiliate Journey Today!</h2>
        <p className="text-xl mb-8">Join now and turn your influence into income by promoting products you believe in.</p>
        <Link to="/affiliate" className="bg-yellow-400 text-gray-900 py-3 px-8 rounded-full text-xl font-semibold hover:bg-yellow-500 transition duration-300">
          Join Now
        </Link>
      </section>

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default Home;
