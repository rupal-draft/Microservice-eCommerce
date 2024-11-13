INSERT INTO Orders (price, order_status) VALUES
(199.99, 'CONFIRMED'),
(349.50, 'PENDING'),
(89.99, 'DELIVERED'),
(150.00, 'CANCELLED'),
(500.00, 'CONFIRMED'),
(120.00, 'PENDING'),
(220.75, 'DELIVERED'),
(300.50, 'CONFIRMED'),
(80.00, 'CANCELLED'),
(750.00, 'DELIVERED');

INSERT INTO order_item (product_id, quantity, order_id) VALUES
(1, 2, 1),  -- Order 1
(2, 1, 1),  -- Order 1
(3, 3, 2),  -- Order 2
(4, 1, 2),  -- Order 2
(5, 5, 3),  -- Order 3
(6, 2, 3),  -- Order 3
(7, 4, 4),  -- Order 4
(2, 1, 5),  -- Order 5
(3, 2, 5),  -- Order 5
(5, 1, 6),  -- Order 6
(1, 3, 6),  -- Order 6
(2, 4, 7),  -- Order 7
(3, 1, 7),  -- Order 7
(4, 2, 8),  -- Order 8
(5, 1, 8),  -- Order 8
(6, 3, 9),  -- Order 9
(7, 2, 9),  -- Order 9
(1, 2, 10), -- Order 10
(3, 3, 10); -- Order 10
