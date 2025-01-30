--Queries para ir debuggeando la app.

CREATE DATABASE IF NOT EXISTS marketservice;
DROP DATABASE marketservice;

USE marketservice;

SELECT * from users;
SELECT * from products;
SELECT * from categories;
SELECT * from sales; 
SELECT * from receipts; 
SELECT * from receipt_item; 


DELETE FROM users;
DELETE FROM products;
DELETE FROM categories;