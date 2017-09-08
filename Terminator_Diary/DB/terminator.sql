SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `terminator` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `terminator`;


-- -----------------------------------------------------
-- Table `terminator`.`workouts`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `terminator`.`workouts` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `number` INT NOT NULL ,
  `usr` INT NOT NULL ,
  PRIMARY KEY (`ID`, `usr`) ,
  INDEX `fk_workouts_users` (`usr` ASC) ,
  UNIQUE INDEX `name_uni` (`name` ASC, `usr` ASC) ,
  UNIQUE INDEX `num_uni` (`number` ASC, `usr` ASC) ,
  CONSTRAINT `fk_workouts_users`
    FOREIGN KEY (`usr` )
    REFERENCES `terminator`.`users` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `terminator`.`exercises`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `terminator`.`exercises` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `usr` INT NOT NULL ,
  PRIMARY KEY (`ID`, `usr`) ,
  UNIQUE INDEX `uni_name` (`name` ASC, `usr` ASC) ,
  INDEX `fk_exercises_users1` (`usr` ASC) ,
  CONSTRAINT `fk_exercises_users1`
    FOREIGN KEY (`usr` )
    REFERENCES `terminator`.`users` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `terminator`.`sets`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `terminator`.`sets` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `number` INT NOT NULL ,
  `weight` DOUBLE NULL ,
  `repeats` INT NULL ,
  `exercise` INT NOT NULL ,
  PRIMARY KEY (`ID`, `exercise`) ,
  INDEX `fk_sets_exercises1` (`exercise` ASC) ,
  CONSTRAINT `fk_sets_exercises1`
    FOREIGN KEY (`exercise` )
    REFERENCES `terminator`.`exercises` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `terminator`.`workouts_exercises`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `terminator`.`workouts_exercises` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `workout` INT NOT NULL ,
  `exercise` INT NOT NULL ,
  `number` INT NOT NULL ,
  PRIMARY KEY (`ID`, `workout`, `exercise`) ,
  INDEX `fk_workouts_exercises_workouts1` (`workout` ASC) ,
  INDEX `fk_workouts_exercises_exercises1` (`exercise` ASC) ,
  UNIQUE INDEX `uni_exercise` (`workout` ASC, `exercise` ASC, `number` ASC) ,
  CONSTRAINT `fk_workouts_exercises_workouts1`
    FOREIGN KEY (`workout` )
    REFERENCES `terminator`.`workouts` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_workouts_exercises_exercises1`
    FOREIGN KEY (`exercise` )
    REFERENCES `terminator`.`exercises` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `terminator`.`exercises_details`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `terminator`.`exercises_details` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `rest` INT NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_exercises_details_exercises1` (`ID` ASC) ,
  CONSTRAINT `fk_exercises_details_exercises1`
    FOREIGN KEY (`ID` )
    REFERENCES `terminator`.`exercises` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;