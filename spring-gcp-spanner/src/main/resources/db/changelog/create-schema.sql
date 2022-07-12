--liquibase formatted sql

--changeset ekram:create_orders_table
CREATE TABLE orders
(
    order_id           STRING(36) NOT NULL,
    description        STRING(255),
    creation_timestamp TIMESTAMP NOT NULL,
    created_by         STRING(255) NOT NULL,
    lastmodified_timestamp TIMESTAMP NOT NULL,
    lastmodified_by         STRING(255) NOT NULL,
) PRIMARY KEY (order_id);

--changeset ekram:create_order_items_table
CREATE TABLE order_items
(
    order_id      STRING(36) NOT NULL,
    order_item_id STRING(36) NOT NULL,
    description   STRING(255),
    quantity      INT64,
) PRIMARY KEY (order_id, order_item_id),
  INTERLEAVE IN PARENT orders ON DELETE CASCADE;