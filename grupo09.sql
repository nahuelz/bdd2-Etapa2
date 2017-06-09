-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-06-2017 a las 07:09:34
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `grupo09`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `ID_COMENTARIO` int(11) NOT NULL,
  `ID_PASAJERO` int(11) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  `calificacion` int(11) DEFAULT NULL,
  `ID_VIAJE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comentario`
--

INSERT INTO `comentario` (`ID_COMENTARIO`, `ID_PASAJERO`, `comentario`, `calificacion`, `ID_VIAJE`) VALUES
(1, 3, 'Mal conductor', 1, 1),
(2, 4, 'Buen conductor', 4, 1),
(3, 4, 'Buen conductor', 4, 2),
(5, 5, 'Muy buen conductor', 5, 2),
(10, 1, 'Conductor agradable', 4, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `muber`
--

CREATE TABLE `muber` (
  `ID_MUBER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `muber`
--

INSERT INTO `muber` (`ID_MUBER`) VALUES
(1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID_USUARIO` int(11) NOT NULL,
  `TIPO_USUARIO` varchar(255) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `FECHA_INGRESO` datetime DEFAULT NULL,
  `CREDITOS` double DEFAULT NULL,
  `VENCIMIENTO_LIC` datetime DEFAULT NULL,
  `ID_MUBER` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID_USUARIO`, `TIPO_USUARIO`, `NOMBRE`, `PASSWORD`, `FECHA_INGRESO`, `CREDITOS`, `VENCIMIENTO_LIC`, `ID_MUBER`) VALUES
(1, 'PASAJERO', 'Alicia', '123456', '2017-05-21 20:51:27', 1500, NULL, 1),
(2, 'CONDUCTOR', 'Roberto', '123456', '2017-05-21 20:51:27', NULL, '2018-01-01 20:51:27', 1),
(3, 'PASAJERO', 'Hugo', '123456', '2017-05-21 20:51:27', 550, NULL, 1),
(4, 'PASAJERO', 'Margarita', '123456', '2017-05-21 20:51:27', 3450, NULL, 1),
(5, 'PASAJERO', 'Germán', '123456', '2017-05-21 20:51:27', 1200, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_viaje`
--

CREATE TABLE `usuario_viaje` (
  `ID_USUARIO` int(11) NOT NULL,
  `ID_VIAJE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario_viaje`
--

INSERT INTO `usuario_viaje` (`ID_USUARIO`, `ID_VIAJE`) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(3, 1),
(4, 1),
(4, 2),
(5, 1),
(5, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viaje`
--

CREATE TABLE `viaje` (
  `ID_VIAJE` int(11) NOT NULL,
  `ID_CONDUCTOR` int(11) DEFAULT NULL,
  `origen` varchar(255) DEFAULT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `costoTotal` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `cantidadMaximaPasajeros` int(11) DEFAULT NULL,
  `estado` char(1) DEFAULT NULL,
  `ID_MUBER` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `viaje`
--

INSERT INTO `viaje` (`ID_VIAJE`, `ID_CONDUCTOR`, `origen`, `destino`, `costoTotal`, `fecha`, `cantidadMaximaPasajeros`, `estado`, `ID_MUBER`) VALUES
(1, 2, 'Cordoba', 'Mar del plata', 3500, '2017-07-05 20:51:27', 10, 'A', 1),
(2, 2, 'La Plata', 'Tres Arroyos', 900, '2017-07-05 20:51:27', 10, 'F', 1),
(3, 2, 'Mar del Plata', 'Capital Federal', 1000, '2017-05-21 20:59:31', 6, 'F', 1),
(4, 2, 'Mar del Plata', 'Capital Federal', 1000, '2017-05-21 21:00:57', 6, 'F', 1),
(5, 2, 'Mar del Plata', 'Capital Federal', 1000, '2017-06-08 22:45:26', 6, 'A', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viaje_pasajero`
--

CREATE TABLE `viaje_pasajero` (
  `ID_VIAJE` int(11) NOT NULL,
  `ID_PASAJERO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `viaje_pasajero`
--

INSERT INTO `viaje_pasajero` (`ID_VIAJE`, `ID_PASAJERO`) VALUES
(1, 1),
(1, 3),
(1, 4),
(1, 5),
(2, 1),
(2, 4),
(2, 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`ID_COMENTARIO`),
  ADD KEY `FK_nxigf1pit8yha3n19rl1dqsyr` (`ID_PASAJERO`),
  ADD KEY `FK_46ymm6jh71xx4oibafd76fxm0` (`ID_VIAJE`);

--
-- Indices de la tabla `muber`
--
ALTER TABLE `muber`
  ADD PRIMARY KEY (`ID_MUBER`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID_USUARIO`),
  ADD KEY `FK_mxt8q6070jncjxyyeokhfo9bf` (`ID_MUBER`);

--
-- Indices de la tabla `usuario_viaje`
--
ALTER TABLE `usuario_viaje`
  ADD PRIMARY KEY (`ID_USUARIO`,`ID_VIAJE`),
  ADD KEY `FK_ahsd8et2erfokk761l5o8m6v3` (`ID_VIAJE`);

--
-- Indices de la tabla `viaje`
--
ALTER TABLE `viaje`
  ADD PRIMARY KEY (`ID_VIAJE`),
  ADD KEY `FK_2mwjjiv6t3u1fk64eyravovnk` (`ID_CONDUCTOR`),
  ADD KEY `FK_3tt7r9vbd0wxbtu66aoxmcdlx` (`ID_MUBER`);

--
-- Indices de la tabla `viaje_pasajero`
--
ALTER TABLE `viaje_pasajero`
  ADD PRIMARY KEY (`ID_VIAJE`,`ID_PASAJERO`),
  ADD KEY `FK_6k29dci20gyuvxbgln4cbb2ta` (`ID_PASAJERO`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comentario`
--
ALTER TABLE `comentario`
  MODIFY `ID_COMENTARIO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `muber`
--
ALTER TABLE `muber`
  MODIFY `ID_MUBER` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID_USUARIO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `viaje`
--
ALTER TABLE `viaje`
  MODIFY `ID_VIAJE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `FK_46ymm6jh71xx4oibafd76fxm0` FOREIGN KEY (`ID_VIAJE`) REFERENCES `viaje` (`ID_VIAJE`),
  ADD CONSTRAINT `FK_nxigf1pit8yha3n19rl1dqsyr` FOREIGN KEY (`ID_PASAJERO`) REFERENCES `usuario` (`ID_USUARIO`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK_mxt8q6070jncjxyyeokhfo9bf` FOREIGN KEY (`ID_MUBER`) REFERENCES `muber` (`ID_MUBER`);

--
-- Filtros para la tabla `usuario_viaje`
--
ALTER TABLE `usuario_viaje`
  ADD CONSTRAINT `FK_ahsd8et2erfokk761l5o8m6v3` FOREIGN KEY (`ID_VIAJE`) REFERENCES `viaje` (`ID_VIAJE`),
  ADD CONSTRAINT `FK_huju9jc8c5y1kp4penav1nqxw` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`);

--
-- Filtros para la tabla `viaje`
--
ALTER TABLE `viaje`
  ADD CONSTRAINT `FK_2mwjjiv6t3u1fk64eyravovnk` FOREIGN KEY (`ID_CONDUCTOR`) REFERENCES `usuario` (`ID_USUARIO`),
  ADD CONSTRAINT `FK_3tt7r9vbd0wxbtu66aoxmcdlx` FOREIGN KEY (`ID_MUBER`) REFERENCES `muber` (`ID_MUBER`);

--
-- Filtros para la tabla `viaje_pasajero`
--
ALTER TABLE `viaje_pasajero`
  ADD CONSTRAINT `FK_6k29dci20gyuvxbgln4cbb2ta` FOREIGN KEY (`ID_PASAJERO`) REFERENCES `usuario` (`ID_USUARIO`),
  ADD CONSTRAINT `FK_s7nb8jjwfm0fdataouclivlem` FOREIGN KEY (`ID_VIAJE`) REFERENCES `viaje` (`ID_VIAJE`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
