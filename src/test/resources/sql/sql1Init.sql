DROP TABLE IF EXISTS transaction_money CASCADE;
DROP SEQUENCE IF EXISTS transaction_money_seq CASCADE;
DROP TABLE IF EXISTS bank_account CASCADE;
DROP SEQUENCE IF EXISTS bank_account_seq CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS users_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS users_seq
    START WITH 50 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS users
(
    id   BIGINT DEFAULT nextval('users_seq') PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    pin  VARCHAR(44) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS bank_account_seq
    START WITH 50 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS bank_account
(
    id       BIGINT DEFAULT nextval('bank_account_seq') PRIMARY KEY,
    users_id BIGINT         NOT NULL,
    money    NUMERIC(38, 2) NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id)
);

CREATE SEQUENCE IF NOT EXISTS transaction_money_seq
    START WITH 50 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS transaction_money
(
    id                   BIGINT DEFAULT nextval('transaction_money_seq') PRIMARY KEY,
    local_date_time      TIMESTAMP      NOT NULL,
    users_id             BIGINT         NOT NULL,
    money                NUMERIC(38, 2) NOT NULL,
    bank_account_from_id BIGINT         NOT NULL,
    bank_account_to_id   BIGINT         NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id),
    FOREIGN KEY (bank_account_from_id) REFERENCES bank_account (id),
    FOREIGN KEY (bank_account_to_id) REFERENCES bank_account (id)
);