CREATE TABLE event (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP
);

CREATE TABLE family_member (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE event_family_member (
    event_id UUID NOT NULL REFERENCES event (id),
    family_member_id UUID NOT NULL REFERENCES family_member (id),
    PRIMARY KEY (event_id, family_member_id)
);
