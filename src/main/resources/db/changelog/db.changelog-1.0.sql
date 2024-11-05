--liquibase formatted sql
--changeset pep_sxx:1
DROP TABLE IF EXISTS transaction_money CASCADE;
--changeset pep_sxx:2
DROP SEQUENCE IF EXISTS transaction_money_seq CASCADE;
--changeset pep_sxx:3
DROP TABLE IF EXISTS bank_account CASCADE;
--changeset pep_sxx:4
DROP SEQUENCE IF EXISTS bank_account_seq CASCADE;
--changeset pep_sxx:5
DROP TABLE IF EXISTS users CASCADE;
--changeset pep_sxx:6
DROP SEQUENCE IF EXISTS users_seq CASCADE;

--changeset pep_sxx:7
CREATE SEQUENCE IF NOT EXISTS users_seq
    START WITH 50 INCREMENT BY 50;

--changeset pep_sxx:8
CREATE TABLE IF NOT EXISTS users
(
    id   BIGINT DEFAULT nextval('users_seq') PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    pin  VARCHAR(44) NOT NULL
);

--changeset pep_sxx:9
CREATE SEQUENCE IF NOT EXISTS bank_account_seq
    START WITH 50 INCREMENT BY 50;

--changeset pep_sxx:10
CREATE TABLE IF NOT EXISTS bank_account
(
    id       BIGINT DEFAULT nextval('bank_account_seq') PRIMARY KEY,
    users_id BIGINT         NOT NULL,
    money    NUMERIC(38, 2) NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id)
);

--changeset pep_sxx:11
CREATE SEQUENCE IF NOT EXISTS transaction_money_seq
    START WITH 50 INCREMENT BY 50;

--changeset pep_sxx:12
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