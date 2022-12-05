INSERT into tasks  (id,  description, user_item, priority_id ) values (3, 'Wash hands', 1, 2);
INSERT into tasks  (id,  description, user_item, priority_id ) values (4, 'Create Tests', 1, 1);
INSERT into tasks  (id,  description, user_item, priority_id ) values (5, 'Study', 1, 1);

insert into categories_tasks(fk_category_id, fk_task_id) values (1, 3);
insert into categories_tasks(fk_category_id, fk_task_id) values (1, 4);
insert into categories_tasks(fk_category_id, fk_task_id) values (2, 5);
insert into categories_tasks(fk_category_id, fk_task_id) values (3, 5);
