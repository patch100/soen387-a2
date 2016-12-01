-- MySQL dump 10.13  Distrib 5.7.16, for osx10.11 (x86_64)
--
-- Host: 127.0.0.1    Database: revenue
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contracts` (
  `ID` int(11) NOT NULL,
  `revenue` decimal(10,0) DEFAULT NULL,
  `dateSigned` date DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `modifiedby` varchar(20) NOT NULL,
  `modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CONTRACT_TABLE_PRODUCT_ID` (`product_id`),
  CONSTRAINT `FK_CONTRACT_TABLE_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `products` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (1,5000,'2016-11-29',1,'pyoung','2016-11-28 21:25:26',0),(2,10000,'2016-11-01',3,'pyoung','2016-11-28 21:25:16',0);
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `ID` int(11) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `modifiedby` varchar(20) NOT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Spreadsheet','Excel Sheet 1','pyoung','2016-11-29 02:26:29',0),(2,'Word Processor','Word 1','pyoung','2016-11-29 02:26:29',0),(3,'Database','Access 1','pyoung','2016-11-29 02:26:29',0),(4,'Database','Access 2','pyoung','2016-11-29 02:26:29',0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revenueRecognitions`
--

DROP TABLE IF EXISTS `revenueRecognitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revenueRecognitions` (
  `ID` int(11) NOT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `recognizedOn` date NOT NULL,
  `contract_id` int(11) NOT NULL,
  `modifiedby` varchar(20) NOT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`contract_id`,`recognizedOn`),
  CONSTRAINT `revenueRecognitions_contracts_ID_fk` FOREIGN KEY (`contract_id`) REFERENCES `contracts` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revenueRecognitions`
--

LOCK TABLES `revenueRecognitions` WRITE;
/*!40000 ALTER TABLE `revenueRecognitions` DISABLE KEYS */;
INSERT INTO `revenueRecognitions` VALUES (1,1667,'2016-11-29',1,'pyoung','2016-11-29 02:51:44',0),(2,1667,'2017-01-28',1,'pyoung','2016-11-29 02:51:44',0),(3,1667,'2017-02-27',1,'pyoung','2016-11-29 02:51:44',0),(4,3333,'2016-11-01',2,'pyoung','2016-11-29 02:51:44',0),(5,3333,'2016-12-01',2,'pyoung','2016-11-29 02:51:44',0),(6,3333,'2016-12-31',2,'pyoung','2016-11-29 02:51:44',0);
/*!40000 ALTER TABLE `revenueRecognitions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-01 13:47:33
