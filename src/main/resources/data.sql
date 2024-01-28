--Initialize persons
INSERT INTO person (uuid, name, birth_date) VALUES ('a09f1b49-a6a8-4c5b-b079-33c9b23fd3e0','Mese Martin', '1999-07-15');
INSERT INTO person (uuid, name, birth_date) VALUES ('bf17571c-8d9d-4b2f-8f15-7f2020fc23a9','Otp Otto', '2000-07-15');
INSERT INTO person (uuid,name, birth_date) VALUES ('9c12f085-8c28-4fd4-8f08-5127637afc4f','Teszt Tamas', '2001-07-15');

--Initialize addresses
INSERT INTO address (uuid, zipcode, city, street_name, house_number, person_id, address_type) VALUES('9f3f7ca0-a59f-4ded-962f-8229055a4493', 1139, 'Budapest', 'Kossuth Lajos Utca', 1, 'a09f1b49-a6a8-4c5b-b079-33c9b23fd3e0', 'TEMPORARY');
INSERT INTO address (uuid, zipcode, city, street_name, house_number, person_id, address_type) VALUES('b074e550-e770-4a18-b106-748acb7ec8a4', 8200, 'Veszprem', 'Jozef Attila Utca', 1, 'a09f1b49-a6a8-4c5b-b079-33c9b23fd3e0', 'PERMANENT');
INSERT INTO address (uuid, zipcode, city, street_name, house_number, person_id, address_type) VALUES('6ac9c663-6a20-4256-aa93-0f79811f58c5', 1138, 'Budapest', 'Ady Endre Utca', 2, 'bf17571c-8d9d-4b2f-8f15-7f2020fc23a9', 'PERMANENT');
INSERT INTO address (uuid, zipcode, city, street_name, house_number, person_id, address_type) VALUES('24cb0ad5-d5b7-434a-a8a5-7029c059778e', 1137, 'Budapest', 'Deak Ferenc Utca', 3, '9c12f085-8c28-4fd4-8f08-5127637afc4f', 'TEMPORARY');

--Initialize contacts
INSERT INTO contact (uuid, contact_type, contact, person_id) VALUES ('0e403f6b-8e07-46ee-9771-c5cef1207176', 'EMAIL', 'teszt.email@otp.com', 'a09f1b49-a6a8-4c5b-b079-33c9b23fd3e0');
INSERT INTO contact (uuid, contact_type, contact, person_id) VALUES ('62d55646-109b-40b8-ba88-9932d417eae8', 'PHONE', '+36301234567', 'bf17571c-8d9d-4b2f-8f15-7f2020fc23a9');
INSERT INTO contact (uuid, contact_type, contact, person_id) VALUES ('0f356f0c-0440-44a6-89ff-6ac4513ac787', 'EMAIL', 'teszt.email@otp.com', '9c12f085-8c28-4fd4-8f08-5127637afc4f');