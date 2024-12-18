CREATE DATABASE  IF NOT EXISTS `tech_shop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tech_shop`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tech_shop
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `detail_location` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),
  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,'Hà Nội','No. 12, Building A','Hoàn Kiếm','Trang Thi',1),(2,'Hà Nội','Tòa nhà A, tầng 3','Cầu Giấy','Nguyễn Phong Sắc',1),(3,'Hà Nội','Số 123','Đống Đa','Tôn Đức Thắng',1),(4,'Hồ Chí Minh','Chung cư B, phòng 202','Quận 1','Nguyễn Huệ',2),(5,'Đà Nẵng','Số 45','Hải Châu','Bạch Đằng',3),(6,'Hà Nội','Nhà số 5, ngõ 12','Ba Đình','Kim Mã',4),(7,'Hà Nội','Biệt thự C','Tây Hồ','Xuân Diệu',5),(8,'Hồ Chí Minh','Số 78','Quận 3','Lý Chính Thắng',6),(9,'Hồ Chí Minh','Tòa nhà D, tầng 8','Quận 10','3 Tháng 2',6),(10,'Hà Nội','Nhà số 67','Hoàn Kiếm','Hàng Ngang',7),(11,'Đà Nẵng','Số 90','Thanh Khê','Điện Biên Phủ',8),(12,'Hồ Chí Minh','Số 15A','Quận 7','Nguyễn Văn Linh',9),(13,'Hà Nội','Nhà số 9','Thanh Xuân','Lê Văn Lương',10),(14,'Hà Nội','Tòa nhà E','Hoàng Mai','Giải Phóng',11),(15,'Đà Nẵng','Số 101','Sơn Trà','Ngô Quyền',11),(16,'Hà Nội','Số 33','Hà Đông','Tố Hữu',10),(17,'Hồ Chí Minh','Nhà số 77','Bình Thạnh','Điện Biên Phủ',5),(18,'Hà Nội','Chung cư F, phòng 1105','Nam Từ Liêm','Hàm Nghi',4),(19,'Hà Nội','Số 89','Đống Đa','Xã Đàn',3),(20,'Hồ Chí Minh','Tòa nhà G, tầng 5','Quận 2','Mai Chí Thọ',2),(21,'Đà Nẵng','Nhà số 15','Ngũ Hành Sơn','Trần Thị Lý',3),(22,'Hà Nội','Số 45','Hai Bà Trưng','Bạch Mai',6),(23,'Hồ Chí Minh','Biệt thự H','Quận 9','Nguyễn Duy Trinh',7),(24,'Đà Nẵng','Chung cư I, phòng 803','Hải Châu','Lý Tự Trọng',8),(25,'Hà Nội','Số 12','Long Biên','Nguyễn Văn Cừ',11),(26,'Hà Nội','Số 100','Thanh Xuân','Khuất Duy Tiến',10);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand_img` varchar(500) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'https://static.vecteezy.com/system/resources/previews/021/514/860/non_2x/dell-logo-brand-computer-symbol-white-design-usa-laptop-illustration-with-black-background-free-vector.jpg',_binary '','Dell'),(2,'https://brandcentral.hp.com/content/dam/sites/brand-central/elem-logo/images/Logo%20Colors_Black.svg',_binary '','HP'),(3,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoa2HEzKITOLoZ_3nSekUZswZY18DYzWQuWytq19LWjgORqe3OpRCd39SGgDDYupRpVVY&usqp=CAU',_binary '','Acer'),(4,'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Branding_lenovo-logo_lenovologoposred_low_res.png/1200px-Branding_lenovo-logo_lenovologoposred_low_res.png',_binary '','Lenovo'),(5,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3s8lbkOlP6KmiUCb8oGKoolB8oKdC1c1RPg&s',_binary '','Apple'),(6,'https://press.asus.com/assets/w_767,h_431/fa3cbcd7-e826-45f9-885e-1d3470be3952/20220801101712676.jpg',_binary '','Asus'),(7,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIKBiMNQDdQz7jVtSpJkR6SyTRaHnagUTE1A&s',_binary '','MSI'),(8,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5zthI1ZSOqZ4gRZ3GdsEuLTC13m1ul-pgAA&s',_binary '','Samsung'),(9,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRZVoGb_qx1ewlnpGZen1i2cbuUpDmBbBV5w&s',_binary '','LG'),(10,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSveZ0yNNKAJcR2O8VHljE2vwRq8QWpD7rVag&s',_binary '','Razer'),(11,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAr28X5Sa9Kv44_tbDZZ__E9ynXsctZyWUOQ&s',_binary '','Xiaomi'),(12,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuzPHKEpJJAuKA0KXWI9tBMBf32R8h2CsqRw&s',_binary '','OnePlus'),(13,'https://cdn.logojoy.com/wp-content/uploads/20230801145635/Google_logo_2013-2015-600x206.png',_binary '','Google'),(14,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGAo9BB8VN42a_u6ygYRaUx7ZNidFS3GwhRw&s',_binary '','Oppo'),(15,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQLOM1EpgAo8icRyDYIohwX3UruYCVR_5Jrw&s',_binary '','Huawei'),(16,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLcNkDaVB8-z63HMXuUgOg6EzjoXLTd57VdA&s',_binary '','Motorola'),(17,'https://pentagram-production.imgix.net/ab2c89c3-33d6-45a2-b0e7-f4277c4cf5f6/02_logomark.jpg?rect=0%2C0%2C6251%2C4167&w=640&crop=1&fm=jpg&q=70&auto=format&fit=crop&h=427',_binary '','Realme'),(18,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGTcuc324di3XZSSWBok1a_vcy4yTQ7ZMMcw&s',_binary '','Sony'),(19,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRS135osj2fY7fR0Fnu0LxtD1gUC9zax5PN0g&s',_binary '','Logitech'),(20,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQn58yAkTLl68_hsjYiSrBsMlAJva10YkFmXg&s',_binary '','JBL'),(21,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5GyU9p280VtSNeLHKDOZuUuf3brHXlK34Vw&s',_binary '','Anker'),(22,'https://cwsmgmt.corsair.com/press/CORSAIRLogo2020_stack_K.png',_binary '','Corsair'),(23,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIy1TO25VdXLjbuYU5Ep7FG5XxpzlT5BL3eg&s',_binary '','WD'),(24,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxVACkslRBOC73jiBEWxEAuzGoZtqo-ggRXQ&s',_binary '','Seagate'),(25,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSk8QL3Qnj2-H9r4aN4UhlgMWfpZhDztdVCig&s',_binary '','Belkin');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_details`
