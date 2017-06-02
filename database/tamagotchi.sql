-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Czas generowania: 02 Cze 2017, 19:15
-- Wersja serwera: 10.1.22-MariaDB
-- Wersja PHP: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `tamagotchi`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `akcja`
--

CREATE TABLE `akcja` (
  `id_akcji` int(11) NOT NULL,
  `id_rodzaj_akcji` int(11) NOT NULL,
  `id_rodzaj_podopieczny` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `historia_akcji`
--

CREATE TABLE `historia_akcji` (
  `id_historia_akcji` int(11) NOT NULL,
  `id_podopieczny` int(11) NOT NULL,
  `id_akcji` int(11) NOT NULL,
  `data_akcji` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `podopieczny`
--

CREATE TABLE `podopieczny` (
  `id_podopieczny` int(11) NOT NULL,
  `id_rodzaj` int(11) NOT NULL,
  `imie` varchar(15) NOT NULL,
  `wiek` int(11) NOT NULL DEFAULT '1',
  `waga` int(11) NOT NULL DEFAULT '1',
  `data_ostatniego_karmienia` date DEFAULT NULL,
  `id_uzytkownik` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `podopieczny`
--

INSERT INTO `podopieczny` (`id_podopieczny`, `id_rodzaj`, `imie`, `wiek`, `waga`, `data_ostatniego_karmienia`, `id_uzytkownik`) VALUES
(29, 3, 'h', 1, 1, NULL, 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rodzaj_akcji`
--

CREATE TABLE `rodzaj_akcji` (
  `id_rodzaj_akcji` int(11) NOT NULL,
  `nazwa_rodzaju_akcji` varchar(25) NOT NULL,
  `nazwa_akcji` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rodzaj_podopiecznego`
--

CREATE TABLE `rodzaj_podopiecznego` (
  `id_rodzaj_podopiecznego` int(11) NOT NULL,
  `nazwa` varchar(25) NOT NULL,
  `sciezka` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `rodzaj_podopiecznego`
--

INSERT INTO `rodzaj_podopiecznego` (`id_rodzaj_podopiecznego`, `nazwa`, `sciezka`) VALUES
(3, 'Dog', NULL),
(4, 'Cat', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownik`
--

CREATE TABLE `uzytkownik` (
  `id` int(11) NOT NULL,
  `login` varchar(15) NOT NULL,
  `imie` varchar(15) NOT NULL,
  `nazwisko` varchar(25) NOT NULL,
  `haslo` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `uzytkownik`
--

INSERT INTO `uzytkownik` (`id`, `login`, `imie`, `nazwisko`, `haslo`) VALUES
(1, 'h', 'h', 'h', 'h'),
(4, 'j', 'j', 'j', 'j'),
(5, 'kamil', 'kamil', 'kamil', 'kamil'),
(6, 'rafal', 'rafal', 'rafal', 'rafal'),
(7, 'antek', 'antek', 'antek', 'antek'),
(8, 'po', 'po', 'po', 'po'),
(9, 'l', 'l', 'l', 'l'),
(10, 'f', 'f', 'f', 'f'),
(11, 's', 's', 's', 's'),
(12, 'p', 'p', 'p', 'p');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `waga`
--

CREATE TABLE `waga` (
  `id_waga` int(50) NOT NULL,
  `id_podopieczny` int(11) NOT NULL,
  `data_pomiaru` date NOT NULL,
  `wartosc_wagi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `akcja`
--
ALTER TABLE `akcja`
  ADD PRIMARY KEY (`id_akcji`),
  ADD UNIQUE KEY `id_akcji` (`id_akcji`),
  ADD KEY `id_rodzaj_akcji` (`id_rodzaj_akcji`),
  ADD KEY `id_rodzaj_podopieczny` (`id_rodzaj_podopieczny`);

--
-- Indexes for table `historia_akcji`
--
ALTER TABLE `historia_akcji`
  ADD PRIMARY KEY (`id_historia_akcji`),
  ADD UNIQUE KEY `id_akcji_2` (`id_akcji`),
  ADD UNIQUE KEY `id_historia_akcji` (`id_historia_akcji`),
  ADD KEY `id_podopieczny` (`id_podopieczny`),
  ADD KEY `id_akcji` (`id_akcji`);

--
-- Indexes for table `podopieczny`
--
ALTER TABLE `podopieczny`
  ADD PRIMARY KEY (`id_podopieczny`),
  ADD UNIQUE KEY `id_podopieczny` (`id_podopieczny`),
  ADD KEY `id_rodzaj` (`id_rodzaj`),
  ADD KEY `id_uzytkownik` (`id_uzytkownik`);

--
-- Indexes for table `rodzaj_akcji`
--
ALTER TABLE `rodzaj_akcji`
  ADD PRIMARY KEY (`id_rodzaj_akcji`),
  ADD KEY `id_rodzaj_akcji` (`id_rodzaj_akcji`) USING BTREE;

--
-- Indexes for table `rodzaj_podopiecznego`
--
ALTER TABLE `rodzaj_podopiecznego`
  ADD PRIMARY KEY (`id_rodzaj_podopiecznego`),
  ADD UNIQUE KEY `id_rodzaj_podopiecznego` (`id_rodzaj_podopiecznego`);

--
-- Indexes for table `uzytkownik`
--
ALTER TABLE `uzytkownik`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `waga`
--
ALTER TABLE `waga`
  ADD PRIMARY KEY (`id_waga`),
  ADD UNIQUE KEY `id_waga` (`id_waga`),
  ADD KEY `id_podopieczny` (`id_podopieczny`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `akcja`
--
ALTER TABLE `akcja`
  MODIFY `id_akcji` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `historia_akcji`
--
ALTER TABLE `historia_akcji`
  MODIFY `id_historia_akcji` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `podopieczny`
--
ALTER TABLE `podopieczny`
  MODIFY `id_podopieczny` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT dla tabeli `rodzaj_akcji`
--
ALTER TABLE `rodzaj_akcji`
  MODIFY `id_rodzaj_akcji` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `rodzaj_podopiecznego`
--
ALTER TABLE `rodzaj_podopiecznego`
  MODIFY `id_rodzaj_podopiecznego` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT dla tabeli `uzytkownik`
--
ALTER TABLE `uzytkownik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT dla tabeli `waga`
--
ALTER TABLE `waga`
  MODIFY `id_waga` int(50) NOT NULL AUTO_INCREMENT;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `akcja`
--
ALTER TABLE `akcja`
  ADD CONSTRAINT `akcja_ibfk_1` FOREIGN KEY (`id_rodzaj_akcji`) REFERENCES `rodzaj_akcji` (`id_rodzaj_akcji`),
  ADD CONSTRAINT `akcja_ibfk_2` FOREIGN KEY (`id_rodzaj_podopieczny`) REFERENCES `rodzaj_podopiecznego` (`id_rodzaj_podopiecznego`);

--
-- Ograniczenia dla tabeli `historia_akcji`
--
ALTER TABLE `historia_akcji`
  ADD CONSTRAINT `historia_akcji_ibfk_1` FOREIGN KEY (`id_akcji`) REFERENCES `akcja` (`id_akcji`),
  ADD CONSTRAINT `historia_akcji_ibfk_2` FOREIGN KEY (`id_podopieczny`) REFERENCES `podopieczny` (`id_podopieczny`);

--
-- Ograniczenia dla tabeli `podopieczny`
--
ALTER TABLE `podopieczny`
  ADD CONSTRAINT `podopieczny_ibfk_1` FOREIGN KEY (`id_rodzaj`) REFERENCES `rodzaj_podopiecznego` (`id_rodzaj_podopiecznego`),
  ADD CONSTRAINT `podopieczny_ibfk_2` FOREIGN KEY (`id_uzytkownik`) REFERENCES `uzytkownik` (`id`);

--
-- Ograniczenia dla tabeli `waga`
--
ALTER TABLE `waga`
  ADD CONSTRAINT `waga_ibfk_1` FOREIGN KEY (`id_podopieczny`) REFERENCES `podopieczny` (`id_podopieczny`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
