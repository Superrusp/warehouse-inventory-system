/*=====================*/
/*         DDL         */
/*=====================*/

-- USER CREATION
CREATE
USER sysadm IDENTIFIED BY qwaqwa123
DEFAULT TABLESPACE
USERS TEMPORARY TABLESPACE temp QUOTA 250M ON USERS;

-- ROLE CREATION
CREATE ROLE sysadmrole;

-- GRANTS FOR CREATION
GRANT
CREATE TABLE TO sysadmrole;
GRANT
CREATE PROCEDURE TO sysadmrole;
GRANT
CREATE TRIGGER TO sysadmrole;
GRANT
CREATE VIEW TO sysadmrole;
GRANT
CREATE SEQUENCE TO sysadmrole;

-- GRANTS FOR UPDATE
GRANT
ALTER
ANY TABLE TO sysadmrole;
GRANT ALTER
ANY PROCEDURE TO sysadmrole;
GRANT ALTER
ANY TRIGGER TO sysadmrole;
GRANT ALTER
PROFILE TO sysadmrole;

-- GRANTS FOR DELETION
GRANT DELETE
ANY TABLE TO sysadmrole;
GRANT DROP
ANY TABLE TO sysadmrole;
GRANT DROP
ANY PROCEDURE TO sysadmrole;
GRANT DROP
ANY TRIGGER TO sysadmrole;
GRANT DROP
ANY VIEW TO sysadmrole;
GRANT DROP
PROFILE TO sysadmrole;

-- GRANT ROLE TO USER
GRANT sysadmrole TO sysadm;


/*=====================*/
/*         DML         */
/*=====================*/

-- GET SPECIFIED ROLE
SELECT *
FROM dba_roles
WHERE role = UPPER('sysadmrole');

-- GET SPECIFIED USER
SELECT *
FROM dba_users
WHERE username = UPPER('sysadm');

-- GET ROLE GRANTS
SELECT *
FROM ROLE_SYS_PRIVS
WHERE role = UPPER('sysadmrole');
