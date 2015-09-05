-- using PostgreSQL 9.3.5
DROP DATABASE IF EXISTS Habrahabr;
CREATE DATABASE Habrahabr;
\c Habrahabr

DROP TABLE IF EXISTS Users CASCADE;
CREATE TABLE Users(
	uid INT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	reg_date TIMESTAMP NOT NULL,

	UNIQUE(name)
);

DROP TABLE IF EXISTS Articles CASCADE;
CREATE TABLE Articles(
	aid INT PRIMARY KEY,
	author INT NOT NULL,
	cr_date TIMESTAMP NOT NULL,
	hid SMALLINT NOT NULL,
	title VARCHAR(100) NOT NULL,
	content TEXT NOT NULL,
	lid INT NOT NULL, -- язык статьи, обязательный атрибут, внешний ключ в паре с aid на таблицу значений атрибутов

	FOREIGN KEY (author) REFERENCES Users(uid) ON DELETE CASCADE
);
CREATE INDEX articleAuthors ON Articles(author);

DROP TABLE IF EXISTS Favourites CASCADE;
CREATE TABLE Favourites(
	uid INT,
	aid INT,

	PRIMARY KEY (uid, aid),
	FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
	FOREIGN KEY (aid) REFERENCES Articles(aid) ON DELETE CASCADE
);
CREATE INDEX favArts ON Favourites(aid);

DROP TABLE IF EXISTS Comments CASCADE;
CREATE TABLE Comments(
	cid BIGINT PRIMARY KEY,
	uid INT NOT NULL,
	aid INT NOT NULL,
	cr_date TIMESTAMP NOT NULL,
	reply BIGINT,
	content TEXT NOT NULL,

	FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
	FOREIGN KEY (aid) REFERENCES Articles(aid) ON DELETE CASCADE,
	FOREIGN KEY (reply) REFERENCES Comments(cid) ON DELETE CASCADE
);
CREATE INDEX commentArticle ON Comments(aid); -- часто нужно получать комментарии к статье

DROP TABLE IF EXISTS Habs CASCADE;
CREATE TABLE Habs(
	hid SMALLINT PRIMARY KEY,
	name VARCHAR(100),

	UNIQUE(name)
);

DROP TABLE IF EXISTS ArticleHabs CASCADE;
CREATE TABLE ArticleHabs(
	aid INT,
	hid SMALLINT,

	PRIMARY KEY (aid, hid),
	FOREIGN KEY (aid) REFERENCES Articles(aid) ON DELETE CASCADE,
	FOREIGN KEY (hid) REFERENCES Habs(hid) ON DELETE CASCADE
);
CREATE INDEX artHabs ON ArticleHabs(aid);

