insert into worker (ID, NAME, BIRTHDAY, LEVEL, SALARY) values
(1,'Anton','1991-07-02','Senior', 6000),
(2, 'Viktor','2003-09-18','Junior', 800),
(3,'Vlad','1998-03-21','Middle', 4500),
(4,'Valeria','2001-08-18','Junior', 850),
(5, 'Oksana','1993-10-10', 'Trainee', 500),
(6, 'Volodymyr', '1998-09-19', 'Senior', 5500),
(7, 'Petro', '1995-12-12', 'Middle', 3000),
(8, 'Yurii', '2000-02-20', 'Middle', 3000),
(9, 'Viktoria', '2001-01-01', 'Middle', 3500),
(10, 'Vitalii', '1992-09-18', 'Senior', 7500);

insert into client (NAME) values
('John'),
('Bill'),
('Jenifer'),
('Roman'),
('Elise');

insert into project (ID,CLIENT_ID,START_DATE,FINISH_DATE) values
(1, 2, '2024-03-01', '2024-05-01'),
(2, 1, '2024-02-15', '2024-03-15'),
(3, 3, '2024-03-03', '2024-09-03'),
(4, 5, '2024-05-11', '2024-09-11'),
(5, 5, '2024-02-28', '2024-04-28'),
(6, 3, '2024-06-09', '2024-10-09'),
(7, 2, '2024-05-10', '2024-08-10'),
(8, 1, '2024-01-01', '2025-01-01'),
(9, 2, '2024-03-13', '2024-05-13'),
(10, 1, '2024-08-08','2024-12-08');
insert into project_worker (PROJECT_ID, WORKER_ID) values
(8,1),
(8,3),
(8,7),
(8,4),
(8,5),
(1,9),
(2,10),
(2,7),
(2,8),
(3,2),
(3,7),
(4,1),
(4,3),
(5,6),
(6,5),
(6,3),
(7,6),
(7,9),
(7,4),
(7,8),
(9,10),
(9,7),
(9,4),
(10,3),
(10,6);