CREATE TABLE master_payment_history
(
    id         UUID PRIMARY KEY,
    master_id  UUID           NOT NULL REFERENCES master_profiles (id),
    order_id   UUID           NOT NULL REFERENCES orders (id),
    type       VARCHAR(20)    NOT NULL,
    amount     NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);
