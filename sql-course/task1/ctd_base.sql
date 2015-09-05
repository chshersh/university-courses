drop database if exists ctd;
create database ctd;
\c ctd

create table Groups(
    group_id int,
    group_no char(4)
);

create table Students(
    student_id int,
    name varchar(30),
    group_id int
);

-- table constraints
alter table Groups
    add constraint group_id_unique unique(group_id);
alter table Students add foreign key (group_id)
    references Groups (group_id);

insert into Groups
    (group_id, group_no) values
    (1, '4538'),
    (2, '4539');

insert into Students
    (student_id, name, group_id) values
    (1, 'Mechanikov Denis', 1),
    (2, 'Piankova Ulia', 2),
    (3, 'Baglai Bogdan', 2);

-- select examples
select group_id, group_no from Groups;
select * from Students;

select name, group_no
    from Students natural join Groups;
select Students.name, Groups.group_no
    from Students
        inner join Groups
        on Students.group_id = Groups.group_id;

insert into Students
    (student_id, name, group_id) values
    (4, 'Иван Петров', 1),
    (5, 'Иван Сидоров', 1),
    (6, 'Яков Сергеев', 1);

select count(*) from Groups;
select count(*) from Students;
select count(*) from Students where name like '%ван%' or name like '%oв';

delete from Groups;
delete from Students;
delete from Groups;


insert into Groups
    (group_id, group_no) values
    (1, '1537'),
    (2, '1538'),
    (3, '1539');

insert into Students
    (student_id, name, group_id) values
    (1, 'A', 1),
    (2, 'B', 2),
    (3, 'C', 2),
    (4, 'D', 3),
    (5, 'E', 3),
    (6, 'F', 3);

select group_no, count(*)
from Groups inner join Students on Groups.group_id = Students.group_id
group by group_no order by group_no desc;
