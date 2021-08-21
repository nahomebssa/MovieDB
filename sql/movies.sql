-- Drop any existing tables.
drop table Members cascade constraints;
drop table MemberProfile cascade constraints;
drop table Movie cascade constraints;
drop table Actor cascade constraints;
drop table Watch cascade constraints;
drop table Movie_genre cascade constraints;
drop table Likes cascade constraints;
drop table Starred_by cascade constraints;
drop view MovieRatings;

-- Add tables.

-- Member Account
create table Members(
    Member_ID       VARCHAR2(10) NOT NULL,
    First_name      VARCHAR2(20) NOT NULL,
    Last_name       VARCHAR2(20) NOT NULL,
    Card_number     VARCHAR2(16) NOT NULL,
    exp_date        VARCHAR2(5),
    PRIMARY KEY(Member_ID)
);

-- [weak] MemberProfile
-- Represents a profile under a member
create table MemberProfile(
    Member_ID       VARCHAR2(10) NOT NULL, -- the member the profile belongs to
    Profile_name    VARCHAR2(20) NOT NULL,
    PRIMARY KEY(Member_ID, Profile_name),
    FOREIGN KEY(Member_ID) references Members(Member_ID)
        ON DELETE CASCADE
);

 -- Trigger for Profile limit
 CREATE OR REPLACE TRIGGER notTooManyProfiles
    BEFORE INSERT ON MemberProfile
    FOR EACH ROW
    DECLARE
            profile_count   INTEGER;
            Too_many        Exception;
    BEGIN
            SELECT COUNT(*) INTO profile_count
            FROM MemberProfile
            WHERE Member_ID = :NEW.Member_ID;
            IF profile_count >= 5 THEN
        RAISE Too_many;
            END IF;
    EXCEPTION 
            WHEN Too_many THEN
                    Raise_application_error(-20001, 'Too many profiles!');
        END; 

 /

 -- Movie
-- Represents a movie
create table Movie(
    Movie_ID        VARCHAR2(10) NOT NULL,
    Movie_name      VARCHAR2(50) NOT NULL,
    Movie_year      INTEGER,
    Producer        VARCHAR2(30),
    avg_rating          FLOAT,
    PRIMARY KEY(Movie_ID)
);


-- Actor
-- Represents a movie actor
create table Actor(
    Actor_ID        VARCHAR2(35) NOT NULL,
    First_name      VARCHAR(20),
    Last_name       VARCHAR(20),
    PRIMARY KEY(Actor_ID)
);
 
 
create table Watch(
    Member_ID       VARCHAR2(10),
    Profile_name    VARCHAR2(15) NOT NULL,
    Movie_ID        VARCHAR2(10) NOT NULL,
    rating          FLOAT CHECK(rating >= 1 AND rating <= 5),
    FOREIGN KEY(Member_ID, Profile_name) REFERENCES MemberProfile(Member_ID, Profile_name)
);

-- view to calculate the average rating of movies
CREATE VIEW MovieRatings AS
SELECT   movie_id, AVG(rating) as movie_rating
FROM     watch
GROUP BY movie_id;


-- MovieGenre
-- Represents a movie genre
create table Movie_genre(
    Movie_ID        VARCHAR2(10) NOT NULL,
    Movie_genre     VARCHAR2(15),
    FOREIGN KEY(Movie_ID) REFERENCES Movie(Movie_ID)
);
 
-- Likes
-- Represents a profile's favorite genres
create table Likes(
    Member_ID       VARCHAR(10) NOT NULL,
    Profile_name    VARCHAR2(10) NOT NULL, 
    Movie_genre     VARCHAR2(15),
    FOREIGN KEY(Member_ID, Profile_name) REFERENCES MemberProfile(Member_ID, Profile_name)
--    FOREIGN KEY(Profile_name) REFERENCES MemberProfile(Profile_name)
);

-- Starred_by
-- Records which actors starred in which movies
create table Starred_by(
    Movie_ID        VARCHAR2(10) NOT NULL,    -- the movie the actor starred in
    Actor_ID        VARCHAR2(35),             -- ID of the starring actor
    FOREIGN KEY(Movie_ID) REFERENCES Movie(Movie_ID),
    FOREIGN KEY(Actor_ID) REFERENCES Actor(Actor_ID)
);
 
 
 -- insert sample data
insert into Members values('Smith03','Joe', 'Smith','1234567891012365','12/22');
insert into Members values('SWhite','Steve','White','4779852259874156', '08/25');
insert into Members values('JReyes52','Julia','Reyes','5123456978521445','05/28');
insert into Members values('JBlack01','Jason','Black','5664789632255697','04/22');
insert into Members values('TOneill','Tasnia','Oneill','6411886652344785','06/21');
insert into Members values('TomHoll','Tom','Holland','8774455633559785','12/23');

insert into MemberProfile values('Smith03','MOM');
insert into MemberProfile values('Smith03','DAD');
insert into MemberProfile values('Smith03','KIDS');
insert into MemberProfile values('Smith03','Allie');
insert into MemberProfile values('Smith03','Joe');
insert into MemberProfile values('Smith03','Walter');
insert into MemberProfile values('SWhite','Steve');
insert into MemberProfile values('SWhite','Alice');
insert into MemberProfile values('JBlack01','Me');
insert into MemberProfile values('JBlack01','Friend1');
insert into MemberProfile values('JReyes52','Julia');
insert into MemberProfile values('JReyes52','Kevin');
insert into MemberProfile values('JReyes52','Kids');
insert into MemberProfile values('TOneill','Blue');
insert into MemberProfile values('TOneill','Green');
insert into MemberProfile values('TomHoll','Mom');
insert into MemberProfile values('TomHoll','Dad');
insert into MemberProfile values('TomHoll','Kid');

