import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function AddProduct() {
  const [productName, setProductName] = useState("");
  const [productBaseurl, setProductBaseurl] = useState("");
  const [type, setProductType] = useState("");
  const [subType, setSubType] = useState("");  // State for subType
  const [perClickPrice, setPerClickPrice] = useState("");
  const [perBuyPrice, setPerBuyPrice] = useState(""); // State for perBuyPrice
  const [description, setDescription] = useState("");  // State for description
  const [shortDescription, setShortDescription] = useState(""); // Short description
  const [tags, setTags] = useState(""); // Tags as comma separated values
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const shouldShowPriceField =
    type === "Product - Amazon" ||
    type === "Product - Flipkart" ||
    type === "Product";

  // Hook to check authentication
  useEffect(() => {
    const token = sessionStorage.getItem("token");
    if (!token) {
      navigate("/Authenticate");
    }
  }, [navigate]);

  // Function to handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    const productData = {
      productName,
      productBaseurl,
      perClickPrice: parseFloat(perClickPrice),
      perBuyPrice: parseFloat(perBuyPrice),
      type,
      subType,  // Include subType in the data
      description,
      shortDescription,
      tags
    };

    console.log("Product data : ",productData);

    try {
      const token = sessionStorage.getItem("token");
      const response = await axios.post(`${import.meta.env.VITE_API}/product/add`, productData, {
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      });

      if (response.status === 200) {
        navigate("/"); // Redirect on success
      }
    } catch (err) {
      setError("Error adding product. Please try again.");
      console.error("Error adding product:", err);
    }
  };

  // Define subtypes based on the product type
  const subTypes = {
    "Product": ["Electronics", "Cloths", "Books", "Furniture", "Accessories"],
    "Product - Amazon": ["Electronics", "Cloths", "Books", "Home Appliances", "Sports Gear"],
    "Product - Flipkart": ["Electronics", "Cloths", "Books", "Furniture", "Toys"],
    "YouTube Video": ["Entertainment", "Gaming", "Music", "Sports", "Science and Technology"],
    "Website": ["Blog Website", "eCommerce Website", "Business Website", "Personal Website", "Portfolio Website", "Educational Website", "News Website", "Entertainment Website"],
    "Landing Page": ["Lead Generation", "Promotional", "Event", "Product Launch", "Services"]
  };

  return (
    <div className="max-w-6xl mx-auto p-6 bg-gray-100 rounded-lg shadow-md">
      <h2 className="text-2xl font-semibold text-center text-gray-700 mb-6">Add a New Product</h2>
      {error && <div className="bg-red-200 text-red-700 p-3 rounded-md mb-4">{error}</div>}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="productName" className="block text-sm font-medium text-gray-700">
            Product Name
          </label>
          <input
            type="text"
            id="productName"
            value={productName}
            onChange={(e) => setProductName(e.target.value)}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter product name"
          />
        </div>

        <div>
          <label htmlFor="productBaseurl" className="block text-sm font-medium text-gray-700">
            Product Base URL
          </label>
          <input
            type="text"
            id="productBaseurl"
            value={productBaseurl}
            onChange={(e) => setProductBaseurl(e.target.value)}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter product base URL"
          />
        </div>

        <div>
          <label htmlFor="productType" className="block text-sm font-medium text-gray-700">
            Product Type
          </label>
          <select
            id="productType"
            value={type}
            onChange={(e) => {
              setProductType(e.target.value);
              setSubType("");  // Reset subType when type changes
            }}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Select Product Type</option>
            <option value="Product - Amazon">Product - Amazon</option>
            <option value="Product - Flipkart">Product - Flipkart</option>
            <option value="Product">Other Products</option>
            <option value="YouTube Video">YouTube Video</option>
            <option value="Website">Website</option>
            <option value="Landing Page">Landing Page</option>
          </select>
        </div>

        {type && (
          <div>
            <label htmlFor="subType" className="block text-sm font-medium text-gray-700">
              Product Subtype
            </label>
            <select
              id="subType"
              value={subType}
              onChange={(e) => setSubType(e.target.value)}
              required
              className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select Subtype</option>
              {subTypes[type]?.map((subtype) => (
                <option key={subtype} value={subtype}>
                  {subtype}
                </option>
              ))}
            </select>
          </div>
        )}

        <div>
          <label htmlFor="perClickPrice" className="block text-sm font-medium text-gray-700">
            Price per Click
          </label>
          <input
            type="number"
            id="perClickPrice"
            value={perClickPrice}
            onChange={(e) => {
              const value = e.target.value;
              const regex = /^\d+(\.\d{0,2})?$/; // Regex for up to 2 decimals
              if (regex.test(value)) {
                setPerClickPrice(value);
              }
            }}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter price per click"
            step="0.01"
          />
        </div>

        {shouldShowPriceField && (
          <>
            <div>
              <label htmlFor="perBuyPrice" className="block text-sm font-medium text-gray-700">
                Price per Buy
              </label>
              <input
                type="number"
                id="perBuyPrice"
                value={perBuyPrice}
                onChange={(e) => {
                  const value = e.target.value;
                  const regex = /^\d+(\.\d{0,2})?$/; // Regex for up to 2 decimals
                  if (regex.test(value)) {
                    setPerBuyPrice(value);
                  }
                }}
                required
                className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter price per buy"
                step="0.01"
              />
            </div>
          </>
        )}

        <div>
          <label htmlFor="description" className="block text-sm font-medium text-gray-700">
            Description
          </label>
          <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter product description"
          />
        </div>

        <div>
          <label htmlFor="shortDescription" className="block text-sm font-medium text-gray-700">
            Short Description
          </label>
          <input
            type="text"
            id="shortDescription"
            value={shortDescription}
            onChange={(e) => setShortDescription(e.target.value)}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter short description"
          />
        </div>

        <div>
          <label htmlFor="tags" className="block text-sm font-medium text-gray-700">
            Tags (comma separated)
          </label>
          <input
            type="text"
            id="tags"
            value={tags}
            onChange={(e) => setTags(e.target.value)}
            required
            className="mt-1 block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Enter tags"
          />
        </div>

        <button
          type="submit"
          className="flex justify-center py-3 px-6 mt-4 bg-green-600 text-white font-semibold rounded-lg shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 mx-auto"
        >
          Add Product
        </button>
      </form>
    </div>
  );
}

export default AddProduct;
