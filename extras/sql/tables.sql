CREATE TABLE `export_history` (
  `id` varchar(36) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(36) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(36) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `query` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
