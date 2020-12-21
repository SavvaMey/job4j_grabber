create table grabber (
    id serial primary key,
    nameVac text,
    description text,
    link text unique,
    created date
)