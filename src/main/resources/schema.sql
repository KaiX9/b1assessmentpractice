CREATE DATABASE IF NOT EXISTS acme_bank;

USE acme_bank;

CREATE TABLE IF NOT EXISTS accounts (
    account_id VARCHAR(255) NOT NULL PRIMARY KEY, 
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
    );

INSERT IGNORE INTO accounts (account_id, name, balance)
VALUES
('1b80114c21','fred',8888.26),
('cf66dae31a','wilma',13705.24),
('a8b9800d56','barney',30987.55),
('66223e28f4','betty',205961.24);