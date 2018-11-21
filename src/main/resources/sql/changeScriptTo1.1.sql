use brainschema;

-- -----------------------------------------------------
-- Table `brainschema`.`responses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brainschema`.`responses` ;

CREATE TABLE IF NOT EXISTS `brainschema`.`versions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `versionNumber` FLOAT NOT NULL,
  `changeBy` VARCHAR(45) NOT NULL,
  `change` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

ALTER TABLE userprofile
DROP column age;

ALTER TABLE userprofile
ADD column age INT;


insert into brainschema.versions values (1, 1.0, "Light", "Initial Schema");
insert into brainschema.versions values (2, 1.1, "Light", "Age from Varchar to Int");

