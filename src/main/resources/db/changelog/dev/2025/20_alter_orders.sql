ALTER TABLE orders
    ADD COLUMN payment_method VARCHAR(50);

UPDATE orders
SET payment_method = 'CASH'
WHERE payment_method IS NULL;