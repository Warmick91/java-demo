DROP SEQUENCE VeranstaltungenSequence;
DROP SEQUENCE SeminarSequence;
DROP TABLE Teilnehmer;
DROP TABLE Veranstaltungen;
DROP TABLE Seminar;


CREATE TABLE Seminar (
	SemNr INTEGER NOT NULL,
	Thema VARCHAR(30) NOT NULL,
	Beschreibung VARCHAR(250),
	PRIMARY KEY (SemNr)
);

CREATE TABLE Veranstaltungen (
	VerID INTEGER NOT NULL,
	SemNr INTEGER NOT NULL,
	Ort VARCHAR (30),
	Termin DATE,
	PRIMARY KEY (VerID),
	FOREIGN KEY (SemNr) REFERENCES Seminar (SemNr) ON DELETE CASCADE
);

CREATE TABLE Teilnehmer (
	VerID INTEGER NOT NULL,
	namen CHAR(255) NOT NULL,
	FOREIGN KEY (VerID) REFERENCES Veranstaltungen (VerID) ON DELETE CASCADE
);



CREATE SEQUENCE SeminarSequence INCREMENT BY 1 START WITH 1;

CREATE SEQUENCE VeranstaltungenSequence INCREMENT BY 1 START WITH 1;


CREATE OR REPLACE TRIGGER SeminarSequenceTrigger
BEFORE INSERT ON Seminar FOR EACH ROW
DECLARE identity NUMBER;
BEGIN
  SELECT SeminarSequence.nextval INTO identity FROM DUAL;
  :new.SemNr := identity;
END;
/

CREATE OR REPLACE TRIGGER VeranstaltungenSequenceTrigger
BEFORE INSERT ON Veranstaltungen FOR EACH ROW
DECLARE identity NUMBER;
BEGIN
  SELECT VeranstaltungenSequence.nextval INTO identity FROM DUAL;
  :new.VerID := identity;
END;
/
