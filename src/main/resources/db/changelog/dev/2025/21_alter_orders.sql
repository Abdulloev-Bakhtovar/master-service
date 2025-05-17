ALTER TABLE orders
    ADD COLUMN payment_method_id UUID REFERENCES payment_method (id);

UPDATE orders
SET payment_method_id = '00000000-1111-1111-1111-000000000000'
WHERE payment_method_id IS NULL;