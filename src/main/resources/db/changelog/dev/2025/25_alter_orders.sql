ALTER TABLE orders
    DROP CONSTRAINT IF EXISTS orders_payment_method_id_fkey;

ALTER TABLE orders
    DROP COLUMN IF EXISTS payment_method_id;


ALTER TABLE orders
    ADD COLUMN payment_method VARCHAR(50);

UPDATE orders
SET payment_method = 'CASH'
WHERE payment_method IS NULL;