DROP DATABASE IF EXISTS seminarverwaltung;
CREATE DATABASE seminarverwaltung;
USE seminarverwaltung;

CREATE TABLE Seminar (
	SemNr BIGINT NOT NULL AUTO_INCREMENT,
	Thema VARCHAR(30) NOT NULL,
	Beschreibung VARCHAR(250) NOT NULL,
	PRIMARY KEY (SemNr)
);


CREATE TABLE Veranstaltungen (
	VerID BIGINT NOT NULL AUTO_INCREMENT,
	SemNr BIGINT NOT NULL,
	Ort VARCHAR(30) NULL,
	Termin DATE NULL,
	PRIMARY KEY (VerID),
	FOREIGN KEY (SemNr) REFERENCES Seminar (SemNr) ON DELETE CASCADE
);


CREATE TABLE Teilnehmer (
	VerID BIGINT NOT NULL,
	Namen CHAR(255) NOT NULL,
	PRIMARY KEY (VerID, Namen),
	FOREIGN KEY(VerID) REFERENCES Veranstaltungen (VerID)
);