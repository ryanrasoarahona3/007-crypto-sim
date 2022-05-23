
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
                        user_id serial PRIMARY KEY,
                        user_email varchar(255),
                        user_pseudo varchar(255),
                        user_password varchar(255)
);

-- la colonne seed permet d'associer un crypto à une série d'évolution de valeur
-- qu'on a enregistré dans le fichier stocks-random.txt
DROP TABLE IF EXISTS "crypto";
CREATE TABLE "crypto" (
                          crypto_id serial PRIMARY KEY,
                          crypto_name varchar(255),
                          crypto_slug varchar(255),
                          crypto_desc text,
                          crypto_seed INT,
                          crypto_seed_cursor INT
);

-- PERMET DE DÉFINIR LA VALEUR D'UN CRYPTO À UN INSTANT DONNÉ
-- Il s'agit d'une association journalisée
-- À chaque instant donné du calendrier correspond
-- à un enregistrement
DROP TABLE IF EXISTS "price";
CREATE TABLE "price" (
                         price_crypto INT,
                         price_date DATE NOT NULL,
                         price_value INT,
                         CONSTRAINT fk_crypto FOREIGN KEY (price_crypto) REFERENCES crypto(crypto_id)
);




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


-- Exchange
DROP TABLE IF EXISTS "exchange";
CREATE TABLE "exchange" (
    exchange_id serial PRIMARY KEY,
    exchange_logo text,
    exchange_name varchar(255),
    exchange_url varchar(255),
    exchange_vol_day int,
    exchange_vol_week int,
    exchange_vol_month int,
    exchange_vol_liquidity int
);


DROP TABLE IF EXISTS "transaction";
CREATE TABLE "transaction" (
    transaction_id serial PRIMARY KEY,
    transaction_transmitter INT NULL,
    transaction_recipient INT NULL,
    transaction_crypto INT NULL,
    transaction_crypto_n INT NULL,
    transaction_sum INT,
    transaction_exchange INT NULL,
    transaction_date DATE NOT NULL,
    CONSTRAINT fk_transmitter FOREIGN KEY (transaction_transmitter) REFERENCES "user"(user_id),
    CONSTRAINT fk_recipient FOREIGN KEY (transaction_recipient) REFERENCES "user"(user_id),
    CONSTRAINT fk_crypto FOREIGN KEY (transaction_crypto) REFERENCES crypto(crypto_id),
    CONSTRAINT fk_exchange FOREIGN KEY (transaction_exchange) REFERENCES exchange(exchange_id)
);


--
-- SELECT


--
-- TECHNICAL SUPPORT
DROP TABLE IF EXISTS "message";
CREATE TABLE "message" (
    message_id serial PRIMARY KEY,
    message_request varchar(255),
    message_title varchar(255),
    message_body text,
    message_sender INT NOT NULL,
    CONSTRAINT fk_sender FOREIGN KEY (message_sender) REFERENCES "user"(user_id"")
)

--
-- SUPPORT REQUEST
DROP TABLE IF EXISTS "supportRequest";
CREATE TABLE "supportRequest" (
    request_id serial PRIMARY KEY,
    request_title varchar(255),
    request_message text,
    request_user int not null,
    CONSTRAINT fk_user FOREIGN KEY (request_user) REFERENCES "user" (user_id)
);

--
-- CURRENCY*
DROP TABLE IF EXISTS "currency";
CREATE TABLE "currency" (
    currency_id serial PRIMARY KEY,
    currency_name varchar(255),
    currency_symbol varchar(255)
)