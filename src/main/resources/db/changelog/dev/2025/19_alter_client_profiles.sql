ALTER TABLE client_profiles
    ADD COLUMN referral_code VARCHAR(30) UNIQUE,
    ADD COLUMN total_earned_points INT;


UPDATE client_profiles
SET referral_code = SUBSTRING(md5(random()::text), 1, 10)
WHERE referral_code IS NULL;

UPDATE client_profiles
SET total_earned_points = 0
WHERE total_earned_points IS NULL;