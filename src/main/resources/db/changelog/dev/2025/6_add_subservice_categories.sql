CREATE TABLE subservice_categories
(
    id                  UUID PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ,
    service_category_id UUID         NOT NULL REFERENCES service_categories (id) ON DELETE CASCADE
);

CREATE INDEX idx_subservice_categories_name ON subservice_categories(name);
CREATE INDEX idx_subservice_category_parent ON subservice_categories(service_category_id);