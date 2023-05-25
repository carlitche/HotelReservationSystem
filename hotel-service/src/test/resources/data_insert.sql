-- Create RoomType table
CREATE TABLE Room_Type
(
    room_type_Id  SERIAL PRIMARY KEY,
    type        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- Create Hotel table
CREATE TABLE Hotel
(
    hotel_Id  SERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    address  VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

-- Create Room table
CREATE TABLE Room
(
    room_Id      SERIAL PRIMARY KEY,
    number      INTEGER      NOT NULL,
    floor       INTEGER      NOT NULL,
    name        VARCHAR(255) NOT NULL,
    available   BOOLEAN      NOT NULL,
    room_type_id INTEGER REFERENCES Room_Type (room_type_Id),
    hotel_id    INTEGER REFERENCES Hotel (hotel_Id)
);

-- -- Insert sample room types
INSERT INTO Room_Type (type, description)
VALUES
    ('Standard', 'Standard room'),
    ('Deluxe', 'Deluxe room'),
    ('Suite', 'Suite room'),
    ('Executive', 'Executive room');

-- -- Insert sample hotels and rooms
-- -- Hotel 1
INSERT INTO Hotel (name, address, location)
VALUES ('Hotel A', '123 Main St', 'London');

INSERT INTO Room (number, floor, name, available, room_type_id, hotel_id)
SELECT number,
       floor,
       CONCAT('Room ', number) AS name,
       TRUE                    AS available,
       (number % 4) + 1        AS room_type_id,
       1                       AS hotel_id
FROM generate_series(1, 15) AS number
         CROSS JOIN generate_series(1, 1) AS floor;

-- Hotel 2
INSERT INTO Hotel (name, address, location)
VALUES ('Hotel B', '456 Elm St', 'Paris');

INSERT INTO Room (number, floor, name, available, room_type_id, hotel_id)
SELECT number,
       floor,
       CONCAT('Room ', number) AS name,
       TRUE                    AS available,
       (number % 4) + 1        AS room_type_id,
       2                       AS hotel_id
FROM generate_series(1, 15) AS number
         CROSS JOIN generate_series(1, 1) AS floor;

-- Hotel 3
INSERT INTO Hotel (name, address, location)
VALUES ('Hotel C', '789 Oak St', 'Rome');

INSERT INTO Room (number, floor, name, available, room_type_id, hotel_id)
SELECT number,
       floor,
       CONCAT('Room ', number) AS name,
       TRUE                    AS available,
       (number % 4) + 1        AS room_type_id,
       3                       AS hotel_id
FROM generate_series(1, 15) AS number
CROSS JOIN generate_series(1, 1) AS floor;


-- -- Continue adding hotels and rooms
--
-- -- Hotel 4
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel D', '10 High St', 'Barcelona');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     4 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--
-- -- Hotel 5
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel E', '20 Elm St', 'Berlin');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     5 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--
-- -- Hotel 6
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel F', '30 Oak St', 'Amsterdam');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     6 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--
-- -- Hotel 7
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel G', '40 Main St', 'Madrid');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     7 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--
-- -- Hotel 8
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel H', '50 Elm St', 'Vienna');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     8 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--
-- -- Hotel 9
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel I', '60 Oak St', 'Dublin');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     9 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--
-- -- Hotel 10
-- INSERT INTO Hotel (name, address, location)
-- VALUES
--     ('Hotel J', '70 Main St', 'Prague');
--
-- INSERT INTO Room (number, floor, name, available, roomtype_id, hotel_id)
-- SELECT
--     number,
--     floor,
--     CONCAT('Room ', number) AS name,
--     TRUE AS available,
--     (number % 4) + 1 AS roomtype_id,
--     10 AS hotel_id
-- FROM generate_series(1, 15) AS number
--          CROSS JOIN generate_series(1, 1) AS floor;
--

