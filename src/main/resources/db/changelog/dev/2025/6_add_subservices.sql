CREATE TABLE subservices
(
    id                  UUID PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ,
    service_category_id UUID         NOT NULL REFERENCES service_categories (id) ON DELETE CASCADE
);

CREATE INDEX idx_subservices_name ON subservices(name);
CREATE INDEX idx_subservices_parent ON subservices(service_category_id);