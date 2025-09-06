import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { FaClipboard } from "react-icons/fa"; // Import the clipboard icon

// Images
// import amazon from "../images/amazon.png"
// import flipkart from "../images/flipkart.jpg"
// import Youtube from "../images/Youtube.png"
// import website from "../images/website.png"
// import Dummy from "../images/Dummy.png"
// import LandingPage from "../images/LandingPage.png"

import amazon from "../assets/images/amazon.png"
import flipkart from "../assets/images/flipkart.jpg"
import Youtube from "../assets/images/Youtube.png"
import website from "../assets/images/website.png"
import Dummy from "../assets/images/Dummy.png"
import LandingPage from "../assets/images/LandingPage.png"
import ProfileImageBG from '../assets/images/Profilebackground.jpg'

function ProductCard({ product }) {
  const [generatedLink, setGeneratedLink] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [copyMessage, setCopyMessage] = useState(''); // To show confirmation when the link is copied
  const navigate = useNavigate();

  // Handle Generate Link button click
  const handleGenerateLink = async () => {
    setGeneratedLink(''); // Reset the generated link
    setError(''); // Clear any error message
    setLoading(true); // Start loading

    // const token = localStorage.getItem("token");
    const token = sessionStorage.getItem("token");
    if (!token) {
      navigate("/Authenticate");
      setLoading(false); // Stop loading as we are redirecting
      return;
    }

    try {
      console.log("Request sent for link generation...");
      console.log("Product:", product);

      const response = await axios.post(
        `${import.meta.env.VITE_API}/link/generate`,
        { productId: product.productId },
        {
          headers: {
            "Authorization": `Bearer ${token}`,
          },
        }
      );

      setGeneratedLink(response.data); // Set the generated link
    } catch (err) {
      setError("Failed to generate link. Please try again.");
      console.error("Error generating link:", err);
    } finally {
      setLoading(false); // Stop loading after the request is finished
    }
  };

  // Function to copy the generated link to clipboard
  const handleCopyLink = () => {
    if (generatedLink) {
      navigator.clipboard.writeText(generatedLink);
      setCopyMessage("Link copied to clipboard!");
      setTimeout(() => setCopyMessage(''), 3000); // Reset the copy message after 3 seconds
    }
  };

  // Conditionally set the image based on product type
  let productImage;
  switch (product.productType) {
    case "Product - Amazon":
      productImage = amazon;
      break;
    case "Product - Flipkart":
      productImage = flipkart;
      break;
    case "YouTube Video":
      productImage = Youtube;
      break;
    case "Website":
      productImage = website;
      break;
    case "Landing Page":
      productImage = LandingPage;
      break;
    case "Product":
    default:
      productImage = ProfileImageBG; // Default image
      break;
  }

  const handleProductDetail = () => {
    // console.log("image : ",productImage);
    navigate("/product-detail", {
      state: {
        productDetail: {
          ...product,  // Spread the existing product details
          image: productImage // Add the image name or path here
        }
      }
    });
  }

  return (
    <div className="max-w-sm rounded-lg shadow-lg bg-gray-200 overflow-hidden border border-gray-200 p-6 space-y-2">
      {/* Image at the top */}
      <div className="w-full h-48 bg-gray-300 rounded-lg mb-4">
        {/* Placeholder image */}
        <img
          src={productImage} // Placeholder image URL
          alt={product.productName}
          className="w-full h-full object-cover rounded-lg shadow-md hover:shadow-xl"
        />
      </div>

      {/* Product details */}
      <h3 className="text-md font-semibold text-gray-800 truncate">
        {product.productName}
      </h3>
      <p className="text-sm text-gray-600">Price per click: ${product.perClickPrice}</p>
      {/* <p className="text-sm text-gray-600">Product Type: {product.productType}</p> */}
      <p className="text-sm text-gray-600">Price per buy: ${product.perBuyPrice}</p>

      {/* Button to generate link */}
      <button
        onClick={handleGenerateLink}
        disabled={loading} // Disable button while loading
        className={`w-full py-2 font-semibold rounded-lg focus:outline-none focus:ring-2 ${loading
          ? "bg-gray-400 text-white cursor-not-allowed"
          : "bg-blue-500 text-white hover:bg-blue-600 focus:ring-blue-500"
          }`}
      >
        {loading ? "Generating..." : "Generate Link"}
      </button>

      <button
        onClick={handleProductDetail}
        className="w-full py-2 font-semibold rounded-lg focus:outline-none focus:ring-2 bg-blue-500 text-white hover:bg-blue-600 focus:ring-blue-500">
        View Details
      </button>

      {/* Show the generated link if it exists */}
      {generatedLink && (
        <div className="generated-link space-y-2 mt-4">
          <p className="text-sm text-gray-700">Generated Link:</p>
          <div className="flex items-center space-x-2">
            <span className="text-blue-500 truncate max-w-xs">{generatedLink}</span>
            {/* Copy button */}
            <button
              onClick={handleCopyLink}
              className="text-blue-500 hover:text-blue-700"
            >
              <FaClipboard className="w-5 h-5" />
            </button>
          </div>
        </div>
      )}

      {/* Show error message if link generation fails */}
      {error && <div className="text-red-600 text-sm mt-2">{error}</div>}

      {/* Show success message when the link is copied */}
      {copyMessage && (
        <div className="text-green-500 text-sm mt-2">{copyMessage}</div>
      )}
    </div>
  );
}

export default ProductCard;
