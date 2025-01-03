CREATE DATABASE "provider_db";

CREATE TABLE "role"
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

INSERT INTO "role" (role_name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_CLIENT');

CREATE TABLE "status"
(
    status_id   SERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL
);

INSERT INTO "status" (status_name)
VALUES ('active'),
       ('inactive'),
       ('banned');

CREATE TABLE "tariff"
(
    tariff_id    SERIAL PRIMARY KEY,
    tariff_name  VARCHAR(50) UNIQUE NOT NULL,
    description  TEXT,
    monthly_cost DECIMAL(10, 2)     NOT NULL,
    data_limit   DOUBLE PRECISION,
    voice_limit  DOUBLE PRECISION
);


CREATE TABLE "user"
(
    user_id    SERIAL PRIMARY KEY,
    username   VARCHAR(32) UNIQUE  NOT NULL,
    "password" VARCHAR(256)        NOT NULL,
    email      VARCHAR(256) UNIQUE NOT NULL,
    phone      VARCHAR(18) UNIQUE  NOT NULL,
    role_id    INT                 REFERENCES "role" (role_id) ON DELETE SET NULL,
    status_id  INT                 REFERENCES "status" (status_id) ON DELETE SET NULL
);

CREATE TABLE email_token
(
    email_token_id SERIAL PRIMARY KEY,
    token          VARCHAR(256) NOT NULL UNIQUE,
    expiry_date    TIMESTAMP    NOT NULL,
    user_id        INT REFERENCES "user" (user_id) ON DELETE CASCADE,
    email          VARCHAR(255) NOT NULL,
    username       VARCHAR(32)  NOT NULL,
    phone          VARCHAR(18)  NOT NULL
);

CREATE TABLE "plan"
(
    plan_id     SERIAL PRIMARY KEY,
    tariff_id   INT REFERENCES tariff (tariff_id) ON DELETE CASCADE,
    plan_name   VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    start_date  DATE,
    end_date    DATE
);

CREATE TABLE "subscription"
(
    subscription_id SERIAL PRIMARY KEY,
    user_id         INT REFERENCES "user" (user_id) ON DELETE CASCADE,
    plan_id         INT REFERENCES plan (plan_id) ON DELETE CASCADE,
    status          VARCHAR(20)
);

CREATE TABLE "promotion"
(
    promotion_id        SERIAL PRIMARY KEY,
    title               VARCHAR(100) UNIQUE NOT NULL,
    description         TEXT,
    discount_percentage DECIMAL(5, 2),
    start_date          DATE,
    end_date            DATE
);

CREATE TABLE "promotions_tariffs"
(
    id           SERIAL PRIMARY KEY,
    promotion_id INT REFERENCES promotion (promotion_id) ON DELETE CASCADE,
    tariff_id    INT REFERENCES tariff (tariff_id) ON DELETE CASCADE,
    UNIQUE (promotion_id, tariff_id)
);

CREATE INDEX idx_username ON "user" (username);

CREATE INDEX idx_email ON "user" (email);

CREATE INDEX idx_plan_name ON plan (plan_name);

CREATE INDEX idx_tariff_name ON tariff (tariff_name);
