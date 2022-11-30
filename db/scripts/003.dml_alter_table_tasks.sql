 alter table tasks
 ADD user_item int not null references users(id);