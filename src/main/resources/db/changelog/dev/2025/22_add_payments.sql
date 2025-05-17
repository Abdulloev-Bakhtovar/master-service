CREATE TABLE payments
(
    id               UUID PRIMARY KEY,
    external_id      VARCHAR(255)   NOT NULL, -- id заказа оплата от yookassa
    status           VARCHAR(50)    NOT NULL,
    amount           NUMERIC(10, 2) NOT NULL,
    description      TEXT,
    paid             BOOLEAN        NOT NULL,
    order_id         UUID           NOT NULL,
    confirmation_url TEXT,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,

    CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);