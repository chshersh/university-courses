drop database if exists ctd;
create database ctd;
\c ctd

create table Students(
    id SERIAL PRIMARY KEY,
    fio varchar(100) NOT NULL,
    doc varchar(50),
    phone varchar(20),
    group_id int
);

create table Professors(
    id SERIAL PRIMARY KEY,
    fio varchar(100) NOT NULL,
    doc varchar(50)
);

create table Courses(
    id SERIAL PRIMARY KEY,
    name varchar(30) NOT NULL,
    term int NOT NULL,
    prof_id int REFERENCES Professors,
    UNIQUE (name, term)
);

create table StudyProgram(
    group_id int,
    course_id int REFERENCES Courses,
    PRIMARY KEY (group_id, course_id)
);

create table Groups(
    id SERIAL PRIMARY KEY,
    name varchar(5) NOT NULL,
    course_id int,
    FOREIGN KEY (id, course_id) REFERENCES StudyProgram
);

alter table StudyProgram add FOREIGN KEY (group_id)
    REFERENCES Groups;
alter table Groups add FOREIGN KEY (id, course_id)
    REFERENCES StudyProgram(group_id, course_id);
alter table Students add FOREIGN KEY (group_id)
    REFERENCES Groups;

create table Rating(
    student_id int REFERENCES Students,
    course_id int REFERENCES Courses,
    rate DECIMAL(5, 2),
    PRIMARY KEY (student_id, course_id)
);

insert into Students(fio) values ('Закирзянов Илья');
select * from Students;

insert into Professors
    (fio) values
    ('Кохась Константин Петрович');
select * from Professors;

insert into Courses
    (name, term, prof_id) values
    ('Математический анализ', 1, 1),
    ('Математический анализ', 2, 1);
select * from Courses;

insert into Groups
    (name) values
    ('1538');
select * from Groups;

insert into StudyProgram
    (group_id, course_id) values
    (1, 1),
    (1, 2);
select * from StudyProgram;

update Groups set course_id = 2 where name = '1538';
select * from Groups;

update Students set group_id = 1 where id = 1;
select * from Students;

insert into Rating
    (student_id, course_id, rate) values
    (1, 1, 92.01),
    (1, 2, 84.15);
select * from Rating;

select fio, rate
    from Students natural join Rating;
select name, course_id, group_id
    from Groups natural join StudyProgram;
