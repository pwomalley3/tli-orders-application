INSERT INTO order_statuses(`name`, created_date, created_by, modified_date, modified_by)
VALUES
('New', NOW(), 1, NOW(), 1),
('Processing', NOW(), 1, NOW(), 1),
('In Transit', NOW(), 1, NOW(), 1),
('Delivered', NOW(), 1, NOW(), 1),
('Canceled', NOW(), 1, NOW(), 1);

INSERT INTO orders(id, status_id, created_date, created_by, modified_date, modified_by)
VALUES
(1, 1, NOW(), 1, NOW(), 1),
(2, 2, NOW(), 1, NOW(), 1),
(3, 3, NOW(), 1, NOW(), 1),
(4, 4, NOW(), 1, NOW(), 1),
(5, 5, NOW(), 1, NOW(), 1);

INSERT INTO order_line_items(`number`, order_id, `name`, price, quantity, created_date, created_by, modified_date, modified_by)
VALUES
(1, 1, 'Widget', 10.99, 100, NOW(), 1, NOW(), 1),
(2, 1, 'Wodget', 10.99, 100, NOW(), 1, NOW(), 1),
(3, 1, 'Wedget', 10.99, 100, NOW(), 1, NOW(), 1),
(1, 2, 'Widget', 10.99, 100, NOW(), 1, NOW(), 1),
(2, 2, 'Wodget', 10.99, 100, NOW(), 1, NOW(), 1),
(3, 2, 'Wadget', 10.99, 100, NOW(), 1, NOW(), 1),
(1, 3, 'Widget', 10.99, 100, NOW(), 1, NOW(), 1),
(2, 3, 'Wodget', 10.99, 100, NOW(), 1, NOW(), 1),
(1, 4, 'Wodget', 10.99, 100, NOW(), 1, NOW(), 1),
(1, 5, 'Wadget', 10.99, 100, NOW(), 1, NOW(), 1),
(2, 4, 'Widget', 10.99, 100, NOW(), 1, NOW(), 1),
(2, 5, 'Wodget', 10.99, 100, NOW(), 1, NOW(), 1);