CREATE TABLE IF NOT EXISTS customer (
    id BIGINT NOT NULL PRIMARY KEY,
    name NVARCHAR(100),
    phoneNumber NVARCHAR(20),
    notes NVARCHAR(200)
);

CREATE TABLE IF NOT EXISTS employee (
    id BIGINT NOT NULL PRIMARY KEY,
    name NVARCHAR(100)
);

CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT NOT NULL PRIMARY KEY,
    name DATE
);

CREATE TABLE IF NOT EXISTS pet (
    id BIGINT NOT NULL PRIMARY KEY,
    name NVARCHAR(100),
    birthDate DATE,
    notes NVARCHAR(200),
    petType int,
    customer_id BIGINT,
    foreign key (customer_id) references customer(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS skill (
    id BIGINT NOT NULL PRIMARY KEY,
    name NVARCHAR(100)
);

CREATE TABLE IF NOT EXISTS dayOkWeek (
    id BIGINT NOT NULL PRIMARY KEY,
    name NVARCHAR(20)
);

CREATE TABLE IF NOT EXISTS employee_skill (
    employee_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    foreign key (employee_id) references employee(id) on delete cascade,
    foreign key (skill_id) references skill(id)
);

CREATE TABLE IF NOT EXISTS employee_available (
    employee_id BIGINT NOT NULL,
    dayOfWeek_id BIGINT NOT NULL,
    foreign key (employee_id) references employee(id) on delete cascade,
    foreign key (dayOfWeek_id) references dayOkWeek(id)
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
    skill_id BIGINT NOT NULL,
    foreign key (schedule_id) references schedule(id) on delete cascade,
    foreign key (skill_id) references skill(id)
);