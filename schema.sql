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
  `scan_id` INT(11) NOT NULL AUTO_INCREMENT,
  `top_image` VARCHAR(255) NOT NULL,
  `front_image` VARCHAR(255) NOT NULL,
  `side_image` VARCHAR(255) NOT NULL,
  `known_good` TINYINT(1) NULL,
  `bad_reason` VARCHAR(255) NULL,
  PRIMARY KEY (`scan_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`account` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`account` (
  `account_id` INT(11) NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(90) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `account_disabled` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`account_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`postcode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`postcode` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`postcode` (
  `postcode_id` INT NOT NULL AUTO_INCREMENT,
  `postcode` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`postcode_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `brainschema`.`ethnicity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`ethnicity` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`ethnicity` (
  `ethnicity_id` INT NOT NULL AUTO_INCREMENT,
  `ethnicity` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`ethnicity_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brainschema`.`religion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`religion` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`religion` (
  `religion_id` INT NOT NULL AUTO_INCREMENT,
  `religion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`religion_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `brainschema`.`sexual_orientation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`sexual_orientation` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`sexual_orientation` (
  `sexual_orientation_id` INT NOT NULL AUTO_INCREMENT,
  `sexual_orientation` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`sexual_orientation_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brainschema`.`relationship`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`relationship` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`relationship` (
  `relationship_id` INT NOT NULL AUTO_INCREMENT,
  `relationship` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`relationship_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `brainschema`.`relationship`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`caring_responsibilities` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`caring_responsibilities` (
  `caring_responsibilities_id` INT NOT NULL AUTO_INCREMENT,
  `caring_responsibilities` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`caring_responsibilities_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `brainschema`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`profile` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`profile` (
  `profile_id` INT(11) NOT NULL AUTO_INCREMENT,
  `display_name` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  `account_id` INT(11) NOT NULL,
  `postcode_id` INT NULL,
  `disability` TINYINT(1) NULL,
  `ethnicity_id` INT(2) NULL,
  `gender_identity_match` TINYINT(1) NULL,
  `religion_id` INT(2) NULL,
  `sex` TINYINT(1) NULL,
  `sexual_orientation_id` INT(2) NULL,
  `relationship_id` INT(2),
  `caring_responsibilities_id` INT(2) NULL,
  PRIMARY KEY (`profile_id`),
  INDEX `fk_userprofile_useraccount1_idx` (`account_id` ASC),
  INDEX `fk_userprofile_postcode1_idx` (`postcode_id` ASC),
  CONSTRAINT `fk_userprofile_useraccount1`
    FOREIGN KEY (`account_id`)
    REFERENCES `brainschema`.`account` (`account_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userprofile_postcode1`
    FOREIGN KEY (`postcode_id`)
    REFERENCES `brainschema`.`postcode` (`postcode_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
    CONSTRAINT `fk_userprofile_ethnicity1`
    FOREIGN KEY (`ethnicity_id`)
    REFERENCES `brainschema`.`ethnicity` (`ethnicity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
    CONSTRAINT `fk_userprofile_religion1`
    FOREIGN KEY (`religion_id`)
    REFERENCES `brainschema`.`religion` (`religion_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_userprofile_sexual_orientation1`
    FOREIGN KEY (`sexual_orientation_id`)
    REFERENCES `brainschema`.`sexual_orientation` (`sexual_orientation_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_userprofile_relationship1`
    FOREIGN KEY (`relationship_id`)
    REFERENCES `brainschema`.`relationship` (`relationship_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_userprofile_caring_responsibilities1`
    FOREIGN KEY (`caring_responsibilities_id`)
    REFERENCES `brainschema`.`caring_responsibilities` (`caring_responsibilities_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`feedback` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`feedback` (
  `feedback_id` INT(11) NOT NULL AUTO_INCREMENT,
  `profile_id` INT(11) NOT NULL,
  `feedback` TEXT NOT NULL,
  PRIMARY KEY (`feedback_id`),
  INDEX `userprofileid` (`profile_id` ASC),
  CONSTRAINT `userfeedback_ibfk_1`
    FOREIGN KEY (`profile_id`)
    REFERENCES `brainschema`.`profile` (`profile_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`rating` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`rating` (
  `rating_id` INT(11) NOT NULL AUTO_INCREMENT,
  `profile_id` INT(11) NULL,
  `scan_id` INT(11) NOT NULL,
  `response` TINYINT(1) NOT NULL,
  PRIMARY KEY (`rating_id`),
  INDEX `profile_id` (`profile_id` ASC),
  INDEX `scan_id` (`scan_id` ASC),
  CONSTRAINT `userrating_ibfk_2`
    FOREIGN KEY (`scan_id`)
    REFERENCES `brainschema`.`scan` (`scan_id`),
  CONSTRAINT `userratings_ibfk_1`
    FOREIGN KEY (`profile_id`)
    REFERENCES `brainschema`.`profile` (`profile_id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `brainschema`.`version`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`version` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`version` (
  `version_number` FLOAT NOT NULL,
  `change_by` VARCHAR(45) NOT NULL,
  `change` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`version_number`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;




DROP TABLE IF EXISTS `brainschema`.`reward`;

CREATE TABLE IF NOT EXISTS `brainschema`.`reward` (
  `profile_id` INT(11) NOT NULL,
  `training` INT(1) NOT NULL,
  `practice` INT(1) NOT NULL,
  `sort25` INT(1) NOT NULL,
  `sort50` INT(1) NOT NULL,
  `feedback` INT(1) NOT NULL,
  CONSTRAINT `user_to_reward` FOREIGN KEY (`profile_id`) REFERENCES `brainschema`.`profile` (`profile_id`) ON DELETE CASCADE
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS `brainschema`.`password_reset_token`;

CREATE TABLE IF NOT EXISTS `brainschema`.`password_reset_token` (
    `token` varchar(44) NOT NULL,
    `account_id` INT(11) NOT NULL,
    `expiry_date` DATETIME NOT NULL,
    constraint `user_to_token` FOREIGN KEY (`account_id`) REFERENCES `brainschema`.`account` (`account_id`) ON DELETE CASCADE,
    PRIMARY KEY (`token`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


USE `brainschema` ;


-- -----------------------------------------------------
-- function get_good_percentage_for_scan
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_good_percentage_for_scan`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_good_percentage_for_scan`(given_scan_id int) RETURNS float
BEGIN

RETURN (SELECT sum(response)/count(scan_id)*100 
		FROM rating WHERE scan_id = given_scan_id group by scan_id);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- function get_side_with_majority
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_side_with_majority`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_side_with_majority`(given_user_profile_id INT) RETURNS decimal(10,0)
BEGIN
	DECLARE variable_scan_rated_id INT;
    DECLARE variable_user_response INT;
    DECLARE variable_user_majority_response FLOAT;
    DECLARE variable_num_of_response_participation INT;
    DECLARE flag INT DEFAULT 0;
	DECLARE rating_cursor CURSOR FOR SELECT scan_id FROM rating WHERE profile_id = given_user_profile_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET flag = 1;
    SET variable_user_majority_response = 0;
    SET variable_num_of_response_participation = 0;
    
    OPEN rating_cursor;
		REPEAT
		FETCH rating_cursor INTO variable_scan_rated_id;
	
			SET variable_num_of_response_participation := variable_num_of_response_participation + 1;
            
            IF (SELECT sum(response)/count(response) FROM rating WHERE scan_id = variable_scan_rated_id AND profile_id = given_user_profile_id GROUP BY scan_id) > 0.5 THEN
				SET variable_user_response := 1;
			ELSE
				SET variable_user_response := 0;
			END IF;
            
            IF (SELECT sum(response)/count(scan_id) FROM rating WHERE scan_id = variable_scan_rated_id GROUP BY scan_id) >= 0.5 THEN
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
-- function get_total_good_responses_for_user
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_total_good_responses_for_user`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_good_responses_for_user`(given_user_email VARCHAR(255)) RETURNS int(11)
BEGIN

	RETURN (SELECT count(*) FROM rating WHERE response = 1 AND profile_id = 
				(SELECT profile_id FROM profile WHERE account_id =
					(SELECT account_id FROM account WHERE email = given_user_email)));
					
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function get_total_responses_for_user
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`get_total_responses_for_user`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_responses_for_user`(given_user_email VARCHAR(255)) RETURNS int(11)
BEGIN

	RETURN (SELECT count(*) FROM rating WHERE profile_id = 
				(SELECT profile_id FROM profile WHERE account_id =
					(SELECT account_id FROM account WHERE email = given_user_email)));
					
END$$

DELIMITER ;


-- -----------------------------------------------------
-- function check_or_add_postcode
-- -----------------------------------------------------

USE `brainschema`;
DROP function IF EXISTS `brainschema`.`check_or_add_postcode`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_or_add_postcode`(given_postcode VARCHAR(255)) RETURNS int(11)
BEGIN
	DECLARE variable_checked_postcode_id INT;
    
    SET variable_checked_postcode_id = (SELECT postcode_id FROM postcode WHERE postcode = given_postcode);
    
    IF(variable_checked_postcode_id IS NULL) THEN
		INSERT INTO postcode (postcode) VALUES (given_postcode);
        SET variable_checked_postcode_id = (SELECT postcode_id FROM postcode WHERE postcode = given_postcode); 
	END IF;
    
    return variable_checked_postcode_id;
	
					
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure remove_user_ratings
-- -----------------------------------------------------

USE `brainschema`;
DROP procedure IF EXISTS `brainschema`.`remove_user_ratings`;

DELIMITER $$
USE `brainschema`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_user_ratings`(given_user_id Int)
BEGIN
    
DECLARE success boolean;

SET success = FALSE;

START TRANSACTION;
	DELETE FROM rating WHERE profile_id = given_user_id;
	IF(SELECT COUNT(*) FROM rating WHERE profile_id = given_user_id) = 0 THEN
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
AFTER INSERT ON `brainschema`.`account`
FOR EACH ROW
INSERT INTO profile (display_name, account_id) VALUES ('Anonymous', NEW.account_id)$$


DELIMITER ;

DELIMITER $$

USE `brainschema`$$
DROP TRIGGER IF EXISTS `brainschema`.`Create User Rewards Entry` $$
USE `brainschema`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `brainschema`.`Create User Rewards Entry`
AFTER INSERT ON `brainschema`.`profile`
FOR EACH ROW
INSERT INTO reward (profile_id, training, practice, sort25, sort50, feedback) VALUES (NEW.profile_id, 0, 0 ,0 ,0 ,0)$$

DELIMITER ;

 -- Default admin account Pass is 'admin'
insert into account (email, password, role) VALUES ('default@admin', '$2a$10$xVWy4YGH2TgjMoA1ITfJRubHPp9ijz926vkEUKHMa.AaVG5gP4ANm', 'admin');


 -- Insert list of inclusive ethnicity's from https://www.hesa.ac.uk/collection/c16025/a/ethnic
insert into ethnicity VALUES (10,'White');
insert into ethnicity VALUES (13,'White - Scottish');
insert into ethnicity VALUES (14,'Irish Traveller');
insert into ethnicity VALUES (15,'Gypsy or Traveller');
insert into ethnicity VALUES (19,'Other White background');
insert into ethnicity VALUES (21,'Black or Black British - Caribbean');
insert into ethnicity VALUES (22,'Black or Black British - African');
insert into ethnicity VALUES (29,'Other Black background');
insert into ethnicity VALUES (31,'Asian or Asian British - Indian');
insert into ethnicity VALUES (32,'Asian or Asian British - Pakistani');
insert into ethnicity VALUES (33,'Asian or Asian British - Bangladeshi');
insert into ethnicity VALUES (34,'Chinese');
insert into ethnicity VALUES (39,'Other Asian background');
insert into ethnicity VALUES (41,'Mixed - White and Black Caribbean');
insert into ethnicity VALUES (42,'Mixed - White and Black African');
insert into ethnicity VALUES (43,'Mixed - White and Asian');
insert into ethnicity VALUES (49,'Other mixed background');
insert into ethnicity VALUES (50,'Arab');
insert into ethnicity VALUES (80,'Other ethnic background');
insert into ethnicity VALUES (90,'Not known');
insert into ethnicity VALUES (98,'Information refused');

 -- Insert list of inclusive religions from https://www.hesa.ac.uk/collection/c16025/a/relblf
insert into religion VALUES (01,'No religion');
insert into religion VALUES (02,'Buddhist');
insert into religion VALUES (03,'Christian');
insert into religion VALUES (04,'Christian - Church of Scotland');
insert into religion VALUES (05,'Christian - Roman Catholic');
insert into religion VALUES (06,'Christian - Presbyterian Church in Ireland');
insert into religion VALUES (07,'Christian - Church of Ireland');
insert into religion VALUES (08,'Christian - Methodist Church in Ireland');
insert into religion VALUES (09,'Christian - Other denomination');
insert into religion VALUES (10,'Hindu');
insert into religion VALUES (11,'Jewish');
insert into religion VALUES (12,'Muslim');
insert into religion VALUES (13,'Sikh');
insert into religion VALUES (14,'Spiritual');
insert into religion VALUES (80,'Any other religion or belief');
insert into religion VALUES (98,'Information refused');

 -- Insert list of sexual orientations
 insert into sexual_orientation VALUES (1, 'Asexual');
 insert into sexual_orientation VALUES (2, 'Bi/bisexual');
 insert into sexual_orientation VALUES (3, 'Gay man');
 insert into sexual_orientation VALUES (4, 'Gay woman/lesbian');
 insert into sexual_orientation VALUES (5, 'Heterosexual/straight');
 insert into sexual_orientation VALUES (6, 'Queer');
 insert into sexual_orientation VALUES (7, 'Other');
 insert into sexual_orientation VALUES (8, 'Prefer not to say');

 -- Insert list of relationship types
insert into relationship VALUES (1, 'Cohabiting');
insert into relationship VALUES (2, 'Divorced or civil partnership dissolved');
insert into relationship VALUES (3, 'In a civil partnership');
insert into relationship VALUES (4, 'Married');
insert into relationship VALUES (5, 'Separated (but still legally married or in a civil partnership)');
insert into relationship VALUES (6, 'Single (never married or never in a civil partnership)');
insert into relationship VALUES (7, 'Widowed or a surviving partner from a civil partnership');
insert into relationship VALUES (8, 'Prefer not to say');

 -- Insert list of caring responsibilities
insert into caring_responsibilities VALUES (1, 'None');
insert into caring_responsibilities VALUES (2, 'Primary carer of a child or children (under 18 years)');
insert into caring_responsibilities VALUES (3, 'Primary carer of a disabled child or children');
insert into caring_responsibilities VALUES (4, 'Primary carer or assistant for a disabled adult (18 years and over)');
insert into caring_responsibilities VALUES (5, 'Primary carer or assistant for an older person or people (65 years and over)');
insert into caring_responsibilities VALUES (6, 'Secondary carer (another person carries out main caring role)');
insert into caring_responsibilities VALUES (7, 'Prefer not to say');





SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