-- DEFERRABLE CONSTRAINT добавлен из-за рекурсивных ограничений внешних ключей
ALTER TABLE Articles
	ADD CONSTRAINT fk_arthab
	FOREIGN KEY (aid, hid) REFERENCES ArticleHabs(aid, hid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;

DROP TABLE IF EXISTS Tags CASCADE;
CREATE TABLE Tags(
	tid SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	aid INT NOT NULL,

	UNIQUE(name)
);

DROP TABLE IF EXISTS ArticleTags CASCADE;
CREATE TABLE ArticleTags(
	tid INT,
	aid INT,

	PRIMARY KEY (aid, tid),
	FOREIGN KEY (aid) REFERENCES Articles(aid) ON DELETE CASCADE,
	FOREIGN KEY (tid) REFERENCES Tags(tid) ON DELETE CASCADE
);
CREATE INDEX artTabs ON ArticleTags(aid);

ALTER TABLE Tags
	ADD CONSTRAINT fk_arttag
	FOREIGN KEY (tid, aid) REFERENCES ArticleTags(tid, aid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;

-- функция, которая принимает id статьи и название тега,
-- добавляет тег в таблицу Tags c новым id, если он там не встречался,
-- а затем добавляет в таблицу ArticleTags соответствие между статьёй и тегом
DROP FUNCTION IF EXISTS addTag(integer, VARCHAR(100));
CREATE FUNCTION addTag(aid integer, tagName VARCHAR(100)) RETURNS void AS $$
DECLARE
	tagID integer;
BEGIN
	SELECT tid INTO tagID FROM Tags WHERE name=tagName;
	IF tagID IS NULL THEN
		INSERT INTO Tags
			(name, aid) VALUES (tagName, aid)
			RETURNING tid INTO tagID;
	END IF;
	INSERT INTO ArticleTags
		(tid, aid) VALUES (tagID, aid);
  	
END; 
$$ LANGUAGE plpgsql;

-- практическое задание на экзамене (конец старой части)
-- 14. Модель сущность-атрибут-значение
DROP TABLE IF EXISTS ArticleAttributes CASCADE;
CREATE TABLE ArticleAttributes(
	attrId INT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	type VARCHAR(20) NOT NULL,

	UNIQUE (name)
);

INSERT INTO ArticleAttributes
	(attrId, name, type) VALUES
	(1, 'artLanguage', 'enumLang'), -- обязательный атрибут для статьи, поэтому прокинем id на таблицу значений атрибутов
	(2, 'artLink', 'string'),       
	(3, 'artType', 'enumType');

-- таблица для ссылок на статьи
DROP TABLE IF EXISTS ArticleLinks CASCADE;
CREATE TABLE ArticleLinks(
	aid INT,
	hyperlink VARCHAR(100) NOT NULL,
	attrId INT NOT NULL CHECK(attrID = 2),

	PRIMARY KEY (aid, hyperlink),
	FOREIGN KEY (aid) REFERENCES Articles(aid) ON DELETE CASCADE,
	FOREIGN KEY (attrId) REFERENCES  ArticleAttributes(attrId) ON DELETE CASCADE
);

-- enum допустимых языков
DROP TABLE IF EXISTS Langs CASCADE;
CREATE TABLE Langs(
	lid INT PRIMARY KEY,
	name VARCHAR(10),

	UNIQUE (name)
);

INSERT INTO Langs
	(lid, name) VALUES
	(1, 'Английский'), 
	(2, 'Русский');

-- таблица для ссылок на статьи
DROP TABLE IF EXISTS ArticleLangs CASCADE;
CREATE TABLE ArticleLangs(
	aid INT PRIMARY KEY,
	lid INT NOT NULL,
	attrId INT NOT NULL CHECK(attrID = 1),

	FOREIGN KEY (aid) REFERENCES Articles(aid) ON DELETE CASCADE,
	FOREIGN KEY (lid) REFERENCES Langs(lid) ON DELETE CASCADE,
	FOREIGN KEY (attrId) REFERENCES  ArticleAttributes(attrId) ON DELETE CASCADE
);

ALTER TABLE Articles
	ADD CONSTRAINT fk_artlang
	FOREIGN KEY (aid) REFERENCES ArticleLangs(aid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;

-- продолжение старой части

INSERT INTO Users
	(uid, name, reg_date) VALUES
	(1, 'alizar', clock_timestamp()),
	(2, 'kfedorod', clock_timestamp()),
	(3, 'milfgard', clock_timestamp()),
	(4, 'Alex Walker', clock_timestamp());

INSERT INTO Habs
	(hid, name) VALUES
	(1, 'DIY или Сделай Сам'),
	(2, 'Java'),
	(3, 'Haskell'),
	(4, 'Алгоритмы');

BEGIN;
	INSERT INTO Articles
		(aid, author, cr_date, hid, title, content, lid) VALUES
		(1, 1, '2011-05-16 15:36:38', 1, 'Статья 1', 'Статья про приватный монитор', 2),
		(2, 2, '2015-01-15 01:02:03', 3, 'Статья 2', 'Статья про теоркат', 2),
		(3, 4, '2015-01-15 11:12:13', 2, 'Статья 3', 'Легковесная библиотека для Warning сообщений от javac через аннотацию', 2),
		(4, 4, '2015-01-15 01:02:03', 2, 'Статья 4', 'MyBatis как более быстрая альтернатива Hibernate', 2);
	INSERT INTO ArticleHabs
		(aid, hid) VALUES
		(1, 1), (2, 3), (3, 2), (4, 2), -- обязательные
		(3, 4), (2, 4);                 -- дополнительные
	INSERT INTO ArticleLangs
		(aid, lid, attrId) VALUES
		(1, 2, 1),
		(2, 1, 1),
		(3, 2, 1),
		(4, 2, 1);
COMMIT;

INSERT INTO Favourites
	(uid, aid) VALUES
	(1, 2), (1, 3), (1, 4),
	(2, 1), (2, 4),
	(3, 1), (3, 2), (3, 3), (3, 4),
	(4, 1), (4, 2), (4, 4);

INSERT INTO Comments
	(cid, uid, aid, cr_date, reply, content) VALUES
	(1, 1, 1, clock_timestamp(), NULL, 'Отличная статья!'),
	(2, 2, 1, clock_timestamp(), NULL, 'У меня не получилось так сделать'),
	(3, 1, 1, clock_timestamp(), 2, 'Попробуй вот это и вот это'),
	(4, 2, 2, clock_timestamp(), NULL, 'Ребята, принимаю ваши предложения для следующей темы'),
	(5, 3, 2, clock_timestamp(), 4, 'Напишите про композицию функций'),
	(6, 2, 2, clock_timestamp(), 5, 'Спасибо, напишу!'),
	(7, 3, 4, clock_timestamp(), NULL, 'Скажите, а будет продолжение?');

BEGIN;
	SELECT addTag(1, 'теги никто не читает');
	SELECT addTag(2, 'фп');
	SELECT addTag(2, 'теги никто не читает');
	SELECT addTag(3, 'теги никто не читает');
	SELECT addTag(4, 'базы данных');
	SELECT addTag(4, 'теги никто не читает');
COMMIT;

--SELECT * FROM Users;
--SELECT * FROM Habs;
--SELECT * FROM ArticleHabs;
--SELECT * FROM Tags;
--SELECT * FROM ArticleTags;
--SELECT * FROM Favourites;
--SELECT * FROM Articles;

-- Примеры запросов
-- 1) Все статьи по данному Хабу
SELECT
    Articles.aid, 
    Articles.author, 
    Articles.title, 
    Articles.content, 
    Articles.cr_date 
FROM
	Articles INNER JOIN (SELECT * FROM ArticleHabs WHERE hid=2) AS AH ON Articles.aid=AH.aid;
-- это довольно частый запрос, нужно добавить хэш-индекс в ArticleHabs 
-- для быстрого поиска соответствующей статьи

-- 2) Все пользователи, которые написали хотя бы одну статью
--EXPLAIN ANALYZE
SELECT 
    UA.uid, UA.name
  FROM
	(Users LEFT JOIN Articles ON Users.uid=Articles.author) AS UA
  WHERE UA.author IS NULL;

-- эквивалентный запрос, может быть быстрее в некоторых случаях
--EXPLAIN ANALYZE
SELECT * FROM Users
WHERE NOT EXISTS(SELECT 1 FROM Articles WHERE Articles.author=Users.uid);

-- 3) Количество различных пользователей, прокомментировавших статью
--EXPLAIN ANALYZE
SELECT count(DISTINCT uid) FROM Comments WHERE aid=1;
--EXPLAIN ANALYZE
SELECT count(*) FROM (SELECT DISTINCT(uid) FROM Comments WHERE aid=1) AS DU; -- иногда быстрей

