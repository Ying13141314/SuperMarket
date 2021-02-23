-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.11-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.1.0.6116
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para superMarkt
CREATE DATABASE IF NOT EXISTS `supermarkt` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */;
USE `superMarkt`;

-- Volcando estructura para tabla superMarkt.compra
DROP TABLE IF EXISTS `compra`;
CREATE TABLE IF NOT EXISTS `compra` (
  `idCompra` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `idEmpleado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCompra`),
  KEY `FKEmpleado` (`idEmpleado`),
  CONSTRAINT `FKEmpleado` FOREIGN KEY (`idEmpleado`) REFERENCES `empleado` (`idEmpleado`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla superMarkt.compra: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` (`idCompra`, `fecha`, `idEmpleado`) VALUES
	(16, '2021-02-23 17:53:11', 1),
	(17, '2021-02-23 17:54:31', 1),
	(18, '2021-02-23 17:54:37', 1),
	(19, '2021-02-23 17:55:32', 1),
	(20, '2021-02-23 17:55:40', 1),
	(21, '2021-02-23 17:55:59', 1),
	(22, '2021-02-23 18:04:52', 1),
	(23, '2021-02-23 18:05:16', 1),
	(24, '2021-02-23 18:19:38', 2),
	(25, '2021-02-23 18:24:12', 2),
	(26, '2021-02-23 18:27:37', 2);
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;

-- Volcando estructura para tabla superMarkt.detalle
DROP TABLE IF EXISTS `detalle`;
CREATE TABLE IF NOT EXISTS `detalle` (
  `idDetalle` int(11) NOT NULL AUTO_INCREMENT,
  `cantidadVenta` int(11) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `idCompra` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDetalle`) USING BTREE,
  KEY `FKProducto` (`idProducto`),
  KEY `FKCompra` (`idCompra`),
  CONSTRAINT `FKCompra` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`idCompra`),
  CONSTRAINT `FKProducto` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla superMarkt.detalle: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `detalle` DISABLE KEYS */;
INSERT INTO `detalle` (`idDetalle`, `cantidadVenta`, `idProducto`, `idCompra`) VALUES
	(16, 12, 1, 16),
	(17, 1, 1, 17),
	(18, 2, 2, 18),
	(19, 1, 1, 19),
	(20, 2, 2, 20),
	(21, 1, 1, 21),
	(22, 1, 2, 22),
	(23, 2, 1, 23),
	(24, 3, 1, 24),
	(25, 4, 1, 25),
	(26, 2, 1, 26);
/*!40000 ALTER TABLE `detalle` ENABLE KEYS */;

-- Volcando estructura para tabla superMarkt.empleado
DROP TABLE IF EXISTS `empleado`;
CREATE TABLE IF NOT EXISTS `empleado` (
  `idEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `ultimaSesion` datetime DEFAULT NULL,
  `fechaContratacion` date DEFAULT NULL,
  `login` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idEmpleado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla superMarkt.empleado: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` (`idEmpleado`, `ultimaSesion`, `fechaContratacion`, `login`) VALUES
	(1, '2021-02-17 13:07:00', '2021-02-17', 'Lario'),
	(2, '2021-02-17 19:24:26', '2021-02-17', 'Fernando');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;

-- Volcando estructura para tabla superMarkt.producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `nombreProducto` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `precioVenta` decimal(10,0) DEFAULT NULL,
  `precioProveedor` decimal(10,0) DEFAULT NULL,
  `cantidadStock` int(11) DEFAULT NULL,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla superMarkt.producto: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`idProducto`, `nombreProducto`, `precioVenta`, `precioProveedor`, `cantidadStock`) VALUES
	(1, 'Disco_duro', 100, 80, 0),
	(2, 'USB', 8, 4, 18),
	(3, 'Monitor', 136, 109, 7),
	(4, 'Ratón', 37, 12, 23);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
