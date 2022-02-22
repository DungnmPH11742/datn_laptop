-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 15, 2022 lúc 04:23 PM
-- Phiên bản máy phục vụ: 10.4.18-MariaDB
-- Phiên bản PHP: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `shop_laptop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img_url` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `verification_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `time_token` datetime DEFAULT NULL,
  `reset_password_token` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `auth_provider` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actived` bit(1) DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `full_name`, `phone`, `email`, `password`, `img_url`, `date_of_birth`, `verification_code`, `time_token`, `reset_password_token`, `auth_provider`, `actived`, `sex`) VALUES
(1, 'Đào Quý Nam', '0374563546', 'admin@gmail.com', '$2a$12$KCCdTXZKuNEVPW4.lgjYUeK/qo5eUbfQkobauR3Nh4i7d.HLB9.3S', 'http://localhost:8080/viewFile/ec6295d5.jpg', '2010-09-08', NULL, NULL, NULL, NULL, b'1', b'0'),
(2, 'Trần Thúy Hằng', '0374656354', 'user@gmail.com', '$2a$12$KCCdTXZKuNEVPW4.lgjYUeK/qo5eUbfQkobauR3Nh4i7d.HLB9.3S', 'http://localhost:8080/viewFile/ec6295d5.jpg', '2003-06-02', NULL, NULL, NULL, NULL, b'1', b'1'),
(3, 'Nguyễn Trung Hiếu', '0374656354', 'user1@gmail.com', '$2a$12$KCCdTXZKuNEVPW4.lgjYUeK/qo5eUbfQkobauR3Nh4i7d.HLB9.3S', 'http://localhost:8080/viewFile/ec6295d5.jpg', '2003-06-02', NULL, NULL, NULL, NULL, b'1', b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account_role`
--

CREATE TABLE `account_role` (
  `id_account` int(11) NOT NULL,
  `id_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `account_role`
--

INSERT INTO `account_role` (`id_account`, `id_role`) VALUES
(1, 2),
(2, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `blogs`
--

CREATE TABLE `blogs` (
  `id` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_account` int(11) NOT NULL,
  `date_created` date NOT NULL,
  `img_url` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description_short` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_hot` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `id` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `actived` bit(1) NOT NULL,
  `parent_id` varchar(55) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id`, `name`, `actived`, `parent_id`) VALUES
('LT01', 'Laptop', b'1', NULL),
('LTAcer', 'Acer', b'1', 'LT01'),
('LTAcer1', 'Nitro', b'1', 'LTAcer'),
('LTAcer2', 'Predator', b'1', 'LTAcer'),
('LTAcer3', 'Aspire', b'1', 'LTAcer'),
('LTAcer4', 'Swift', b'1', 'LTAcer'),
('LTApple', 'Apple', b'1', 'LT01'),
('LTApple1', 'MacBook Air ', b'1', 'LTApple'),
('LTApple2', 'MacBook Pro', b'1', 'LTApple'),
('LTAsus', 'Asus', b'1', 'LT01'),
('LTAsus1', 'ExpertBook', b'1', 'LTAsus'),
('LTAsus2', 'VivoBook', b'1', 'LTAsus'),
('LTAsus3', 'ZenBook', b'1', 'LTAsus'),
('LTAsus4', 'TUF/ROG Gaming', b'1', 'LTAsus'),
('LTAsus5', 'ProArt Studio', b'1', 'LTAsus'),
('LTDELL', 'Dell', b'1', 'LT01'),
('LTDELL1', 'Dell Latitude', b'1', 'LTDELL'),
('LTDELL2', 'Dell XPS', b'1', 'LTDELL'),
('LTDELL3', 'Dell Precision', b'1', 'LTDELL'),
('LTDELL4', 'Dell Inspiron', b'1', 'LTDELL'),
('LTDELL5', 'Dell Gaming G Series', b'1', 'LTDELL'),
('LTDELL6', 'Dell Alienware', b'1', 'LTDELL'),
('LTDELL7', 'Dell Vostro', b'1', 'LTDELL'),
('LTHP', 'HP', b'1', 'LT01'),
('LTHP1', 'Elitebook', b'1', 'LTHP'),
('LTHP2', 'ZBook', b'1', 'LTHP'),
('LTHP3', 'Pavilion', b'1', 'LTHP'),
('LTHP4', '14s/15s', b'1', 'LTHP'),
('LTHP5', 'Envy', b'1', 'LTHP'),
('LTHP6', 'Omen', b'1', 'LTHP'),
('LTLenovo', 'Lenovo', b'1', 'LT01'),
('LTLenovo1', 'Thinkpad', b'1', 'LTLenovo'),
('LTLenovo2', 'IdeaPad', b'1', 'LTLenovo'),
('LTLenovo3', 'Legion', b'1', 'LTLenovo'),
('LTLenovo4', 'ThinkBook', b'1', 'LTLenovo'),
('LTLg', 'Lg', b'1', 'LT01'),
('LTLg1', 'Gram', b'1', 'LTLg'),
('LTMicrosoft', 'Microsoft', b'1', 'LT01'),
('LTMicrosoft1', 'Surface Book', b'1', 'LTMicrosoft'),
('LTMicrosoft2', 'Surface Laptop', b'1', 'LTMicrosoft'),
('LTMicrosoft3', 'Surface Pro', b'1', 'LTMicrosoft'),
('LTMSI', 'MSI', b'1', 'LT01'),
('LTMSI1', 'GF Series', b'1', 'LTMSI'),
('LTMSI2', 'Prestige', b'1', 'LTMSI'),
('LTMSI3', 'Modern', b'1', 'LTMSI'),
('LTMSI4', 'Alpha', b'1', 'LTMSI'),
('LTMSI5', 'Bravo', b'1', 'LTMSI'),
('LTMSI6', 'GL Series', b'1', 'LTMSI'),
('LTMSI7', 'GE Series', b'1', 'LTMSI'),
('LTMSI8', 'GS Series', b'1', 'LTMSI'),
('LTMSI9', 'GT Series', b'1', 'LTMSI'),
('LTRazer', 'Razer', b'1', 'LT01'),
('LTRazer1', 'Blade 14', b'1', 'LTRazer'),
('LTRazer2', 'Blade 15', b'1', 'LTRazer'),
('LTRazer3', 'Blade 17', b'1', 'LTRazer'),
('LTRazer4', 'Blade Stealth', b'1', 'LTRazer'),
('MN01', 'Màn Hình', b'1', NULL),
('MNAcer', 'Màn Hình Acer', b'1', 'MN01'),
('MNAOC', 'Màn Hình AOC', b'1', 'MN01'),
('MNApple', 'Màn Hình Apple', b'1', 'MN01'),
('MNAsus', 'Màn Hình Asus', b'1', 'MN01'),
('MNBenQ', 'Màn Hình BenQ', b'1', 'MN01'),
('MNConcept', 'Màn Hình Concept D', b'1', 'MN01'),
('MNDell', 'Màn Hình Dell', b'1', 'MN01'),
('MNDell1', 'Màn Hình Dell UltraSharp', b'1', 'MNDell'),
('MNDell2', 'Màn Hình Dell E-Series', b'1', 'MNDell'),
('MNDell3', 'Màn Hình Dell S-Series', b'1', 'MNDell'),
('MNDell4', 'Màn Hình Dell Gaming', b'1', 'MNDell'),
('MNGigabyte', 'Màn Hình Gigabyte', b'1', 'MN01'),
('MNHKC', 'Màn Hình HKC', b'1', 'MN01'),
('MNHP', 'Màn Hình HP', b'1', 'MN01'),
('MNHuntKey', 'Màn Hình HuntKey', b'1', 'MN01'),
('MNLenovo', 'Màn Hình Lenovo', b'1', 'MN01'),
('MNLG', 'Màn Hình LG', b'1', 'MN01'),
('MNMSI', 'Màn Hình MSI', b'1', 'MN01'),
('MNSamsung', 'Màn Hình Samsung', b'1', 'MN01'),
('MNViewSonic', 'Màn Hình ViewSonic', b'1', 'MN01'),
('PC01', 'PC Văn Phòng', b'1', NULL),
('PCAcer', 'Máy Tính Văn Phòng Acer', b'1', 'PC01'),
('PCAsus', 'Máy Tính Văn Phòng Asus', b'1', 'PC01'),
('PCDell', 'Máy Tính Văn Phòng Dell', b'1', 'PC01'),
('PCDell1', 'Dell OptiPlex', b'1', 'PCDell'),
('PCDell2', 'Dell Inspiron', b'1', 'PCDell'),
('PCDell3', 'Dell Vostro', b'1', 'PCDell'),
('PCDell4', 'Dell XPS', b'1', 'PCDell'),
('PCHP', 'Máy Tính Văn Phòng HP', b'1', 'PC01'),
('PCHP1', 'HP 280', b'1', 'PCHP'),
('PCHP2', 'HP 290', b'1', 'PCHP'),
('PCHP3', 'HP 390', b'1', 'PCHP'),
('PCHP4', 'HP Pavilion', b'1', 'PCHP'),
('PCHP5', 'HP ProDesk', b'1', 'PCHP'),
('PCHP6', 'HP EliteDesk', b'1', 'PCHP'),
('PCHP7', 'HP ProOne', b'1', 'PCHP'),
('PCHP8', 'HP Pro', b'1', 'PCHP'),
('PCHP9', 'HP EliteOne', b'1', 'PCHP'),
('PCLenovo', 'Máy Tính Văn Phòng Lenovo', b'1', 'PC01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contact`
--

CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `subject` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `contact_date` datetime DEFAULT NULL,
  `reply_date` datetime DEFAULT NULL,
  `reply_content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contact_person_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `delivery_address`
--

CREATE TABLE `delivery_address` (
  `id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `set_as_default` bit(1) DEFAULT NULL,
  `id_account` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `delivery_address`
--

INSERT INTO `delivery_address` (`id`, `name`, `phone`, `address`, `set_as_default`, `id_account`) VALUES
(1, 'Nguyễn Mạnh Dũng', '0349968564', '27 Ngõ 143 Xuân Phương, Phường Phương Canh, Quận Nam Từ Liêm, Thành phố Hà Nội', b'1', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `description`
--

CREATE TABLE `description` (
  `id` int(11) NOT NULL,
  `product_sku` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_blog` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img_url` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `description`
--

INSERT INTO `description` (`id`, `product_sku`, `id_blog`, `img_url`, `title`, `content`) VALUES
(13, 'LTDELL4214667101KoC', NULL, 'http://localhost:8080/viewFile/503260f6.jpg', 'Màn hình với độ phủ màu cao, trải nghiệm làm việc và giải trí thêm phần đã mắt', 'Màn hình vẫn luôn mà điểm đáng chú ý nhất của các dòng Zenbook, và tất nhiên Asus Zenbook Q408UG cũng không phải là ngoại lệ. Máy sở hữu màn hình 14 inch với tấm nền IPS, hỗ trợ độ phân giải Full HD. Phải nói rằng màn hình trên Asus Zenbook Q408UG phù hợp với mọi nhu cầu từ học tập, làm việc cho tới giải trí bởi nó có độ sáng và độ tương phản cao, cho dù ở trong điều kiện môi trường nào cũng vẫn có thể hoạt động tốt. Bên cạnh đó, màn hình của máy có độ phủ màu lên tới 90% sRGB, nên việc thiết kế in ấn máy hoàn toàn có thể đáp ứng được. \n\n'),
(14, 'LTDELL4214667101KoC', NULL, 'http://localhost:8080/viewFile/65729e1c.jpg', 'Màn hình với độ phủ màu cao, trải nghiệm làm việc và giải trí thêm phần đã mắt', 'Màn hình vẫn luôn mà điểm đáng chú ý nhất của các dòng Zenbook, và tất nhiên Asus Zenbook Q408UG cũng không phải là ngoại lệ. Máy sở hữu màn hình 14 inch với tấm nền IPS, hỗ trợ độ phân giải Full HD. Phải nói rằng màn hình trên Asus Zenbook Q408UG phù hợp với mọi nhu cầu từ học tập, làm việc cho tới giải trí bởi nó có độ sáng và độ tương phản cao, cho dù ở trong điều kiện môi trường nào cũng vẫn có thể hoạt động tốt. Bên cạnh đó, màn hình của máy có độ phủ màu lên tới 90% sRGB, nên việc thiết kế in ấn máy hoàn toàn có thể đáp ứng được. \n\n');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `image_detail`
--

CREATE TABLE `image_detail` (
  `id` int(11) NOT NULL,
  `id_product_dt` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name_path` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `type_img` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `image_detail`
--

INSERT INTO `image_detail` (`id`, `id_product_dt`, `name_path`, `type_img`) VALUES
(1, 'LTDELL421845479KoC', 'http://localhost:8080/viewFile/58533678.jpg', 0),
(2, 'LTDELL421845479KoC', 'http://localhost:8080/viewFile/af9022c3.jpg', 1),
(3, 'LTDELL421845479KoC', 'http://localhost:8080/viewFile/8a23b4e5.jpg', 2),
(4, 'LTDELL421845479KoC', 'http://localhost:8080/viewFile/1597b3a9.jpg', 3),
(5, 'LTDELL421888479KoC', 'http://localhost:8080/viewFile/4c9162b1.jpg', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `order_code` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_account` int(11) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `phone_number` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payment_status` bit(1) DEFAULT NULL,
  `payment_methods` int(11) DEFAULT NULL,
  `completion_date` date DEFAULT NULL,
  `authenticator` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `received` int(11) DEFAULT NULL,
  `total_price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `order_code`, `id_account`, `order_date`, `phone_number`, `address`, `description`, `payment_status`, `payment_methods`, `completion_date`, `authenticator`, `received`, `total_price`) VALUES
(15, '01185116954', 2, '2022-01-15', '0349968564', 'Nguyễn Mạnh Dũng - 27 Ngõ 143 Xuân Phương, Phường Phương Canh, Quận Nam Từ Liêm, Thành phố Hà Nội', '', b'1', 1, NULL, NULL, 0, 151300000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_details`
--

CREATE TABLE `order_details` (
  `id` int(11) NOT NULL,
  `id_order` int(11) DEFAULT NULL,
  `sku` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_dt_price` float DEFAULT NULL,
  `into_money` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `serial_number` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `order_details`
--

INSERT INTO `order_details` (`id`, `id_order`, `sku`, `product_dt_price`, `into_money`, `quantity`, `serial_number`, `status`) VALUES
(1, 15, 'LTDELL421845479KoC', 8900000, 8900000, 17, NULL, 0),
(2, 15, 'LTDELL421894312KoC', 79, 63.2, 1, NULL, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_acount` int(11) DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type_of_item` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_category` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unit` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `release_year` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL,
  `active` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `id_acount`, `name`, `type_of_item`, `id_category`, `company`, `unit`, `release_year`, `active`) VALUES
('', NULL, '', 'LT01', 'LTDELL4', '', '', '2022 ', 1),
('DellXpS172021', NULL, 'Dell XPS 17 9710', 'LT01', 'LTDELL4', 'Dell', 'Chiếc', '2021 ', 1),
('Legion7A2102CF', NULL, 'Lenovo Legion 7 16ACHg6', 'LT01', 'LTLenovo3', 'Lenovo', 'Chiếc', '2022 ', 1),
('LTDEL2342378', NULL, 'Asus Zbook 15', 'LT01', 'LTAsus2', 'Asus', 'Chiếc', '2022 ', 1),
('SPDLE89w2347JDS', NULL, 'Ten San Pham Xp9383', 'LT01', 'LTAsus2', 'Asus', 'Chiếc', '2022 ', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products_detail`
--

CREATE TABLE `products_detail` (
  `sku` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_product` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `img_url` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_on` date NOT NULL,
  `price` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `dimensions` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cpu` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ram` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hard_drive` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `webcam` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vga` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `os` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `power` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `display_size` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `screen_ratio` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scan_frequency` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `background_panels` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `resolution` varchar(35) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contrast` varchar(105) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `response_time` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `brightness` varchar(35) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `view_screenshots` varchar(55) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `connectivity` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `battery` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `warranty_period` int(11) NOT NULL,
  `accessories_included` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `see_more` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `on_sale` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `products_detail`
--

INSERT INTO `products_detail` (`sku`, `id_product`, `img_url`, `date_on`, `price`, `quantity`, `dimensions`, `cpu`, `color`, `ram`, `hard_drive`, `webcam`, `vga`, `os`, `power`, `display_size`, `screen_ratio`, `scan_frequency`, `background_panels`, `resolution`, `contrast`, `response_time`, `brightness`, `view_screenshots`, `connectivity`, `battery`, `warranty_period`, `accessories_included`, `see_more`, `on_sale`, `status`) VALUES
('LTAsus222846382KoC', 'LTDEL2342378', '', '2022-01-06', 23900000, 6, '0', 'Intel® Core™ i5-10600K', 'Abyss Blue', 'DDR4 8GB', 'SSD 256GB', 'HD', 'Intel® Iris® Xᵉ', 'windows', '', '14 Inch', '16:9', '60Hz', 'IPS', 'FHD(1920x1080)', '1200:1', '', '', '', '', '', 0, '', '', NULL, 1),
('LTDELL4214667101KoC', 'DellXpS172021', 'http://localhost:8080/viewFile/78009edd.jpg', '2021-12-26', 8900000, 4, '0', 'Intel® Core™ i9-12900K', 'Đen', 'DDR5 32GB', 'SSD 2TB', '', 'Geforce RTX 3080', 'windows', '', '17 Inch', '16:10', '120Hz', 'AMOLED', '4K', '10000:1', '1ms', '', '', '', '', 0, '', '', 'SL004', 1),
('LTDELL421657097KoC', 'DellXpS172021', 'http://localhost:8080/viewFile/78009edd.jpg', '2021-12-26', 8900000, 4, '0', 'Intel® Core™ i9-12900K', 'Đen', 'DDR5 32GB', 'SSD 2TB', '', 'Geforce RTX 3080', 'windows', '', '17 Inch', '16:10', '120Hz', 'AMOLED', '4K', '10000:1', '1ms', '', '', '', '', 0, '', '', 'SL001', 0),
('LTDELL421845479KoC', 'DellXpS172021', 'http://localhost:8080/viewFile/c8bf0f.jpg', '2021-12-26', 8900000, 90, '0', 'Intel® Core™ i9-12900K', 'Đen', 'DDR5 32GB', 'SSD 2TB', '', 'Geforce RTX 3080', 'windows', '', '17 Inch', '16:10', '120Hz', 'AMOLED', '4K', '10000:1', '1ms', '', '', 'Bluetooth, Wifi', '', 0, '', '', NULL, 1),
('LTDELL421888479KoC', 'DellXpS172021', 'http://localhost:8080/viewFile/ebe8bdf8.jpg', '2021-12-26', 8900000, 74, '0', 'Intel® Core™ i9-12900K', 'Đen', 'DDR5 32GB', 'SSD 2TB', '', 'Geforce RTX 3080', 'windows', '', '17 Inch', '16:10', '120Hz', 'AMOLED', '4K', '10000:1', '1ms', '', '', NULL, '', 0, '', '', 'SL002', 0),
('LTDELL421894312KoC', 'DellXpS172021', 'http://localhost:8080/viewFile/a6f0da78.jpg', '2021-12-26', 79, 789, '0', 'Intel® Core™ i9-11900H', 'Đen', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, '', '', 'SL003', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_rating`
--

CREATE TABLE `product_rating` (
  `id` int(11) NOT NULL,
  `sku_product` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_account` int(11) NOT NULL,
  `img_url` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `star_rating` int(11) NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_comment` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `role_name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sale_product`
--

CREATE TABLE `sale_product` (
  `sale_code` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
  `promotion_type` varchar(50) CHARACTER SET utf8 NOT NULL,
  `date_on` date NOT NULL,
  `date_off` date NOT NULL,
  `promotion` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sale_product`
--

INSERT INTO `sale_product` (`sale_code`, `promotion_type`, `date_on`, `date_off`, `promotion`, `quantity`, `status`) VALUES
('SL001', 'Giảm giác sốc', '2022-01-14', '2022-01-27', 10, NULL, 1),
('SL002', 'Dell cùng bạn vui đến trường', '2021-10-01', '2022-01-16', 10, 300, 1),
('SL003', 'Chợ tết laptop - ngập tràn khuyến mãi', '2021-11-01', '2022-11-17', 20, 500, 1),
('SL004', 'Tâm điểm khai trường – chọn quà chất tôi', '2021-01-15', '2022-10-15', 5, 400, 1),
('SL005', 'Máy chuẩn gu, quà vi vu', '2021-05-20', '2022-06-17', 15, 200, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vouchers`
--

CREATE TABLE `vouchers` (
  `id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_category` varchar(55) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_product` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `promotion` int(11) NOT NULL,
  `start_day` date NOT NULL,
  `end_date` date NOT NULL,
  `description` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `actived` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `account_role`
--
ALTER TABLE `account_role`
  ADD PRIMARY KEY (`id_account`,`id_role`),
  ADD KEY `id_role` (`id_role`);

--
-- Chỉ mục cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `delivery_address`
--
ALTER TABLE `delivery_address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`);

--
-- Chỉ mục cho bảng `description`
--
ALTER TABLE `description`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_blog` (`id_blog`),
  ADD KEY `product_sku` (`product_sku`);

--
-- Chỉ mục cho bảng `image_detail`
--
ALTER TABLE `image_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_product_dt` (`id_product_dt`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`);

--
-- Chỉ mục cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sku` (`sku`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_category` (`id_category`),
  ADD KEY `id_acount` (`id_acount`);

--
-- Chỉ mục cho bảng `products_detail`
--
ALTER TABLE `products_detail`
  ADD PRIMARY KEY (`sku`),
  ADD KEY `on_sale` (`on_sale`),
  ADD KEY `id_product` (`id_product`);

--
-- Chỉ mục cho bảng `product_rating`
--
ALTER TABLE `product_rating`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`),
  ADD KEY `sku_product` (`sku_product`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sale_product`
--
ALTER TABLE `sale_product`
  ADD PRIMARY KEY (`sale_code`);

--
-- Chỉ mục cho bảng `vouchers`
--
ALTER TABLE `vouchers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `contact`
--
ALTER TABLE `contact`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `delivery_address`
--
ALTER TABLE `delivery_address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `description`
--
ALTER TABLE `description`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `image_detail`
--
ALTER TABLE `image_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `product_rating`
--
ALTER TABLE `product_rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `account_role`
--
ALTER TABLE `account_role`
  ADD CONSTRAINT `account_role_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `account_role_ibfk_2` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`);

--
-- Các ràng buộc cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Các ràng buộc cho bảng `delivery_address`
--
ALTER TABLE `delivery_address`
  ADD CONSTRAINT `delivery_address_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Các ràng buộc cho bảng `description`
--
ALTER TABLE `description`
  ADD CONSTRAINT `description_ibfk_1` FOREIGN KEY (`product_sku`) REFERENCES `products_detail` (`sku`);

--
-- Các ràng buộc cho bảng `image_detail`
--
ALTER TABLE `image_detail`
  ADD CONSTRAINT `image_detail_ibfk_1` FOREIGN KEY (`id_product_dt`) REFERENCES `products_detail` (`sku`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Các ràng buộc cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`sku`) REFERENCES `products_detail` (`sku`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`id_acount`) REFERENCES `account` (`id`);

--
-- Các ràng buộc cho bảng `products_detail`
--
ALTER TABLE `products_detail`
  ADD CONSTRAINT `products_detail_ibfk_1` FOREIGN KEY (`on_sale`) REFERENCES `sale_product` (`sale_code`),
  ADD CONSTRAINT `products_detail_ibfk_2` FOREIGN KEY (`id_product`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `product_rating`
--
ALTER TABLE `product_rating`
  ADD CONSTRAINT `product_rating_ibfk_1` FOREIGN KEY (`sku_product`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `product_rating_ibfk_2` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `product_rating_ibfk_3` FOREIGN KEY (`sku_product`) REFERENCES `products_detail` (`sku`);

--
-- Các ràng buộc cho bảng `vouchers`
--
ALTER TABLE `vouchers`
  ADD CONSTRAINT `vouchers_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `vouchers_ibfk_2` FOREIGN KEY (`id_product`) REFERENCES `products` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
