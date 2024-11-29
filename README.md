Tài khoản admin mặc định cho chương trình: username = admin, password = admin
Nếu tài khoản mật khẩu không đúng -> CSDL cũ -> DROP DATABASE techshop; -> CREATE DATABASE techshop; -> Run Program
Tạo payment gồm: cod, paypal, vnpay


Code to insert data to database

INSERT INTO tech_shop.brands (name, brand_img, active) VALUES
('Dell', 'https://static.vecteezy.com/system/resources/previews/021/514/860/non_2x/dell-logo-brand-computer-symbol-white-design-usa-laptop-illustration-with-black-background-free-vector.jpg', 1),
('HP', 'https://brandcentral.hp.com/content/dam/sites/brand-central/elem-logo/images/Logo%20Colors_Black.svg', 1),
('Acer', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoa2HEzKITOLoZ_3nSekUZswZY18DYzWQuWytq19LWjgORqe3OpRCd39SGgDDYupRpVVY&usqp=CAU', 1),
('Lenovo', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Branding_lenovo-logo_lenovologoposred_low_res.png/1200px-Branding_lenovo-logo_lenovologoposred_low_res.png', 1),
('Apple', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3s8lbkOlP6KmiUCb8oGKoolB8oKdC1c1RPg&s', 1),
('Asus', 'https://press.asus.com/assets/w_767,h_431/fa3cbcd7-e826-45f9-885e-1d3470be3952/20220801101712676.jpg', 1),
('MSI', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIKBiMNQDdQz7jVtSpJkR6SyTRaHnagUTE1A&s', 1),
('Samsung', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5zthI1ZSOqZ4gRZ3GdsEuLTC13m1ul-pgAA&s', 1),
('LG', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRZVoGb_qx1ewlnpGZen1i2cbuUpDmBbBV5w&s', 1),
('Razer', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSveZ0yNNKAJcR2O8VHljE2vwRq8QWpD7rVag&s', 1),
('Xiaomi', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAr28X5Sa9Kv44_tbDZZ__E9ynXsctZyWUOQ&s', 1),
('OnePlus', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuzPHKEpJJAuKA0KXWI9tBMBf32R8h2CsqRw&s', 1),
('Google', 'https://cdn.logojoy.com/wp-content/uploads/20230801145635/Google_logo_2013-2015-600x206.png', 1),
('Oppo', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGAo9BB8VN42a_u6ygYRaUx7ZNidFS3GwhRw&s', 1),
('Huawei', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQLOM1EpgAo8icRyDYIohwX3UruYCVR_5Jrw&s', 1),
('Motorola', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLcNkDaVB8-z63HMXuUgOg6EzjoXLTd57VdA&s', 1),
('Realme', 'https://pentagram-production.imgix.net/ab2c89c3-33d6-45a2-b0e7-f4277c4cf5f6/02_logomark.jpg?rect=0%2C0%2C6251%2C4167&w=640&crop=1&fm=jpg&q=70&auto=format&fit=crop&h=427', 1),
('Sony', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGTcuc324di3XZSSWBok1a_vcy4yTQ7ZMMcw&s', 1),
('Logitech', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRS135osj2fY7fR0Fnu0LxtD1gUC9zax5PN0g&s', 1),
('JBL', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQn58yAkTLl68_hsjYiSrBsMlAJva10YkFmXg&s', 1),
('Anker', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5GyU9p280VtSNeLHKDOZuUuf3brHXlK34Vw&s', 1),
('Corsair', 'https://cwsmgmt.corsair.com/press/CORSAIRLogo2020_stack_K.png', 1),
('WD', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIy1TO25VdXLjbuYU5Ep7FG5XxpzlT5BL3eg&s', 1),
('Seagate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxVACkslRBOC73jiBEWxEAuzGoZtqo-ggRXQ&s', 1),
('Belkin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSk8QL3Qnj2-H9r4aN4UhlgMWfpZhDztdVCig&s', 1);


INSERT INTO tech_shop.categories (id, description, name, active) VALUES
(1, 'An electronic machine that is used for storing, organizing, and finding words, numbers, etc.', 'Computer', 1),
(2, 'A mobile phone that performs many of the functions of a computer, typically having a touchscreen interface, internet access, and an operating system capable of running downloaded applications.', 'Phone', 1),
(3, 'Additional components or devices that enhance functionality or aesthetics, often used with primary devices.', 'Accessory', 1);


INSERT INTO payments(id, payment_name)
VALUES
(1, 'cod'),
(2, 'paypal'),
(3, 'vnpay');


INSERT INTO tech_shop.products (
    id, created_at, updated_at, battery, brand_id, category_id, cpu, description, front_camera, 
    graphic_card, monitor, product_name, os, port, price, ram, rear_camera, stock_quantity, 
    thumbnail, warranty, weight
) VALUES
(1, '2024-11-01', '2024-11-01', '6000mAh', 1, 1, 'Intel i7', 'Gaming Laptop', 'N/A', 
    'NVIDIA RTX 3060', '15.6 inch', 'Dell G15', 'Windows 11', 'USB-C, HDMI, Thunderbolt', 15000000.00, 
    '16GB', 'N/A', 25, 'https://product.hstatic.net/200000680839/product/8902_dell_gaming_g15_5530_1_e1679458858791_2048x1402_5d965588efd04f5b83b846868f774458.jpg', '1 year', 2.5),
(2, '2024-11-01', '2024-11-01', '5000mAh', 2, 1, 'Intel i5', 'Workstation Laptop', 'N/A', 
    'NVIDIA GTX 1650', '14 inch', 'HP ProBook', 'Windows 10', 'USB 3.0, HDMI', 12000000.00, 
    '8GB', 'N/A', 30, 'https://giaiphapvanphong.vn/Image/Picture/HP/Laptop/6M0Y8PA.png', '1 year', 2),
(3, '2024-11-01', '2024-11-01', '4500mAh', 3, 1, 'AMD Ryzen 5', 'High-performance laptop', 'N/A', 
    'NVIDIA GTX 1050', '13.3 inch', 'Acer Swift 3', 'Windows 10', 'USB-C, HDMI', 9000000.00, 
    '8GB', 'N/A', 20, 'https://cdn.tgdd.vn/Products/Images/44/269313/acer-swift-3-sf314-511-55qe-i5-nxabnsv003-120122-022600-600x600.jpg', '1 year', 1.8),
(4, '2024-11-01', '2024-11-01', '7000mAh', 4, 1, 'AMD Ryzen 7', 'Business laptop', 'N/A', 
    'NVIDIA GTX 1660 Ti', '15.6 inch', 'Lenovo ThinkPad', 'Windows 10 Pro', 'USB 3.1, HDMI', 11000000.00, 
    '12GB', 'N/A', 15, 'https://p3-ofp.static.pub//fes/cms/2024/04/01/osqn4brfn79vgwdhq64mis2ddur9a6681695.png', '2 years', 2.2),
(5, '2024-11-01', '2024-11-01', '4800mAh', 5, 1, 'Apple M1', 'Ultrabook', 'N/A', 
    'Apple GPU', '13 inch', 'MacBook Air', 'macOS', 'USB-C', 14000000.00, 
    '8GB', 'N/A', 10, 'https://cdn.tgdd.vn/Products/Images/44/231244/macbook-air-m1-2020-gray-600x600.jpg', '1 year', 1.3),
(6, '2024-11-01', '2024-11-01', '5200mAh', 6, 1, 'Intel i5', 'Gaming laptop', 'N/A', 
    'NVIDIA RTX 3050', '14 inch', 'Asus TUF', 'Windows 11', 'USB-C, HDMI', 13000000.00, 
    '16GB', 'N/A', 18, 'https://m.media-amazon.com/images/I/71-jd5R6c7L._AC_SL1500_.jpg', '1 year', 2.3),
(7, '2024-11-01', '2024-11-01', '5000mAh', 7, 1, 'Intel i9', 'High-end gaming laptop', 'N/A', 
    'NVIDIA RTX 3080', '17 inch', 'MSI Titan', 'Windows 11', 'USB 3.2, HDMI', 25000000.00, 
    '32GB', 'N/A', 5, 'https://bizweb.dktcdn.net/thumb/grande/100/386/607/products/msi-titan-18-hx-man-hinh.jpg?v=1723993732720', '3 years', 3),
(8, '2024-11-01', '2024-11-01', '4800mAh', 8, 1, 'Intel i7', 'Ultrabook', 'N/A', 
    'Intel Iris', '15 inch', 'Samsung Galaxy Book', 'Windows 10', 'USB-C', 10000000.00, 
    '16GB', 'N/A', 12, 'https://bizweb.dktcdn.net/thumb/large/100/362/971/products/screenshot-2024-05-26-181255.png?v=1716723118897', '2 years', 1.5),
(9, '2024-11-01', '2024-11-01', '4300mAh', 9, 1, 'Intel i5', 'Lightweight laptop', 'N/A', 
    'Intel UHD Graphics', '14 inch', 'LG Gram', 'Windows 10', 'USB-C, HDMI', 8000000.00, 
    '8GB', 'N/A', 40, 'https://newtechshop.vn/wp-content/uploads/2024/05/LG-Gram-2024-17-23.jpg', '1 year', 1),
(10, '2024-11-01', '2024-11-01', '5200mAh', 10, 1, 'Intel i7', 'High-performance gaming laptop', 'N/A', 
    'NVIDIA RTX 3070', '15.6 inch', 'Razer Blade', 'Windows 11', 'USB-C, HDMI, Thunderbolt', 22000000.00, 
    '16GB', 'N/A', 8, 'https://bizweb.dktcdn.net/100/512/769/products/razer-blade-15-2021.jpg?v=1719731675653', '2 years', 2);


INSERT INTO tech_shop.products (
    id, created_at, updated_at, battery, brand_id, category_id, cpu, description, front_camera, 
    monitor, os, price, product_name, ram, rear_camera, stock_quantity, warranty, graphic_card, 
    port, thumbnail, weight
) VALUES
(11, '2024-11-01', '2024-11-01', '4500mAh', 8, 2, 'Exynos 990', 'Flagship smartphone', '12MP', 
    '6.2 inch', 'Android 10', 9000000.00, 'Samsung Galaxy S20', '8GB', '64MP', 100, '1 year', 
    'N/A', 'USB-C', 'https://galaxydidong.vn/wp-content/uploads/2022/07/samsung-galaxy-s20-fe-galaxydidong-1.webp', 0.18),
(12, '2024-11-01', '2024-11-01', '4000mAh', 5, 2, 'A14 Bionic', 'High-end smartphone', '12MP', 
    '6.1 inch', 'iOS 14', 11000000.00, 'iPhone 12', '4GB', '12MP', 50, '1 year', 
    'N/A', 'Lightning', 'https://cdn.tgdd.vn/Products/Images/42/213031/iphone-12-xanh-la-new-2-600x600.jpg', 0.16),
(13, '2024-11-01', '2024-11-01', '5000mAh', 11, 2, 'Snapdragon 870', 'Mid-range phone', '20MP', 
    '6.67 inch', 'Android 11', 6000000.00, 'Xiaomi Mi 10T', '6GB', '64MP', 200, '1 year', 
    'N/A', 'USB-C', 'https://cdn.tgdd.vn/Products/Images/42/228133/xiaomi-mi-10t-600jpg-600x600.jpg', 0.21),
(14, '2024-11-01', '2024-11-01', '4300mAh', 12, 2, 'Snapdragon 865', 'Flagship phone', '16MP', 
    '6.55 inch', 'Android 10', 7000000.00, 'OnePlus 8', '8GB', '48MP', 75, '1 year', 
    'N/A', 'USB-C', 'https://cdn.tgdd.vn/Products/Images/42/212356/oneplus-8-600x600-2-600x600.jpg', 0.18),
(15, '2024-11-01', '2024-11-01', '4500mAh', 13, 2, 'Snapdragon 765G', '5G phone', '8MP', 
    '6 inch', 'Android 11', 8000000.00, 'Google Pixel 5', '8GB', '12.2MP', 25, '1 year', 
    'N/A', 'USB-C', 'https://cdn.tgdd.vn/Products/Images/42/198422/google-pixel-5-600jpg-600x600.jpg', 0.15),
(16, '2024-11-01', '2024-11-01', '4500mAh', 14, 2, 'Snapdragon 720G', 'Affordable smartphone', '32MP', 
    '6.4 inch', 'Android 11', 5000000.00, 'Oppo Reno 4', '8GB', '48MP', 60, '1 year', 
    'N/A', 'USB-C', 'https://cdn.tgdd.vn/Products/Images/42/227112/600-oppo-reno4-5g-600x600.jpg', 0.17),
(17, '2024-11-01', '2024-11-01', '4000mAh', 15, 2, 'Kirin 990', 'High-end phone', '32MP', 
    '6.53 inch', 'HarmonyOS', 9500000.00, 'Huawei Mate 30 Pro', '8GB', '40MP', 30, '1 year', 
    'N/A', 'USB-C', 'https://cdn.tgdd.vn/Products/Images/42/199046/huawei-mate-30-pro-600x600-1-600x600.jpg', 0.19),
(18, '2024-11-01', '2024-11-01', '5000mAh', 16, 2, 'Snapdragon 730', 'Affordable phone', '16MP', 
    '6.8 inch', 'Android 10', 4000000.00, 'Moto G9 Plus', '4GB', '64MP', 120, '1 year', 
    'N/A', 'USB-C', 'https://cdn.tgdd.vn/Products/Images/42/226245/motorola-moto-g9-plus-121220-021258-600x600.jpg', 0.22),
(19, '2024-11-01', '2024-11-01', '4500mAh', 17, 2, 'MediaTek Helio G95', 'Gaming phone', '16MP', 
    '6.5 inch', 'Android 11', 3000000.00, 'Realme 7', '8GB', '48MP', 150, '1 year', 
    'N/A', 'USB-C', 'https://cdn2.cellphones.com.vn/x/media/catalog/product/r/e/realme-7-2.jpg', 0.2),
(20, '2024-11-01', '2024-11-01', '5000mAh', 18, 2, 'Snapdragon 888', 'Photography-focused phone', '8MP', 
    '6.5 inch', 'Android 11', 12000000.00, 'Sony Xperia 1 III', '12GB', '12MP', 15, '1 year', 
    'N/A', 'USB-C', 'https://clickbuy.com.vn/uploads/product-variant/sony-xperia-1-iii-mark-3-12gb-256gb-nhat-cu-99-black-195939-2047.png', 0.18);


INSERT INTO tech_shop.products (
    id, created_at, updated_at, brand_id, category_id, description, price, product_name, stock_quantity, 
    warranty, battery, cpu, front_camera, graphic_card, monitor, os, port, ram, rear_camera, 
    thumbnail, weight
) VALUES
(21, '2024-11-01', '2024-11-01', 19, 3, 'Wireless mouse', 300000.00, 'Logitech M330', 150, '1 year', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB', 'N/A', 'N/A', 'https://lh3.googleusercontent.com/SC5iDjIfTUeyNtslrnKftwI_IbUPf1LtnoPD0dTklXnzCGQt4GkOlKMU-KL49OfExXB2kioJm_FI7rBnjuX2cWIErAD8D6Vv', 0.1),
(22, '2024-11-01', '2024-11-01', 18, 3, 'Wireless headphones', 1500000.00, 'Sony WH-1000XM4', 75, '2 years', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB-C', 'N/A', 'N/A', 'https://www.sony.com.vn/image/5d02da5df552836db894cead8a68f5f3?fmt=pjpeg&wid=330&bgcolor=FFFFFF&bgc=FFFFFF', 0.25),
(23, '2024-11-01', '2024-11-01', 20, 3, 'Portable Bluetooth speaker', 1000000.00, 'JBL Flip 5', 50, '1 year', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB-C', 'N/A', 'N/A', 'https://vietmusic.vn/cdn/shop/files/loa-jbl-flip-5-bluetooth-viet-music-2.jpg?v=1711289106&width=1946', 0.3),
(24, '2024-11-01', '2024-11-01', 21, 3, 'Portable charger 20000mAh', 500000.00, 'Anker PowerCore', 200, '1 year', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB-C', 'N/A', 'N/A', 'https://bizweb.dktcdn.net/thumb/1024x1024/100/405/018/products/1-jpeg-b41dcf90-d608-43a1-9267-09f92310ece7.jpg?v=1671512257140', 0.45),
(25, '2024-11-01', '2024-11-01', 22, 3, 'Mechanical keyboard', 1200000.00, 'Corsair K95', 30, '2 years', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB', 'N/A', 'N/A', 'https://songphuong.vn/Content/uploads/2020/05/1_headphone_corsair_k95_rgb_platinum_xt_mx_brown_songphuong.vn_.png', 0.8),
(26, '2024-11-01', '2024-11-01', 23, 3, 'Portable SSD 1TB', 1500000.00, 'WD My Passport', 40, '3 years', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB-C', 'N/A', 'N/A', 'https://lagihitech.vn/wp-content/uploads/2019/07/o-cung-di-dong-WD-My-Passport-Ultra-2TB-USB-Type-C-WDBC3C0020BSL.jpg', 0.25),
(27, '2024-11-01', '2024-11-01', 8, 3, 'Wireless charging pad', 400000.00, 'Samsung Wireless Charger', 90, '1 year', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB-C', 'N/A', 'N/A', 'https://images.samsung.com/is/image/samsung/p6pim/vn/ep-p5400bbegww/gallery/vn-ep-p5400-421515-ep-p5400bbegww-533706769?$650_519_PNG$', 0.2),
(28, '2024-11-01', '2024-11-01', 24, 3, 'External HDD 2TB', 800000.00, 'Seagate Expansion', 120, '2 years', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB 3.0', 'N/A', 'N/A', 'https://lagihitech.vn/wp-content/uploads/2020/03/HDD-Seagate-Expansion-4TB-2.5-inch-USB-3.0-STEA4000400.jpg', 0.5),
(29, '2024-11-01', '2024-11-01', 2, 3, 'USB-C hub', 250000.00, 'HP Travel Hub', 100, '1 year', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'USB-C', 'N/A', 'N/A', 'https://product.hstatic.net/200000454827/product/3_e79e66d926034e20b983eb706a2b9c90_master.png', 0.15),
(30, '2024-11-01', '2024-11-01', 25, 3, 'Laptop stand', 600000.00, 'Belkin Stand', 80, '1 year', 
    'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A', 'https://product.hstatic.net/200000890439/product/cetracker_straighton_ortho-device_web_37f91475d8ec4c18961011bc6f01e17e_86744c8b57e441c6aa1c6439fa92a5f1.jpg', 0.4);

INSERT INTO tech_shop.vouchers (id, created_at, updated_at, expired_date, name, quantity, value)
VALUES
(1, '2024-11-01', '2024-11-01', '2024-12-01', 'Voucher 1', 100, 10000.00),
(2, '2024-11-02', '2024-11-02', '2024-12-02', 'Voucher 2', 150, 15000.00),
(3, '2024-11-03', '2024-11-03', '2024-12-03', 'Voucher 3', 200, 20000.00),
(4, '2024-11-04', '2024-11-04', '2024-12-04', 'Voucher 4', 250, 25000.00),
(5, '2024-11-05', '2024-11-05', '2024-12-05', 'Voucher 5', 300, 30000.00),
(6, '2024-11-06', '2024-11-06', '2024-12-06', 'Voucher 6', 120, 35000.00),
(7, '2024-11-07', '2024-11-07', '2024-12-07', 'Voucher 7', 180, 40000.00),
(8, '2024-11-08', '2024-11-08', '2024-12-08', 'Voucher 8', 220, 45000.00),
(9, '2024-11-09', '2024-11-09', '2024-12-09', 'Voucher 9', 130, 50000.00),
(10, '2024-11-10', '2024-11-10', '2024-12-10', 'Voucher 10', 170, 55000.00);

