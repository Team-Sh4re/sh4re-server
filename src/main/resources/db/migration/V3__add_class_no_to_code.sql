ALTER TABLE code ADD COLUMN class_no BIGINT;

UPDATE code
SET class_no = u.class_number
    FROM "user" u WHERE code.user_id = u.id;
