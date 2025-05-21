CREATE TABLE master_documents
(
    id          UUID PRIMARY KEY,
    type        VARCHAR(50),
    description TEXT,
    master_id   UUID NOT NULL REFERENCES master_profiles (id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ,
    updated_at  TIMESTAMPTZ
);