-- Insert categories
INSERT INTO CATEGORY (ID, DESIGNATION) VALUES
(1, 'Electrodomésticos'),
(2, 'Tecnologia'),
(3, 'Roupas'),
(4, 'Móveis'),
(5, 'Ferramentas e Bricolage'),
(6, 'Brinquedos e Jogos'),
(7, 'Livros e Material Escolar'),
(8, 'Utensílios Domésticos'),
(9, 'Arte e Decoração'),
(10, 'Outros');

-- Insert items
INSERT INTO ITEM (ID, IMAGE, CONDITION, CATEGORY_ID) VALUES
-- Electrodomesticos
(1, 'https://example.com/image1.jpg', 1, 1),
(2, 'https://example.com/image2.jpg', 2, 1),
(3, 'https://example.com/image3.jpg', 2, 1),

-- Moveis
(4, 'https://example.com/image4.jpg', 2, 4),

--Tecnologia
(5, 'https://example.com/image5.jpg', 2, 2);

-- Insert advertisements
INSERT INTO ADVERTISEMENT (ID, TITLE, DESCRIPTION, DATE, MUNICIPALITY, STATUS, ITEM_ID, CLIENT_ID) VALUES
--00ec311a-9631-4221-baec-fc706af9cc95
(1, 'Frigorífico para doar - urgente', 'Frigorifico com congelador para doar. Tenho urgência que venham retirar', '2025-02-14', 'Porto', 0, 1, '00ec311a-9631-4221-baec-fc706af9cc95'),
(2, 'Máquina de lavar a loiça para doar', 'Máquina de lavar a loiça em funcionamento.', '2025-02-10', 'Porto', 1, 2, '00ec311a-9631-4221-baec-fc706af9cc95'),
(3, 'Máquina de café', 'Máquina de café da Nespresso em bom estado.', '2025-01-01', 'Porto', 1, 3, '00ec311a-9631-4221-baec-fc706af9cc95'),

-- 647fe1ef-40b5-4957-96cc-c83a4b3131da
(4, 'Sofá 3 lugares', 'Sofá de 3 lugares em ótimo estado. Ideal para a sua sala de estar.', '2025-02-10', 'Porto', 0, 4, '647fe1ef-40b5-4957-96cc-c83a4b3131da'),
(5, 'Televisão LED 40 polegadas', 'Televisão LED de 40 polegadas em excelente estado de funcionamento.', '2025-02-15', 'Porto', 0, 5, '647fe1ef-40b5-4957-96cc-c83a4b3131da');

-- Insert reservationAttempts
INSERT INTO RESERVATION_ATTEMPT (ID, ADVERTISEMENT_ID, CLIENT_ID, DATE, STATUS) VALUES

--Requests advertisement 1
(1, 1, '6da5e884-ef26-4f1b-9aea-613dfeaaad84', '2025-02-14', 0),
(2, 1, '4ee2c547-0287-41a6-8e12-b2a76bba7b5b', '2025-02-15', 2),

--Requests advertisement 3
(3, 1, '6da5e884-ef26-4f1b-9aea-613dfeaaad84', '2025-02-14', 2),
(4, 3, '6da5e884-ef26-4f1b-9aea-613dfeaaad84', '2025-02-14', 4),

--Requests advertisement 4
(5, 4, '647fe1ef-40b5-4957-96cc-c83a4b3131da', '2025-02-15', 0);