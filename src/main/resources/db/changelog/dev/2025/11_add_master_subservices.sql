CREATE TABLE master_subservices
(
    id            UUID PRIMARY KEY,
    master_id     UUID        NOT NULL,
    service_id    UUID        NOT NULL,
    subservice_id UUID        NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL,
    updated_at    TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_master_subservices_master
        FOREIGN KEY (master_id) REFERENCES master_profiles (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_master_subservices_service
        FOREIGN KEY (service_id) REFERENCES service_categories (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_master_subservices_subservice
        FOREIGN KEY (subservice_id) REFERENCES subservice_categories (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_master_service_subservice
        UNIQUE (master_id, service_id, subservice_id)
);

CREATE INDEX idx_master_subservices_master ON master_subservices(master_id);
CREATE INDEX idx_master_subservices_service ON master_subservices(service_id);
CREATE INDEX idx_master_subservices_subservice ON master_subservices(subservice_id);