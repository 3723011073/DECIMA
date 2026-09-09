DO $$
DECLARE
    constraint_name TEXT;
BEGIN
    FOR constraint_name IN
        SELECT conname
        FROM pg_constraint
        WHERE conrelid = 'users'::regclass
          AND contype = 'c'
          AND pg_get_constraintdef(oid) LIKE '%role%'
    LOOP
        EXECUTE format('ALTER TABLE users DROP CONSTRAINT %I', constraint_name);
    END LOOP;
END $$;

ALTER TABLE users ADD CONSTRAINT users_role_check
    CHECK (role IN ('SYSTEM_ADMIN', 'COMPANY_OWNER'));