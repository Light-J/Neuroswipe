use brainschema;

ALTER TABLE `images` ADD `known_good` BOOLEAN NULL DEFAULT NULL AFTER `path`;

insert into brainschema.versions values (3, 1.2, "McAlinden", "Adds several columns to image table");
-- -----------------------------------------------------
-- Table `brainschema`.`userresponse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`userresponse` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`userresponse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userprofileid` INT NULL,
  `imageid` INT NULL,
  `response` TINYINT(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

insert into brainschema.versions values (3, 1.2, "Light", "userresponse table added");