-- 4) Самые популярные статьи за последнюю неделю
DO $$ 
    DECLARE currentTime TIMESTAMP;
BEGIN
	currentTime = clock_timestamp();

	DROP TABLE IF EXISTS LocalVar;
    CREATE TEMPORARY TABLE LocalVar AS

	SELECT 
		ArtFav.art_id,
		count(ArtFav.uid) AS Likes
	FROM (  
	    (SELECT Articles.aid AS art_id
		  FROM Articles 
		  WHERE cr_date BETWEEN currentTime - INTERVAL '1 week' AND currentTime
	    ) AS RecentArts 
	  INNER JOIN 
	    Favourites 
	  ON RecentArts.art_id=Favourites.aid) AS ArtFav
	  GROUP BY
	  	art_id
	  ORDER BY Likes DESC;
END $$;

SELECT * FROM LocalVar; 

-- 5) Пользователи, которые комментировали свои статьи
SELECT DISTINCT ArtComment.auid AS uid FROM ((
	SELECT
		Articles.aid AS AID,
		Articles.author AS AUID
	FROM 
		Articles
	) AS ArticleIds INNER JOIN (
	SELECT 
		Comments.uid AS CUID,
		Comments.aid AS CAID
	FROM 
		Comments 
	) AS CommentsIDS 
	ON CUID=AUID AND CAID=AID) AS ArtComment;