CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;

insert into my_entity(id, name) values (nextval('hibernate_sequence'), 'bla');