CREATE TABLE event
(
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title          VARCHAR(256)             NOT NULL UNIQUE,
    event_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    max_enrolments INTEGER                  NOT NULL,

    created_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by     VARHCAR(256) NOT NULL
);

CREATE TABLE event_enrolment
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    event_id   UUID REFERENCES event (id) NOT NULL,
    id_code    VARCHAR(11)                NOT NULL,
    first_name VARCHAR(256)               NOT NULL,
    last_name  VARCHAR(256)               NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE   NOT NULL,

    UNIQUE (id, id_code)
);

