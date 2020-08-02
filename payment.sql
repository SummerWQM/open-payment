# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.28)
# Database: payment
# Generation Time: 2020-07-20 15:20:59 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table payment_channel
# ------------------------------------------------------------

CREATE TABLE `payment_channel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '通道唯一编码: WECHAT、ALIPAY_GATEWAY 、ALIPAYWAP、ALIPAY_QR etcd',
  `creator` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `params` text COMMENT '支付通道定制化配置参数:JSON',
  `status` enum('open','close') DEFAULT 'open' COMMENT '支付通道状态: open 开启; close 关闭',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table payment_transaction
# ------------------------------------------------------------

CREATE TABLE `payment_transaction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `unid` varchar(64) NOT NULL COMMENT '支付交易唯一编号',
  `from_system` varchar(20) NOT NULL DEFAULT '' COMMENT '来源系统: 比如来自订单系统、用户中心 etc',
  `transaction_name` varchar(200) NOT NULL DEFAULT '' COMMENT '交易名称',
  `amount` int(10) unsigned NOT NULL COMMENT '支付金额(分)',
  `status` enum('new','paying','success','failed') DEFAULT 'new' COMMENT '支付状态',
  `callback_url` varchar(128) DEFAULT '' COMMENT '内部系统回调地址',
  `notify_count` tinyint(3) unsigned DEFAULT '0' COMMENT '通知次数',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unid` (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table payment_transaction_result
# ------------------------------------------------------------

CREATE TABLE `payment_transaction_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `payment_transaction_unid` varchar(64) NOT NULL COMMENT 'payment_transaction 表 unid',
  `amount` int(10) unsigned NOT NULL COMMENT '支付成功金额金额(分)',
  `discount_amount` int(10) unsigned NOT NULL COMMENT '拆分 扣减费用',
  `status` enum('success','failed') DEFAULT NULL COMMENT '支付完成状态',
  `finish_at` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '支付完时间',
  `message_info` text COMMENT '支付通知报文',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_payment_transaction_unid` (`payment_transaction_unid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
