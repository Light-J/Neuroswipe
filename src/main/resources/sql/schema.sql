-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema brainschema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `brainschema` DEFAULT CHARACTER SET utf8 ;
USE `brainschema` ;

-- -----------------------------------------------------
-- Table `brainschema`.`scans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`scans` (
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

CREATE TRIGGER `Create User Profile` AFTER INSERT ON `useraccounts` FOR EACH ROW INSERT INTO userprofiles (id, username, useraccountid) VALUES (NEW.id, NEW.email, NEW.id);

-- -----------------------------------------------------
-- Table `brainschema`.`userratings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`userratings` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userprofileid` INT(11) NULL DEFAULT NULL,
  `scanid` INT(11) NULL DEFAULT NULL,
  `response` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userprofileid` (`userprofileid` ASC),
  INDEX `scanid` (`scanid` ASC),
  CONSTRAINT `userratings_ibfk_1`
    FOREIGN KEY (`userprofileid`)
    REFERENCES `brainschema`.`userprofiles` (`id`),
  CONSTRAINT `userratings_ibfk_2`
    FOREIGN KEY (`scanid`)
    REFERENCES `brainschema`.`scans` (`id`))
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

-- -----------------------------------------------------
-- Table `brainschema`.`feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brainschema`.`userfeedback` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userprofileid` INT(11) NULL DEFAULT NULL,
  `feedback` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userprofileid` (`userprofileid` ASC),
  CONSTRAINT `userfeedback_ibfk_1`
    FOREIGN KEY (`userprofileid`)
    REFERENCES `brainschema`.`userprofiles` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
