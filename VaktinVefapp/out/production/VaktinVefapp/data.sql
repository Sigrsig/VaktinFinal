DROP TABLE IF EXISTS billionaires;

CREATE TABLE billionaires (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    career VARCHAR(100) DEFAULT NULL
);

INSERT INTO billionaires (first_name, last_name, career) VALUES
    ('Aliko', 'Dangote', 'Billionaire Industrialist'),
    ('Bill', 'Gates', 'Billionaire Tech Entrepreneur'),
    ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate');


