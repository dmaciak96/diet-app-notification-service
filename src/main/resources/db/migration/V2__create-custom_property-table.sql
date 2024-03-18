create table if not exists custom_property
(
    id              uuid primary key unique,
    key             varchar(255)  not null,
    value           varchar(2550) not null,
    notification_id uuid          not null,
    foreign key (notification_id) references notification (id)
)