--

DROP TABLE IF EXISTS `cart_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_details` (
  `quantity` int NOT NULL,
  `total_price` decimal(19,2) NOT NULL DEFAULT '0.00',
  `cart_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`cart_id`,`product_id`),
  KEY `FK9rlic3aynl3g75jvedkx84lhv` (`product_id`),
  CONSTRAINT `FK9rlic3aynl3g75jvedkx84lhv` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKkcochhsa891wv0s9wrtf36wgt` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_details`
--

LOCK TABLES `cart_details` WRITE;
/*!40000 ALTER TABLE `cart_details` DISABLE KEYS */;
INSERT INTO `cart_details` VALUES (2,30000000.00,1,1),(2,12000000.00,1,2),(1,9000000.00,1,3),(1,11000000.00,2,4),(1,14000000.00,2,5),(2,13000000.00,2,6),(1,25000000.00,3,7),(1,10000000.00,3,8),(1,8000000.00,3,9),(1,22000000.00,4,10),(2,9000000.00,4,11),(1,11000000.00,4,12),(1,6000000.00,5,13),(1,7000000.00,5,14),(2,8000000.00,5,15),(1,5000000.00,6,16),(1,9500000.00,6,17),(2,4000000.00,6,18),(1,3000000.00,7,19),(1,12000000.00,7,20),(1,300000.00,7,21),(2,1500000.00,8,22),(1,1000000.00,8,23),(1,900000.00,8,24),(1,1200000.00,9,25),(2,800000.00,9,26),(1,700000.00,9,27),(1,2000000.00,10,28),(1,5000000.00,10,29),(2,10000000.00,10,30);
/*!40000 ALTER TABLE `cart_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_price` decimal(19,2) NOT NULL DEFAULT '0.00',
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,63000000.00,1),(2,51000000.00,2),(3,43000000.00,3),(4,42000000.00,4),(5,29000000.00,5),(6,22500000.00,6),(7,15300000.00,7),(8,4900000.00,8),(9,3500000.00,9),(10,27000000.00,10);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'An electronic machine that is used for storing, organizing, and finding words, numbers, etc.',_binary '','Computer'),(2,'A mobile phone that performs many of the functions of a computer, typically having a touchscreen interface, internet access, and an operating system capable of running downloaded applications.',_binary '','Phone'),(3,'Additional components or devices that enhance functionality or aesthetics, often used with primary devices.',_binary '','Accessory');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `total_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,1,15000000.00,1,1),(2,2,24000000.00,1,2),(3,3,27000000.00,1,3),(4,1,11000000.00,2,4),(5,2,28000000.00,2,5),(6,3,39000000.00,2,6),(7,1,25000000.00,3,7),(8,2,20000000.00,3,8),(9,3,24000000.00,3,9),(10,1,22000000.00,4,10),(11,2,18000000.00,4,11),(12,3,33000000.00,4,12),(13,1,6000000.00,5,13),(14,2,12000000.00,5,14),(15,3,21000000.00,5,15),(16,1,5000000.00,6,16),(17,2,19000000.00,6,17),(18,3,12000000.00,6,18),(19,1,3000000.00,7,19),(20,2,24000000.00,7,20),(21,3,900000.00,7,21),(22,1,1500000.00,8,22),(23,2,2000000.00,8,23),(24,3,1500000.00,8,24),(25,1,1200000.00,9,25),(26,2,3000000.00,9,26),(27,3,1200000.00,9,27),(28,1,800000.00,10,28),(29,2,500000.00,10,29),(30,3,1800000.00,10,30),(31,1,15000000.00,11,1),(32,2,24000000.00,11,2),(33,3,27000000.00,11,3),(34,1,11000000.00,12,4),(35,2,28000000.00,12,5);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `payment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `voucher_id` bigint DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `status` enum('CANCELLED', 'COMPLETED', 'DELIVERED', 'REFUND', 'PENDING','SHIPPING') NOT NULL,
  `shipper_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8aol9f99s97mtyhij0tvfj41f` (`payment_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  KEY `FKhlglkvf5i60dv6dn397ethgpt` (`address_id`),
  KEY `FKdimvsocblb17f45ikjr6xn1wj` (`voucher_id`),
  KEY `FKmjnf91amspoh2m4qg8ct5y8uj` (`shipper_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK8aol9f99s97mtyhij0tvfj41f` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`),
  CONSTRAINT `FKdimvsocblb17f45ikjr6xn1wj` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`),
  CONSTRAINT `FKhlglkvf5i60dv6dn397ethgpt` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  CONSTRAINT `FKmjnf91amspoh2m4qg8ct5y8uj` FOREIGN KEY (`shipper_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2024-12-05','2024-12-05',_binary '',66000000.00,1,1,NULL,1,'PENDING',10),(2,'2024-12-05','2024-12-07',_binary '',77990000.00,2,1,1,2,'SHIPPING',3),(3,'2024-12-05','2024-12-05',_binary '',69000000.00,3,2,NULL,3,'DELIVERED',3),(4,'2024-12-05','2024-12-05',_binary '',72970000.00,2,2,5,4,'CANCELLED',9),(5,'2024-12-05','2024-12-05',_binary '',38955000.00,3,3,8,5,'PENDING',10),(6,'2024-12-05','2024-12-05',_binary '',35965000.00,2,3,6,6,'SHIPPING',3),(7,'2024-12-05','2024-12-07',_binary '',27900000.00,1,4,NULL,7,'CANCELLED',3),(8,'2024-12-05','2024-12-05',_binary '',4975000.00,1,4,4,8,'CANCELLED',10),(9,'2024-12-05','2024-12-05',_binary '',5385000.00,3,5,2,9,'PENDING',9),(10,'2024-12-05','2024-12-05',_binary '',3045000.00,2,5,10,10,'SHIPPING',10),(11,'2024-12-05','2024-12-05',_binary '',66000000.00,1,6,NULL,11,'DELIVERED',9),(12,'2024-12-05','2024-12-05',_binary '',39000000.00,3,6,NULL,12,'CANCELLED',10);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,'cod'),(2,'vnpay'),(3,'paypal');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,'https://tanphat.com.vn/media/product/2877_laptop_dell_gaming_g15_5530_i7h165w11gr4060.jpg',1),(2,'https://laptopaz.vn/media/product/2999_2987_2946_dell_gaming_g15_5530_laptopaz_2.png',1),(3,'https://tanphat.com.vn/media/product/2877_laptop_dell_gaming_g15_5530_i7h165w11gr4060.jpg',1),(4,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH7baYASYe45fx_REeGP4NY9nnjO0LfCAHhQ&s',2),(5,'https://bizweb.dktcdn.net/thumb/1024x1024/100/446/400/products/hp-probook-650-g7-0-gia-loc.jpg?v=1703217442140',2),(6,'https://bizweb.dktcdn.net/thumb/grande/100/512/769/products/acer-swift-3-2020-laptop-k1-1.jpg?v=1714407605737',3),(7,'https://images.acer.com/is/image/acer/acer-laptop-swift-3-as-bright-as-it-is-brilliant-l-1?$responsive$',3),(8,'https://p3-ofp.static.pub//fes/cms/2024/04/18/wwmfqrqc9q8tpc9prr9hoqokbw8p88326359.png',4),(9,'https://cdn.tgdd.vn/Products/Images/44/313333/lenovo-ideapad-slim-3-15iah8-i5-83er00evn-thumb-600x600.jpg',4),(10,'https://laptops.vn/wp-content/uploads/2024/06/lenovo_thinkpad_x260_1695354387.png',4),(11,'https://www.apple.com/v/macbook-air/s/images/meta/macbook_air_mx__ez5y0k5yy7au_og.png',5),(12,'https://uscom.vn/wp-content/uploads/2023/01/macden.jpg',5),(13,'https://bizweb.dktcdn.net/thumb/large/100/318/659/products/air-2018-gold-ac760c88-7653-4e47-ada0-8699a391afaf-c6b88015-1e93-496d-b381-e74ac62e5cd6-01e821e9-a78a-43f2-91ff-7075385fb8c8-652519c1-9175-4dcb-a60f-649a29f9ddfc.jpg?v=1603939006223',5),(14,'https://bizweb.dktcdn.net/thumb/large/100/372/934/products/asus-tuf-gaming-f15-fx507-2022-4-c2663bab-5367-4bfa-ae00-81cb707e0a65.jpg?v=1729398134647',6),(15,'https://product.hstatic.net/200000722513/product/ava_c8a92176125145c5a743e6a836ebef42.png',6),(16,'https://laptoptld.com/wp-content/uploads/2022/06/Laptop-MSI-GT75-8RX-TITAN-COREI7-1.png',7),(17,'https://bizweb.dktcdn.net/thumb/large/100/372/934/products/msi-titan-18-hx-cong-ket-noi-phai.jpg?v=1725642025423',7),(18,'https://product.hstatic.net/1000409936/product/1024_521700ac72204e20b2a2aaa376d71ec1_master.png',7),(19,'https://bizweb.dktcdn.net/100/512/769/products/samsung-galaxy-book4.jpg?v=1715325369183',8),(20,'https://product.hstatic.net/1000370129/product/galaxy-book-s-digiphone_d8f8f08b40534f938acb73c6230c8eb5_master.jpg',8),(21,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6vGTleGv8BZIb8y78Dsj9VoOFUQ6xtJHyDA&s',9),(22,'https://laptopworld.vn/media/product/19714_lg_gram_pro_2in1_16t90sp_k_adb9u1__3.jpg',9),(23,'https://minhvu.vn/thumb/lggram/lggramstyle/lggramstyle202314z90rsgah54a5cbfbkic_480_360.jpg',9),(24,'https://laptopgaumeo.vn/uploads/images/san-pham/razer-blade-15-advanced-2020/razer-blade-15-advanced-model-2020.jpg',10),(25,'https://file.hstatic.net/1000079076/file/razer_13_i7_edg.vn_10_20361f0376e14c4296234af34867536e.jpg',10),(26,'https://laptoptld.com/wp-content/uploads/2024/03/razer-blade-17-5-1633339165.jpg',10),(27,'https://taozinsaigon.com/files_upload/product/07_2023/thumbs/600_samsung_galaxy_s20_ultra_mau_den_tao_zin_sai_gon.jpg',11),(28,'https://img.global.news.samsung.com/vn/wp-content/uploads/2020/02/Galaxy-S20-S20-Plus-S20-Ultra-1.jpg',11),(29,'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MHLM3?wid=1144&hei=1144&fmt=jpeg&qlt=90&.v=1623348211000',12),(30,'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-12.png',12),(31,'https://product.hstatic.net/1000063620/product/iphone-12-pro-vang-mhm_6efe8dfd81694ce4bc738c4d3d9bb1ed_large__1__9a4fe3d5e1744c3b9e2265a9cc4da835_grande.jpg',12),(32,'https://images-cdn.ubuy.co.in/65d605ce954a5e7cb730019e-pre-owned-apple-iphone-12-pro-max-a2342.jpg',12),(33,'https://tmobile368.com/wp-content/uploads/2021/05/mi-10t-pro.jpg',13),(34,'https://cdn.tgdd.vn/Products/Images/42/228971/xiaomi-mi-10t-lite-600jpg-600x600.jpg',13),(35,'https://cdn.tgdd.vn/Products/Images/42/228133/xiaomi-mi-10t-115320-025346.jpg',13),(36,'https://p-vn.ipricegroup.com/uploaded_d91251adde689974ed72fd8f8d263575.jpg',14),(37,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeUkuEJQxQZtQ2jG6MIFR3km16S9NtwRh4Aw&s',14),(38,'https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:quality(100)/2020_4_1_637213802707412160_oneplus-8-pro.jpg',14),(39,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAXWF6VifVWKNd-KfFu2o7H49rRZ8PkTDLTg&s',15),(40,'https://i.ebayimg.com/images/g/O-oAAOSwvOhiucWd/s-l1200.jpg',15),(41,'https://vienthinh.vn/assets/Uploads/sanpham/2858/xanh.JPG',16),(42,'https://p-vn.ipricegroup.com/media/1Phanh/The-new-OPPO-Reno-4-in-Space-Black-and-Galactic-Blue-1.jpg',16),(43,'https://phukienpico.com/wp-content/uploads/2020/07/dan-lung-dan-man-hinh-dan-camera-oppo-reno-4-6-1.jpg',16),(44,'https://cdn.tgdd.vn/Files/2019/09/20/1199909/huawei-mate-30_pro-color_805x440-800-resize.jpg',17),(45,'https://cdn.tgdd.vn/Products/Images/42/199046/Kit/huawei-mate-30-pro-note.jpg',17),(46,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDJ9i_lOJfScbRjcu70ymQxs0HAtHkXFjp1g&s',17),(47,'https://cellphones.com.vn/sforum/wp-content/uploads/2020/09/moto-g9-plus-3.jpg',18),(48,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1m42t_48o0AIrKXhYV1MghwlwTEIUkpIG1Q&s',18),(49,'https://chuyensidienthoai.vn/wp-content/uploads/2024/09/realme-7.png',19),(50,'https://cdn.dienmaygiakhanh.com/Products/Images/42/227731/Slider/vi-vn-realme-7-pro-110920-0404569-2.jpg',19),(51,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4NfhIMI_tpPGd4N1dTfxFb_aQ_5uanBkKiw&s',19),(52,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgvV74E5VF2Noma_HM8q4obT1PdKVcKKQWQA&s',19),(53,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1cUzERQ468u_goGLcWUsdeB_Vyn_Ntx6F8g&s',20),(54,'https://bizweb.dktcdn.net/thumb/medium/100/506/962/products/dien-thoai-sony-xperia-1-mark-iii-8-23f40e36-3cc8-437c-b795-c5256d4598b1.jpg?v=1727497173100',20),(55,'https://m.media-amazon.com/images/I/61SPQ9SnfpL.jpg',20),(56,'https://nvs.tn-cdn.net/2024/05/Chuot-Khong-day-Logitech-M330-Silent-1.jpg',21),(57,'https://img.lazcdn.com/g/p/cfe47b3d6d869192eda755b40d91e52c.jpg_720x720q80.jpg',21),(58,'https://bizweb.dktcdn.net/100/319/996/products/logitech-m330-2-chi-u-con-l-n-chu-t-kh-ng-d-y-usb-kh.png?v=1603529347483',21),(59,'https://vn-test-11.slatic.net/p/ef88983784531a3aaad003345bc8ceae.jpg',22),(60,'https://songlongmedia.com/media/product/3068_tai_nghe_sony_wh_1000xm4_hang_cu_like_new_songlongmedia__5_.jpg',22),(61,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR39IgPkCS8i73gVcfOnTqFxnTMND98ZWVmtw&s',23),(62,'https://bizweb.dktcdn.net/thumb/1024x1024/100/387/315/products/2-1-0ce0a97d-3aff-414d-a993-1adebfaa059f.jpg?v=1606442934043',23),(63,'https://antuan.vn/public/uploads/anh-tin-tuc/logo-loa/loa-jbl-flip-5.jpg',23),(64,'https://ankervietnam.vn/wp-content/uploads/2024/07/1257.png',24),(65,'https://cdn2.cellphones.com.vn/x/media/catalog/product/4/h/4h57_2.png',24),(66,'https://tanphat.com.vn/media/product/3749_38596_corsair_k95_rgb_platinum_xt_mx_ha4.jpg',25),(67,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDWQ2L1qgyyz9O3avtA9qZ43VTCA2qmpt7tw&s',25),(68,'https://product.hstatic.net/1000333506/product/-phim-co-corsair-k95-rgb-platinum-xt-_c0b76c216bfd4a2fb00948b27e04cc32_93c86379aed74cdb8849e131f6637fd4.png',25),(69,'https://www.techone.vn/wp-content/uploads/2019/03/aq3.jpg',26),(70,'https://tinhocthanhkhang.vn/media/lib/02-08-2024/o-cung-gn-wd-my-passport-2tb-do-2-5inch-3-2-wdbyvg.jpg',26),(71,'https://phukiengiare.com/images/detailed/31/wd-my-passport-ultra-2tb-1.jpg',26),(72,'https://images-cdn.ubuy.co.in/65976242324c905ee0411dca-wireless-charging-pad-for-car-15w-10w.jpg',27),(73,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN8KgfFPSmGDaQ6c4SpHJPx9XWAmm5DlowDQ&s',27),(74,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTkc9o1B8j4LaWsQxBM5MAmvrCdKb_HiH7Rg&s',28),(75,'https://down-vn.img.susercontent.com/file/be9a800c396259fc45314ef52be858fe',28),(76,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpRaxlwZ9gdMkvL9c-2c7Jl78lssjlqnd6_Q&s',28),(77,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQi8ELMJkEZ3rta7-vIgOpJHGrpdTXgdVG8Uw&s',29),(78,'https://m.media-amazon.com/images/I/71-9hVPgcgL._AC_UF350,350_QL80_.jpg',29),(79,'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/HRGR2?wid=1144&hei=1144&fmt=jpeg&qlt=90&.v=1715882204035',30),(80,'https://down-vn.img.susercontent.com/file/vn-11134201-7ras8-m3316bkvbzsgaa',30),(81,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKpgQU-WcS38LAwjLSr2B1VQ8F0aauoYvsCA&s',30);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `battery` varchar(255) DEFAULT NULL,
  `cpu` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `front_camera` varchar(255) DEFAULT NULL,
  `graphic_card` varchar(255) DEFAULT NULL,
  `monitor` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `os` varchar(255) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `ram` varchar(255) DEFAULT NULL,
  `rear_camera` varchar(255) DEFAULT NULL,
  `stock_quantity` int NOT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `warranty` varchar(255) NOT NULL,
  `weight` double DEFAULT NULL,
  `brand_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_chk_1` CHECK ((`price` >= 1))
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2024-11-01','2024-11-01','6000mAh','Intel i7','Gaming Laptop','N/A','NVIDIA RTX 3060','15.6 inch','Dell G15','Windows 11','USB-C, HDMI, Thunderbolt',15000000.00,'16GB','N/A',25,'https://product.hstatic.net/200000680839/product/8902_dell_gaming_g15_5530_1_e1679458858791_2048x1402_5d965588efd04f5b83b846868f774458.jpg','1 year',2.5,1,1),(2,'2024-11-01','2024-11-01','5000mAh','Intel i5','Workstation Laptop','N/A','NVIDIA GTX 1650','14 inch','HP ProBook','Windows 10','USB 3.0, HDMI',12000000.00,'8GB','N/A',30,'https://giaiphapvanphong.vn/Image/Picture/HP/Laptop/6M0Y8PA.png','1 year',2,2,1),(3,'2024-11-01','2024-11-01','4500mAh','AMD Ryzen 5','High-performance laptop','N/A','NVIDIA GTX 1050','13.3 inch','Acer Swift 3','Windows 10','USB-C, HDMI',9000000.00,'8GB','N/A',20,'https://cdn.tgdd.vn/Products/Images/44/269313/acer-swift-3-sf314-511-55qe-i5-nxabnsv003-120122-022600-600x600.jpg','1 year',1.8,3,1),(4,'2024-11-01','2024-11-01','7000mAh','AMD Ryzen 7','Business laptop','N/A','NVIDIA GTX 1660 Ti','15.6 inch','Lenovo ThinkPad','Windows 10 Pro','USB 3.1, HDMI',11000000.00,'12GB','N/A',15,'https://p3-ofp.static.pub//fes/cms/2024/04/01/osqn4brfn79vgwdhq64mis2ddur9a6681695.png','2 years',2.2,4,1),(5,'2024-11-01','2024-11-01','4800mAh','Apple M1','Ultrabook','N/A','Apple GPU','13 inch','MacBook Air','macOS','USB-C',14000000.00,'8GB','N/A',10,'https://cdn.tgdd.vn/Products/Images/44/231244/macbook-air-m1-2020-gray-600x600.jpg','1 year',1.3,5,1),(6,'2024-11-01','2024-11-01','5200mAh','Intel i5','Gaming laptop','N/A','NVIDIA RTX 3050','14 inch','Asus TUF','Windows 11','USB-C, HDMI',13000000.00,'16GB','N/A',18,'https://m.media-amazon.com/images/I/71-jd5R6c7L._AC_SL1500_.jpg','1 year',2.3,6,1),(7,'2024-11-01','2024-11-01','5000mAh','Intel i9','High-end gaming laptop','N/A','NVIDIA RTX 3080','17 inch','MSI Titan','Windows 11','USB 3.2, HDMI',25000000.00,'32GB','N/A',5,'https://bizweb.dktcdn.net/thumb/grande/100/386/607/products/msi-titan-18-hx-man-hinh.jpg?v=1723993732720','3 years',3,7,1),(8,'2024-11-01','2024-11-01','4800mAh','Intel i7','Ultrabook','N/A','Intel Iris','15 inch','Samsung Galaxy Book','Windows 10','USB-C',10000000.00,'16GB','N/A',12,'https://bizweb.dktcdn.net/thumb/large/100/362/971/products/screenshot-2024-05-26-181255.png?v=1716723118897','2 years',1.5,8,1),(9,'2024-11-01','2024-11-01','4300mAh','Intel i5','Lightweight laptop','N/A','Intel UHD Graphics','14 inch','LG Gram','Windows 10','USB-C, HDMI',8000000.00,'8GB','N/A',40,'https://newtechshop.vn/wp-content/uploads/2024/05/LG-Gram-2024-17-23.jpg','1 year',1,9,1),(10,'2024-11-01','2024-11-01','5200mAh','Intel i7','High-performance gaming laptop','N/A','NVIDIA RTX 3070','15.6 inch','Razer Blade','Windows 11','USB-C, HDMI, Thunderbolt',22000000.00,'16GB','N/A',8,'https://bizweb.dktcdn.net/100/512/769/products/razer-blade-15-2021.jpg?v=1719731675653','2 years',2,10,1),(11,'2024-11-01','2024-11-01','4500mAh','Exynos 990','Flagship smartphone','12MP','N/A','6.2 inch','Samsung Galaxy S20','Android 10','USB-C',9000000.00,'8GB','64MP',100,'https://galaxydidong.vn/wp-content/uploads/2022/07/samsung-galaxy-s20-fe-galaxydidong-1.webp','1 year',0.18,8,2),(12,'2024-11-01','2024-11-01','4000mAh','A14 Bionic','High-end smartphone','12MP','N/A','6.1 inch','iPhone 12','iOS 14','Lightning',11000000.00,'4GB','12MP',50,'https://cdn.tgdd.vn/Products/Images/42/213031/iphone-12-xanh-la-new-2-600x600.jpg','1 year',0.16,5,2),(13,'2024-11-01','2024-11-01','5000mAh','Snapdragon 870','Mid-range phone','20MP','N/A','6.67 inch','Xiaomi Mi 10T','Android 11','USB-C',6000000.00,'6GB','64MP',200,'https://cdn.tgdd.vn/Products/Images/42/228133/xiaomi-mi-10t-600jpg-600x600.jpg','1 year',0.21,11,2),(14,'2024-11-01','2024-11-01','4300mAh','Snapdragon 865','Flagship phone','16MP','N/A','6.55 inch','OnePlus 8','Android 10','USB-C',7000000.00,'8GB','48MP',75,'https://cdn.tgdd.vn/Products/Images/42/212356/oneplus-8-600x600-2-600x600.jpg','1 year',0.18,12,2),(15,'2024-11-01','2024-11-01','4500mAh','Snapdragon 765G','5G phone','8MP','N/A','6 inch','Google Pixel 5','Android 11','USB-C',8000000.00,'8GB','12.2MP',25,'https://cdn.tgdd.vn/Products/Images/42/198422/google-pixel-5-600jpg-600x600.jpg','1 year',0.15,13,2),(16,'2024-11-01','2024-11-01','4500mAh','Snapdragon 720G','Affordable smartphone','32MP','N/A','6.4 inch','Oppo Reno 4','Android 11','USB-C',5000000.00,'8GB','48MP',60,'https://cdn.tgdd.vn/Products/Images/42/227112/600-oppo-reno4-5g-600x600.jpg','1 year',0.17,14,2),(17,'2024-11-01','2024-11-01','4000mAh','Kirin 990','High-end phone','32MP','N/A','6.53 inch','Huawei Mate 30 Pro','HarmonyOS','USB-C',9500000.00,'8GB','40MP',30,'https://cdn.tgdd.vn/Products/Images/42/199046/huawei-mate-30-pro-600x600-1-600x600.jpg','1 year',0.19,15,2),(18,'2024-11-01','2024-11-01','5000mAh','Snapdragon 730','Affordable phone','16MP','N/A','6.8 inch','Moto G9 Plus','Android 10','USB-C',4000000.00,'4GB','64MP',120,'https://cdn.tgdd.vn/Products/Images/42/226245/motorola-moto-g9-plus-121220-021258-600x600.jpg','1 year',0.22,16,2),(19,'2024-11-01','2024-11-01','4500mAh','MediaTek Helio G95','Gaming phone','16MP','N/A','6.5 inch','Realme 7','Android 11','USB-C',3000000.00,'8GB','48MP',150,'https://cdn2.cellphones.com.vn/x/media/catalog/product/r/e/realme-7-2.jpg','1 year',0.2,17,2),(20,'2024-11-01','2024-11-01','5000mAh','Snapdragon 888','Photography-focused phone','8MP','N/A','6.5 inch','Sony Xperia 1 III','Android 11','USB-C',12000000.00,'12GB','12MP',15,'https://clickbuy.com.vn/uploads/product-variant/sony-xperia-1-iii-mark-3-12gb-256gb-nhat-cu-99-black-195939-2047.png','1 year',0.18,18,2),(21,'2024-11-01','2024-11-01','N/A','N/A','Wireless mouse','N/A','N/A','N/A','Logitech M330','N/A','USB',300000.00,'N/A','N/A',150,'https://lh3.googleusercontent.com/SC5iDjIfTUeyNtslrnKftwI_IbUPf1LtnoPD0dTklXnzCGQt4GkOlKMU-KL49OfExXB2kioJm_FI7rBnjuX2cWIErAD8D6Vv','1 year',0.1,19,3),(22,'2024-11-01','2024-11-01','N/A','N/A','Wireless headphones','N/A','N/A','N/A','Sony WH-1000XM4','N/A','USB-C',1500000.00,'N/A','N/A',75,'https://www.sony.com.vn/image/5d02da5df552836db894cead8a68f5f3?fmt=pjpeg&wid=330&bgcolor=FFFFFF&bgc=FFFFFF','2 years',0.25,18,3),(23,'2024-11-01','2024-11-01','N/A','N/A','Portable Bluetooth speaker','N/A','N/A','N/A','JBL Flip 5','N/A','USB-C',1000000.00,'N/A','N/A',50,'https://vietmusic.vn/cdn/shop/files/loa-jbl-flip-5-bluetooth-viet-music-2.jpg?v=1711289106&width=1946','1 year',0.3,20,3),(24,'2024-11-01','2024-11-01','N/A','N/A','Portable charger 20000mAh','N/A','N/A','N/A','Anker PowerCore','N/A','USB-C',500000.00,'N/A','N/A',200,'https://bizweb.dktcdn.net/thumb/1024x1024/100/405/018/products/1-jpeg-b41dcf90-d608-43a1-9267-09f92310ece7.jpg?v=1671512257140','1 year',0.45,21,3),(25,'2024-11-01','2024-11-01','N/A','N/A','Mechanical keyboard','N/A','N/A','N/A','Corsair K95','N/A','USB',1200000.00,'N/A','N/A',30,'https://songphuong.vn/Content/uploads/2020/05/1_headphone_corsair_k95_rgb_platinum_xt_mx_brown_songphuong.vn_.png','2 years',0.8,22,3),(26,'2024-11-01','2024-11-01','N/A','N/A','Portable SSD 1TB','N/A','N/A','N/A','WD My Passport','N/A','USB-C',1500000.00,'N/A','N/A',40,'https://lagihitech.vn/wp-content/uploads/2019/07/o-cung-di-dong-WD-My-Passport-Ultra-2TB-USB-Type-C-WDBC3C0020BSL.jpg','3 years',0.25,23,3),(27,'2024-11-01','2024-11-01','N/A','N/A','Wireless charging pad','N/A','N/A','N/A','Samsung Wireless Charger','N/A','USB-C',400000.00,'N/A','N/A',90,'https://images.samsung.com/is/image/samsung/p6pim/vn/ep-p5400bbegww/gallery/vn-ep-p5400-421515-ep-p5400bbegww-533706769?$650_519_PNG$','1 year',0.2,8,3),(28,'2024-11-01','2024-11-01','N/A','N/A','External HDD 2TB','N/A','N/A','N/A','Seagate Expansion','N/A','USB 3.0',800000.00,'N/A','N/A',120,'https://lagihitech.vn/wp-content/uploads/2020/03/HDD-Seagate-Expansion-4TB-2.5-inch-USB-3.0-STEA4000400.jpg','2 years',0.5,24,3),(29,'2024-11-01','2024-11-01','N/A','N/A','USB-C hub','N/A','N/A','N/A','HP Travel Hub','N/A','USB-C',250000.00,'N/A','N/A',100,'https://product.hstatic.net/200000454827/product/3_e79e66d926034e20b983eb706a2b9c90_master.png','1 year',0.15,2,3),(30,'2024-11-01','2024-11-01','N/A','N/A','Laptop stand','N/A','N/A','N/A','Belkin Stand','N/A','N/A',600000.00,'N/A','N/A',80,'https://product.hstatic.net/200000890439/product/cetracker_straighton_ortho-device_web_37f91475d8ec4c18961011bc6f01e17e_86744c8b57e441c6aa1c6439fa92a5f1.jpg','1 year',0.4,25,3);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `content` text NOT NULL,
  `star` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`,`user_id`),
  KEY `FK228us4dg38ewge41gos8y761r` (`product_id`),
  KEY `FKb3354ee2xxvdrbyq9f42jdayd` (`user_id`),
  CONSTRAINT `FK228us4dg38ewge41gos8y761r` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKb3354ee2xxvdrbyq9f42jdayd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKu2smk2142p4u673g5h2w419l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
