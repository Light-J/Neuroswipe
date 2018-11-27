-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema brainschema
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `brainschema` ;

-- -----------------------------------------------------
-- Schema brainschema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `brainschema` DEFAULT CHARACTER SET utf8 ;
USE `brainschema` ;

-- -----------------------------------------------------
-- Table `brainschema`.`responses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`responses` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`responses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userprofileid` INT NULL,
  `imageid` VARCHAR(45) NULL,
  `response` TINYINT(1) NULL,
  `responsescol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brainschema`.`images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`images` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`images` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `path` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brainschema`.`useraccount`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`useraccount` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`useraccount` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(90) NOT NULL,
  `email` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brainschema`.`userprofile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`userprofile` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`userprofile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `postcode` VARCHAR(90) NULL,
  `useraccountid` INT NULL,
  `age` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- use basic queries --
USE brainschema;
SELECT * FROM images;
SELECT * FROM responses;
SELECT * FROM useraccount;
SELECT * FROM userprofile;
