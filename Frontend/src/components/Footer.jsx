import React from 'react'

function Footer() {
  return (
    <>
      <footer className="bg-gray-800 text-white py-8">
        <div className="container mx-auto text-center">
          <p>&copy; 2025 AffiliateAdda. All rights reserved.</p>
          <p>Follow us on social media:
            <a href="https://www.linkedin.com/in/yash-sojitra-918107256" className="hover:text-white text-blue-400 transition duration-300 ml-2">Facebook</a> |
            <a href="https://www.linkedin.com/in/yash-sojitra-918107256" className="hover:text-white text-blue-400 transition duration-300 ml-2">Instagram</a> |
            <a href="https://www.linkedin.com/in/yash-sojitra-918107256" className="hover:text-white text-blue-400 transition duration-300 ml-2">Twitter</a>
          </p>
          {/* <hr/> */}
          <ul>
            <li>🤝 Connect: <a href="https://www.linkedin.com/in/yash-sojitra-918107256" target="_blank" className="hover:text-white text-blue-400 transition duration-300">Linkedin</a> | <a href="https://github.com/yash-sojitra-20" target="_blank" className="hover:text-white text-blue-400 transition duration-300">Github</a></li>
          </ul>
        </div>
      </footer>
    </>
  )
}

export default Footer