INSERT INTO `ratings` VALUES ('Good product',4,1,1,1),('Great quality product!',5,1,1,10),('Received in perfect condition.',5,1,3,3),('Satisfied overall.',5,1,5,5),('Fast shipping, excellent service.',4,2,2,3),('Very poor customer service.',1,2,4,4),('Fast and efficient service.',4,2,6,6),('Product as described. Satisfied.',5,3,3,3),('Worth every penny!',5,3,5,5),('Poor quality material.',1,3,7,7),('Received broken item. Disappointed.',1,4,4,9),('Product is fine but packaging was bad.',3,4,6,6),('Works as advertised.',5,4,8,8),('Good value for the price.',3,5,5,10),('Affordable and reliable.',4,5,7,7),('Delivery took too long.',2,5,9,9),('Customer support was helpful.',4,6,6,3),('Would buy again.',5,6,8,8),('Well packed and delivered.',5,6,10,10),('Exceptional quality!',5,7,1,1),('Late delivery but worth the wait.',3,7,7,3),('Lacks some features.',2,7,9,9),('Will recommend to others.',4,8,2,2),('Perfect for everyday use.',5,8,8,10),('Quick response from seller.',4,8,10,10),('Received wrong item.',1,9,1,1),('Defective item received.',1,9,3,3),('Not as expected. Needs improvement.',2,9,9,9),('Easy to use.',5,10,2,2),('Smooth transaction.',5,10,4,4),('Highly recommended!',5,10,10,10),('Good overall experience.',4,11,1,1),('Packaging was perfect.',4,11,3,3),('Great after-sales service.',5,11,5,5),('Average quality.',3,12,2,2),('Item was missing parts.',2,12,4,4);
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,_binary '','user'),(2,_binary '','admin'),(3,_binary '','manager'),(4,_binary '','shipper');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `day_of_birth` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2024-11-26','2024-11-26','1998-02-17','admin@gmail.com','admin','Male','avtdefault.jpg',_binary '','tech system','$2a$10$/myu1zj8VKZqdZvZkRandu40Z2GGGsTWojd4I4hcmE8jl5c1t02cK','0242518421','admin',2),(2,'2024-12-01','2024-12-07','1985-05-21','user0@gmail.com','Huệ','Nữ','avtdefault.jpg',_binary '','Nguyễn Hạ','$2a$10$N.9Gn2LG2BoCLfCpJAQ7Sekq.FmM2xqEqsdUF0RwSWOYMuVnaeEH6','0468392418','user0',1),(3,'2024-12-01','2024-12-01','1997-03-19','user1@gmail.com','Vũ','Male','avtdefault.jpg',_binary '','Chu Dực','$2a$10$VPo9xI4w46Gs49IM27Uvgepx4N6JAC0TxIH1.kOsZ5w8zZawy/y5m','0482749317','user1',4),(4,'2024-12-01','2024-12-01','1983-11-23','user2@gmail.com','Nam','Male','avtdefault.jpg',_binary '','Nguyễn Hải','$2a$10$tiH8Z2RDNg5jYGi/kZIAUuJVRAh3gqLivp6bi1xT8vy3XGMed46YW','0849284184','user2',3),(5,'2024-12-01','2024-12-01','2002-01-28','user3@gmail.com','Kha','Male','avtdefault.jpg',_binary '','Cao Vũ','$2a$10$7egE7VfRkb9yijX3mRCBwObiT1R6Xhfvt9yWtRhNcnay8wBdk2yyK','0683184931','user3',2),(6,'2024-12-01','2024-12-01','2005-08-12','user4@gmail.com','Linh','Female','avtdefault.jpg',_binary '','Mai Hồng','$2a$10$l8bsB64Lx9RsKMD.vN2zL.VkCNhMgVVOUkYt9GsYPfKTDRVyTIDX2','0958274924','user4',1),(7,'2024-12-01','2024-12-01','2003-09-13','user5@gmail.com','Hà','Female','avtdefault.jpg',_binary '','Khả Dực','$2a$10$3rVyCNYm4a/7WacLxVdQ3.gem55/cy.xOkCud/jhJiKPr/AZbPCWO','0938593719','user5',3),(8,'2024-12-01','2024-12-01','2001-12-30','user6@gmail.com','Qúy','Male','avtdefault.jpg',_binary '','Huỳnh Cao','$2a$10$iefUosDmtXHDu4ZrvnpzeeneZP4Ible80QMbJ4o9ywRdf/Pc.7KAW','0484352421','user6',2),(9,'2024-12-01','2024-12-01','1979-07-30','user7@gmail.com','Lân','Male','avtdefault.jpg',_binary '','Hạ Huỳnh','$2a$10$YwYwdpoB84WpwmZv1HERW.ndaOvf4ipeGr/eWjIqjYXLkf17D6mka','0384728462','user7',4),(10,'2024-12-01','2024-12-01','1978-12-12','user8@gmail.com','Hân','Female','avtdefault.jpg',_binary '','Lê Khánh','$2a$10$dudt3HkihTqISMCopp0dD.EM49vEijBJcEgPFg8D4FmDkeUTAfk4q','0482673517','user8',4),(11,'2024-12-01','2024-12-01','2003-11-15','user9@gmail.com','Long','Male','avtdefault.jpg',_binary '','Vũ Bá','$2a$10$0znOanNbJUAuzGL3g3wIAedw./NL1J16dxu9PHO1.EVUs6Dj65P1q','0274682648','user9',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verifytion_tokens`
--

