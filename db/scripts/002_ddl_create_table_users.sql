create table users (
id SERIAL PRIMARY KEY,
 name text,
 login varchar unique,
 password text
)