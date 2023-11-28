-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-11-2023 a las 21:36:12
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `jcvd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuegos`
--

CREATE TABLE `videojuegos` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Categoría` varchar(25) DEFAULT NULL,
  `FechaLanzamiento` date DEFAULT NULL,
  `Compañía` varchar(50) DEFAULT NULL,
  `Precio` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `videojuegos`
--

INSERT INTO `videojuegos` (`id`, `Nombre`, `Categoría`, `FechaLanzamiento`, `Compañía`, `Precio`) VALUES
(1, 'Rocket League', 'Coches', NULL, 'Epic Games', 0.00),
(2, 'Fortnite', 'Shooter', NULL, 'Epic Games', 0.00),
(3, 'Grand Theft Auto V', 'Mundo Abierto', '2013-09-17', 'Rockstar Games', 18.95),
(4, 'Gran Turismo 7', 'Coches', '2022-03-04', NULL, 29.90),
(5, 'Spider-Man', 'Mundo Abierto', '2018-09-07', 'Insomniac Games', 79.90),
(10, ' ', 'dslk', '2009-05-17', 'd', 0.00),
(11, 'Prueba', 'sdf', '2003-11-28', '', 0.00),
(12, 'asdf', 'd', '2003-11-28', 'd', 5.00),
(13, 'sdf', 'fd', '2003-11-28', 'd', 0.00),
(14, 'Prueba4', 'Shooter', '2007-05-12', 'compania', 10.00),
(15, 'OtroPrueba', 'Shooter', '2007-05-12', 'compania', 10.00),
(16, 'Prueba teclado', 'no', '2023-11-28', 'si', 10.30);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
