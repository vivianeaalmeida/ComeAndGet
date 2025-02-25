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
(1, 'uploads/images/img_03.jpeg', 1, 1),
(2, 'uploads/images/1693400873733.png', 2, 1),
(3, 'uploads/images/0001.jpg', 2, 1),

-- Moveis
(4, 'uploads/images/IMG_0092.jpg', 2, 4),

--Tecnologia
(5, 'uploads/images/tv.jpeg', 2, 2),
(6, 'uploads/images/portatil.jpg', 1, 2),

-- Roupas
(7, 'uploads/images/casaco.jpg', 2, 3),

-- Livros e Material Escolar
(8, 'uploads/images/livros.jpg', 1, 7),

-- Ferramentas e Bricolage
(9, 'uploads/images/kit.jpg', 2, 5),

-- Brinquedos e Jogos
(10, 'uploads/images/carrinho.jpg', 1, 6),

-- Utensílios Domésticos
(11, 'uploads/images/panelas.jpg', 2, 8),
(12, 'uploads/images/panelapressao.jpg', 1, 8);

-- Insert advertisements
INSERT INTO ADVERTISEMENT (ID, TITLE, DESCRIPTION, DATE, MUNICIPALITY, STATUS, ITEM_ID, CLIENT_ID) VALUES
--09469a66-0b32-42ba-804a-33c10ec03982
(1, 'Frigorífico para doar - urgente', 'Frigorifico com congelador para doar. Tenho urgência que venham retirar', '2025-02-14', 'Porto', 0, 1, '09469a66-0b32-42ba-804a-33c10ec03982'),
(2, 'Máquina de lavar a loiça', 'Máquina de lavar a loiça em funcionamento.', '2025-02-10', 'Porto', 1, 2, '09469a66-0b32-42ba-804a-33c10ec03982'),
(3, 'Máquina de café', 'Máquina de café da Nespresso em bom estado.', '2025-01-01', 'Porto', 1, 3, '09469a66-0b32-42ba-804a-33c10ec03982'),

-- 1f5ba815-4d84-4ba2-a08d-5557837655e7
(4, 'Sofá 3 lugares', 'Sofá de 3 lugares em ótimo estado. Ideal para a sua sala de estar.', '2025-02-10', 'Lisboa', 0, 4, '1f5ba815-4d84-4ba2-a08d-5557837655e7'),
(5, 'Televisão LED 40 polegadas', 'Televisão LED de 40 polegadas em excelente estado de funcionamento.', '2025-02-15', 'Lisboa', 0, 5, '1f5ba815-4d84-4ba2-a08d-5557837655e7'),

--167c98f0-e74f-4d89-9eed-f07ea7234b7f
(6, 'Computador portátil - bom estado', 'Portátil HP com bom desempenho, ideal para estudantes.', '2025-02-18', 'Braga', 0, 6, '167c98f0-e74f-4d89-9eed-f07ea7234b7f'),
(7, 'Casaco de inverno tamanho M', 'Casaco quente, perfeito para o frio.', '2025-02-18', 'Amarante', 0, 7, '167c98f0-e74f-4d89-9eed-f07ea7234b7f'),
(8, 'Livros escolares', 'Vários livros do ensino básico para doação.', '2025-02-18', 'Porto', 0, 8, '167c98f0-e74f-4d89-9eed-f07ea7234b7f'),
(9, 'Kit de ferramentas básico', 'Kit com chave de fendas, alicate e martelo.', '2025-02-18', 'Braga', 0, 9, '167c98f0-e74f-4d89-9eed-f07ea7234b7f'),
(10, 'Carrinho de brinquedo elétrico', 'Carrinho elétrico para crianças de 3 a 6 anos.', '2025-02-18', 'Coimbra', 0, 10, '167c98f0-e74f-4d89-9eed-f07ea7234b7f'),
(11, 'Conjunto de panelas inox', 'Conjunto com 3 panelas de inox em bom estado.', '2025-02-18', 'Porto', 0, 11, '167c98f0-e74f-4d89-9eed-f07ea7234b7f'),

--dac79cce-277d-48a6-b8db-eb88d9b192ea
(12, 'Panela de pressão', 'Panela de pressão com alguma ferrugem, mas pronta a ser reabilitada', '2025-02-20', 'Porto', 0, 12, 'dac79cce-277d-48a6-b8db-eb88d9b192ea');


-- Insert reservationAttempts
INSERT INTO RESERVATION_ATTEMPT (ID, ADVERTISEMENT_ID, CLIENT_ID, DATE, STATUS) VALUES

-- 09469a66-0b32-42ba-804a-33c10ec03982
--Requests advertisement 1
(1, 1, '167c98f0-e74f-4d89-9eed-f07ea7234b7f', '2025-02-14', 0),
(2, 1, '272925d6-2816-4e18-9061-c360104e8dfa', '2025-02-15', 2),

--Requests advertisement 2
(3, 2, '272925d6-2816-4e18-9061-c360104e8dfa', '2025-01-10', 1),

--Requests advertisement 3
(4, 1, '272925d6-2816-4e18-9061-c360104e8dfa', '2025-01-14', 2),
(5, 3, '167c98f0-e74f-4d89-9eed-f07ea7234b7f', '2025-01-15', 4),

--1f5ba815-4d84-4ba2-a08d-5557837655e7
--Requests advertisement 4
(6, 4, 'd9ae1423-3844-4959-81b1-7cd802902c62', '2025-02-15', 0),

--167c98f0-e74f-4d89-9eed-f07ea7234b7f
--Requests advertisement 6
(7, 6, 'd9ae1423-3844-4959-81b1-7cd802902c62', '2025-02-20', 1),
(8, 6, 'b6ee76ff-85d9-4954-a6bb-0b9a9a395f70', '2025-02-21', 2),
(9, 6, 'dac79cce-277d-48a6-b8db-eb88d9b192ea', '2025-02-22', 0),
('b763f623-48f7-494f-a303-8dc25b12b4fb', 1, '290648c4-badb-47cd-9d6d-445dbe839abb', '2025-02-24', 0),
('pp63f623-48f7-494f-a303-8dc25b12b4mm', 6, '290648c4-badb-47cd-9d6d-445dbe839abb', '2025-02-24', 1),
(78, 6, '290648c4-badb-47cd-9d6d-445dbe839abb', '2025-02-22', 3);