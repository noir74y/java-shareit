DELETE FROM requests;
INSERT INTO requests (id, description, requestor_id, created) VALUES (1, 'Хочу сложную дрель', 2, CURRENT_TIMESTAMP);
INSERT INTO requests (id, description, requestor_id, created) VALUES (2, 'Хочу простую дрель', 1, CURRENT_TIMESTAMP);

