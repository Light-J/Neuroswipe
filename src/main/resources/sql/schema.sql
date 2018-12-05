-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema brainschema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema brainschema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `brainschema` DEFAULT CHARACTER SET utf8 ;
USE `brainschema` ;

-- -----------------------------------------------------
-- Table `brainschema`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`images` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `path1` VARCHAR(255) NULL DEFAULT NULL,
  `path2` VARCHAR(255) NULL DEFAULT NULL,
  `path3` VARCHAR(255) NULL DEFAULT NULL,
  `known_good` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`useraccounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`useraccounts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(90) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `role` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`userprofiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`userprofiles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `postcode` VARCHAR(90) NULL DEFAULT NULL,
  `useraccountid` INT(11) NULL DEFAULT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `useraccountid` (`useraccountid` ASC),
  CONSTRAINT `userprofiles_ibfk_1`
    FOREIGN KEY (`useraccountid`)
    REFERENCES `brainschema`.`useraccounts` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`userratings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`userratings` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userprofileid` INT(11) NULL DEFAULT NULL,
  `imageid` INT(11) NULL DEFAULT NULL,
  `response` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userprofileid` (`userprofileid` ASC),
  INDEX `imageid` (`imageid` ASC),
  CONSTRAINT `userratings_ibfk_1`
    FOREIGN KEY (`userprofileid`)
    REFERENCES `brainschema`.`userprofiles` (`id`),
  CONSTRAINT `userratings_ibfk_2`
    FOREIGN KEY (`imageid`)
    REFERENCES `brainschema`.`images` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`userresponses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`userresponses` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userprofileid` INT(11) NULL DEFAULT NULL,
  `imageid` INT(11) NULL DEFAULT NULL,
  `response` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userprofileid` (`userprofileid` ASC),
  INDEX `imageid` (`imageid` ASC),
  CONSTRAINT `userresponses_ibfk_1`
    FOREIGN KEY (`userprofileid`)
    REFERENCES `brainschema`.`userprofiles` (`id`),
  CONSTRAINT `userresponses_ibfk_2`
    FOREIGN KEY (`imageid`)
    REFERENCES `brainschema`.`images` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`versions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`versions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `versionNumber` FLOAT NOT NULL,
  `changeBy` VARCHAR(45) NOT NULL,
  `change` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