DROP TABLE IF EXISTS `verifytion_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verifytion_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `is_confirmed` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKglptnrnclfy2ony8po4beisg8` (`user_id`),
  CONSTRAINT `FKfh2147m2jhvi2vnjg80c6bwil` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verifytion_tokens`
--

LOCK TABLES `verifytion_tokens` WRITE;
/*!40000 ALTER TABLE `verifytion_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `verifytion_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `expired_date` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  `value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES (1,'2024-11-01','2024-11-01','2024-12-01','Voucher 1',100,10.00),(2,'2024-11-02','2024-11-02','2024-12-02','Voucher 2',150,5.00),(3,'2024-11-03','2024-11-03','2024-12-03','Voucher 3',200,20.00),(4,'2024-11-04','2024-11-04','2024-12-04','Voucher 4',250,11.00),(5,'2024-11-05','2024-11-05','2024-12-05','Voucher 5',300,8.00),(6,'2024-11-06','2024-11-06','2024-12-06','Voucher 6',120,4.00),(7,'2024-11-07','2024-11-07','2024-12-07','Voucher 7',180,19.00),(8,'2024-11-08','2024-11-08','2024-12-08','Voucher 8',220,17.00),(9,'2024-11-09','2024-11-09','2024-12-09','Voucher 9',130,13.00),(10,'2024-11-10','2024-11-10','2024-12-10','Voucher 10',170,9.00);
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist_items`
--

DROP TABLE IF EXISTS `wishlist_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` bit(1) NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `wishlist_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqxj7lncd242b59fb78rqegyxj` (`product_id`),
  KEY `FKkem9l8vd14pk3cc4elnpl0n00` (`wishlist_id`),
  CONSTRAINT `FKkem9l8vd14pk3cc4elnpl0n00` FOREIGN KEY (`wishlist_id`) REFERENCES `wishlists` (`id`),
  CONSTRAINT `FKqxj7lncd242b59fb78rqegyxj` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist_items`
