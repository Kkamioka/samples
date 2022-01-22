ALTER TABLE todo ADD COLUMN owner_id INTEGER;
UPDATE todo SET owner_id = 1;
