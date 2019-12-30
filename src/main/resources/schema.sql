drop TABLE IF EXISTS ship CASCADE;
drop TABLE IF EXISTS category;
drop TABLE IF EXISTS owner CASCADE;
drop TABLE IF EXISTS ship_owner;

CREATE TABLE ship
(
ship_id INTEGER PRIMARY KEY,
ship_name TEXT,
imo_number TEXT
);

CREATE TABLE category
(
 category_id INTEGER PRIMARY KEY,
 ship_id INTEGER REFERENCES ship(ship_id),
 ship_type TEXT,
 ship_tonnage INTEGER
);

CREATE TABLE owner
(
owner_id INTEGER PRIMARY KEY,
owner_name TEXT
);

CREATE TABLE ship_owner
(
ship_id INTEGER REFERENCES ship(ship_id),
owner_id INTEGER REFERENCES owner(owner_id)
);