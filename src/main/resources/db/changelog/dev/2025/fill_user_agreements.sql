INSERT INTO user_agreements (
    id,
    personal_data_consent,
    notifications_allowed,
    location_access_allowed,
    service_terms_accepted,
    service_rules_accepted,
    user_id,
    created_at,
    updated_at
) VALUES (
             'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
             true,
             true,
             false,
             null,
             null,
             '76b36661-4bf2-b367-4cd8-dddc47e10001',
             NOW(),
             NOW()
         );