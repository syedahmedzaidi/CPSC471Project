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
-- Table structure for table `stock_daily_performance`
--

DROP TABLE IF EXISTS `stock_daily_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_daily_performance` (
  `Date` datetime NOT NULL,
  `StockID` varchar(255) NOT NULL,
  `Volume` double NOT NULL,
  `Opening_Price` double NOT NULL,
  `Closing_Price` double DEFAULT NULL,
  `High` double DEFAULT NULL,
  `Low` double DEFAULT NULL,
  `Currency` varchar(50) NOT NULL,
  PRIMARY KEY (`StockID`,`Date`),
  CONSTRAINT `stock_daily_performance_ibfk_1` FOREIGN KEY (`StockID`) REFERENCES `stock` (`StockID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_daily_performance`
--

LOCK TABLES `stock_daily_performance` WRITE;
/*!40000 ALTER TABLE `stock_daily_performance` DISABLE KEYS */;
INSERT INTO `stock_daily_performance` VALUES ('2016-00-01 00:00:00','AIR.PA',2104695,60.26,62.84,64.98,58.09,'USD'),('2016-00-02 00:00:00','AIR.PA',1976295,53.6,56.02,56.48,52.2,'USD'),('2016-00-03 00:00:00','AIR.PA',2115795,53.84,54.15,55.9,51.5,'USD'),('2016-00-25 00:00:00','AIR.PA',2023395,59.77,58.35,60.48,58.03,'USD'),('2017-00-01 00:00:00','AIR.PA',1676195,69.69,70.02,71.85,68.28,'USD'),('2017-00-02 00:00:00','AIR.PA',1833095,62.68,62.76,66.32,62.31,'USD'),('2016-00-01 00:00:00','BABA',10579098,94,87.809998,94.055,86.010002,'USD'),('2016-00-02 00:00:00','BABA',21688098,76.889999,82,82.029999,74.120003,'USD'),('2016-00-03 00:00:00','BABA',12021998,105.449997,101.690002,109,99,'USD'),('2016-00-28 00:00:00','BABA',12987498,76.449997,79.029999,79.839996,74.900002,'USD'),('2017-00-01 00:00:00','BABA',9425598,103.68,108.040001,109.129997,102.099998,'USD'),('2017-00-03 00:00:00','BABA',12135798,89,101.309998,104.57,88.080002,'USD'),('2016-00-01 00:00:00','INTC',20160598,34.860001,36.27,37.349998,33.560001,'USD'),('2016-00-02 00:00:00','INTC',21642098,30.450001,31.59,31.65,29.5,'USD'),('2016-00-03 00:00:00','INTC',23373198,37.689999,34.869999,38.360001,34.709999,'USD'),('2016-00-28 00:00:00','INTC',19775998,31.870001,32.349998,32.75,31.620001,'USD'),('2017-00-01 00:00:00','INTC',26260898,35.849998,35.16,36.299999,34.66,'USD'),('2017-00-03 00:00:00','INTC',24169098,36.610001,36.82,38.450001,36.189999,'USD'),('2016-00-01 00:00:00','TSLA',4411200,188.25,213.690002,223.800003,180,'USD'),('2016-00-02 00:00:00','TSLA',5051600,241.5,223.229996,243.190002,203.660004,'USD'),('2016-00-03 00:00:00','TSLA',4610700,212.300003,197.729996,215.669998,192,'USD'),('2016-00-28 00:00:00','TSLA',6999700,231.610001,229.770004,237.419998,225,'USD'),('2017-00-01 00:00:00','TSLA',4807900,254.179993,263.160004,265.75,242.779999,'USD'),('2017-00-03 00:00:00','TSLA',5238600,214.860001,251.929993,258.459991,210.960007,'USD'),('2016-00-01 00:00:00','YHOO',10912200,41,38.669998,41.799999,38.240002,'USD'),('2016-00-02 00:00:00','YHOO',13390000,36.650002,37.939999,38.080002,35.470001,'USD'),('2016-00-03 00:00:00','YHOO',8704100,42.950001,41.549999,44.080002,41.169998,'USD'),('2016-00-28 00:00:00','YHOO',23448000,35.389999,36.810001,37.279999,34.619999,'USD'),('2017-00-01 00:00:00','YHOO',5825100,45.889999,46.400002,47.189999,45.389999,'USD'),('2017-00-03 00:00:00','YHOO',8554700,39.110001,44.07,45.080002,38.639999,'USD');
/*!40000 ALTER TABLE `stock_daily_performance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-28 16:28:22