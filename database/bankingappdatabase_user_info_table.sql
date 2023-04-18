-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: bankingappdatabase
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `user_info_table`
--

DROP TABLE IF EXISTS `user_info_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info_table` (
  `user_ID` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL,
  `user_surname` varchar(30) DEFAULT NULL,
  `user_password` varchar(64) DEFAULT NULL,
  `user_password_salt` int DEFAULT NULL,
  `user_IBAN` varchar(34) DEFAULT NULL,
  `user_email_address` varchar(45) DEFAULT NULL,
  `user_phone_number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_ID`),
  UNIQUE KEY `user_ID_UNIQUE` (`user_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info_table`
--

LOCK TABLES `user_info_table` WRITE;
/*!40000 ALTER TABLE `user_info_table` DISABLE KEYS */;
INSERT INTO `user_info_table` VALUES (16,'Test','Account','4f5e9f89d01350a011227ba411fdb6e35cfcaf855a15c6441873667f014f90bb',3674,'TR95 3907 7984 4977 7812 2299 44','testaccount@test.com','05304021998'),(19,'Ulas Tan','Ersoyak','909d5a06aaa5a12df14c4b29494993a832a08a461b09993d29447f2380932e70',1836,'TR24 5431 1023 3492 2731 1741 48','ulastanersoyak@hotmail.com','05347369310'),(20,'Mehtap','Ersoyak','391431803caa8dd76a8dd82d3772b0e752348fb97d9c9cefff94e8d8983730af',1494,'TR75 0324 4146 6816 6489 9180 74','mehtapersoyak@hotmail.com','05304021997');
/*!40000 ALTER TABLE `user_info_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-15 16:07:21
