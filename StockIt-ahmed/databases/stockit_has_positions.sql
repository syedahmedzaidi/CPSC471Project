-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: stockit
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `has_positions`
--

DROP TABLE IF EXISTS `has_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `has_positions` (
  `Account_ID` int(11) NOT NULL,
  `Stock_ID` varchar(255) NOT NULL,
  `Qty` int(11) NOT NULL,
  PRIMARY KEY (`Stock_ID`,`Account_ID`),
  KEY `Account_ID` (`Account_ID`),
  CONSTRAINT `has_positions_ibfk_1` FOREIGN KEY (`Stock_ID`) REFERENCES `stock` (`StockID`),
  CONSTRAINT `has_positions_ibfk_2` FOREIGN KEY (`Account_ID`) REFERENCES `account` (`Account_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has_positions`
--

LOCK TABLES `has_positions` WRITE;
/*!40000 ALTER TABLE `has_positions` DISABLE KEYS */;
INSERT INTO `has_positions` VALUES (1,'AIR.PA',669),(4,'AIR.PA',441),(5,'AIR.PA',211),(9,'AIR.PA',2334),(1,'BABA',21),(3,'BABA',2012),(5,'BABA',5677),(6,'BABA',1285),(7,'BABA',211),(8,'BABA',23),(1,'GOOGL',790),(5,'GOOGL',125),(8,'GOOGL',244),(10,'GOOGL',221),(1,'INTC',19),(2,'INTC',1987),(5,'INTC',2456),(6,'INTC',211),(7,'INTC',2244),(10,'INTC',19),(1,'TSLA',19),(2,'TSLA',671),(4,'TSLA',2016),(5,'TSLA',567),(10,'TSLA',244),(2,'YHOO',778),(5,'YHOO',963),(6,'YHOO',2465),(9,'YHOO',211);
/*!40000 ALTER TABLE `has_positions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-28 16:28:23