insert into Movie values('01','The Shawshank Redemption',1994,'Frank Darabont',0);
insert into Movie values('02','The Godfather',1972,'Francis Ford Coppola',0);
insert into Movie values('03','The Dark Knight',2008,'Christopher Nolan',0);
insert into Movie values('04','Pulp Fiction',1994,'Quentin Tarantino',0);
insert into Movie values('05','Fight Club',1999,'David Fincher',0);
insert into Movie values('06','Forrest Gump',1994,'Robert Zemeckis',0);
insert into Movie values('07','Inception',2010,'Christopher Nolan',0);
insert into Movie values('08','Star Wars: Episode V - The Empire Strikes Back', 1980 ,'Irvin Kershner',0);
insert into Movie values('09','Spirited Away',2001,'Hayao Miyazaki',0);
insert into Movie values('10','Back to the Future',1985,'Robert Zemeckis',0);

insert into Actor values('TimRobbins','Tim','Robbins');
insert into Actor values('MarlonBrando','Marlon','Brando');
insert into Actor values('AlPacino','Al','Pacino');
insert into Actor values('ChristianBale','Christian','Bale');
insert into Actor values('HeathLedger','Heath','Ledger');
insert into Actor values('JohnTravolta','John','Travolta');
insert into Actor values('BradPitt','Brad','Pitt');
insert into Actor values('EdwardNorton','Edward','Norton');
insert into Actor values('TomHanks','Tom','Hanks');
insert into Actor values('RobinWright','Robin','Wright');
insert into Actor values('LeoDicaprio','','');
insert into Actor values('JosephGordon','Joseph','Gordon-Levitt');
insert into Actor values('DaveighChase','Daveigh','Chase');
insert into Actor values('MarkHamill','Mark','Hamill');
insert into Actor values('HarrisonFord','Harrison','Ford');
insert into Actor values('CarrieFisher','Carrie','Fisher');
insert into Actor values('MichaelJ','Michael','Fox');
insert into Actor values('ChrisLloyd','Christopher','Lloyd');
insert into Actor values('UmaThurman','Uma','Thurman');
insert into Actor values('MorganFreeman','Morgan','Freeman');


insert into Movie_genre values('02','Crime');
insert into Movie_genre values('02','Drama');
insert into Movie_genre values('01','Drama');
insert into Movie_genre values('03','Action');
insert into Movie_genre values('03','Crime');
insert into Movie_genre values('04','Crime');
insert into Movie_genre values('05','Drama');
insert into Movie_genre values('06','Drama');
insert into Movie_genre values('06','Romance');
insert into Movie_genre values('07','Sci-Fi');
insert into Movie_genre values('07','Adventure');
insert into Movie_genre values('08','Fantasy');
insert into Movie_genre values('08','Action');
insert into Movie_genre values('09','Animation');
insert into Movie_genre values('10','Sci-fi');

insert into Likes values('Smith03','DAD','Action');
insert into Likes values('Smith03','KIDS','Animation');
insert into Likes values('SWhite','Steve','Sci-fi');
insert into Likes values('SWhite','Alice','Drama');
insert into Likes values('JBlack01','Me','Adventure');
insert into Likes values('JBlack01','Friend1','Romance');
insert into Likes values('TOneill','Blue','Action');
insert into Likes values('TomHoll','Kid','Animation');

insert into Starred_by values('01','TimRobbins');
insert into Starred_by values('01','MorganFreeman');
insert into Starred_by values('02','MarlonBrando');
insert into Starred_by values('02','AlPacino');
insert into Starred_by values('03','ChristianBale');
insert into Starred_by values('03','HeathLedger');
insert into Starred_by values('04','JohnTravolta');
insert into Starred_by values('04','UmaThurman');
insert into Starred_by values('05','BradPitt');
insert into Starred_by values('05','EdwardNorton');
insert into Starred_by values('06','TomHanks');
insert into Starred_by values('06','RobinWright');
insert into Starred_by values('07','LeoDicaprio');
insert into Starred_by values('07','JosephGordon');
insert into Starred_by values('08','MarkHamill');
insert into Starred_by values('08','HarrisonFord');
insert into Starred_by values('08','CarrieFisher');
insert into Starred_by values('09','DaveighChase');
insert into Starred_by values('10','ChrisLloyd');

insert into Watch values('Smith03','KIDS','09','4');
insert into Watch values('SWhite','Steve','02','3.5');
insert into Watch values('JReyes52','Julia','01','2');
insert into Watch values('Smith03','DAD','07','5');
insert into Watch values('TomHoll','Mom','04','4.5');
insert into Watch values('Smith03','DAD','01','3');
insert into Watch values('Smith03','DAD','02','4');
insert into Watch values('JReyes52','Julia','05','3');


 --print tables
 select * from Members;
 select * from MemberProfile;
 select * from Movie;
 select * from Actor;
 select * from Watch;
 select * from Movie_genre;
 select * from Likes;
 select * from Starred_by;
 select * from MovieRatings;


