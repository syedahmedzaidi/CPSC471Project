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
-- Table structure for table `buy_sell`
--

DROP TABLE IF EXISTS `buy_sell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buy_sell` (
  `Trader_SSN` int(11) NOT NULL,
  `Stock_ID` varchar(255) NOT NULL,
  `Account_ID` int(11) NOT NULL,
  `Transaction_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Date` datetime NOT NULL,
  `Qty` int(11) NOT NULL,
  `PPS` double NOT NULL,
  PRIMARY KEY (`Transaction_ID`),
  KEY `Stock_ID` (`Stock_ID`),
  KEY `Account_ID` (`Account_ID`),
  KEY `Trader_SSN` (`Trader_SSN`),
  CONSTRAINT `buy_sell_ibfk_1` FOREIGN KEY (`Stock_ID`) REFERENCES `stock` (`StockID`),
  CONSTRAINT `buy_sell_ibfk_2` FOREIGN KEY (`Account_ID`) REFERENCES `account` (`Account_ID`),
  CONSTRAINT `buy_sell_ibfk_3` FOREIGN KEY (`Trader_SSN`) REFERENCES `trader` (`Trader_SSN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buy_sell`
--

LOCK TABLES `buy_sell` WRITE;
/*!40000 ALTER TABLE `buy_sell` DISABLE KEYS */;
/*!40000 ALTER TABLE `buy_sell` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-28 16:28:21
