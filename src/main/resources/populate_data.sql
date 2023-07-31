DELETE FROM users;
INSERT INTO users (id, name, email) VALUES (1, 'user1', 'user1@user.com');
INSERT INTO users (id, name, email) VALUES (2, 'user2', 'user2@user.com');

DELETE FROM requests;
INSERT INTO requests (id, description, requestor_id, created) VALUES (1, 'Хочу простую дрель', 1, CURRENT_TIMESTAMP);
INSERT INTO requests (id, description, requestor_id, created) VALUES (2, 'Хочу сложную дрель', 2, CURRENT_TIMESTAMP);

DELETE FROM items;
INSERT INTO items (id, name, description, available, owner_id, request_id) VALUES (1, 'Дрель','Простая дрель', true, 1, 1);
INSERT INTO items (id, name, description, available, owner_id, request_id) VALUES (2, 'Дрель','Сложная дрель', true, 1, 2);

