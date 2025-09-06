CREATE SEQUENCE IF NOT EXISTS seq_roles_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_users_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_students_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_teachers_id
    START WITH 1
    INCREMENT BY 1;


CREATE TABLE roles (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_roles_id'),
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_users_id'),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id BIGINT NOT NULL ,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);


CREATE TABLE students (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_students_id'),
    name VARCHAR(50) NOT NULL,
    user_id BIGINT UNIQUE ,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE teachers (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_teachers_id'),
    name VARCHAR(50) NOT NULL,
    user_id BIGINT UNIQUE ,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE teachers_students (
    teacher_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (teacher_id, student_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
INSERT INTO roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_TEACHER'),
    ('ROLE_STUDENT');
