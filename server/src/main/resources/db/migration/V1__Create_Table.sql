CREATE TABLE reminder (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    group_id BIGINT
);

CREATE TABLE reminder_group (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_date DATE NOT NULL
);
