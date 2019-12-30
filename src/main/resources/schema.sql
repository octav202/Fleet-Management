drop TABLE IF EXISTS ship CASCADE;
drop TABLE IF EXISTS category;
drop TABLE IF EXISTS owner CASCADE;
drop TABLE IF EXISTS ship_owner;

CREATE TABLE ship
(
id SERIAL PRIMARY KEY ,
ship_name TEXT,
imo_number TEXT
);

CREATE TABLE category
(
 id SERIAL PRIMARY KEY,
 ship_id INTEGER REFERENCES ship(id),
 ship_type TEXT,
 ship_tonnage INTEGER
);

CREATE TABLE owner
(
id SERIAL PRIMARY KEY,
owner_name TEXT
);

CREATE TABLE ship_owner
(
ship_id INTEGER REFERENCES ship(id),
owner_id INTEGER REFERENCES owner(id)
);

CREATE UNIQUE INDEX idx_category_ship_id ON category(ship_id);