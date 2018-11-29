use brainschema;

ALTER TABLE `images` ADD `known_good` BOOLEAN NULL DEFAULT NULL AFTER `path`;

insert into brainschema.versions values (3, 1.2, "McAlinden", "Adds several columns to image table");
