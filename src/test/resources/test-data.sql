INSERT INTO users (id, name, email) VALUES
(1, 'Owner', 'owner@ya.ru'),
(2, 'Booker', 'booker@ya.ru');

INSERT INTO items (id, name, description, status, user_id) VALUES
(1, 'Item1', 'Description1', true, 1),
(2, 'Item2', 'Description2', false, 1);

INSERT INTO booking (id, start_of_booking, end_of_booking, item_id, user_id, status) VALUES
(1, '2025-01-01 10:00', '2025-01-02 10:00', 1, 2, 'WAITING'),
(2, '2025-02-01 10:00', '2025-02-02 10:00', 1, 2, 'APPROVED');

ALTER TABLE items ALTER COLUMN id RESTART WITH 3;
ALTER TABLE booking ALTER COLUMN id RESTART WITH 3;