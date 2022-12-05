create table categories_tasks (
id serial primary key,
fk_category_id int not null REFERENCES categories (id),
fk_task_id int not null REFERENCES tasks (id)
);

