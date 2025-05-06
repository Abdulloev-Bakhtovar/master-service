ALTER TABLE master_profiles
    ADD COLUMN balance NUMERIC(10, 2) DEFAULT 0.00;

UPDATE master_profiles SET balance = 0.00 WHERE balance IS NULL;
