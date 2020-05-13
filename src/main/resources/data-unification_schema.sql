-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS MOVIES;
DROP TABLE IF EXISTS RATINGS;
DROP TABLE IF EXISTS USERS;


# Dump of table MOVIES
# ------------------------------------------------------------

CREATE TABLE `MOVIES` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `movie_id` bigint(11) NOT NULL,
  `movie_title` tinytext,
  `movie_year` int(4) DEFAULT NULL,
  `genre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8101 DEFAULT CHARSET=utf8;



# Dump of table RATINGS
# ------------------------------------------------------------

CREATE TABLE `RATINGS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `movie_id` bigint(11) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  `rating_timestamp` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table USERS
# ------------------------------------------------------------

CREATE TABLE `USERS` (
  `user_id` bigint(11) unsigned NOT NULL,
  `twitter_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

