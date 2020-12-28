CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);
insert into company values (1, 'volvo');
insert into company values (2, 'lenovo');
insert into company values (3, 'yell');
insert into company values (4, 'lg');
insert into company values (5, 'sobor');

insert into person values (1, 'Иван', 1);
insert into person values (2, 'Ден', 1);
insert into person values (3, 'Нико', 2);
insert into person values (4, 'Савва', 2);
insert into person values (5, 'Петр');
insert into person values (6, 'Тимур', 3);
insert into person values (7, 'Алексей', 3);
insert into person values (8, 'Рок');
insert into person values (9, 'Антон', 3);
insert into person values (10, 'Key', 5);
insert into person values (11, 'denzel', 5);

select * from company;
select * from person;

-- 1) Retrieve in a single query:
-- names of all persons that are NOT in the company with id = 5
-- company name for each person
select p.name, c.name from person as p left join company c on p.company_id = c.id
where p.company_id !=5 or p.company_id is null;

-- 2) Select the name of the company with the maximum number of persons + number of persons in this company

select   total.name, total.countPerson  from (
select company.name, count(person.id) as countPerson  from person
join company on person.company_id = company.id
group by company.name) AS total where total.countPerson = (SELECT MAX(total.countPerson) from (
select company.name, count(person.id) as countPerson  from person
join company on person.company_id = company.id group by company.name) AS total);