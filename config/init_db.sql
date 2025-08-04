

CREATE SCHEMA public;

CREATE TABLE resumes
(
    uuid      VARCHAR(36) NOT NULL,
    full_name TEXT        NOT NULL,
    PRIMARY KEY (uuid)
);


CREATE TABLE contacts
(
    id          SERIAL PRIMARY KEY,
    resume_uuid VARCHAR(36) NOT NULL REFERENCES resumes (uuid) ON DELETE CASCADE,
    type        TEXT        NOT NULL,
    value       TEXT        NOT NULL

);

CREATE UNIQUE INDEX resume_uuid_type_contacts_index ON
contacts(resume_uuid, type);

CREATE TABLE sections
(
    id          SERIAL PRIMARY KEY,
    resume_uuid VARCHAR(36) NOT NULL REFERENCES resumes (uuid) ON DELETE CASCADE,
    type        TEXT        NOT NULL,
    content     TEXT        NOT NULL
);

CREATE UNIQUE INDEX resume_uuid_type_sections_index ON
    sections (resume_uuid, type);
