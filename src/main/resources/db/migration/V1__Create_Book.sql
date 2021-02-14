CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE book (
 id uuid DEFAULT uuid_generate_v4(),
 title varchar ,
 author varchar,
 isbn varchar,
 number_of_pages integer,
 rating integer,
 PRIMARY KEY (id)
)
