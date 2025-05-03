CREATE TABLE master_feedbacks
(
    id         UUID PRIMARY KEY,
    review     VARCHAR(2000),
    rating     NUMERIC(2, 1),
    order_id   UUID        NOT NULL REFERENCES client_orders (id) ON DELETE CASCADE,
    master_id  UUID        NOT NULL REFERENCES master_profiles (id) ON DELETE SET NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_master_feedbacks_order ON master_feedbacks(order_id);
CREATE INDEX idx_master_feedbacks_master ON master_feedbacks(master_id);
CREATE INDEX idx_master_feedbacks_rating ON master_feedbacks(rating);