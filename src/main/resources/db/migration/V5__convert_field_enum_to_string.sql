ALTER TABLE code ADD COLUMN field_temp VARCHAR(255);

UPDATE code SET field_temp =
  CASE field
    WHEN 0 THEN 'PYTHON'
    WHEN 1 THEN 'WEB'
    ELSE NULL
  END;

ALTER TABLE code DROP COLUMN field;

ALTER TABLE code RENAME COLUMN field_temp TO field;
