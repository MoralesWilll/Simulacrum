-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema RiwiAcademyDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema RiwiAcademyDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RiwiAcademyDB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `RiwiAcademyDB` ;

-- -----------------------------------------------------
-- Table `RiwiAcademyDB`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RiwiAcademyDB`.`course` (
  `idCourse` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`idCourse`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `RiwiAcademyDB`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RiwiAcademyDB`.`student` (
  `idStudent` INT NOT NULL AUTO_INCREMENT,
  `active` TINYINT NULL DEFAULT '1',
  `name` VARCHAR(225) NOT NULL,
  `email` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`idStudent`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `RiwiAcademyDB`.`grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RiwiAcademyDB`.`grade` (
  `idGrade` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(225) NOT NULL,
  `value` INT NOT NULL,
  `idCourse` INT NOT NULL,
  `idStudent` INT NOT NULL,
  PRIMARY KEY (`idGrade`),
  INDEX `idCourse_idx` (`idCourse` ASC) VISIBLE,
  INDEX `idStudent_idx` (`idStudent` ASC) VISIBLE,
  CONSTRAINT `idCourse1`
    FOREIGN KEY (`idCourse`)
    REFERENCES `RiwiAcademyDB`.`course` (`idCourse`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `idStudent1`
    FOREIGN KEY (`idStudent`)
    REFERENCES `RiwiAcademyDB`.`student` (`idStudent`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `RiwiAcademyDB`.`inscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RiwiAcademyDB`.`inscription` (
  `idInscription` INT NOT NULL AUTO_INCREMENT,
  `idCourse` INT NOT NULL,
  `idStudent` INT NOT NULL,
  PRIMARY KEY (`idInscription`),
  INDEX `idCourse_idx` (`idCourse` ASC) VISIBLE,
  INDEX `idStudent_idx` (`idStudent` ASC) VISIBLE,
  CONSTRAINT `idCourse`
    FOREIGN KEY (`idCourse`)
    REFERENCES `RiwiAcademyDB`.`course` (`idCourse`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `idStudent`
    FOREIGN KEY (`idStudent`)
    REFERENCES `RiwiAcademyDB`.`student` (`idStudent`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
