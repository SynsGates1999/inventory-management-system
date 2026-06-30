    -- =====================================================
    -- V1__initial_schema.sql
    -- Initial schema for Inventory Management System
    -- =====================================================

    -- ============ ROLES ============
    CREATE TABLE roles (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(30) UNIQUE NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );

    -- ============ USERS ============
    CREATE TABLE users (
        id BIGSERIAL PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        password_hash VARCHAR(255) NOT NULL,
        full_name VARCHAR(100) NOT NULL,
        role_id BIGINT NOT NULL REFERENCES roles(id),
        is_active BOOLEAN NOT NULL DEFAULT true,
        created_at TIMESTAMP NOT NULL DEFAULT now(),
        updated_at TIMESTAMP NOT NULL DEFAULT now()
    );
    CREATE INDEX idx_users_role_id ON users(role_id);

    -- ============ CATEGORIES ============
    CREATE TABLE categories (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(100) UNIQUE NOT NULL,
        description TEXT,
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );

    -- ============ SUPPLIERS ============
    CREATE TABLE suppliers (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(150) NOT NULL,
        contact_email VARCHAR(100),
        contact_phone VARCHAR(20),
        address TEXT,
        is_active BOOLEAN NOT NULL DEFAULT true,
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );

    -- ============ CUSTOMERS ============
    CREATE TABLE customers (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(150) NOT NULL,
        email VARCHAR(100),
        phone VARCHAR(20),
        address TEXT,
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );

    -- ============ PRODUCTS ============
    CREATE TABLE products (
        id BIGSERIAL PRIMARY KEY,
        sku VARCHAR(50) UNIQUE NOT NULL,
        name VARCHAR(150) NOT NULL,
        description TEXT,
        category_id BIGINT NOT NULL REFERENCES categories(id),
        supplier_id BIGINT REFERENCES suppliers(id),
        unit_price NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
        reorder_threshold INTEGER NOT NULL DEFAULT 10,
        is_active BOOLEAN NOT NULL DEFAULT true,
        created_at TIMESTAMP NOT NULL DEFAULT now(),
        updated_at TIMESTAMP NOT NULL DEFAULT now()
    );
    CREATE INDEX idx_products_category_id ON products(category_id);
    CREATE INDEX idx_products_supplier_id ON products(supplier_id);

    -- ============ WAREHOUSES ============
    CREATE TABLE warehouses (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(100) UNIQUE NOT NULL,
        location VARCHAR(200),
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );

    -- ============ STOCK ITEMS ============
    CREATE TABLE stock_items (
        id BIGSERIAL PRIMARY KEY,
        product_id BIGINT NOT NULL REFERENCES products(id),
        warehouse_id BIGINT NOT NULL REFERENCES warehouses(id),
        quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
        version BIGINT NOT NULL DEFAULT 0,
        updated_at TIMESTAMP NOT NULL DEFAULT now(),
        UNIQUE(product_id, warehouse_id)
    );
    CREATE INDEX idx_stock_items_product_id ON stock_items(product_id);
    CREATE INDEX idx_stock_items_warehouse_id ON stock_items(warehouse_id);

    -- ============ STOCK MOVEMENTS ============
    CREATE TABLE stock_movements (
        id BIGSERIAL PRIMARY KEY,
        stock_item_id BIGINT NOT NULL REFERENCES stock_items(id),
        user_id BIGINT NOT NULL REFERENCES users(id),
        movement_type VARCHAR(20) NOT NULL CHECK (movement_type IN ('IN','OUT','TRANSFER','ADJUSTMENT')),
        quantity INTEGER NOT NULL CHECK (quantity > 0),
        reference_type VARCHAR(30),
        reference_id BIGINT,
        notes TEXT,
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );
    CREATE INDEX idx_stock_movements_stock_item_id ON stock_movements(stock_item_id);
    CREATE INDEX idx_stock_movements_user_id ON stock_movements(user_id);
    CREATE INDEX idx_stock_movements_created_at ON stock_movements(created_at);

    -- ============ PURCHASE ORDERS ============
    CREATE TABLE purchase_orders (
        id BIGSERIAL PRIMARY KEY,
        supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
        status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','RECEIVED','CANCELLED')),
        order_date DATE NOT NULL DEFAULT CURRENT_DATE,
        received_date DATE,
        created_by BIGINT NOT NULL REFERENCES users(id),
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );
    CREATE INDEX idx_po_supplier_id ON purchase_orders(supplier_id);
    CREATE INDEX idx_po_status ON purchase_orders(status);

    -- ============ PURCHASE ORDER ITEMS ============
    CREATE TABLE purchase_order_items (
        id BIGSERIAL PRIMARY KEY,
        purchase_order_id BIGINT NOT NULL REFERENCES purchase_orders(id) ON DELETE CASCADE,
        product_id BIGINT NOT NULL REFERENCES products(id),
        quantity INTEGER NOT NULL CHECK (quantity > 0),
        unit_price NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
        UNIQUE(purchase_order_id, product_id)
    );
    CREATE INDEX idx_poi_po_id ON purchase_order_items(purchase_order_id);
    CREATE INDEX idx_poi_product_id ON purchase_order_items(product_id);

    -- ============ SALES ORDERS ============
    CREATE TABLE sales_orders (
        id BIGSERIAL PRIMARY KEY,
        customer_id BIGINT NOT NULL REFERENCES customers(id),
        status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','FULFILLED','CANCELLED')),
        order_date DATE NOT NULL DEFAULT CURRENT_DATE,
        fulfilled_date DATE,
        created_by BIGINT NOT NULL REFERENCES users(id),
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );
    CREATE INDEX idx_so_customer_id ON sales_orders(customer_id);
    CREATE INDEX idx_so_status ON sales_orders(status);

    -- ============ SALES ORDER ITEMS ============
    CREATE TABLE sales_order_items (
        id BIGSERIAL PRIMARY KEY,
        sales_order_id BIGINT NOT NULL REFERENCES sales_orders(id) ON DELETE CASCADE,
        product_id BIGINT NOT NULL REFERENCES products(id),
        quantity INTEGER NOT NULL CHECK (quantity > 0),
        unit_price NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
        UNIQUE(sales_order_id, product_id)
    );
    CREATE INDEX idx_soi_so_id ON sales_order_items(sales_order_id);
    CREATE INDEX idx_soi_product_id ON sales_order_items(product_id);

    -- ============ AUDIT LOGS ============
    CREATE TABLE audit_logs (
        id BIGSERIAL PRIMARY KEY,
        user_id BIGINT REFERENCES users(id),
        action VARCHAR(100) NOT NULL,
        entity_name VARCHAR(50) NOT NULL,
        entity_id BIGINT,
        details JSONB,
        created_at TIMESTAMP NOT NULL DEFAULT now()
    );
    CREATE INDEX idx_audit_logs_entity ON audit_logs(entity_name, entity_id);
    CREATE INDEX idx_audit_logs_created_at ON audit_logs(created_at);