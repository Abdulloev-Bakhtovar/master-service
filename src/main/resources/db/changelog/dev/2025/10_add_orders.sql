CREATE TABLE orders (
    id UUID PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    comment TEXT,
    preferred_date_time TIMESTAMPTZ,
    urgent BOOLEAN NOT NULL DEFAULT FALSE,
    agree_to_terms BOOLEAN NOT NULL,
    price NUMERIC(10, 2),
    rejection_reason TEXT,
    service_type VARCHAR(50) NOT NULL,
    client_order_status VARCHAR(50) NOT NULL,
    master_order_status VARCHAR(50) NOT NULL,
    city_id UUID REFERENCES cities(id) ON DELETE SET NULL,
    client_profile_id UUID REFERENCES client_profiles(id) ON DELETE SET NULL,
    subservice_id UUID REFERENCES subservices(id) ON DELETE SET NULL,
    master_profile_id UUID REFERENCES master_profiles(id) ON DELETE SET NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_orders_client ON orders(client_profile_id);
CREATE INDEX idx_orders_master ON orders(master_profile_id);
CREATE INDEX idx_orders_client_status ON orders(client_order_status);
CREATE INDEX idx_orders_master_status ON orders(master_order_status);
CREATE INDEX idx_orders_service_type ON orders(service_type);
CREATE INDEX idx_orders_created ON orders(created_at);