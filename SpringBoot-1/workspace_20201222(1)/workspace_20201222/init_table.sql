CREATE TABLE todo
(
  id           SERIAL PRIMARY KEY,
  title        TEXT,     
  importance   INTEGER,  
  urgency      INTEGER,
  deadline     DATE,
  done         TEXT
);
CREATE TABLE IF NOT EXISTS sample_schema.account(
    account_id    int PRIMARY KEY AUTO_INCREMENT,
    email         varchar(50) NOT NULL,
    password      varchar(30) NOT NULL,
    user_name     varchar(30) NOT NULL,
    INDEX(account_id)
);
