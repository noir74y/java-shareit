DELETE FROM bookings;
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (1, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 1, 'WAITING');
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 1, 'APPROVED');
