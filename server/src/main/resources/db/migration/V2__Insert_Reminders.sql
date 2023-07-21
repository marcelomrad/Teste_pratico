-- Criar grupos de lembretes
INSERT INTO reminder_group (group_date) VALUES ('2023-07-21');
INSERT INTO reminder_group (group_date) VALUES ('2023-07-22');
INSERT INTO reminder_group (group_date) VALUES ('2023-07-23');
INSERT INTO reminder_group (group_date) VALUES ('2023-07-24');

-- Lembrete 1 pertence ao grupo de lembretes com ID 1 (2023-07-21)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 1', '2023-07-21', 1);

-- Lembrete 2 pertence ao grupo de lembretes com ID 2 (2023-07-22)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 2', '2023-07-22', 2);

-- Lembrete 3 pertence ao grupo de lembretes com ID 3 (2023-07-23)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 3', '2023-07-23', 3);

-- Lembrete 4 pertence ao grupo de lembretes com ID 4 (2023-07-24)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 4', '2023-07-24', 4);

-- Mais lembretes para o grupo de lembretes com ID 1 (2023-07-21)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 5', '2023-07-21', 1);
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 6', '2023-07-21', 1);

-- Mais lembretes para o grupo de lembretes com ID 2 (2023-07-22)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 7', '2023-07-22', 2);

-- Mais lembretes para o grupo de lembretes com ID 3 (2023-07-23)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 8', '2023-07-23', 3);
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 9', '2023-07-23', 3);

-- Mais lembretes para o grupo de lembretes com ID 4 (2023-07-24)
INSERT INTO reminder (name, date, group_id) VALUES ('Lembrete 10', '2023-07-24', 4);
