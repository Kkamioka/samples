DROP TABLE account;
CREATE TABLE account
(
    id           SERIAL PRIMARY KEY,
    login_id     TEXT UNIQUE,
    name         TEXT,
    password     TEXT
);
    
INSERT INTO account(login_id, name, password) VALUES('okada', '岡田 是則', 's6rizqfk');
INSERT INTO account(login_id, name, password) VALUES('inoue', '井上 俊憲', 'g73phw5n');
INSERT INTO account(login_id, name, password) VALUES('inagaki', '稲垣 絵美', 's59mrtw3');

