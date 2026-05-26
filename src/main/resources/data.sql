CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO users (username, password, role) VALUES ('endmin', 'gugugaga', 'ADMIN');
INSERT INTO users (username, password, role) VALUES ('admin', 'SuperSecretAdminPassword2026', 'ADMIN');
INSERT INTO users (username, password, role) VALUES ('john_doe', 'password123', 'USER');
INSERT INTO users (username, password, role) VALUES ('attacker_test', 'testpass', 'USER');
