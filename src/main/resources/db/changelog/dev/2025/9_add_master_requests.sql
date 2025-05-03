CREATE TABLE master_requests
(
    id                   UUID PRIMARY KEY,
    rejection_reason     TEXT,
    reviewed_by_admin_id UUID,
    user_id              UUID        NOT NULL,
    created_at           TIMESTAMPTZ NOT NULL,
    updated_at           TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_master_requests_user
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_master_requests_admin
        FOREIGN KEY (reviewed_by_admin_id) REFERENCES users (id)
            ON DELETE SET NULL
);

CREATE INDEX idx_master_requests_user ON master_requests(user_id);
CREATE INDEX idx_master_requests_admin ON master_requests(reviewed_by_admin_id);