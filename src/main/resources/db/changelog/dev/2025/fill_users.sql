INSERT INTO users (id, phone_number, created_at, updated_at, role, verification_status)
VALUES
    ('76b36661-4bf2-b367-4cd8-dddc47e10000', '111', NOW(), NOW(), 'MASTER', 'PHONE_NOT_VERIFIED'),
    ('76b36661-4bf2-b367-4cd8-dddc47e10001', '222', NOW(), NOW(), 'CLIENT', 'PHONE_NOT_VERIFIED');
