ALTER TABLE client_profiles
    ADD COLUMN referral_code UUID UNIQUE,
    ADD COLUMN total_points INT,
    ADD COLUMN referred_by UUID UNIQUE REFERENCES client_profiles (id) ON DELETE SET NULL;


UPDATE client_profiles
SET referral_code = gen_random_uuid()
WHERE referral_code IS NULL;

UPDATE client_profiles
SET total_points = 0
WHERE total_points IS NULL;