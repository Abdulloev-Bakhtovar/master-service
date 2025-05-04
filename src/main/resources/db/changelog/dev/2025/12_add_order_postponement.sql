CREATE TABLE order_postponement
(
    id                   UUID PRIMARY KEY,
    order_id             UUID        NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    new_appointment_date TIMESTAMPTZ NOT NULL,
    reason               TEXT        NOT NULL,
    master_id            UUID        NOT NULL REFERENCES master_profiles (id) ON DELETE SET NULL,
    created_at           TIMESTAMPTZ NOT NULL,
    updated_at           TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_order_postponement_order_id ON order_postponement(order_id);
CREATE INDEX idx_order_postponement_master_id ON order_postponement(master_id);