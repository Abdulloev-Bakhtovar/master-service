INSERT INTO client_orders (
    id,
    first_name,
    last_name,
    address,
    phone_number,
    comment,
    preferred_date_time,
    urgent,
    agree_to_terms,
    price,
    service_type,
    client_order_status,
    master_order_status,
    city_id,
    client_profile_id,
    service_category_id,
    subservice_category_id
) VALUES (
             '11111111-1111-1111-1111-111115111111',
             'Иван',
             'Иванов',
             'ул. Ленина, д. 10, кв. 5',
             '+79991234567',
             'Срочно нужно починить холодильник',
             '2024-03-15 14:00:00+03',
             TRUE,
             TRUE,
             2500.00,
             'REPAIR',
             'SEARCH_MASTER',
             'SEARCH_MASTER',
             '76b36661-4bf2-b367-4cd8-dddc47e10003', -- ID города
             '76b36661-4bf2-b367-4cd8-dddc47e10001', -- ID профиля клиента
             '11111111-1111-1111-1111-111111111111', -- ID категории услуг
             '44444444-4444-4444-4444-444444444444'  -- ID подкатегории
         ),
         (
             '22222222-2222-2222-2222-222222222222',
             'Петр',
             'Петров',
             'ул. Пушкина, д. 15',
             '+79998765432',
             'Протекает кран на кухне',
             '2024-03-16 10:00:00+03',
             FALSE,
             TRUE,
             1500.00,
             'INSTALLATION',
             'SEARCH_MASTER',
             'SEARCH_MASTER',
             '76b36661-4bf2-b367-4cd8-dddc47e10004', -- ID города
             '76b36661-4bf2-b367-4cd8-dddc47e10001', -- ID профиля клиента
             '22222222-2222-2222-2222-222222222222', -- ID категории услуг
             '66666666-6666-6666-6666-666666666666'  -- ID подкатегории
         );