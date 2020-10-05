CREATE DATABASE IF NOT EXISTS critter;

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(100),
    phone_number NVARCHAR(20),
    notes NVARCHAR(200)
);

CREATE TABLE IF NOT EXISTS employee (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(100)
);

CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date DATE
);

CREATE TABLE IF NOT EXISTS pet (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(100),
    birth_date DATE,
    notes NVARCHAR(200),
    pet_type int,
    customer_id BIGINT,
    foreign key (customer_id) references customer(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS employee_skill (
    employee_id BIGINT NOT NULL,
    skill_name NVARCHAR(100) NOT NULL,
    foreign key (employee_id) references employee(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS employee_available (
    employee_id BIGINT NOT NULL,
    day_of_week_id INT NOT NULL,
    foreign key (employee_id) references employee(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS schedule_employee (
    schedule_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    foreign key (schedule_id) references schedule(id) on delete cascade,
    foreign key (employee_id) references employee(id)
);

CREATE TABLE IF NOT EXISTS schedule_pet (
    schedule_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    foreign key (schedule_id) references schedule(id) on delete cascade,
    foreign key (pet_id) references pet(id)
);

CREATE TABLE IF NOT EXISTS schedule_activity (
    schedule_id BIGINT NOT NULL,
    skill_name NVARCHAR(100) NOT NULL,
    foreign key (schedule_id) references schedule(id) on delete cascade
);