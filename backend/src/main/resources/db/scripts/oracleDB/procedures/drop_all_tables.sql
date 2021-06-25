create or replace PROCEDURE drop_all_tables
    IS
    table_name VARCHAR2(30);
    CURSOR usertables IS SELECT *
                         FROM user_tables
                         WHERE table_name NOT LIKE 'BIN$%';
BEGIN
    FOR i IN usertables
        LOOP
            EXECUTE IMMEDIATE 'drop table ' || i.table_name || ' cascade constraints';
        END LOOP;
END;
