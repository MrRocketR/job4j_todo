alter table users add Column user_zone TEXT;

ALTER TABLE tasks ALTER COLUMN created TYPE TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE tasks ALTER COLUMN created SET DEFAULT now();
