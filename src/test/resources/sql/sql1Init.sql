DROP TABLE IF EXISTS transaction_money CASCADE;
DROP TABLE IF EXISTS bank_account CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    pin  VARCHAR(44) NOT NULL
);

CREATE TABLE IF NOT EXISTS bank_account
(
    id       BIGSERIAL PRIMARY KEY,
    users_id BIGINT  NOT NULL,
    money    INTEGER NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS transaction_money
(
    id                   BIGSERIAL PRIMARY KEY,
    local_date_time      TIMESTAMP NOT NULL,
    users_id             BIGINT    NOT NULL,
    money                INTEGER   NOT NULL,
    bank_account_from_id BIGINT    NOT NULL,
    bank_account_to_id   BIGINT    NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id),
    FOREIGN KEY (bank_account_from_id) REFERENCES bank_account (id),
    FOREIGN KEY (bank_account_to_id) REFERENCES bank_account (id)
);