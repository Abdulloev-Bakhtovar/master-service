ALTER TABLE payment_method
    ADD COLUMN is_visible BOOLEAN;

UPDATE payment_method
SET is_visible = FALSE
WHERE is_visible IS NULL;