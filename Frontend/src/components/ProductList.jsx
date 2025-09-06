import React, { useEffect, useState } from "react";
import ProductCard from "./ProductCard";
import axios from "axios";

function ProductList({ searchTerm, selectedType, selectedSubType, perClickPrice, perBuyPrice }) {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  // Fetch product list from backend
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API}/product/list`);
        setProducts(response.data);
      } catch (err) {
        setError("Failed to fetch products");
        console.error("Error fetching products:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  // Filter products based on all filters
  const filteredProducts = products.filter((product) => {
    const matchesType = selectedType ? product.productType === selectedType : true;
    const matchesSubType = selectedSubType ? product.productSubType === selectedSubType : true;
    const matchesSearch = searchTerm ? product.productName.toLowerCase().includes(searchTerm.toLowerCase()) : true;
    const matchesPerClickPrice = perClickPrice ? product.perClickPrice <= perClickPrice : true;
    const matchesPerBuyPrice = perBuyPrice ? product.perBuyPrice <= perBuyPrice : true;

    return matchesType && matchesSubType && matchesSearch && matchesPerClickPrice && matchesPerBuyPrice;
  });

  return (
    <div className="min-h-screen bg-gray-50 py-10 px-4 sm:px-6 lg:px-8 ">
      {error && (
        <div className="mb-4 text-center text-white bg-red-500 p-4 rounded-lg shadow-md">
          {error}
        </div>
      )}

      {loading && (
        <div className="flex justify-center items-center space-x-2">
          <div className="w-8 h-8 border-4 border-t-4 border-blue-500 rounded-full animate-spin"></div>
          <p>Loading products...</p>
        </div>
      )}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {filteredProducts.length > 0 ? (
          filteredProducts.map((product) => (
            <ProductCard key={product.productId} product={product} />
          ))
        ) : (
          !loading && (
            <div className="col-span-4 text-center text-gray-500">
              No products found matching your search criteria.
            </div>
          )
        )}
      </div>
    </div>
  );
}

export default ProductList;
