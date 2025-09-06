import React, { useState, useEffect } from "react";
import ProductList from "../components/ProductList";

function Affiliate() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedType, setSelectedType] = useState('');
  const [selectedSubType, setSelectedSubType] = useState('');
  const [perClickPrice, setPerClickPrice] = useState('');
  const [perBuyPrice, setPerBuyPrice] = useState('');
  const [subTypes, setSubTypes] = useState([]);
  
  const handleSearchChange = (e) => setSearchTerm(e.target.value);

  // Define the available subtypes based on type
  const availableSubTypes = {
    "Product": ["Electronics", "Cloths", "Books", "Furniture", "Accessories"],
    "Product - Amazon": ["Electronics", "Cloths", "Books", "Home Appliances", "Sports Gear"],
    "Product - Flipkart": ["Electronics", "Cloths", "Books", "Furniture", "Toys"],
    "YouTube Video": ["Entertainment", "Gaming", "Music", "Sports", "Science and Technology"],
    "Website": ["Blog Website", "eCommerce Website", "Business Website", "Personal Website", "Portfolio Website", "Educational Website", "News Website", "Entertainment Website"],
    "Landing Page": ["Lead Generation", "Promotional", "Event", "Product Launch", "Services"]
  };

  useEffect(() => {
    // Update the subtypes based on selected product type
    setSubTypes(availableSubTypes[selectedType] || []);
    setSelectedSubType('')
  }, [selectedType]);

  // console.log("--->",selectedType," - ",selectedSubType);

  return (
    <div className="min-h-screen bg-gray-200 py-8 px-4 sm:px-6 lg:px-8 ">
      {/* Filters Section */}
      <div className="mb-8">
        <div className="flex flex-wrap gap-4 justify-center mb-6">
          {/* Type Filter */}
          <select
            id="productType"
            value={selectedType}
            onChange={(e) => {
              setSelectedType(e.target.value)  
            }}
            className="p-3 w-full sm:w-1/2 md:w-1/4 lg:w-1/6 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Select Product Type</option>
            {['Product - Amazon', 'Product - Flipkart', 'Product', 'YouTube Video', 'Website', 'Landing Page'].map((category) => (
              <option key={category} value={category}>{category}</option>
            ))}
          </select>

          {/* Subtype Filter */}
          {selectedType && (
            <select
              id="subType"
              value={selectedSubType}
              onChange={(e) => setSelectedSubType(e.target.value)}
              className="p-3 w-full sm:w-1/2 md:w-1/4 lg:w-1/6 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select Subtype</option>
              {subTypes.map((subtype) => (
                <option key={subtype} value={subtype}>{subtype}</option>
              ))}
            </select>
          )}

          {/* Price Filters */}
          <input
            type="number"
            className="p-3 w-full sm:w-1/2 md:w-1/4 lg:w-1/6 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Max Price per Click"
            value={perClickPrice}
            onChange={(e) => setPerClickPrice(e.target.value)}
          />
          <input
            type="number"
            className="p-3 w-full sm:w-1/2 md:w-1/4 lg:w-1/6 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Max Price per Buy"
            value={perBuyPrice}
            onChange={(e) => setPerBuyPrice(e.target.value)}
          />

          

        </div>

        {/* Search Bar */}
        <div className="flex justify-center mb-8">
          <input
            type="text"
            className="p-3 w-full sm:w-1/2 md:w-1/3 lg:w-1/4 border border-gray-300 rounded-lg"
            placeholder="Search by product name"
            value={searchTerm}
            onChange={handleSearchChange}
          />
          <button
            className="ml-4 p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
            onClick={() => console.log(`Searching for: ${searchTerm}`)}
          >
            Search
          </button>
        </div>
      </div>

      {/* Product List */}
      <ProductList
        searchTerm={searchTerm}
        selectedType={selectedType}
        selectedSubType={selectedSubType}
        perClickPrice={perClickPrice}
        perBuyPrice={perBuyPrice}
      />
    </div>
  );
}

export default Affiliate;
