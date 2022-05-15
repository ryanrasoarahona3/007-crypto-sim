

DROP TABLE IF EXISTS "user";
DROP TYPE IF EXISTS gender;
CREATE TYPE gender AS ENUM('MALE', 'FEMALE', 'UNKNOWN'); -- u as unknown
CREATE TABLE "user" (
                        user_id serial PRIMARY KEY,
                        user_email varchar(255),
                        user_pseudo varchar(255),
                        user_password varchar(255),
                        user_firstname varchar(255),
                        user_lastname varchar(255),
                        user_picture text,
                        user_birth date,
                        user_gender gender,
                        user_phone varchar(255),
                        user_address varchar(255)
);
