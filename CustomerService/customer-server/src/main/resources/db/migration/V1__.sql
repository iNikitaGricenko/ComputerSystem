create TABLE customer_order
(
    customer_order_id BIGINT AUTO_INCREMENT NOT NULL,
    payment_method    VARCHAR(255)          NOT NULL,
    payment_currency  VARCHAR(255)          NOT NULL,
    address           VARCHAR(255)          NOT NULL,
    city              VARCHAR(255)          NOT NULL,
    state             VARCHAR(255)          NOT NULL,
    country           VARCHAR(255)          NOT NULL,
    zip_code          VARCHAR(255)          NOT NULL,
    `description`     VARCHAR(1024)         NULL,
    created           datetime              NOT NULL,
    status            VARCHAR(255)          NOT NULL,
    completed         datetime              NULL,
    customer_id       BIGINT                NULL,
    deleted           BIT(1)                NULL,
    deleted_at        datetime              NULL,
    CONSTRAINT pk_customer_order PRIMARY KEY (customer_order_id)
);

create TABLE customer_order_items
(
    customer_order_id     BIGINT       NOT NULL,
    id                    BIGINT       NULL,
    order_item_name       VARCHAR(255) NULL,
    order_item_model      VARCHAR(255) NULL,
    quantity              BIGINT       NULL,
    unit_price            DOUBLE       NULL,
    order_item_class_name VARCHAR(255) NULL
);

create TABLE customers
(
    customer_id   BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255)          NULL,
    second_name   VARCHAR(255)          NULL,
    phone         VARCHAR(255)          NULL,
    email         VARCHAR(255)          NULL,
    register_date datetime              NULL,
    CONSTRAINT pk_customers PRIMARY KEY (customer_id)
);

alter table customer_order
    add CONSTRAINT FK_CUSTOMER_ORDER_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (customer_id);

alter table customer_order_items
    add CONSTRAINT fk_customer_order_items_on_entity_customer_order FOREIGN KEY (customer_order_id) REFERENCES customer_order (customer_order_id);