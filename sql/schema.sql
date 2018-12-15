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
DROP SCHEMA IF EXISTS `brainschema` ;

-- -----------------------------------------------------
-- Schema brainschema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `brainschema` DEFAULT CHARACTER SET utf8 ;
USE `brainschema` ;

-- -----------------------------------------------------
-- Table `brainschema`.`scan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`scan` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`scan` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `path1` VARCHAR(255) NULL DEFAULT NULL,
  `path2` VARCHAR(255) NULL DEFAULT NULL,
  `path3` VARCHAR(255) NULL DEFAULT NULL,
  `known_good` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`useraccount`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`useraccount` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`useraccount` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(90) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `role` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`userprofile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`userprofile` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`userprofile` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `postcode` VARCHAR(90) NULL DEFAULT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  `useraccountid` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `useraccountid`),
  INDEX `fk_userprofile_useraccount1_idx` (`useraccountid` ASC),
  CONSTRAINT `fk_userprofile_useraccount1`
    FOREIGN KEY (`useraccountid`)
    REFERENCES `brainschema`.`useraccount` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`userfeedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`userfeedback` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`userfeedback` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userprofileid` INT(11) NULL DEFAULT NULL,
  `feedback` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userprofileid` (`userprofileid` ASC),
  CONSTRAINT `userfeedback_ibfk_1`
    FOREIGN KEY (`userprofileid`)
    REFERENCES `brainschema`.`userprofile` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`userrating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`userrating` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`userrating` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userprofileid` INT(11) NULL DEFAULT NULL,
  `scanid` INT(11) NULL DEFAULT NULL,
  `response` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userprofileid` (`userprofileid` ASC),
  INDEX `scanid` (`scanid` ASC),
  CONSTRAINT `userrating_ibfk_2`
    FOREIGN KEY (`scanid`)
    REFERENCES `brainschema`.`scan` (`id`),
  CONSTRAINT `userratings_ibfk_1`
    FOREIGN KEY (`userprofileid`)
    REFERENCES `brainschema`.`userprofile` (`id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`version`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`version` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`version` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `versionNumber` FLOAT NOT NULL,
  `changeBy` VARCHAR(45) NOT NULL,
  `change` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;

USE `brainschema` ;

-- -----------------------------------------------------
-- function get_good_percentage_for_scan
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_good_percentage_for_scan`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_good_percentage_for_scan`(scanId int) RETURNS float
BEGIN

RETURN (SELECT sum(response)/count(scanid)*100 
		FROM userrating WHERE scanid = scanId group by scanid);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- function get_total_good_responses_for_user
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_total_good_responses_for_user`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_good_responses_for_user`(user_email VARCHAR(255)) RETURNS int(11)
BEGIN

	RETURN (SELECT count(*) FROM userrating WHERE response = 1 AND userprofileid = 
				(SELECT id FROM userprofile WHERE useraccountid =
					(SELECT id FROM useraccount WHERE email = user_email)));
					
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function get_total_responses_for_user
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_total_responses_for_user`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_responses_for_user`(user_email VARCHAR(255)) RETURNS int(11)
BEGIN

	RETURN (SELECT count(*) FROM userrating WHERE userprofileid = 
				(SELECT id FROM userprofile WHERE useraccountid =
					(SELECT id FROM useraccount WHERE email = user_email)));
					
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function get_side_with_majority
-- -----------------------------------------------------
 -- Function used to get a percentage of howmany times a user is siding with the majority
DELIMITER $$
USE `brainschema`$$
DROP FUNCTION IF EXISTS get_side_with_majority $$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_side_with_majority`(user_profile_id INT) 
RETURNS DECIMAL
BEGIN
	DECLARE variable_scan_rated_id INT;
    DECLARE variable_user_response INT;
    DECLARE variable_user_majority_response FLOAT;
    DECLARE variable_num_of_response_participation INT;
    DECLARE flag INT DEFAULT 0;
	DECLARE rating_cursor CURSOR FOR SELECT scanid FROM userrating WHERE userprofileid = user_profile_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET flag = 1;
    SET variable_user_majority_response = 0;
    SET variable_num_of_response_participation = 0;
    
    OPEN rating_cursor;
		REPEAT
		FETCH rating_cursor INTO variable_scan_rated_id;
	
			SET variable_num_of_response_participation := variable_num_of_response_participation + 1;
            
            IF (SELECT sum(response)/count(response) FROM userrating WHERE scanid = variable_scan_rated_id AND userprofileid = user_profile_id GROUP BY scanid) > 0.5 THEN
				SET variable_user_response := 1;
			ELSE
				SET variable_user_response := 0;
			END IF;
            
            IF (SELECT sum(response)/count(scanid) FROM userrating WHERE scanid = variable_scan_rated_id GROUP BY scanid) >= 0.5 THEN
				IF(variable_user_response = 1) THEN
					SET variable_user_majority_response := variable_user_majority_response + 1;
				END IF;
			ELSE
				IF(variable_user_response = 0) THEN
					SET variable_user_majority_response := variable_user_majority_response + 1;
				END IF;
			END IF;
            
		UNTIL flag END REPEAT;
	CLOSE rating_cursor;
    
    RETURN variable_user_majority_response / variable_num_of_response_participation *100;
				
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure remove_user_ratings
-- -----------------------------------------------------

USE `brainschema`;
DROP procedure IF EXISTS `brainschema`.`remove_user_ratings`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_user_ratings`(userId Int)
BEGIN
    
DECLARE success boolean;

SET success = FALSE;

START TRANSACTION;
	DELETE FROM userrating WHERE userprofileid = userId;
	IF(SELECT COUNT(*) FROM userrating WHERE userprofileid = userId) = 0 THEN
        SET success = TRUE;
	END IF;
    
	IF Success = FALSE THEN
		ROLLBACK;
		SELECT "Transaction has been rolled back because the delete failed." as Message;
	ELSE
		COMMIT;
		SELECT CONCAT("Transaction has been commited.") as Message;
	END IF;
END$$

DELIMITER ;
USE `brainschema`;

DELIMITER $$

USE `brainschema`$$
DROP TRIGGER IF EXISTS `brainschema`.`Create User Profile` $$
USE `brainschema`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `brainschema`.`Create User Profile`
AFTER INSERT ON `brainschema`.`useraccount`
FOR EACH ROW
INSERT INTO userprofile (id, username, useraccountid) VALUES (NEW.id, NEW.email, NEW.id)$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
