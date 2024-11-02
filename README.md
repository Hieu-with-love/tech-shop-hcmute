Code to insert data to database

INSERT INTO tech_shop.brands (name, brand_img) VALUES 
('Dell', 'https://example.com/images/dell.png'),
('HP', 'https://example.com/images/hp.png'),
('Acer', 'https://example.com/images/acer.png'),
('Lenovo', 'https://example.com/images/lenovo.png'),
('Apple', 'https://example.com/images/apple.png'),
('Asus', 'https://example.com/images/asus.png'),
('MSI', 'https://example.com/images/msi.png'),
('Samsung', 'https://example.com/images/samsung.png'),
('LG', 'https://example.com/images/lg.png'),
('Razer', 'https://example.com/images/razer.png'),
('Xiaomi', 'https://example.com/images/xiaomi.png'),
('OnePlus', 'https://example.com/images/oneplus.png'),
('Google', 'https://example.com/images/google.png'),
('Oppo', 'https://example.com/images/oppo.png'),
('Huawei', 'https://example.com/images/huawei.png'),
('Motorola', 'https://example.com/images/motorola.png'),
('Realme', 'https://example.com/images/realme.png'),
('Sony', 'https://example.com/images/sony.png'),
('Logitech', 'https://example.com/images/logitech.png'),
('JBL', 'https://example.com/images/jbl.png'),
('Anker', 'https://example.com/images/anker.png'),
('Corsair', 'https://example.com/images/corsair.png'),
('WD', 'https://example.com/images/wd.png'),
('Seagate', 'https://example.com/images/seagate.png'),
('Belkin', 'https://example.com/images/belkin.png');

INSERT INTO tech_shop.products (battery, brand_id, category_id, cpu, description, graphic_card, monitor, os, port, price, product_name, ram, stock_quantity, warranty, weight) VALUES
('6000mAh', 1, 1, 'Intel i7', 'Gaming Laptop', 'NVIDIA RTX 3060', '15.6 inch', 'Windows 11', 'USB-C, HDMI, Thunderbolt', '1500', 'Dell G15', '16GB', '25', '1 year', 2.5),
('5000mAh', 2, 1, 'Intel i5', 'Workstation Laptop', 'NVIDIA GTX 1650', '14 inch', 'Windows 10', 'USB 3.0, HDMI', '1200', 'HP ProBook', '8GB', '30', '1 year', 2),
('4500mAh', 3, 1, 'AMD Ryzen 5', 'High-performance laptop', 'NVIDIA GTX 1050', '13.3 inch', 'Windows 10', 'USB-C, HDMI', '900', 'Acer Swift 3', '8GB', '20', '1 year', 1.8),
('7000mAh', 4, 1, 'AMD Ryzen 7', 'Business laptop', 'NVIDIA GTX 1660 Ti', '15.6 inch', 'Windows 10 Pro', 'USB 3.1, HDMI', '1100', 'Lenovo ThinkPad', '12GB', '15', '2 years', 2.2),
('4800mAh', 5, 1, 'Apple M1', 'Ultrabook', 'Apple GPU', '13 inch', 'macOS', 'USB-C', '1400', 'MacBook Air', '8GB', '10', '1 year', 1.3),
('5200mAh', 6, 1, 'Intel i5', 'Gaming laptop', 'NVIDIA RTX 3050', '14 inch', 'Windows 11', 'USB-C, HDMI', '1300', 'Asus TUF', '16GB', '18', '1 year', 2.3),
('5000mAh', 7, 1, 'Intel i9', 'High-end gaming laptop', 'NVIDIA RTX 3080', '17 inch', 'Windows 11', 'USB 3.2, HDMI', '2500', 'MSI Titan', '32GB', '5', '3 years', 3),
('4800mAh', 8, 1, 'Intel i7', 'Ultrabook', 'Intel Iris', '15 inch', 'Windows 10', 'USB-C', '1000', 'Samsung Galaxy Book', '16GB', '12', '2 years', 1.5),
('4300mAh', 9, 1, 'Intel i5', 'Lightweight laptop', 'Intel UHD Graphics', '14 inch', 'Windows 10', 'USB-C, HDMI', '800', 'LG Gram', '8GB', '40', '1 year', 1),
('5200mAh', 10, 1, 'Intel i7', 'High-performance gaming laptop', 'NVIDIA RTX 3070', '15.6 inch', 'Windows 11', 'USB-C, HDMI, Thunderbolt', '2200', 'Razer Blade', '16GB', '8', '2 years', 2);

