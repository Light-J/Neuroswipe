use brainschema;

-- -----------------------------------------------------
-- Table `brainschema`.`userrating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`userrating` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`userrating` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userprofileid` INT NULL,
  `imageid` INT NULL,
  `response` TINYINT(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

insert into brainschema.versions values (4, 1.3, "Light", "userrating table addedgo");