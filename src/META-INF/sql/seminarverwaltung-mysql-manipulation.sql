USE seminarverwaltung;
SET CHARACTER SET utf8mb4;
BEGIN;

INSERT INTO Seminar VALUES (1, "Java", "Eine kleine Einfuehrung in Java");
INSERT INTO Seminar VALUES (2, "Java DB", "Vertiefung von Java und Datenbanken");
INSERT INTO Seminar VALUES (3, "C++", "Einfuehrung in die Programmierung mit C++");
INSERT INTO Seminar VALUES (4, "JavaScript", "Internetprogrammierung leicht gemacht");
INSERT INTO Seminar VALUES (5, "HTML", "Eigene Internetseiten Ruck-Zuck erstellen");

INSERT INTO Veranstaltungen VALUES (1, 1, "Hamburg", NULL);
INSERT INTO Veranstaltungen VALUES (2, 1, "Muenchen", NULL);
INSERT INTO Veranstaltungen VALUES (3, 2, "Hamburg", NULL);
INSERT INTO Veranstaltungen VALUES (4, 3, "Duesseldorf", NULL);

COMMIT;
SELECT * FROM Seminar;
SELECT * FROM Veranstaltungen;
SELECT * FROM Teilnehmer;

