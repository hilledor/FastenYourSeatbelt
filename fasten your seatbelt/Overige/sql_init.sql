-- MySQL Script generated by MySQL Workbench
-- Sun Jan  3 15:12:53 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema fys2
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `fys2` ;

-- -----------------------------------------------------
-- Schema fys2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fys2` DEFAULT CHARACTER SET utf8 ;
USE `fys2` ;

-- -----------------------------------------------------
-- Table `fys2`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fys2`.`user` ;

CREATE TABLE IF NOT EXISTS `fys2`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `middlename` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `rol` INT NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `inactive` INT ZEROFILL NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fys2`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fys2`.`client` ;

CREATE TABLE IF NOT EXISTS `fys2`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `middlename` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phonenumber` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `streetnumber` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fys2`.`missingbagage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fys2`.`missingbagage` ;

CREATE TABLE IF NOT EXISTS `fys2`.`missingbagage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `brand` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  `wheels` INT NOT NULL,
  `material` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `fromlocation` VARCHAR(45) NOT NULL,
  `tolocation` VARCHAR(45) NOT NULL,
  `datemissing` DATETIME NOT NULL,
  `status` INT ZEROFILL NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_missingbagage_client_idx` (`client_id` ASC),
  CONSTRAINT `fk_missingbagage_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `fys2`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fys2`.`foundbagage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fys2`.`foundbagage` ;

CREATE TABLE IF NOT EXISTS `fys2`.`foundbagage` (
  `id` INT NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  `wheels` INT NOT NULL,
  `material` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `locationfound` VARCHAR(45) NOT NULL,
  `datefound` DATETIME NOT NULL,
  `status` INT ZEROFILL NOT NULL,
  `client_id` INT NULL,
  `missingbagage_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_foundbagage_client1_idx` (`client_id` ASC),
  INDEX `fk_foundbagage_missingbagage1_idx` (`missingbagage_id` ASC),
  UNIQUE INDEX `missingbagage_id_UNIQUE` (`missingbagage_id` ASC),
  CONSTRAINT `fk_foundbagage_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `fys2`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_foundbagage_missingbagage1`
    FOREIGN KEY (`missingbagage_id`)
    REFERENCES `fys2`.`missingbagage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fys2`.`log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fys2`.`log` ;

CREATE TABLE IF NOT EXISTS `fys2`.`log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tableid` INT NOT NULL,
  `tablename` VARCHAR(45) NOT NULL,
  `logdate` DATETIME NOT NULL,
  `memo` LONGTEXT NULL,
  `user_id` INT NOT NULL,
  `updatetype` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_log_user1_idx` (`user_id` ASC),
  INDEX `table_id` (`tableid` ASC, `tablename` ASC),
  CONSTRAINT `fk_log_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fys2`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
