CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE book_comment (
                      id uuid DEFAULT uuid_generate_v4(),
                      text text,
                      created_date timestamp DEFAULT now(),
                      book_id uuid,
                      PRIMARY KEY (id),
                      CONSTRAINT fk_book
                           FOREIGN KEY (book_id)
                           REFERENCES book(id)
                           ON DELETE CASCADE
)
