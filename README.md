# AffiliateAdda 

This is an affiliate marketing platform where users can either promote products as affiliates or add products to be promoted as sellers. The website allows users to earn commissions for products they promote or pay commissions for products they list. The platform tracks all activities and transactions, and users can view detailed statistics about their earnings and activities.

## Features

### 1. **Product Promotion & Affiliate Program**
   - **Sellers** can add products for promotion and set commission rates.
   - **Affiliates** can promote these products and generate affiliate links for tracking.
   - **Commissions**: Sellers pay a commission for promotion (after the website cuts its share), and affiliates earn commissions based on their referral links.
   - **Ratings**: Affiliates can rate the products they've promoted.

### 2. **Product & Affiliate Link Deactivation**
   - **Sellers** can deactivate a product at any time, stopping affiliates from generating new links and halting link tracking.
   - **Affiliates** can deactivate their affiliate links anytime, stopping the tracking and redirection to the final product.

### 3. **Easy and Fast Login (OAuth 2.0 & JWT)**
   - The platform uses OAuth 2.0 and JWT with Spring Security for authentication and authorization.
   - This enables quick and easy login without the need for manually entering username and password every time.

### 4. **User Profile Section**
   The user profile provides a detailed visualization of their activities and statistics:
   - **Overall Summary**: Displays total earnings, payable amount, paid amount, withdrawals, remaining earnings, and remaining payable amount.
   - **Monthly/Yearly Summary**: Visualization of added products, generated affiliate links, reviewed products, and transactions (pay and withdrawals) via bar charts.
   - **Earnings Summary**: Includes information on affiliate links, clicks count, price per click, buy count, price per buy, and total earnings.
     - Earnings are visualized using bar charts for monthly/affiliate earnings and line charts for yearly earnings.
   - **Payable Amount Summary**: Includes details about payable amounts, product details, clicks count, buy count, and total payable amounts.
     - Payable amounts are visualized in bar charts for monthly and per product summaries and line charts for yearly summaries.
   - **Transaction History**: Displays a list of all transactions for easy analysis.

### 5. **Transactions**
   - Users can perform transactions within the platform, through the integration of the **RazorPay** payment gateway.
   - Payments are securely processed through RazorPay, allowing users to make seamless transactions.
   - The system supports a variety of payment methods, including debit/credit cards, net banking, and wallets.
   - Future updates may include additional payment options or enhancements to improve the transaction experience.

### 6. **Admin Section**
   - **Admin Dashboard**: Admin users can analyze earnings, manage users, and activate or deactivate users.
   - Admins also have the ability to manage product promotion and affiliate link statuses.

## Future Scope

We are actively working on dynamic content generation. This feature will provide affiliates with ready-made content for their promotion needs, including blog posts, social media posts, and SEO-friendly content. Affiliates will no longer need to search for content or create it manually. This will save time and make the promotional process easier and more efficient.

## Tech Stack

- **Backend**: Spring Boot with Spring Security for OAuth 2.0 and JWT authentication.
- **Frontend**: ReactJs (Vite)
- **Database**: Postgres (SQL Database)

## Installation

1. Clone the repository.
   ```bash
   git clone https://github.com/itsme3030/AffiliateAdda.git
   ```

2. Install dependencies.
3. Set up the database and configuration files.
4. Run the application.

## Contribution

We welcome contributions to enhance the platform. If you'd like to contribute, please fork the repository, make your changes, and submit a pull request.
