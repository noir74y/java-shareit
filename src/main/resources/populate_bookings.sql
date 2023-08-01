DELETE FROM bookings;
-- FUTURE, WAITING
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (1, 1, 2, {ts '2030-09-17 00:00:00'}, {ts '2030-09-18 00:00:00'}, 'WAITING');
-- FUTURE, WAITING
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (2, 2, 1, {ts '2031-09-17 00:00:00'}, {ts '2032-09-18 00:00:00'}, 'WAITING');
-- CURRENT, APPROVED
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (4, 2, 1, {ts '2010-09-18 00:00:00'}, {ts '2030-09-18 00:00:00'}, 'APPROVED');
-- PAST, APPROVED
INSERT INTO bookings (id, item_id, booker_id, start_date, end_date, status) VALUES (6, 2, 1, {ts '2010-09-17 00:00:00'}, {ts '2020-09-18 00:00:00'}, 'REJECTED');