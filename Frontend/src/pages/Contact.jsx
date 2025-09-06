import React, { useState } from 'react';

const Contact = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: '',
  });

  const [formStatus, setFormStatus] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Simulate form submission (you can integrate with an API here)
    setFormStatus('Thank you for reaching out! We will get back to you soon.');
    setFormData({ name: '', email: '', message: '' });
  };

  return (
    <div className="bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 py-6 px-8">
      <div className="container mx-auto text-center text-white">
        <div className="bg-white bg-opacity-50 flex flex-col items-center rounded-lg shadow-lg p-6 m-6 transform hover:scale-105 transition duration-300 ease-in-out text-gray-800">
          <h1 className="text-2xl font-extrabold mb-2 text-center animate__animated animate__fadeIn">
            Contact Us
          </h1>

          <p className="text-lg mb-4 max-w-3xl mx-auto text-center leading-relaxed animate__animated animate__fadeIn">
            We value your input and are here to assist you! Whether you have a question, need technical support, have some feedback, or just want to say hello, we’d love to hear from you. Simply fill out the contact form below with your details and message, and a member of our team will get back to you as soon as possible.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-2 px-4">
          {/* Contact Form */}
          <div className="bg-white bg-opacity-50 max-w-lg rounded-lg shadow-xl p-2 space-y-6 transform hover:shadow-gray-800">
            <form onSubmit={handleSubmit}>
              <div className="mb-4">
                <label className="block text-gray-800 text-lg mb-2" htmlFor="name">
                  Your Name
                </label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 transition"
                  required
                />
              </div>

              <div className="mb-4">
                <label className="block text-gray-800 text-lg mb-2" htmlFor="email">
                  Your Email
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 transition"
                  required
                />
              </div>

              <div className="mb-4">
                <label className="block text-gray-800 text-lg mb-2" htmlFor="message">
                  Your Message
                </label>
                <textarea
                  id="message"
                  name="message"
                  value={formData.message}
                  onChange={handleChange}
                  className="w-full px-4 py-3 border text-gray-800 border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 transition"
                  rows="5"
                  required
                ></textarea>
              </div>

              <button
                type="submit"
                className="w-full bg-gradient-to-r from-purple-500 to-indigo-700 text-white font-semibold py-3 rounded-lg hover:bg-indigo-700 transition duration-300 ease-in-out"
              >
                Send Message
              </button>
            </form>

            {/* Status Message */}
            {formStatus && (
              <div className="mt-6 text-green-500 font-semibold">
                <p>{formStatus}</p>
              </div>
            )}
          </div>

          {/* Additional Contact Info */}
          <div className="mt-32 text-white">
            <h3 className="text-3xl font-semibold mb-6 text-gray-800">Contact Information</h3>
            <ul className="space-y-2 text-lg">
              <li>📍 Address: 123 Affiliate Street, Marketing City.</li>
              <li>📞 Phone: +91 1234567890</li>
              <li>📧 Email: contact@affiliateadda.com</li>
              <li className="text-xl">🤝 Connect: <a href="https://www.linkedin.com/in/yash-sojitra-918107256" target="_blank" className="text-black hover:text-white transition duration-300">Linkedin</a> | <a href="https://github.com/yash-sojitra-20" target="_blank" className="text-black hover:text-white transition duration-300">Github</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Contact;
