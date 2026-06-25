-- ============================================================
-- 01_create_database.sql
-- 创建数据库、用户并授权
-- ============================================================

-- 创建数据库
CREATE DATABASE pub_supervision
    WITH ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- 创建用户
CREATE USER pub_admin WITH PASSWORD 'PubAdmin@2024';

-- 授权
GRANT ALL PRIVILEGES ON DATABASE pub_supervision TO pub_admin;

-- 连接到数据库后授权 Schema
\connect pub_supervision;

GRANT ALL ON SCHEMA public TO pub_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO pub_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO pub_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON FUNCTIONS TO pub_admin;
