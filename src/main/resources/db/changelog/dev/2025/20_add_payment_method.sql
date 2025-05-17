CREATE TABLE payment_method
(
    id               UUID PRIMARY KEY,
    value            VARCHAR(50),
    admin_id         UUID,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,

    CONSTRAINT fk_payment_method_admin FOREIGN KEY (admin_id) REFERENCES admin_profiles (id) ON DELETE SET NULL
);