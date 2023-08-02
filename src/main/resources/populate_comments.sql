DELETE FROM comments;
-- fairy comment
INSERT INTO comments (id, text, item_id, author_id, created) VALUES (1, 'Хорошая дрель', 2, 1, {ts '2020-09-18 00:00:01'});
-- unfair comment
INSERT INTO comments (id, text, item_id, author_id, created) VALUES (2, 'Плохая дрель', 1, 2, {ts '2010-09-18 00:00:00'});

