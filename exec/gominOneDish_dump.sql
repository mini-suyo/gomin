-- --------------------------------------------------------
-- 호스트:                          stg-yswa-kr-practice-db-master.mariadb.database.azure.com
-- 서버 버전:                        10.3.23-MariaDB - MariaDB Server
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- S12P12B109 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `s12p12b109` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `S12P12B109`;

-- 테이블 S12P12B109.answer 구조 내보내기
CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_liked` bit(1) NOT NULL,
  `sushi_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `content` text COLLATE utf8mb4_bin NOT NULL,
  `is_negative` bit(1) NOT NULL,
  `is_gpt` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `answer_userId_sushiId_unique` (`user_id`,`sushi_id`),
  KEY `FKb8w4bkb75tvihq2ypgcyen54i` (`sushi_id`),
  CONSTRAINT `FK68tbcw6bunvfjaoscaj851xpb` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKb8w4bkb75tvihq2ypgcyen54i` FOREIGN KEY (`sushi_id`) REFERENCES `sushi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1554 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.category 구조 내보내기
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.fcm_tokens 구조 내보내기
CREATE TABLE IF NOT EXISTS `fcm_tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fcm_tokens_user_token` (`user_id`,`token`),
  CONSTRAINT `fcm_tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.notification 구조 내보내기
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_read` bit(1) NOT NULL,
  `notification_type` char(1) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `message` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `redirect_url` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `sushi_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yvoep4h4k92ipon31wmdf7e` (`user_id`),
  KEY `fk_notification_sushi` (`sushi_id`),
  CONSTRAINT `FKb0yvoep4h4k92ipon31wmdf7e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_notification_sushi` FOREIGN KEY (`sushi_id`) REFERENCES `sushi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=946 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.share_token 구조 내보내기
CREATE TABLE IF NOT EXISTS `share_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sushi_id` int(11) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhiosusnvuruxrb63lgvbse4fd` (`sushi_id`),
  CONSTRAINT `FKhiosusnvuruxrb63lgvbse4fd` FOREIGN KEY (`sushi_id`) REFERENCES `sushi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=540 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.sushi 구조 내보내기
CREATE TABLE IF NOT EXISTS `sushi` (
  `category_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_closed` bit(1) NOT NULL,
  `max_answers` int(11) NOT NULL,
  `remaining_answers` int(11) NOT NULL,
  `sushi_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expire_time` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `content` text COLLATE utf8mb4_bin NOT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yc0gfwlh3nxtdcrei5kqh0ay` (`category_id`),
  KEY `FK9vquqdkm4qcet82o9i0ga62n4` (`sushi_type_id`),
  KEY `FK20wrjospfn84f534lpby6c5b7` (`user_id`),
  CONSTRAINT `FK20wrjospfn84f534lpby6c5b7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK5yc0gfwlh3nxtdcrei5kqh0ay` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK9vquqdkm4qcet82o9i0ga62n4` FOREIGN KEY (`sushi_type_id`) REFERENCES `sushi_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=541 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.sushi_exposure 구조 내보내기
CREATE TABLE IF NOT EXISTS `sushi_exposure` (
  `sushi_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sushi_exposure_userId_sushiId_unique` (`user_id`,`sushi_id`),
  KEY `FK7y8vigu3hehl31s2e3l6g70ki` (`sushi_id`),
  CONSTRAINT `FK7y8vigu3hehl31s2e3l6g70ki` FOREIGN KEY (`sushi_id`) REFERENCES `sushi` (`id`),
  CONSTRAINT `FKehh5g837tjmsdy9nlt3or8yjg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8257 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.sushi_type 구조 내보내기
CREATE TABLE IF NOT EXISTS `sushi_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `required_likes` int(11) DEFAULT NULL,
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 S12P12B109.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_likes` int(11) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `provider_id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `provider` enum('GOOGLE','KAKAO') COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;



-- 코드 테이블 데이터

-- 테이블 데이터 S12P12B109.category:~6 rows (대략적) 내보내기
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'LOVE'),
	(2, 'FRIENDSHIP'),
	(3, 'CAREER'),
	(4, 'HEALTH'),
	(5, 'FAMILY'),
	(6, 'OTHER');

-- 테이블 데이터 S12P12B109.sushi_type:~12 rows (대략적) 내보내기
INSERT INTO `sushi_type` (`id`, `required_likes`, `name`) VALUES
	(1, 0, 'EGG'),
	(2, 1, 'SALMON'),
	(3, 2, 'SHRIMP'),
	(4, 3, 'CUTTLEFISH'),
	(5, 6, 'OCTOPUS'),
	(6, 10, 'EEL'),
	(7, 15, 'WAGYU'),
	(8, 20, 'SCALLOP'),
	(9, 30, 'FLATFISH'),
	(10, 50, 'UNI'),
	(11, 80, 'TUNA'),
	(12, 100, 'SALMONROE');


-- 시스템 유저

INSERT INTO user (nickname,total_likes, provider, provider_id, created_at, updated_at)
VALUES ('고양이마스터', 0, 'KAKAO', 1111111, NOW(), NOW());
