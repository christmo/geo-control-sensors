SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `monitorsensores` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;

USE `monitorsensores`;

CREATE  TABLE IF NOT EXISTS `monitorsensores`.`usuarios` (
  `id_usu` INT(11) NOT NULL AUTO_INCREMENT ,
  `nombre_usu` VARCHAR(150) NULL DEFAULT NULL ,
  `usuario_usu` VARCHAR(50) NULL DEFAULT NULL ,
  `clave_usu` VARCHAR(50) NULL DEFAULT NULL ,
  `id_suc` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_usu`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `monitorsensores`.`contactos` (
  `id_con` INT(11) NOT NULL ,
  `nombre_con` VARCHAR(150) NULL DEFAULT NULL ,
  `mail_con` VARCHAR(50) NULL DEFAULT NULL ,
  `telefono` VARCHAR(10) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_con`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `monitorsensores`.`sucursales` (
  `id_suc` INT(11) NOT NULL AUTO_INCREMENT ,
  `nombre_suc` VARCHAR(100) NULL DEFAULT NULL ,
  `direccion_suc` VARCHAR(150) NULL DEFAULT NULL ,
  `telefono_suc` VARCHAR(10) NULL DEFAULT NULL ,
  `nota_suc` VARCHAR(250) NULL DEFAULT NULL ,
  `latitud` DOUBLE NULL DEFAULT NULL ,
  `longitud` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`id_suc`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `monitorsensores`.`sensores` (
  `id_sen` VARCHAR(5) NOT NULL ,
  `nombre_sen` VARCHAR(100) NULL DEFAULT NULL ,
  `modulo_sen` VARCHAR(5) NULL DEFAULT NULL ,
  `par_max_sen` INT(11) NULL DEFAULT NULL ,
  `par_min_sen` INT(11) NULL DEFAULT NULL ,
  `id_suc` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_sen`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `monitorsensores`.`datos` (
  `id_dat` INT(11) NOT NULL ,
  `id_sen` VARCHAR(5) NULL DEFAULT NULL ,
  `fecha_dat` DATE NULL DEFAULT NULL ,
  `hora_dat` TIME NULL DEFAULT NULL ,
  `parametro_dat` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_dat`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci PARTITION BY HASH(id_dat) PARTITIONS 365;

CREATE  TABLE IF NOT EXISTS `monitorsensores`.`notificaciones` (
  `id_not` INT(11) NOT NULL ,
  `id_con` INT(11) NULL DEFAULT NULL ,
  `fecha_not` DATE NULL DEFAULT NULL ,
  `hora_not` TIME NULL DEFAULT NULL ,
  `id_dat` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_not`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
