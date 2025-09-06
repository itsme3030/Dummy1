import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { FaStar } from "react-icons/fa"; // For rating stars
import axios from "axios";

function ProductDetail() {
  const navigate = useNavigate();
  const location = useLocation();
  const { productDetail } = location.state || {}; // Extract ProductDetail from the state

  if (!productDetail) {
    return <div>Product not found</div>;
  }

  const { productId, productName, perClickPrice, perBuyPrice, description, rating, ratingCount, reviews, image } = productDetail;

  // Fetch logged-in username from sessionStorage
  const username = sessionStorage.getItem("username");

  const [userRating, setUserRating] = useState(0);
  const [userReview, setUserReview] = useState('');
  const [hasReviewed, setHasReviewed] = useState(false);
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [error, setError] = useState(null); // Error state
  const [successMessage, setSuccessMessage] = useState(null); // Success message state

  // Check if the user has already reviewed the product
  useEffect(() => {
    if (reviews) {
      const existingReview = reviews.find((review) => review.username === username);
      if (existingReview) {
        setUserRating(existingReview.rating);
        setUserReview(existingReview.reviewText);
        setHasReviewed(true);
        setShowReviewForm(false); // Hide form if already reviewed
      } else {
        setShowReviewForm(true); // Show review form if not reviewed
      }
    }
  }, [reviews, username]);

  const renderStars = (rating) => {
    let stars = [];
    for (let i = 0; i < 5; i++) {
      stars.push(
        <FaStar
          key={i}
          className={`w-5 h-5 ${i < rating ? 'text-yellow-500' : 'text-gray-300'}`}
        />
      );
    }
    return stars;
  };

  const handleReviewSubmit = (e) => {
    e.preventDefault();

    const reviewData = {
      productId,
      username: sessionStorage.getItem("username"), // Get username from sessionStorage
      rating: userRating,
      reviewText: userReview,
    };

    const token = sessionStorage.getItem("token");
    if (!token) {
      navigate("/Authenticate");
      return;
    }

    axios
      .post(`${import.meta.env.VITE_API}/review/submit`, reviewData, {
        headers: {
          Authorization: `Bearer ${token}`,  // Attach the token in the Authorization header
        }
      })
      .then((response) => {
        console.log('Review submitted successfully:', response.data);
        setHasReviewed(true);  // Mark the review as submitted
        setSuccessMessage('Review submitted successfully!'); // Set success message
        navigate("/affiliate");

        //Will Work in future : same page with updated data
        // navigate("/product-detail", {
        //   state: {
        //     productDetail: {
        //       ...response.data,  // Spread the existing product details
        //       image: image // Add the image name or path here
        //     }
        //   }
        // });

      })
      .catch((error) => {
        console.error('Error submitting review:', error);
        setError('Error submitting your review. Please try again.'); // Set error message
      });
  };

  return (
    <div className="bg-gray-50 container mx-auto py-8 px-4 lg:px-8">
      {/* Product Image and Details Section */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="flex flex-col lg:flex-row gap-8 bg-gray-100">
          <div className="flex-1 flex justify-center items-center">
            <img
              src={image}
              alt={productName}
              className="w-auto h-auto object-cover rounded-lg shadow-lg"
            />
          </div>
        </div>
        <div className="flex flex-col lg:flex-row gap-8">
          <div className="flex-1">
            <h2 className="text-3xl font-semibold text-gray-800 mb-4">{productName}</h2>
            <p className="text-xl text-gray-600 mb-4">Price per click: ${perClickPrice}</p>
            <p className="text-xl text-gray-600 mb-4">Price per buy: ${perBuyPrice}</p>
            <div className="flex items-center mb-4">
              {renderStars(rating)}
              <span className="ml-2 text-gray-600">({ratingCount})</span>
            </div>
            <h3 className="text-2xl font-medium text-gray-800 mb-2">Description</h3>
            <p className="text-gray-600 mb-4">{description}</p>
            {productDetail.tags && (
              <div className="mb-4">
                <h3 className="text-xl font-medium text-gray-800">Tags</h3>
                <ul className="flex flex-wrap gap-2">
                  {productDetail.tags.split(',').map((tag, idx) => (
                    <li key={idx} className="bg-blue-100 text-blue-600 rounded-full px-4 py-1 text-sm">
                      {tag.trim()}
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        </div>
      </div>

      <br />
      <hr />

      {/* Review Form Section */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mt-8">
        <div className="flex flex-col space-y-4">
          <div className="w-full">
            <h4 className="text-xl font-medium text-gray-800">
              {hasReviewed ? "Update Rating" : "Rate the Product"}
            </h4>
            <form onSubmit={handleReviewSubmit} className="mt-4 space-y-4">
              <div>
                <h4 className="text-lg font-medium text-gray-800">Rate the Product</h4>
                <div className="flex items-center space-x-2">
                  {[1, 2, 3, 4, 5].map((star) => (
                    <FaStar
                      key={star}
                      onClick={() => setUserRating(star)}
                      className={`w-6 h-6 cursor-pointer ${star <= userRating ? 'text-yellow-500' : 'text-gray-300'}`}
                    />
                  ))}
                </div>
              </div>

              <div>
                <h4 className="text-lg font-medium text-gray-800">Your Review</h4>
                <textarea
                  value={userReview}
                  onChange={(e) => setUserReview(e.target.value)}
                  placeholder="Write your review here..."
                  className="w-full h-32 p-3 border border-gray-300 rounded-lg"
                />
              </div>

              <button
                type="submit"
                className="w-full py-2 font-semibold rounded-lg focus:outline-none focus:ring-2 bg-green-500 text-white hover:bg-green-600 focus:ring-green-500"
              >
                Submit Review
              </button>
            </form>

            {/* Error or Success Message */}
            {error && <p className="mt-4 text-red-600">{error}</p>}
            {successMessage && <p className="mt-4 text-green-600">{successMessage}</p>}

            {hasReviewed && (
              <p className="mt-4 text-gray-600">
                You have already rated this product. Thank you for your feedback!
              </p>
            )}
          </div>
        </div>

        {/* Reviews Section */}
        <div className="flex flex-col space-y-4">
          {reviews && reviews.length > 0 && (
            <div className="mb-6 overflow-y-auto" style={{ maxHeight: "350px" }}>
              <h3 className="text-xl font-medium text-gray-800">Customer Reviews</h3>
              <div className="space-y-4">
                {reviews.map((review) => (
                  <div key={review.reviewId} className="border-b py-4">
                    <p className="text-gray-700">{review.reviewText}</p>
                    <div className="flex items-center mt-2">
                      {renderStars(review.rating)}
                      <span className="ml-2 text-gray-600">by {review.username}</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;
