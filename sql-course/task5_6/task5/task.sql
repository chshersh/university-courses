drop database if exists ctd;
create database ctd;
\c ctd

create table Students (
	SId int PRIMARY KEY,
  	SName varchar(50)
);

create table Courses (
  	CId int PRIMARY KEY,
  	CName varchar(30)
);

create table Groups (
  	GId int PRIMARY KEY,
  	GName varchar(5)
);

create table Lecturers (
  	LId int PRIMARY KEY,
  	LName varchar(50)
);

create table GroupByStudent (
  	SId int,
  	GId int,
  	PRIMARY KEY (SId)
);

create table Schedule (
  	GId int,
  	CId int,
  	LId int,
  	PRIMARY KEY (GId, CId)
);

create table Marks (
  	SId int,
  	CId int,
  	Mark char(1),
  	PRIMARY KEY (SId, CId)
);

insert into Students
	(SId, SName) values
	(0, 'Ткаченко Григорий'),
	(1, 'Коваников Дмитрий'),
	(2, 'Титкова Екатерина'),
	(3, 'Бобров Дмитрий'),
	(4, 'Забашта Алексей');

insert into Courses
	(CId, CName) values 
	(0, 'Базы данных'),
	(1, 'Методы трансляции'),
	(2, 'Алгоритмы и Структуры данных');

insert into Groups 
	(GId, GName) values 
	(0, '4537'),
	(1, '4538'),
	(2, '4539');

insert into Lecturers
	(LId, LName) values
	(0, 'Корнеев Г. А.'),
	(1, 'Станкевич А. С.');

insert into Schedule
	(GId, CId, LId) values
	(0, 0, 0), (0, 1, 1), (0, 2, 1),
	(1, 0, 0), (1, 1, 1), (1, 2, 1),
	(2, 1, 0);

insert into GroupByStudent
	(SId, GId) values 
	(0, 0), (1, 1), (2, 1), (3, 1), (4, 2);

insert into Marks
	(SId, CId, Mark) values
	(0, 0, 'B'), (0, 1, 'A'),
	(1, 0, 'B'), (2, 0, 'A'), 
	(3, 1, 'B'), (3, 2, 'C');

-- 1 - студенты с заданной оценкой по базам данных
select SName from Students
             natural join Marks
             natural join Courses
       where CName = 'Базы данных' and Mark = 'B';

-- 2(a) - студенты, не имеющие оценцки по БД
select Students.SName from Students
                      left join
                      (select SId, SName from Students
                                         natural join Marks
                                         natural join Courses
                              where CName = 'Базы данных') as TDb
                      on Students.SId = TDb.SId
       where TDb.SId is NULL;


-- 2(b) - студенты, не имеющие оценцки по БД, но у которых есть БД
select TDb.SName from (select SId, SName from Students
                                         natural join GroupByStudent
                                         natural join Schedule
                                         natural join Courses
                              where CName = 'Базы данных') as TDb
                      left join
                      (select SId, SName from Students
                                         natural join Marks
                                         natural join Courses
                              where CName = 'Базы данных') as TMDb
                      on TDb.SId = TMDb.SId where TMDb.SId is NULL;

-- 3 - студенты, имеющие хотя бы одну оценку у заданного лектора.
select distinct SId, SName from Students
                           natural join GroupByStudent
                           natural join Schedule
                           natural join Marks
                           natural join Lecturers
       where LName = 'Станкевич А. С.';


-- 4 - идентификаторы студентов, не имеющих ни одной оценки у заданного лектора
select Students.SId from Students
                    left join 
                    (select distinct SId, SName from Students
                                                natural join GroupByStudent
                                                natural join Schedule
                                                natural join Marks
                                                natural join Lecturers
                                     where LName = 'Станкевич А. С.') as TMark
                    on Students.SId = TMark.SId where TMark.SId is NULL;


-- 5 - студенты, имеющие оценки по всем предметам заданного лектора
select distinct StMarks.SId from
       (select distinct SId from Lecturers
                            natural join Schedule
                            natural join Marks
               where LName = 'Станкевич А. С.') as StMarks
       left join (select distinct StMarks2.SId from
                          (select distinct SId from Lecturers
                                               natural join Schedule
                                               natural join Marks
                                  where LName = 'Станкевич А. С.') as StMarks2
                          cross join
                          (select distinct CId from Lecturers
                                               natural join Schedule
                                  where LName = 'Станкевич А. С.') as StSchTable
                          left join
                          (select distinct SId, CId from Lecturers
                                                    natural join Schedule
                                                    natural join Marks
                                  where LName = 'Станкевич А. С.') as StMarks3
                          on StMarks2.SId = StMarks3.SId and StSchTable.CId = StMarks3.CId
                        where StMarks3.SId is NULL) as HasMarksTable
      on StMarks.SId = HasMarksTable.SId
      where HasMarksTable.SId is NULL;

-- 6 - для каждого студента имя и курсы, которые он должен посещать
select distinct SName, CName from Students
                             natural join GroupByStudent
                             natural join Schedule
                             natural join Courses;

-- 7 - по лектору всех студентов, у которых он хоть что-нибудь преподавал
select distinct LName, SName from Lecturers
                             natural join Schedule
                             natural join GroupByStudent
                             natural join Students;