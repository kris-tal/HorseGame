CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(80) UNIQUE NOT NULL,
    email VARCHAR(120),
    password_hash VARCHAR(256) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TYPE horse_races AS ENUM ('arabian', 'mustang', 'shire', 'mule');

CREATE  TABLE horses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(80),
    race horse_races,
    speed INT DEFAULT 10,
    stamina INT DEFAULT 10,
    owner_id INT REFERENCES users(id) ON DELETE CASCADE
);

