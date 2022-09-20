INSERT INTO Seminar VALUES (0, 'Java', 'Eine kleine Einfuehrung in Java');
INSERT INTO Seminar VALUES (0, 'Java DB', 'Vertiefung von Java und Datenbanken');
INSERT INTO Seminar VALUES (0, 'C++', 'Einfuehrung in die Programmierung mit C++');
INSERT INTO Seminar VALUES (0, 'JavaScript', 'Internetprogrammierung leicht gemacht');
INSERT INTO Seminar VALUES (0, 'HTML', 'Eigene Internetseiten Ruck-Zuck erstellen');

INSERT INTO Veranstaltungen VALUES (0, 1, 'Hamburg', NULL);
INSERT INTO Veranstaltungen VALUES (0, 1, 'Muenchen', NULL);
INSERT INTO Veranstaltungen VALUES (0, 2, 'Hamburg', NULL);
INSERT INTO Veranstaltungen VALUES (0, 3, 'Duesseldorf', NULL);

COMMIT;
SELECT * FROM Seminar;
SELECT * FROM Veranstaltungen;
SELECT * FROM Teilnehmer;