INSERT INTO tech_shop.products (battery, brand_id, category_id, cpu, description, front_camera, monitor, os, price, product_name, ram, rear_camera, stock_quantity, warranty) VALUES
('4500mAh', 8, 2, 'Exynos 990', 'Flagship smartphone', '12MP', '6.2 inch', 'Android 10', '900', 'Samsung Galaxy S20', '8GB', '64MP', '100', '1 year'),
('4000mAh', 5, 2, 'A14 Bionic', 'High-end smartphone', '12MP', '6.1 inch', 'iOS 14', '1100', 'iPhone 12', '4GB', '12MP', '50', '1 year'),
('5000mAh', 11, 2, 'Snapdragon 870', 'Mid-range phone', '20MP', '6.67 inch', 'Android 11', '600', 'Xiaomi Mi 10T', '6GB', '64MP', '200', '1 year'),
('4300mAh', 12, 2, 'Snapdragon 865', 'Flagship phone', '16MP', '6.55 inch', 'Android 10', '700', 'OnePlus 8', '8GB', '48MP', '75', '1 year'),
('4500mAh', 13, 2, 'Snapdragon 765G', '5G phone', '8MP', '6 inch', 'Android 11', '800', 'Google Pixel 5', '8GB', '12.2MP', '25', '1 year'),
('4500mAh', 14, 2, 'Snapdragon 720G', 'Affordable smartphone', '32MP', '6.4 inch', 'Android 11', '500', 'Oppo Reno 4', '8GB', '48MP', '60', '1 year'),
('4000mAh', 15, 2, 'Kirin 990', 'High-end phone', '32MP', '6.53 inch', 'HarmonyOS', '950', 'Huawei Mate 30 Pro', '8GB', '40MP', '30', '1 year'),
('5000mAh', 16, 2, 'Snapdragon 730', 'Affordable phone', '16MP', '6.8 inch', 'Android 10', '400', 'Moto G9 Plus', '4GB', '64MP', '120', '1 year'),
('4500mAh', 17, 2, 'MediaTek Helio G95', 'Gaming phone', '16MP', '6.5 inch', 'Android 11', '300', 'Realme 7', '8GB', '48MP', '150', '1 year'),
('5000mAh', 18, 2, 'Snapdragon 888', 'Photography-focused phone', '8MP', '6.5 inch', 'Android 11', '1200', 'Sony Xperia 1 III', '12GB', '12MP', '15', '1 year');

INSERT INTO tech_shop.products (brand_id, category_id, description, price, product_name, stock_quantity, warranty) VALUES
(19, 3, 'Wireless mouse', '30', 'Logitech M330', '150', '1 year'),
(18, 3, 'Wireless headphones', '150', 'Sony WH-1000XM4', '75', '2 years'),
(20, 3, 'Portable Bluetooth speaker', '100', 'JBL Flip 5', '50', '1 year'),
(21, 3, 'Portable charger 20000mAh', '50', 'Anker PowerCore', '200', '1 year'),
(22, 3, 'Mechanical keyboard', '120', 'Corsair K95', '30', '2 years'),
(23, 3, 'Portable SSD 1TB', '150', 'WD My Passport', '40', '3 years'),
(8, 3, 'Wireless charging pad', '40', 'Samsung Wireless Charger', '90', '1 year'),
(24, 3, 'External HDD 2TB', '80', 'Seagate Expansion', '120', '2 years'),
(2, 3, 'USB-C hub', '25', 'HP Travel Hub', '100', '1 year'),
(25, 3, 'Laptop stand', '60', 'Belkin Stand', '80', '1 year');

INSERT INTO tech_shop.categories(id, description, name) VALUES
(1, 'An electronic machine that is used for storing, organizing, and finding words, numbers,...', 'Computer'),
(2, 'a mobile phone that performs many of the functions of a computer, typically having a touchscreen interface, internet access, and an operating system capable of running downloaded applications', 'Smartphone');

