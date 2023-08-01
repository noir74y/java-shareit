DELETE FROM bookings;
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (1, 1, 2, {ts '2030-09-17 00:00:00'}, {ts '2030-09-18 00:00:00'}, 'WAITING');
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (2, 2, 1, {ts '2030-09-17 00:00:00'}, {ts '2030-09-18 00:00:00'}, 'WAITING');
