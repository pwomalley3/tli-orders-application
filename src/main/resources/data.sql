INSERT INTO order_statuses(`name`, created_date, created_by, modified_date, modified_by)
VALUES
('New', NOW(), 1, NOW(), 1),
('Processing', NOW(), 1, NOW(), 1),
('In Transit', NOW(), 1, NOW(), 1),
('Delivered', NOW(), 1, NOW(), 1),
('Canceled', NOW(), 1, NOW(), 1);
