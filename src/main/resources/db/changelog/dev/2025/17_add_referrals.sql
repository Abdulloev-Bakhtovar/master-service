CREATE TABLE referrals
(
    id                    UUID PRIMARY KEY,
    referrer_id           UUID NOT NULL,
    referred_id           UUID NOT NULL,
    first_order_completed BOOLEAN,
    registered_at         TIMESTAMPTZ,
    first_order_at        TIMESTAMPTZ,
    created_at            TIMESTAMPTZ,
    updated_at            TIMESTAMPTZ,

    FOREIGN KEY (referrer_id) REFERENCES client_profiles (id),
    FOREIGN KEY (referred_id) REFERENCES client_profiles (id),
    UNIQUE (referrer_id, referred_id)
);