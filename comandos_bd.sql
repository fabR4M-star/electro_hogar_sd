--Para crear las tablas
CREATE TABLE productos (
    id_producto VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50),
    categoria VARCHAR(50),
    precio NUMERIC(12, 0) NOT NULL,
    moneda VARCHAR(3) DEFAULT 'PYG'
);

--Para insertar algunos productos
INSERT INTO productos (id_producto, nombre, marca, categoria, precio, moneda) VALUES
('PROD-001', 'Smartphone Galaxy S24', 'Samsung', 'Electrónica', 6500000, 'PYG'),
('PROD-002', 'Notebook ThinkPad E14', 'Lenovo', 'Computadoras', 5200000, 'PYG'),
('PROD-003', 'Teclado Mecánico Kpro', 'Redragon', 'Periféricos', 380000, 'PYG'),
('PROD-004', 'Monitor Curvo 27"', 'LG', 'Monitores', 1850000, 'PYG'),
('PROD-005', 'Auriculares Inalámbricos WH-1000XM5', 'Sony', 'Audio', 2900000, 'PYG'),
('PROD-006', 'Smart TV 55" 4K', 'TCL', 'Electrónica', 3100000, 'PYG'),
('PROD-007', 'Mouse Ergónomo MX Master 3S', 'Logitech', 'Periféricos', 750000, 'PYG'),
('PROD-008', 'Impresora EcoTank L3250', 'Epson', 'Impresión', 1450000, 'PYG'),
('PROD-009', 'Disco Solido SSD NVMe 1TB', 'Kingston', 'Almacenamiento', 520000, 'PYG'),
('PROD-010', 'Silla Gamer Ergonomica', 'ThunderX3', 'Mobiliario', 1600000, 'PYG');