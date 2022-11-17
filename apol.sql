-- MariaDB dump 10.19  Distrib 10.4.22-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: registro_calificaciones
-- ------------------------------------------------------
-- Server version	10.4.22-MariaDB

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
-- Table structure for table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumnos` (
  `curp` varchar(18) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido_paterno` varchar(50) NOT NULL,
  `apellido_materno` varchar(50) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `genero` char(1) NOT NULL,
  `id_tutor` int(11) NOT NULL,
  `id_aula` int(11) NOT NULL,
  PRIMARY KEY (`curp`),
  UNIQUE KEY `registro_unico` (`nombre`,`apellido_paterno`,`apellido_materno`),
  KEY `id_tutor` (`id_tutor`),
  KEY `id_aula` (`id_aula`),
  CONSTRAINT `alumnos_ibfk_1` FOREIGN KEY (`id_tutor`) REFERENCES `tutores` (`id_tutor`),
  CONSTRAINT `alumnos_ibfk_2` FOREIGN KEY (`id_aula`) REFERENCES `aulas` (`id_aula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos`
--

LOCK TABLES `alumnos` WRITE;
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO `alumnos` VALUES ('AOCM011012HGRPSNA1','Maria','Torres','Villanueva','2016-10-01','M',39,9),('AOCM120101HGRPSNA9','carolina','Astudillo','Hernandez','2012-01-01','M',40,22),('AOCM141012HGRPSNA9','juanito','perez','sanchez','2014-10-12','H',40,16),('AOCM160101HGRPSNA9','Manuel','apolinar','castillo','2016-01-01','H',40,9),('AOCM161010HGRPSNA9','Juan','Torres','Villalobos','2016-10-10','H',40,9);
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignaturas`
--

DROP TABLE IF EXISTS `asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignaturas` (
  `id_asignatura` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `id_tipo_asignatura` int(11) NOT NULL,
  `id_grado` int(11) NOT NULL,
  PRIMARY KEY (`id_asignatura`),
  KEY `id_grado` (`id_grado`),
  KEY `id_tipo_asignatura` (`id_tipo_asignatura`),
  CONSTRAINT `asignaturas_ibfk_1` FOREIGN KEY (`id_grado`) REFERENCES `grados` (`id_grado`),
  CONSTRAINT `asignaturas_ibfk_2` FOREIGN KEY (`id_tipo_asignatura`) REFERENCES `tipo_asignaturas` (`id_tipo_asignatura`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturas`
--

LOCK TABLES `asignaturas` WRITE;
/*!40000 ALTER TABLE `asignaturas` DISABLE KEYS */;
INSERT INTO `asignaturas` VALUES (1,'Lengua Materna',3,1),(2,'Matemáticas',3,1),(3,'Ingles',3,1),(4,'Conocim del medio',3,1),(5,'Artes',3,1),(6,'ED. Fisica',3,1),(7,'Computacion',3,1),(8,'Edu. socioemocional',4,1),(9,'Inf. y robotica',4,1),(10,'vida saludable',4,1),(11,'Lengua Materna',3,2),(12,'Matemáticas',3,2),(13,'Ingles',3,2),(14,'Conocim del medio',3,2),(15,'Artes',3,2),(16,'ED. Fisica',3,2),(17,'Computacion',3,2),(18,'Edu. socioemocional',4,2),(19,'Inf. y robotica',4,2),(20,'vida saludable',4,2),(21,'Español',3,3),(22,'Matemáticas',3,3),(23,'Ingles',3,3),(24,'Ciencias Naturales',3,3),(25,'Entidad Donde Vivo',3,3),(26,'Formacion Civica Etica',3,3),(27,'Artes',3,3),(28,'ED. fisica',3,3),(29,'Computacion',3,3),(30,'Edu. socioemocional',4,3),(31,'Inf. y robotica',4,3),(32,'vida saludable',4,3),(33,'Español',3,4),(34,'Matemáticas',3,4),(35,'Ingles',3,4),(36,'Ciencias Naturales',3,4),(37,'Geografia',3,4),(38,'Historia',3,4),(39,'Formacion Civica Etica',3,4),(40,'Artes',3,4),(41,'ED. fisica',3,4),(42,'Computacion',3,4),(43,'Edu. socioemocional',4,4),(44,'Inf. y robotica',4,4),(45,'Vida Saludable',4,4),(46,'Español',3,5),(47,'Matemáticas',3,5),(48,'Ingles',3,5),(49,'Ciencias Naturales',3,5),(50,'Geografia',3,5),(51,'Historia',3,5),(52,'Formacion Civica Etica',3,5),(53,'Artes',3,5),(54,'ED. fisica',3,5),(55,'Computacion',3,5),(56,'Edu. socioemocional',4,5),(57,'Inf. y robotica',4,5),(58,'Vida Saludable',4,5),(59,'Español',3,6),(60,'Matemáticas',3,6),(61,'Ingles',3,6),(62,'Ciencias Naturales',3,6),(63,'Geografia',3,6),(64,'Historia',3,6),(65,'Formacion Civica Etica',3,6),(66,'Artes',3,6),(67,'ED. fisica',3,6),(68,'Computacion',3,6),(69,'Edu. socioemocional',4,6),(70,'Inf. y robotica',4,6),(71,'Vida Saludable',4,6);
/*!40000 ALTER TABLE `asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aulas`
--

DROP TABLE IF EXISTS `aulas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aulas` (
  `id_aula` int(11) NOT NULL AUTO_INCREMENT,
  `id_grado` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  PRIMARY KEY (`id_aula`),
  UNIQUE KEY `aulaUnica` (`id_grado`,`id_grupo`),
  KEY `id_grupo` (`id_grupo`),
  CONSTRAINT `aulas_ibfk_1` FOREIGN KEY (`id_grado`) REFERENCES `grados` (`id_grado`),
  CONSTRAINT `aulas_ibfk_2` FOREIGN KEY (`id_grupo`) REFERENCES `grupos` (`id_grupo`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aulas`
--

LOCK TABLES `aulas` WRITE;
/*!40000 ALTER TABLE `aulas` DISABLE KEYS */;
INSERT INTO `aulas` VALUES (9,1,1),(10,1,2),(12,1,3),(13,2,1),(14,2,2),(15,2,3),(16,3,1),(17,3,2),(18,3,3),(19,4,1),(20,4,2),(21,4,3),(22,5,1),(23,5,2),(24,5,3),(25,6,1),(26,6,2),(27,6,3);
/*!40000 ALTER TABLE `aulas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacora_sesion_usuarios`
--

DROP TABLE IF EXISTS `bitacora_sesion_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bitacora_sesion_usuarios` (
  `id_bitacora_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `dia` tinyint(4) NOT NULL,
  `anio` smallint(4) NOT NULL,
  `mes` varchar(25) NOT NULL,
  `hora_entrada` varchar(50) NOT NULL,
  `hora_salida` varchar(50) NOT NULL,
  PRIMARY KEY (`id_bitacora_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacora_sesion_usuarios`
--

LOCK TABLES `bitacora_sesion_usuarios` WRITE;
/*!40000 ALTER TABLE `bitacora_sesion_usuarios` DISABLE KEYS */;
INSERT INTO `bitacora_sesion_usuarios` VALUES (10,'secre1',5,2022,'OCTOBER','23:3','23:3'),(11,'direc',6,2022,'OCTOBER','17:23','17:23'),(12,'direc',6,2022,'OCTOBER','17:23','17:23'),(13,'direc',6,2022,'OCTOBER','17:24','17:24'),(14,'manuel',6,2022,'OCTOBER','17:25','17:25'),(15,'direc',6,2022,'OCTOBER','17:25','17:25'),(16,'direc',6,2022,'OCTOBER','17:38','17:38'),(17,'direc',6,2022,'OCTOBER','22:35','22:35'),(18,'secretaria1',6,2022,'OCTOBER','22:38','22:38'),(19,'secretaria1',6,2022,'OCTOBER','22:39','22:39'),(20,'direc',6,2022,'OCTOBER','22:39','22:39'),(21,'direc',6,2022,'OCTOBER','23:32','23:32'),(22,'direc',6,2022,'OCTOBER','23:34','23:34'),(23,'direc',7,2022,'OCTOBER','10:35','10:35'),(24,'direc',13,2022,'OCTOBER','18:4','18:4'),(25,'direc',13,2022,'OCTOBER','18:4','18:4'),(26,'direc',13,2022,'OCTOBER','18:8','18:8'),(27,'direc',13,2022,'OCTOBER','18:9','18:9'),(28,'direc',13,2022,'OCTOBER','18:13','18:13'),(29,'direc',13,2022,'OCTOBER','18:14','18:14'),(30,'direc',13,2022,'OCTOBER','18:16','18:16'),(31,'direc',13,2022,'OCTOBER','18:17','18:17'),(32,'direc',13,2022,'OCTOBER','18:19','18:19'),(33,'direc',13,2022,'OCTOBER','18:19','18:20'),(34,'direc',13,2022,'OCTOBER','18:44','18:44'),(35,'direc',13,2022,'OCTOBER','22:14','22:15'),(36,'direc',14,2022,'OCTOBER','1:38','1:38'),(37,'direc',14,2022,'OCTOBER','1:38','1:38'),(38,'direc',21,2022,'OCTOBER','16:32','16:33'),(39,'margarita',24,2022,'OCTOBER','16:49','16:49'),(40,'direc',30,2022,'OCTOBER','22:55','22:55'),(41,'direc',30,2022,'OCTOBER','22:59','22:59');
/*!40000 ALTER TABLE `bitacora_sesion_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `boletas`
--

DROP TABLE IF EXISTS `boletas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boletas` (
  `folio` int(11) NOT NULL AUTO_INCREMENT,
  `ruta_pdf` varchar(100) DEFAULT NULL,
  `curp` varchar(18) NOT NULL,
  `id_tipo_boleta` int(11) NOT NULL,
  PRIMARY KEY (`folio`),
  UNIQUE KEY `ruta_pdf` (`ruta_pdf`),
  KEY `curp` (`curp`),
  KEY `id_tipo_boleta` (`id_tipo_boleta`),
  CONSTRAINT `boletas_ibfk_1` FOREIGN KEY (`curp`) REFERENCES `alumnos` (`curp`),
  CONSTRAINT `boletas_ibfk_2` FOREIGN KEY (`id_tipo_boleta`) REFERENCES `tipos_boleta` (`id_tipo_boleta`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boletas`
--

LOCK TABLES `boletas` WRITE;
/*!40000 ALTER TABLE `boletas` DISABLE KEYS */;
INSERT INTO `boletas` VALUES (1,'asdsadsadsa','AOCM011012HGRPSNA1',3),(2,'sdfdsfdsf','AOCM120101HGRPSNA9',4);
/*!40000 ALTER TABLE `boletas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calificaciones`
--

DROP TABLE IF EXISTS `calificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calificaciones` (
  `id_calificacion` int(11) NOT NULL AUTO_INCREMENT,
  `resultado` decimal(4,2) NOT NULL,
  `curp` varchar(18) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_grado` int(11) NOT NULL,
  `id_asignatura` int(11) NOT NULL,
  `id_ciclo_escolar` int(11) NOT NULL,
  `id_mes` int(11) NOT NULL,
  PRIMARY KEY (`id_calificacion`),
  KEY `id_asignatura` (`id_asignatura`),
  KEY `id_ciclo_escolar` (`id_ciclo_escolar`),
  KEY `id_grado` (`id_grado`),
  KEY `id_grupo` (`id_grupo`),
  KEY `id_mes` (`id_mes`),
  KEY `curp` (`curp`),
  CONSTRAINT `calificaciones_ibfk_1` FOREIGN KEY (`id_asignatura`) REFERENCES `asignaturas` (`id_asignatura`),
  CONSTRAINT `calificaciones_ibfk_2` FOREIGN KEY (`id_ciclo_escolar`) REFERENCES `ciclos_escolares` (`id_ciclo_escolar`),
  CONSTRAINT `calificaciones_ibfk_3` FOREIGN KEY (`id_grado`) REFERENCES `grados` (`id_grado`),
  CONSTRAINT `calificaciones_ibfk_4` FOREIGN KEY (`id_grupo`) REFERENCES `grupos` (`id_grupo`),
  CONSTRAINT `calificaciones_ibfk_5` FOREIGN KEY (`id_mes`) REFERENCES `meses` (`id_mes`),
  CONSTRAINT `calificaciones_ibfk_6` FOREIGN KEY (`curp`) REFERENCES `alumnos` (`curp`)
) ENGINE=InnoDB AUTO_INCREMENT=1823 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones`
--

LOCK TABLES `calificaciones` WRITE;
/*!40000 ALTER TABLE `calificaciones` DISABLE KEYS */;
INSERT INTO `calificaciones` VALUES (1553,10.00,'AOCM120101HGRPSNA9',1,5,46,1,4),(1554,10.00,'AOCM120101HGRPSNA9',1,5,47,1,4),(1555,10.00,'AOCM120101HGRPSNA9',1,5,48,1,4),(1556,10.00,'AOCM120101HGRPSNA9',1,5,49,1,4),(1557,10.00,'AOCM120101HGRPSNA9',1,5,50,1,4),(1558,10.00,'AOCM120101HGRPSNA9',1,5,51,1,4),(1559,10.00,'AOCM120101HGRPSNA9',1,5,52,1,4),(1560,10.00,'AOCM120101HGRPSNA9',1,5,53,1,4),(1561,10.00,'AOCM120101HGRPSNA9',1,5,54,1,4),(1562,10.00,'AOCM120101HGRPSNA9',1,5,55,1,4),(1563,10.00,'AOCM120101HGRPSNA9',1,5,56,1,4),(1564,10.00,'AOCM120101HGRPSNA9',1,5,57,1,4),(1565,10.00,'AOCM120101HGRPSNA9',1,5,58,1,4),(1566,10.00,'AOCM120101HGRPSNA9',1,5,46,1,5),(1567,10.00,'AOCM120101HGRPSNA9',1,5,47,1,5),(1568,10.00,'AOCM120101HGRPSNA9',1,5,48,1,5),(1569,10.00,'AOCM120101HGRPSNA9',1,5,49,1,5),(1570,10.00,'AOCM120101HGRPSNA9',1,5,50,1,5),(1571,10.00,'AOCM120101HGRPSNA9',1,5,51,1,5),(1572,10.00,'AOCM120101HGRPSNA9',1,5,52,1,5),(1573,10.00,'AOCM120101HGRPSNA9',1,5,53,1,5),(1574,10.00,'AOCM120101HGRPSNA9',1,5,54,1,5),(1575,10.00,'AOCM120101HGRPSNA9',1,5,55,1,5),(1576,10.00,'AOCM120101HGRPSNA9',1,5,56,1,5),(1577,10.00,'AOCM120101HGRPSNA9',1,5,57,1,5),(1578,10.00,'AOCM120101HGRPSNA9',1,5,58,1,5),(1579,10.00,'AOCM120101HGRPSNA9',1,5,46,1,6),(1580,10.00,'AOCM120101HGRPSNA9',1,5,47,1,6),(1581,10.00,'AOCM120101HGRPSNA9',1,5,48,1,6),(1582,10.00,'AOCM120101HGRPSNA9',1,5,49,1,6),(1583,10.00,'AOCM120101HGRPSNA9',1,5,50,1,6),(1584,10.00,'AOCM120101HGRPSNA9',1,5,51,1,6),(1585,10.00,'AOCM120101HGRPSNA9',1,5,52,1,6),(1586,10.00,'AOCM120101HGRPSNA9',1,5,53,1,6),(1587,10.00,'AOCM120101HGRPSNA9',1,5,54,1,6),(1588,10.00,'AOCM120101HGRPSNA9',1,5,55,1,6),(1589,10.00,'AOCM120101HGRPSNA9',1,5,56,1,6),(1590,10.00,'AOCM120101HGRPSNA9',1,5,57,1,6),(1591,10.00,'AOCM120101HGRPSNA9',1,5,58,1,6),(1592,10.00,'AOCM120101HGRPSNA9',1,5,46,1,7),(1593,10.00,'AOCM120101HGRPSNA9',1,5,47,1,7),(1594,10.00,'AOCM120101HGRPSNA9',1,5,48,1,7),(1595,10.00,'AOCM120101HGRPSNA9',1,5,49,1,7),(1596,10.00,'AOCM120101HGRPSNA9',1,5,50,1,7),(1597,10.00,'AOCM120101HGRPSNA9',1,5,51,1,7),(1598,10.00,'AOCM120101HGRPSNA9',1,5,52,1,7),(1599,10.00,'AOCM120101HGRPSNA9',1,5,53,1,7),(1600,10.00,'AOCM120101HGRPSNA9',1,5,54,1,7),(1601,10.00,'AOCM120101HGRPSNA9',1,5,55,1,7),(1602,10.00,'AOCM120101HGRPSNA9',1,5,56,1,7),(1603,10.00,'AOCM120101HGRPSNA9',1,5,57,1,7),(1604,10.00,'AOCM120101HGRPSNA9',1,5,58,1,7),(1605,10.00,'AOCM120101HGRPSNA9',1,5,46,1,8),(1606,10.00,'AOCM120101HGRPSNA9',1,5,47,1,8),(1607,10.00,'AOCM120101HGRPSNA9',1,5,48,1,8),(1608,10.00,'AOCM120101HGRPSNA9',1,5,49,1,8),(1609,10.00,'AOCM120101HGRPSNA9',1,5,50,1,8),(1610,10.00,'AOCM120101HGRPSNA9',1,5,51,1,8),(1611,10.00,'AOCM120101HGRPSNA9',1,5,52,1,8),(1612,10.00,'AOCM120101HGRPSNA9',1,5,53,1,8),(1613,10.00,'AOCM120101HGRPSNA9',1,5,54,1,8),(1614,10.00,'AOCM120101HGRPSNA9',1,5,55,1,8),(1615,10.00,'AOCM120101HGRPSNA9',1,5,56,1,8),(1616,10.00,'AOCM120101HGRPSNA9',1,5,57,1,8),(1617,10.00,'AOCM120101HGRPSNA9',1,5,58,1,8),(1618,10.00,'AOCM120101HGRPSNA9',1,5,46,1,9),(1619,10.00,'AOCM120101HGRPSNA9',1,5,47,1,9),(1620,10.00,'AOCM120101HGRPSNA9',1,5,48,1,9),(1621,10.00,'AOCM120101HGRPSNA9',1,5,49,1,9),(1622,10.00,'AOCM120101HGRPSNA9',1,5,50,1,9),(1623,10.00,'AOCM120101HGRPSNA9',1,5,51,1,9),(1624,10.00,'AOCM120101HGRPSNA9',1,5,52,1,9),(1625,10.00,'AOCM120101HGRPSNA9',1,5,53,1,9),(1626,10.00,'AOCM120101HGRPSNA9',1,5,54,1,9),(1627,10.00,'AOCM120101HGRPSNA9',1,5,55,1,9),(1628,10.00,'AOCM120101HGRPSNA9',1,5,56,1,9),(1629,10.00,'AOCM120101HGRPSNA9',1,5,57,1,9),(1630,10.00,'AOCM120101HGRPSNA9',1,5,58,1,9),(1631,10.00,'AOCM120101HGRPSNA9',1,5,46,1,10),(1632,10.00,'AOCM120101HGRPSNA9',1,5,47,1,10),(1633,10.00,'AOCM120101HGRPSNA9',1,5,48,1,10),(1634,10.00,'AOCM120101HGRPSNA9',1,5,49,1,10),(1635,10.00,'AOCM120101HGRPSNA9',1,5,50,1,10),(1636,10.00,'AOCM120101HGRPSNA9',1,5,51,1,10),(1637,10.00,'AOCM120101HGRPSNA9',1,5,52,1,10),(1638,10.00,'AOCM120101HGRPSNA9',1,5,53,1,10),(1639,10.00,'AOCM120101HGRPSNA9',1,5,54,1,10),(1640,10.00,'AOCM120101HGRPSNA9',1,5,55,1,10),(1641,10.00,'AOCM120101HGRPSNA9',1,5,56,1,10),(1642,10.00,'AOCM120101HGRPSNA9',1,5,57,1,10),(1643,10.00,'AOCM120101HGRPSNA9',1,5,58,1,10),(1644,10.00,'AOCM120101HGRPSNA9',1,5,46,1,11),(1645,10.00,'AOCM120101HGRPSNA9',1,5,47,1,11),(1646,10.00,'AOCM120101HGRPSNA9',1,5,48,1,11),(1647,10.00,'AOCM120101HGRPSNA9',1,5,49,1,11),(1648,10.00,'AOCM120101HGRPSNA9',1,5,50,1,11),(1649,10.00,'AOCM120101HGRPSNA9',1,5,51,1,11),(1650,10.00,'AOCM120101HGRPSNA9',1,5,52,1,11),(1651,10.00,'AOCM120101HGRPSNA9',1,5,53,1,11),(1652,10.00,'AOCM120101HGRPSNA9',1,5,54,1,11),(1653,10.00,'AOCM120101HGRPSNA9',1,5,55,1,11),(1654,10.00,'AOCM120101HGRPSNA9',1,5,56,1,11),(1655,10.00,'AOCM120101HGRPSNA9',1,5,57,1,11),(1656,10.00,'AOCM120101HGRPSNA9',1,5,58,1,11),(1657,10.00,'AOCM120101HGRPSNA9',1,5,46,1,12),(1658,10.00,'AOCM120101HGRPSNA9',1,5,47,1,12),(1659,10.00,'AOCM120101HGRPSNA9',1,5,48,1,12),(1660,10.00,'AOCM120101HGRPSNA9',1,5,49,1,12),(1661,10.00,'AOCM120101HGRPSNA9',1,5,50,1,12),(1662,10.00,'AOCM120101HGRPSNA9',1,5,51,1,12),(1663,10.00,'AOCM120101HGRPSNA9',1,5,52,1,12),(1664,10.00,'AOCM120101HGRPSNA9',1,5,53,1,12),(1665,10.00,'AOCM120101HGRPSNA9',1,5,54,1,12),(1666,10.00,'AOCM120101HGRPSNA9',1,5,55,1,12),(1667,10.00,'AOCM120101HGRPSNA9',1,5,56,1,12),(1668,10.00,'AOCM120101HGRPSNA9',1,5,57,1,12),(1669,10.00,'AOCM120101HGRPSNA9',1,5,58,1,12),(1670,10.00,'AOCM120101HGRPSNA9',1,5,46,1,13),(1671,10.00,'AOCM120101HGRPSNA9',1,5,47,1,13),(1672,10.00,'AOCM120101HGRPSNA9',1,5,48,1,13),(1673,10.00,'AOCM120101HGRPSNA9',1,5,49,1,13),(1674,10.00,'AOCM120101HGRPSNA9',1,5,50,1,13),(1675,10.00,'AOCM120101HGRPSNA9',1,5,51,1,13),(1676,10.00,'AOCM120101HGRPSNA9',1,5,52,1,13),(1677,10.00,'AOCM120101HGRPSNA9',1,5,53,1,13),(1678,10.00,'AOCM120101HGRPSNA9',1,5,54,1,13),(1679,10.00,'AOCM120101HGRPSNA9',1,5,55,1,13),(1680,10.00,'AOCM120101HGRPSNA9',1,5,56,1,13),(1681,10.00,'AOCM120101HGRPSNA9',1,5,57,1,13),(1682,10.00,'AOCM120101HGRPSNA9',1,5,58,1,13),(1683,10.00,'AOCM160101HGRPSNA9',1,1,1,1,4),(1684,10.00,'AOCM160101HGRPSNA9',1,1,2,1,4),(1685,10.00,'AOCM160101HGRPSNA9',1,1,3,1,4),(1686,10.00,'AOCM160101HGRPSNA9',1,1,4,1,4),(1687,10.00,'AOCM160101HGRPSNA9',1,1,5,1,4),(1688,10.00,'AOCM160101HGRPSNA9',1,1,6,1,4),(1689,10.00,'AOCM160101HGRPSNA9',1,1,7,1,4),(1690,10.00,'AOCM160101HGRPSNA9',1,1,8,1,4),(1691,10.00,'AOCM160101HGRPSNA9',1,1,9,1,4),(1692,9.00,'AOCM160101HGRPSNA9',1,1,10,1,4),(1693,8.00,'AOCM160101HGRPSNA9',1,1,1,1,5),(1694,8.00,'AOCM160101HGRPSNA9',1,1,2,1,5),(1695,9.00,'AOCM160101HGRPSNA9',1,1,3,1,5),(1696,9.00,'AOCM160101HGRPSNA9',1,1,4,1,5),(1697,8.00,'AOCM160101HGRPSNA9',1,1,5,1,5),(1698,9.00,'AOCM160101HGRPSNA9',1,1,6,1,5),(1699,8.00,'AOCM160101HGRPSNA9',1,1,7,1,5),(1700,9.00,'AOCM160101HGRPSNA9',1,1,8,1,5),(1701,8.00,'AOCM160101HGRPSNA9',1,1,9,1,5),(1702,9.00,'AOCM160101HGRPSNA9',1,1,10,1,5),(1703,10.00,'AOCM160101HGRPSNA9',1,1,1,1,6),(1704,10.00,'AOCM160101HGRPSNA9',1,1,2,1,6),(1705,10.00,'AOCM160101HGRPSNA9',1,1,3,1,6),(1706,10.00,'AOCM160101HGRPSNA9',1,1,4,1,6),(1707,10.00,'AOCM160101HGRPSNA9',1,1,5,1,6),(1708,10.00,'AOCM160101HGRPSNA9',1,1,6,1,6),(1709,10.00,'AOCM160101HGRPSNA9',1,1,7,1,6),(1710,10.00,'AOCM160101HGRPSNA9',1,1,8,1,6),(1711,10.00,'AOCM160101HGRPSNA9',1,1,9,1,6),(1712,10.00,'AOCM160101HGRPSNA9',1,1,10,1,6),(1713,9.00,'AOCM160101HGRPSNA9',1,1,1,1,7),(1714,9.00,'AOCM160101HGRPSNA9',1,1,2,1,7),(1715,8.00,'AOCM160101HGRPSNA9',1,1,3,1,7),(1716,9.00,'AOCM160101HGRPSNA9',1,1,4,1,7),(1717,9.00,'AOCM160101HGRPSNA9',1,1,5,1,7),(1718,9.00,'AOCM160101HGRPSNA9',1,1,6,1,7),(1719,9.00,'AOCM160101HGRPSNA9',1,1,7,1,7),(1720,9.00,'AOCM160101HGRPSNA9',1,1,8,1,7),(1721,9.00,'AOCM160101HGRPSNA9',1,1,9,1,7),(1722,9.00,'AOCM160101HGRPSNA9',1,1,10,1,7),(1723,7.00,'AOCM160101HGRPSNA9',1,1,1,1,8),(1724,7.00,'AOCM160101HGRPSNA9',1,1,2,1,8),(1725,7.00,'AOCM160101HGRPSNA9',1,1,3,1,8),(1726,7.00,'AOCM160101HGRPSNA9',1,1,4,1,8),(1727,7.00,'AOCM160101HGRPSNA9',1,1,5,1,8),(1728,7.00,'AOCM160101HGRPSNA9',1,1,6,1,8),(1729,7.00,'AOCM160101HGRPSNA9',1,1,7,1,8),(1730,7.00,'AOCM160101HGRPSNA9',1,1,8,1,8),(1731,7.00,'AOCM160101HGRPSNA9',1,1,9,1,8),(1732,7.00,'AOCM160101HGRPSNA9',1,1,10,1,8),(1733,8.00,'AOCM160101HGRPSNA9',1,1,1,1,9),(1734,8.00,'AOCM160101HGRPSNA9',1,1,2,1,9),(1735,8.00,'AOCM160101HGRPSNA9',1,1,3,1,9),(1736,8.00,'AOCM160101HGRPSNA9',1,1,4,1,9),(1737,8.00,'AOCM160101HGRPSNA9',1,1,5,1,9),(1738,8.00,'AOCM160101HGRPSNA9',1,1,6,1,9),(1739,8.00,'AOCM160101HGRPSNA9',1,1,7,1,9),(1740,8.00,'AOCM160101HGRPSNA9',1,1,8,1,9),(1741,8.00,'AOCM160101HGRPSNA9',1,1,9,1,9),(1742,8.00,'AOCM160101HGRPSNA9',1,1,10,1,9),(1743,9.00,'AOCM160101HGRPSNA9',1,1,1,1,10),(1744,9.00,'AOCM160101HGRPSNA9',1,1,2,1,10),(1745,9.00,'AOCM160101HGRPSNA9',1,1,3,1,10),(1746,9.00,'AOCM160101HGRPSNA9',1,1,4,1,10),(1747,9.00,'AOCM160101HGRPSNA9',1,1,5,1,10),(1748,9.00,'AOCM160101HGRPSNA9',1,1,6,1,10),(1749,9.00,'AOCM160101HGRPSNA9',1,1,7,1,10),(1750,9.00,'AOCM160101HGRPSNA9',1,1,8,1,10),(1751,9.00,'AOCM160101HGRPSNA9',1,1,9,1,10),(1752,9.00,'AOCM160101HGRPSNA9',1,1,10,1,10),(1753,9.00,'AOCM160101HGRPSNA9',1,1,1,1,11),(1754,9.00,'AOCM160101HGRPSNA9',1,1,2,1,11),(1755,9.00,'AOCM160101HGRPSNA9',1,1,3,1,11),(1756,9.00,'AOCM160101HGRPSNA9',1,1,4,1,11),(1757,9.00,'AOCM160101HGRPSNA9',1,1,5,1,11),(1758,9.00,'AOCM160101HGRPSNA9',1,1,6,1,11),(1759,9.00,'AOCM160101HGRPSNA9',1,1,7,1,11),(1760,9.00,'AOCM160101HGRPSNA9',1,1,8,1,11),(1761,9.00,'AOCM160101HGRPSNA9',1,1,9,1,11),(1762,9.00,'AOCM160101HGRPSNA9',1,1,10,1,11),(1763,7.00,'AOCM160101HGRPSNA9',1,1,1,1,12),(1764,8.00,'AOCM160101HGRPSNA9',1,1,2,1,12),(1765,9.00,'AOCM160101HGRPSNA9',1,1,3,1,12),(1766,7.00,'AOCM160101HGRPSNA9',1,1,4,1,12),(1767,8.00,'AOCM160101HGRPSNA9',1,1,5,1,12),(1768,9.00,'AOCM160101HGRPSNA9',1,1,6,1,12),(1769,7.00,'AOCM160101HGRPSNA9',1,1,7,1,12),(1770,8.00,'AOCM160101HGRPSNA9',1,1,8,1,12),(1771,9.00,'AOCM160101HGRPSNA9',1,1,9,1,12),(1772,10.00,'AOCM160101HGRPSNA9',1,1,10,1,12),(1773,10.00,'AOCM160101HGRPSNA9',1,1,1,1,13),(1774,10.00,'AOCM160101HGRPSNA9',1,1,2,1,13),(1775,10.00,'AOCM160101HGRPSNA9',1,1,3,1,13),(1776,10.00,'AOCM160101HGRPSNA9',1,1,4,1,13),(1777,10.00,'AOCM160101HGRPSNA9',1,1,5,1,13),(1778,10.00,'AOCM160101HGRPSNA9',1,1,6,1,13),(1779,10.00,'AOCM160101HGRPSNA9',1,1,7,1,13),(1780,10.00,'AOCM160101HGRPSNA9',1,1,8,1,13),(1781,10.00,'AOCM160101HGRPSNA9',1,1,9,1,13),(1782,10.00,'AOCM160101HGRPSNA9',1,1,10,1,13),(1783,7.00,'AOCM011012HGRPSNA1',1,1,1,1,4),(1784,7.00,'AOCM011012HGRPSNA1',1,1,2,1,4),(1785,7.00,'AOCM011012HGRPSNA1',1,1,3,1,4),(1786,7.00,'AOCM011012HGRPSNA1',1,1,4,1,4),(1787,7.00,'AOCM011012HGRPSNA1',1,1,5,1,4),(1788,7.00,'AOCM011012HGRPSNA1',1,1,6,1,4),(1789,7.00,'AOCM011012HGRPSNA1',1,1,7,1,4),(1790,7.00,'AOCM011012HGRPSNA1',1,1,8,1,4),(1791,7.00,'AOCM011012HGRPSNA1',1,1,9,1,4),(1792,7.00,'AOCM011012HGRPSNA1',1,1,10,1,4),(1793,6.00,'AOCM011012HGRPSNA1',1,1,1,1,5),(1794,7.00,'AOCM011012HGRPSNA1',1,1,2,1,5),(1795,8.00,'AOCM011012HGRPSNA1',1,1,3,1,5),(1796,6.00,'AOCM011012HGRPSNA1',1,1,4,1,5),(1797,7.00,'AOCM011012HGRPSNA1',1,1,5,1,5),(1798,8.00,'AOCM011012HGRPSNA1',1,1,6,1,5),(1799,6.00,'AOCM011012HGRPSNA1',1,1,7,1,5),(1800,7.00,'AOCM011012HGRPSNA1',1,1,8,1,5),(1801,8.00,'AOCM011012HGRPSNA1',1,1,9,1,5),(1802,6.00,'AOCM011012HGRPSNA1',1,1,10,1,5),(1803,6.00,'AOCM011012HGRPSNA1',1,1,1,1,6),(1804,7.00,'AOCM011012HGRPSNA1',1,1,2,1,6),(1805,8.00,'AOCM011012HGRPSNA1',1,1,3,1,6),(1806,9.00,'AOCM011012HGRPSNA1',1,1,4,1,6),(1807,6.00,'AOCM011012HGRPSNA1',1,1,5,1,6),(1808,7.00,'AOCM011012HGRPSNA1',1,1,6,1,6),(1809,8.00,'AOCM011012HGRPSNA1',1,1,7,1,6),(1810,9.00,'AOCM011012HGRPSNA1',1,1,8,1,6),(1811,6.00,'AOCM011012HGRPSNA1',1,1,9,1,6),(1812,7.00,'AOCM011012HGRPSNA1',1,1,10,1,6),(1813,6.00,'AOCM011012HGRPSNA1',1,1,1,1,7),(1814,7.00,'AOCM011012HGRPSNA1',1,1,2,1,7),(1815,8.00,'AOCM011012HGRPSNA1',1,1,3,1,7),(1816,9.00,'AOCM011012HGRPSNA1',1,1,4,1,7),(1817,6.00,'AOCM011012HGRPSNA1',1,1,5,1,7),(1818,7.00,'AOCM011012HGRPSNA1',1,1,6,1,7),(1819,8.00,'AOCM011012HGRPSNA1',1,1,7,1,7),(1820,9.00,'AOCM011012HGRPSNA1',1,1,8,1,7),(1821,6.00,'AOCM011012HGRPSNA1',1,1,9,1,7),(1822,7.00,'AOCM011012HGRPSNA1',1,1,10,1,7);
/*!40000 ALTER TABLE `calificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciclos_escolares`
--

DROP TABLE IF EXISTS `ciclos_escolares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ciclos_escolares` (
  `id_ciclo_escolar` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_ciclo_escolar`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciclos_escolares`
--

LOCK TABLES `ciclos_escolares` WRITE;
/*!40000 ALTER TABLE `ciclos_escolares` DISABLE KEYS */;
INSERT INTO `ciclos_escolares` VALUES (1,'2022-2023');
/*!40000 ALTER TABLE `ciclos_escolares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `correos_tutores`
--

DROP TABLE IF EXISTS `correos_tutores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `correos_tutores` (
  `id_correo` int(11) NOT NULL AUTO_INCREMENT,
  `correo` varchar(80) NOT NULL,
  `id_tutor` int(11) NOT NULL,
  PRIMARY KEY (`id_correo`),
  UNIQUE KEY `correo` (`correo`),
  KEY `id_tutor` (`id_tutor`),
  CONSTRAINT `correos_tutores_ibfk_1` FOREIGN KEY (`id_tutor`) REFERENCES `tutores` (`id_tutor`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `correos_tutores`
--

LOCK TABLES `correos_tutores` WRITE;
/*!40000 ALTER TABLE `correos_tutores` DISABLE KEYS */;
INSERT INTO `correos_tutores` VALUES (50,'rob@gmail.com',39),(51,'rob1@gmail.com',39),(52,'apolinar121070@gmail.com',40);
/*!40000 ALTER TABLE `correos_tutores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grados`
--

DROP TABLE IF EXISTS `grados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grados` (
  `id_grado` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_grado`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grados`
--

LOCK TABLES `grados` WRITE;
/*!40000 ALTER TABLE `grados` DISABLE KEYS */;
INSERT INTO `grados` VALUES (4,'cuarto'),(1,'primero'),(5,'quinto'),(2,'segundo'),(6,'sexto'),(3,'tercero');
/*!40000 ALTER TABLE `grados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupos`
--

DROP TABLE IF EXISTS `grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupos` (
  `id_grupo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` char(1) NOT NULL,
  PRIMARY KEY (`id_grupo`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos`
--

LOCK TABLES `grupos` WRITE;
/*!40000 ALTER TABLE `grupos` DISABLE KEYS */;
INSERT INTO `grupos` VALUES (1,'A'),(2,'B'),(3,'C');
/*!40000 ALTER TABLE `grupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inasistencias`
--

DROP TABLE IF EXISTS `inasistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inasistencias` (
  `id_inasistencia` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` tinyint(4) DEFAULT NULL,
  `curp` varchar(18) NOT NULL,
  `id_mes` int(11) NOT NULL,
  `id_ciclo_escolar` int(11) NOT NULL,
  PRIMARY KEY (`id_inasistencia`),
  KEY `id_ciclo_escolar` (`id_ciclo_escolar`),
  KEY `id_mes` (`id_mes`),
  KEY `curp` (`curp`),
  CONSTRAINT `inasistencias_ibfk_1` FOREIGN KEY (`id_ciclo_escolar`) REFERENCES `ciclos_escolares` (`id_ciclo_escolar`),
  CONSTRAINT `inasistencias_ibfk_2` FOREIGN KEY (`id_mes`) REFERENCES `meses` (`id_mes`),
  CONSTRAINT `inasistencias_ibfk_3` FOREIGN KEY (`curp`) REFERENCES `alumnos` (`curp`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inasistencias`
--

LOCK TABLES `inasistencias` WRITE;
/*!40000 ALTER TABLE `inasistencias` DISABLE KEYS */;
INSERT INTO `inasistencias` VALUES (25,1,'AOCM120101HGRPSNA9',4,1),(26,1,'AOCM120101HGRPSNA9',5,1),(27,1,'AOCM120101HGRPSNA9',6,1),(28,1,'AOCM120101HGRPSNA9',7,1),(29,1,'AOCM120101HGRPSNA9',8,1),(30,1,'AOCM120101HGRPSNA9',9,1),(31,1,'AOCM120101HGRPSNA9',10,1),(32,1,'AOCM120101HGRPSNA9',11,1),(33,1,'AOCM120101HGRPSNA9',12,1),(34,1,'AOCM120101HGRPSNA9',13,1),(35,1,'AOCM160101HGRPSNA9',4,1),(36,5,'AOCM160101HGRPSNA9',5,1),(37,2,'AOCM160101HGRPSNA9',6,1),(38,1,'AOCM160101HGRPSNA9',7,1),(39,1,'AOCM160101HGRPSNA9',8,1),(40,2,'AOCM160101HGRPSNA9',9,1),(41,3,'AOCM160101HGRPSNA9',10,1),(42,2,'AOCM160101HGRPSNA9',11,1),(43,4,'AOCM160101HGRPSNA9',12,1),(44,1,'AOCM160101HGRPSNA9',13,1),(45,5,'AOCM011012HGRPSNA1',4,1),(46,1,'AOCM011012HGRPSNA1',5,1),(47,2,'AOCM011012HGRPSNA1',6,1),(48,2,'AOCM011012HGRPSNA1',7,1);
/*!40000 ALTER TABLE `inasistencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meses`
--

DROP TABLE IF EXISTS `meses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meses` (
  `id_mes` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_periodo` int(11) NOT NULL,
  PRIMARY KEY (`id_mes`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `id_periodo` (`id_periodo`),
  CONSTRAINT `meses_ibfk_1` FOREIGN KEY (`id_periodo`) REFERENCES `periodos` (`id_periodo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meses`
--

LOCK TABLES `meses` WRITE;
/*!40000 ALTER TABLE `meses` DISABLE KEYS */;
INSERT INTO `meses` VALUES (4,'diagnostico',0),(5,'septiembre',1),(6,'octubre',1),(7,'noviembre/diciembre',1),(8,'enero',2),(9,'febrero',2),(10,'marzo',2),(11,'abril',4),(12,'mayo',4),(13,'junio',4);
/*!40000 ALTER TABLE `meses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodos`
--

DROP TABLE IF EXISTS `periodos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodos` (
  `id_periodo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_periodo`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodos`
--

LOCK TABLES `periodos` WRITE;
/*!40000 ALTER TABLE `periodos` DISABLE KEYS */;
INSERT INTO `periodos` VALUES (0,'0°'),(1,'1°'),(2,'2°'),(4,'3°');
/*!40000 ALTER TABLE `periodos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilegios`
--

DROP TABLE IF EXISTS `privilegios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privilegios` (
  `id_privilegio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_privilegio`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilegios`
--

LOCK TABLES `privilegios` WRITE;
/*!40000 ALTER TABLE `privilegios` DISABLE KEYS */;
INSERT INTO `privilegios` VALUES (1,'create'),(4,'delete'),(3,'read'),(2,'update');
/*!40000 ALTER TABLE `privilegios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id_rol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'director'),(2,'secretaria');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_privilegios`
--

DROP TABLE IF EXISTS `roles_privilegios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_privilegios` (
  `id_rol` int(11) NOT NULL,
  `id_privilegio` int(11) NOT NULL,
  PRIMARY KEY (`id_rol`,`id_privilegio`),
  KEY `id_privilegio` (`id_privilegio`),
  CONSTRAINT `roles_privilegios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`),
  CONSTRAINT `roles_privilegios_ibfk_2` FOREIGN KEY (`id_privilegio`) REFERENCES `privilegios` (`id_privilegio`),
  CONSTRAINT `roles_privilegios_ibfk_3` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`),
  CONSTRAINT `roles_privilegios_ibfk_4` FOREIGN KEY (`id_privilegio`) REFERENCES `privilegios` (`id_privilegio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_privilegios`
--

LOCK TABLES `roles_privilegios` WRITE;
/*!40000 ALTER TABLE `roles_privilegios` DISABLE KEYS */;
INSERT INTO `roles_privilegios` VALUES (1,1),(1,2),(1,3),(1,4),(2,1),(2,3);
/*!40000 ALTER TABLE `roles_privilegios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_asignaturas`
--

DROP TABLE IF EXISTS `tipo_asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_asignaturas` (
  `id_tipo_asignatura` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_tipo_asignatura`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_asignaturas`
--

LOCK TABLES `tipo_asignaturas` WRITE;
/*!40000 ALTER TABLE `tipo_asignaturas` DISABLE KEYS */;
INSERT INTO `tipo_asignaturas` VALUES (3,'academica'),(4,'complementaria');
/*!40000 ALTER TABLE `tipo_asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_boleta`
--

DROP TABLE IF EXISTS `tipos_boleta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_boleta` (
  `id_tipo_boleta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id_tipo_boleta`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_boleta`
--

LOCK TABLES `tipos_boleta` WRITE;
/*!40000 ALTER TABLE `tipos_boleta` DISABLE KEYS */;
INSERT INTO `tipos_boleta` VALUES (4,'externa'),(3,'interna');
/*!40000 ALTER TABLE `tipos_boleta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutores`
--

DROP TABLE IF EXISTS `tutores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutores` (
  `id_tutor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido_paterno` varchar(50) NOT NULL,
  `apellido_materno` varchar(50) NOT NULL,
  PRIMARY KEY (`id_tutor`),
  UNIQUE KEY `tutor_unico` (`nombre`,`apellido_paterno`,`apellido_materno`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutores`
--

LOCK TABLES `tutores` WRITE;
/*!40000 ALTER TABLE `tutores` DISABLE KEYS */;
INSERT INTO `tutores` VALUES (40,'Manuel','Apolinar','Castillo'),(39,'Roberto','Torres','Cruz');
/*!40000 ALTER TABLE `tutores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `correo` varchar(80) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasenia` varchar(50) NOT NULL,
  `codigo` varchar(3) DEFAULT NULL,
  `id_rol` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  KEY `id_rol` (`id_rol`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`),
  CONSTRAINT `usuarios_ibfk_2` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'director@gmail.com','direc','1234','',1),(13,'secretaria1@gmail.com','secretaria1','1234','',2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-17  2:34:46
