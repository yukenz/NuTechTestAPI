-- MariaDB dump 10.19-11.1.2-MariaDB, for osx10.16 (x86_64)
--
-- Host: 127.0.0.1    Database: nutech
-- ------------------------------------------------------
-- Server version	11.1.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `image_url` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES
(1,'Banner 1','Lerem Ipsum Dolor sit amet','https://nutech-integrasi.app/dummy.jpg'),
(2,'Banner 2','Lerem Ipsum Dolor sit amet','https://nutech-integrasi.app/dummy.jpg'),
(3,'Banner 3','Lerem Ipsum Dolor sit amet','https://nutech-integrasi.app/dummy.jpg');
/*!40000 ALTER TABLE `banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `members` (
  `email` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `profile_image` varchar(200) DEFAULT NULL,
  `balance` bigint(20) DEFAULT 0,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES
('0LkCBdwizN@gmail.com','yuyun','purniawan','bbaaaa526720a6dda0a0548031af2ad3',NULL,0),
('user@nutech-integrasi.com','User Edited','Nutech Edited','9bb793c73de0193293096d68f93d2e75',NULL,40000),
('yuyun.purniawan@gmail.com','yukenz','purniawan','bbaaaa526720a6dda0a0548031af2ad3','http://localhost:8080/yuyun_purniawan_gmail_com.jpg',380000);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `price` bigint(20) NOT NULL,
  `icon_url` varchar(200) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES
('MUSIK','Musik Berlangganan','Musik Spotify',50000,'https://nutech-integrasi.app/dummy.jpg'),
('PAJAK','Pajak PBB','Pajak Bangunan',40000,'https://nutech-integrasi.app/dummy.jpg'),
('PAKET_DATA','Paket data','Data Internet Telkom',50000,'https://nutech-integrasi.app/dummy.jpg'),
('PDAM','PDAM Berlangganan','Air Kran',40000,'https://nutech-integrasi.app/dummy.jpg'),
('PGN','PGN Berlangganan','PGN Service',50000,'https://nutech-integrasi.app/dummy.jpg'),
('PLN','Listrik','PLN Pascabayar',10000,'https://nutech-integrasi.app/dummy.jpg'),
('PULSA','Pulsa','Pulsa Indosat',40000,'https://nutech-integrasi.app/dummy.jpg'),
('QURBAN','Qurban','Qurban Sapi',200000,'https://nutech-integrasi.app/dummy.jpg'),
('TV','TV Berlangganan','TV Kabel',50000,'https://nutech-integrasi.app/dummy.jpg'),
('VOUCHER_GAME','Voucher Game','Game Mobile Legend',100000,'https://nutech-integrasi.app/dummy.jpg'),
('VOUCHER_MAKANAN','Voucher Makanan','Makanan Starbuck',100000,'https://nutech-integrasi.app/dummy.jpg'),
('ZAKAT','Zakat','Zakat Puasa',300000,'https://nutech-integrasi.app/dummy.jpg');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `invoice` varchar(50) NOT NULL,
  `member_email` varchar(50) DEFAULT NULL,
  `type` enum('PAYMENT','TOPUP') DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `created_on` timestamp NOT NULL,
  PRIMARY KEY (`invoice`),
  KEY `member_transact` (`member_email`),
  CONSTRAINT `member_transact` FOREIGN KEY (`member_email`) REFERENCES `members` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES
('INV28102023-162707953','yuyun.purniawan@gmail.com','PAYMENT','Pajak Bangunan',40000,'2023-10-28 09:27:07'),
('INV28102023-163635224','yuyun.purniawan@gmail.com','PAYMENT','Pajak Bangunan',40000,'2023-10-28 09:36:35'),
('INV28102023-163702795','yuyun.purniawan@gmail.com','PAYMENT','Pajak Bangunan',40000,'2023-10-28 09:37:02'),
('INV28102023-170808312','yuyun.purniawan@gmail.com','TOPUP','Top Up',10000,'2023-10-28 10:08:08'),
('INV28102023-170824799','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-28 10:08:24'),
('INV28102023-171327367','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-28 10:13:27'),
('INV28102023-171359173','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-28 10:13:59'),
('INV29102023-081347450','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:13:47'),
('INV29102023-081356913','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:13:56'),
('INV29102023-081434992','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:14:34'),
('INV29102023-081443920','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:14:43'),
('INV29102023-081506850','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:15:06'),
('INV29102023-081529997','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:15:29'),
('INV29102023-081545236','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:15:45'),
('INV29102023-082034883','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:20:34'),
('INV29102023-082104636','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:21:04'),
('INV29102023-082133406','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:21:33'),
('INV29102023-083118342','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:31:18'),
('INV29102023-083118386','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:31:18'),
('INV29102023-083646852','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:36:46'),
('INV29102023-083646894','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:36:46'),
('INV29102023-083814107','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:38:14'),
('INV29102023-08381468','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:38:14'),
('INV29102023-083909522','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:39:09'),
('INV29102023-083909576','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:39:09'),
('INV29102023-084029126','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:40:29'),
('INV29102023-08402986','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:40:29'),
('INV29102023-084219108','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:42:19'),
('INV29102023-084219156','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:42:19'),
('INV29102023-084335917','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 01:43:35'),
('INV29102023-084335958','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 01:43:35'),
('INV29102023-092636400','user@nutech-integrasi.com','TOPUP','Top Up',10000,'2023-10-29 02:26:36'),
('INV29102023-092702670','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:02'),
('INV29102023-092707219','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:07'),
('INV29102023-092710522','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:10'),
('INV29102023-092712688','user@nutech-integrasi.com','TOPUP','Top Up',10000,'2023-10-29 02:27:12'),
('INV29102023-092714650','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:14'),
('INV29102023-09272752','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:27'),
('INV29102023-092748110','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:48'),
('INV29102023-092750116','user@nutech-integrasi.com','TOPUP','Top Up',10000,'2023-10-29 02:27:50'),
('INV29102023-09275271','user@nutech-integrasi.com','TOPUP','Top Up',0,'2023-10-29 02:27:52'),
('INV29102023-093020930','user@nutech-integrasi.com','TOPUP','Top Up',50000,'2023-10-29 02:30:20'),
('INV29102023-093023385','user@nutech-integrasi.com','PAYMENT','Pulsa Indosat',40000,'2023-10-29 02:30:23'),
('INV29102023-113617781','yuyun.purniawan@gmail.com','PAYMENT','PLN Pascabayar',10000,'2023-10-29 04:36:17'),
('INV29102023-113617870','yuyun.purniawan@gmail.com','TOPUP','Top Up',50000,'2023-10-29 04:36:17');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-29 12:41:16
