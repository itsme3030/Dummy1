import React, { useState } from "react";
import { FaStar } from "react-icons/fa";

const UserReviews = ({ reviews }) => {
  const [visibleReviews, setVisibleReviews] = useState(3); // Initially show 3 reviews

  // Function to handle showing more reviews
  const handleShowMore = () => {
    setVisibleReviews((prev) => prev + 3); // Show 3 more reviews on each click
  };

  return (
    <div className="bg-white shadow-md rounded-lg p-6 max-w-4xl mx-auto my-8">
      <h2 className="text-2xl font-semibold text-gray-800 mb-6">User Reviews</h2>

      {/* No reviews message */}
      {reviews.length === 0 ? (
        <p className="text-center text-gray-500">No reviews yet. Be the first to review!</p>
      ) : (
        <>
          {/* Review list container */}
          <div
            className="space-y-6 overflow-y-auto max-h-[300px] pr-4" // Fixed height for scrollable area
          >
            {reviews.slice(0, visibleReviews).map((review) => (
              <div
                key={review.reviewId}
                className="border-b border-gray-200 pb-6"
              >
                <div className="flex items-center mb-3">
                  <h3 className="text-xl font-semibold text-gray-900">{review.productName}</h3>
                  <div className="flex ml-3 text-yellow-500">
                    {[...Array(5)].map((_, index) => (
                      <FaStar
                        key={index}
                        className={`h-5 w-5 ${
                          index < review.rating ? "text-yellow-500" : "text-gray-300"
                        }`}
                      />
                    ))}
                  </div>
                </div>
                
                <p className="text-sm text-gray-500">{review.reviewText}</p>

                <div className="text-xs text-gray-400 mt-2">
                  <span>Reviewed on: {new Date(review.reviewDate).toLocaleDateString()}</span>
                </div>
              </div>
            ))}
          </div>

          {/* Show more button */}
          {reviews.length > visibleReviews && (
            <div className="text-center mt-4">
              <button
                onClick={handleShowMore}
                className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 transition"
              >
                See More Reviews
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default UserReviews;