--

LOCK TABLES `wishlist_items` WRITE;
/*!40000 ALTER TABLE `wishlist_items` DISABLE KEYS */;
INSERT INTO `wishlist_items` VALUES (2,_binary '',1,1),(3,_binary '',2,1),(4,_binary '\0',3,2),(5,_binary '',4,2),(6,_binary '',5,3),(7,_binary '\0',6,3),(8,_binary '',7,4),(9,_binary '\0',8,4),(10,_binary '',9,5),(11,_binary '',10,5),(12,_binary '\0',11,6),(13,_binary '',12,6),(14,_binary '',13,7),(15,_binary '\0',14,7),(16,_binary '',15,8),(17,_binary '\0',16,8),(18,_binary '',17,9),(19,_binary '',18,9),(20,_binary '\0',19,10),(21,_binary '',20,10),(22,_binary '',21,4),(23,_binary '\0',22,7),(24,_binary '',23,10),(25,_binary '',24,5),(26,_binary '\0',25,9);
/*!40000 ALTER TABLE `wishlist_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlists`
--

DROP TABLE IF EXISTS `wishlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlists` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKobh8c909a28dx3aqh4cbdhh25` (`user_id`),
  CONSTRAINT `FK330pyw2el06fn5g28ypyljt16` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlists`
--

LOCK TABLES `wishlists` WRITE;
/*!40000 ALTER TABLE `wishlists` DISABLE KEYS */;
INSERT INTO `wishlists` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10);
/*!40000 ALTER TABLE `wishlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'tech_shop'
--

--
-- Dumping routines for database 'tech_shop'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-08 11:25:37
