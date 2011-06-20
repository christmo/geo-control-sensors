-- MySQL dump 10.13  Distrib 5.1.36, for Win32 (ia32)
--
-- Host: localhost    Database: monitorsensores
-- ------------------------------------------------------
-- Server version	5.1.36-community-log

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

create database monitorsensores;
use monitorsensores;
--
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuracion` (
  `nombre` varchar(10) NOT NULL,
  `valor` varchar(100) NOT NULL,
  PRIMARY KEY (`nombre`,`valor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contactos`
--

DROP TABLE IF EXISTS `contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactos` (
  `id_con` int(11) NOT NULL,
  `nombre_con` varchar(150) DEFAULT NULL,
  `mail_con` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_con`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datos`
--

DROP TABLE IF EXISTS `datos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datos` (
  `id_dat_inc` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_dat_par` int(11) NOT NULL DEFAULT '0',
  `id_sen` varchar(10) NOT NULL,
  `fecha_dat` date NOT NULL DEFAULT '0000-00-00',
  `hora_dat` time NOT NULL DEFAULT '00:00:00',
  `parametro_dat` double NOT NULL,
  PRIMARY KEY (`id_dat_inc`,`id_dat_par`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=latin1
/*!50100 PARTITION BY HASH (id_dat_par)
PARTITIONS 365 */;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TGR_DATOS_SENSOR BEFORE INSERT ON DATOS

FOR EACH ROW BEGIN

    SET NEW.HORA_DAT = CURTIME();

    SET NEW.FECHA_DAT = CURDATE();

    SET NEW.ID_DAT_PAR = DATE_FORMAT(CURDATE(),'%Y%m%d');

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificaciones` (
  `id_not` int(11) NOT NULL AUTO_INCREMENT,
  `id_con` int(11) DEFAULT NULL,
  `fecha_not` date DEFAULT NULL,
  `hora_not` time DEFAULT NULL,
  `id_dat_inc` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_not`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sensores`
--

DROP TABLE IF EXISTS `sensores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensores` (
  `id_sen` varchar(10) NOT NULL,
  `nombre_sen` varchar(100) DEFAULT NULL,
  `modulo_sen` varchar(5) DEFAULT NULL,
  `par_max_sen` double DEFAULT NULL,
  `par_min_sen` double DEFAULT NULL,
  `id_suc` int(11) DEFAULT NULL,
  `tipo_sen` varchar(100) NOT NULL DEFAULT '##',
  PRIMARY KEY (`id_sen`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sucursales`
--

DROP TABLE IF EXISTS `sucursales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sucursales` (
  `id_suc` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_suc` varchar(100) DEFAULT NULL,
  `direccion_suc` varchar(150) DEFAULT NULL,
  `telefono_suc` varchar(10) DEFAULT NULL,
  `nota_suc` varchar(250) DEFAULT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  PRIMARY KEY (`id_suc`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id_usu` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usu` varchar(150) DEFAULT NULL,
  `usuario_usu` varchar(50) DEFAULT NULL,
  `clave_usu` varchar(50) DEFAULT NULL,
  `id_suc` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usu`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'monitorsensores'
--
/*!50003 DROP FUNCTION IF EXISTS `SF_ENVIAR_ALERTA` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_ENVIAR_ALERTA`(

  id VARCHAR(10)

) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN



DECLARE resul BOOLEAN;

DECLARE NO_HAY_DATO CONDITION FOR 1329;



DECLARE CONTINUE HANDLER FOR NO_HAY_DATO

BEGIN

  DECLARE dato int(5);

  SELECT COUNT(*)

  INTO dato

  FROM notificaciones n, datos d

  WHERE n.id_dat_inc = d.id_dat_inc and d.id_sen = id;



  if dato > 0 then

    /*Significa ya estan ingresado y que no debe insrtar*/

    return 0;

  else

    /*Significa que aun no esta ingresado un dato y que debe insertar*/

    return 1;

  end if;

END;







SELECT NOW()>=MAX(concat(n.fecha_not,' ',n.hora_not))+ INTERVAL SF_GET_CONFIG('tiempo') MINUTE AS notif

INTO resul

FROM notificaciones n, datos d

where n.id_dat_inc = d.id_dat_inc

AND d.id_sen = id

group by d.id_sen

HAVING notif = 1;



RETURN resul;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `SF_GET_CONFIG` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_GET_CONFIG`(

  nomb VARCHAR(10)

) RETURNS varchar(100) CHARSET latin1
    DETERMINISTIC
BEGIN



DECLARE resul VARCHAR(100);



SELECT valor

INTO resul

FROM configuracion

where nombre = nomb;



RETURN resul;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `SF_OBTENER_ID_DATO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_OBTENER_ID_DATO`(

  idsen VARCHAR(10),

  hora TIME,

  par DOUBLE

) RETURNS int(11)
    DETERMINISTIC
BEGIN



  DECLARE id int(11);



  SELECT ID_DAT_INC

	INTO id

	FROM DATOS

  WHERE ID_SEN = idsen AND FECHA_DAT = CURDATE() AND HORA_DAT = hora AND PARAMETRO_DAT = par;



  RETURN id;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_NOTIFICACION` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_NOTIFICACION`(

  CON INT(11),

  DAT INT(11)

)
BEGIN



  INSERT INTO NOTIFICACIONES(ID_CON,FECHA_NOT,HORA_NOT,ID_DAT_INC)

  VALUES (CON,CURDATE(),CURTIME(),DAT);



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-02 12:28:51
