ALTER TABLE payments DROP CONSTRAINT fk_payments_order;

ALTER TABLE payments
    ALTER COLUMN order_id DROP NOT NULL;

ALTER TABLE payments
    ADD CONSTRAINT fk_payments_order
        FOREIGN KEY (order_id) REFERENCES orders (id)
            ON DELETE SET NULL;

ALTER TABLE payments
    ADD COLUMN master_id UUID;

ALTER TABLE payments
    ADD CONSTRAINT fk_payments_master
        FOREIGN KEY (master_id) REFERENCES master_profiles(id)
            ON DELETE SET NULL;

ALTER TABLE payments
    ADD COLUMN payment_purpose VARCHAR(50);

UPDATE payments
SET payment_purpose = 'ORDER_PAYMENT'
WHERE payment_purpose IS NULL;