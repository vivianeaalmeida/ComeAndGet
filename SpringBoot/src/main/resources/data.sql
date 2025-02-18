-- Insert users
INSERT INTO USER_TEMP (ID, EMAIL, NAME, PHONE_NUMBER, ROLE) VALUES
(1, 'user1@example.com','Alice Silva', '912345678', 'client'),
(2, 'user2@example.com', 'Bruno Costa', '923456789', 'client'),
(3, 'user3@example.com', 'Carla Mendes', '934567890', 'client'),
(4, 'user4@example.com', 'Daniel Rocha', '945678901', 'client');

-- Insert categories
INSERT INTO CATEGORY (ID, DESIGNATION) VALUES
(1, 'Tecnologia'),
(2, 'Roupas'),
(3, 'Electrodomésticos'),
(4, 'Móveis');

-- Insert items
INSERT INTO ITEM (ID, IMAGE, CONDITION, CATEGORY_ID) VALUES
(1, 'https://example.com/image1.jpg', 1, 3),
(2, 'https://example.com/image2.jpg', 2, 4),
(3, 'https://example.com/image3.jpg', 2, 3),
(4, 'https://example.com/image4.jpg', 2, 3);

-- Insert advertisements
INSERT INTO ADVERTISEMENT (ID, TITLE, DESCRIPTION, DATE, MUNICIPALITY, STATUS, ITEM_ID, CLIENT_ID) VALUES
(1, 'Frigorífico para doar - urgente', 'Frigorifico com congelador para doar. Tenho urgência que venham retirar', '2025-02-14', 'Porto', 0, 1, 1),
(2, 'Sofá 3 lugares', 'Sofá de 3 lugares em ótimo estado. Ideal para a sua sala de estar.', '2025-02-10', 'Porto', 0, 2, 2),
(3, 'Máquina de lavar a loiça para doar', 'Máquina de lavar a loiça em funcionamento.', '2025-02-10', 'Porto', 1, 3, 1),
(4, 'Máquina de café', 'Máquina de café da Nespresso em bom estado.', '2025-01-01', 'Porto', 1, 4, 1);

-- Insert reservationAttempts
INSERT INTO RESERVATION_ATTEMPT (ID, ADVERTISEMENT_ID, USER_ID, DATE, STATUS) VALUES
(1, 1, 2, '2025-02-14', 0),
(2, 3, 2, '2025-02-14', 4);