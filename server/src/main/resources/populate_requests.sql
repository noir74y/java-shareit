DELETE FROM requests;
INSERT INTO requests (id, description, requestor_id, created) VALUES (1, 'description1', 2, {ts '2010-09-17 00:00:00'});
INSERT INTO requests (id, description, requestor_id, created) VALUES (2, 'description2', 1, {ts '2010-09-18 00:00:00'});

