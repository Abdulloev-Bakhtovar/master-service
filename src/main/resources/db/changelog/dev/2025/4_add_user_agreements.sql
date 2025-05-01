CREATE TABLE user_agreements
(
    id                      UUID        PRIMARY KEY,
    personal_data_consent   BOOLEAN     NOT NULL,
    notifications_allowed   BOOLEAN     NOT NULL,
    location_access_allowed BOOLEAN     NOT NULL,
    service_terms_accepted  BOOLEAN,
    service_rules_accepted  BOOLEAN,
    user_id                 UUID        NOT NULL,
    created_at              TIMESTAMPTZ NOT NULL,
    updated_at              TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_user_agreements_user
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_user_agreements_user
        UNIQUE (user_id)
);

CREATE INDEX idx_user_agreements_user ON user_agreements(user_id);