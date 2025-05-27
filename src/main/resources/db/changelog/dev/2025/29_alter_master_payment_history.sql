UPDATE master_payment_history
SET type = 'CREDIT_ORDER'
WHERE type = 'CREDIT';

UPDATE master_payment_history
SET type = 'DEBIT_COMMISSION'
WHERE type = 'DEBIT';