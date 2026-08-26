--liquibase formatted sql

--changeset fenix:001-create-db


-- =========================================================
-- USERS
-- =========================================================

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,

                       CONSTRAINT uk_users_username UNIQUE (username)
);


-- =========================================================
-- PRODUCTS
-- =========================================================

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          unit VARCHAR(50) NOT NULL,
                          last_price DECIMAL(10, 2),

                          CONSTRAINT uk_products_name UNIQUE (name)
);


-- =========================================================
-- SHOPPING LISTS
-- =========================================================

CREATE TABLE shopping_lists (
                                id BIGSERIAL PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                owner_id BIGINT NOT NULL,
                                status VARCHAR(50) NOT NULL DEFAULT 'OPEN',

                                CONSTRAINT fk_shopping_lists_owner
                                    FOREIGN KEY (owner_id)
                                        REFERENCES users(id)
);


-- =========================================================
-- SHOPPING LIST MEMBERS
-- =========================================================

CREATE TABLE shopping_list_members (
                                       id BIGSERIAL PRIMARY KEY,
                                       shopping_list_id BIGINT NOT NULL,
                                       user_id BIGINT NOT NULL,

                                       CONSTRAINT fk_members_list
                                           FOREIGN KEY (shopping_list_id)
                                               REFERENCES shopping_lists(id)
                                               ON DELETE CASCADE,

                                       CONSTRAINT fk_members_user
                                           FOREIGN KEY (user_id)
                                               REFERENCES users(id)
                                               ON DELETE CASCADE,

                                       CONSTRAINT uk_members_list_user
                                           UNIQUE (shopping_list_id, user_id)
);


-- =========================================================
-- SHOPPING LIST ITEMS
-- =========================================================

CREATE TABLE shopping_list_items (
                                     id BIGSERIAL PRIMARY KEY,

                                     shopping_list_id BIGINT NOT NULL,
                                     product_id BIGINT NOT NULL,

                                     quantity DECIMAL(10, 2) NOT NULL,
                                     purchased_price DECIMAL(10, 2),

                                     status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',

                                     added_by BIGINT NOT NULL,
                                     bought_by BIGINT,
                                     bought_at TIMESTAMP,

                                     CONSTRAINT fk_items_list
                                         FOREIGN KEY (shopping_list_id)
                                             REFERENCES shopping_lists(id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT fk_items_product
                                         FOREIGN KEY (product_id)
                                             REFERENCES products(id),

                                     CONSTRAINT fk_items_added_by
                                         FOREIGN KEY (added_by)
                                             REFERENCES users(id),

                                     CONSTRAINT fk_items_bought_by
                                         FOREIGN KEY (bought_by)
                                             REFERENCES users(id)
);