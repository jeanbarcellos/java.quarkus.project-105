
ALTER TABLE IF EXISTS post
ADD COLUMN created_at timestamp(6);

ALTER TABLE IF EXISTS post
ADD COLUMN updated_at timestamp(6);

-- atualiza as datas

UPDATE post SET created_at = NOW(), updated_at = NOW();

-- Define o null

ALTER TABLE IF EXISTS post
ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE IF EXISTS post
ALTER COLUMN updated_at SET NOT NULL;
