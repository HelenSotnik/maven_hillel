CREATE TABLE IF NOT EXISTS clients (
	id SERIAL CONSTRAINT clients_pk PRIMARY KEY,
	name VARCHAR(20),
	email VARCHAR(255) UNIQUE NOT NULL,
	phone BIGINT UNIQUE NOT NULL,
	about TEXT
);

CREATE TABLE IF NOT EXISTS statuses (
	id SERIAL CONSTRAINT statuses_pk PRIMARY KEY,
	alias VARCHAR(15) UNIQUE NOT NULL,
	description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS client_status (
	client_id INT NOT NULL,
	status_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
	id SERIAL CONSTRAINT accounts_pk PRIMARY KEY,
	client_id INT NOT NULL,
	number VARCHAR(20) UNIQUE NOT NULL,
	value DECIMAL NOT NULL
);

ALTER TABLE client_status
	ADD CONSTRAINT fk_client_id
		FOREIGN KEY (client_id)
		REFERENCES clients(id)
		ON DELETE CASCADE,
	ADD CONSTRAINT fk_status_id
		FOREIGN KEY (status_id)
		REFERENCES statuses(id)
		ON DELETE CASCADE;

ALTER TABLE accounts
	ADD CONSTRAINT fk_client_accounts
		FOREIGN KEY (client_id)
		REFERENCES clients(id)
		ON DELETE CASCADE;

ALTER TABLE clients
    ADD COLUMN age INT;
