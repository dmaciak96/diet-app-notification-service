create table if not exists notification
(
    id                uuid primary key unique,
    version           int           NOT NULL,
    created_date      TIMESTAMP     NOT NULL,
    last_updated_date TIMESTAMP     NOT NULL,
    message           varchar(2550) NOT NULL,
    code              varchar(255)  NOT NULL
);