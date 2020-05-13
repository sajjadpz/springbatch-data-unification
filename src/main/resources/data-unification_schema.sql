-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS MOVIES;
DROP TABLE IF EXISTS RATINGS;
DROP TABLE IF EXISTS USERS;


# Dump of table MOVIES
# ------------------------------------------------------------

CREATE TABLE `MOVIES` (
  `movie_id` bigint(11) NOT NULL,
  `movie_title` varchar(100) DEFAULT NULL,
  `movie_year` int(4) DEFAULT NULL,
  `genre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table RATINGS
# ------------------------------------------------------------

CREATE TABLE `RATINGS` (
  `user_id` bigint(11) unsigned NOT NULL,
  `movie_id` bigint(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `rating_timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table USERS
# ------------------------------------------------------------

CREATE TABLE `USERS` (
  `user_id` bigint(11) unsigned NOT NULL,
  `twitter_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

