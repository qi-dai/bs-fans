/**1.创建序列表*/
CREATE TABLE IF NOT EXISTS `bs_sequence` (

  `name` VARCHAR(50) NOT NULL,

  `current_value` INT(11) NOT NULL,

  `increment` INT(11) NOT NULL DEFAULT '1'

) ENGINE=MYISAM DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='序列表，命名s_[table_name]';

/**2.插入序列数据*/
INSERT INTO `bs_sequence` (`name`, `current_value`, `increment`) VALUES

('user_code1', 100001, 1);

/**3.创建选择当前值函数currval*/
DROP FUNCTION IF EXISTS `currval`;

DELIMITER//

CREATE  FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS INT(11)

    READS SQL DATA

    DETERMINISTIC

BEGIN

DECLARE S_VALUE INTEGER;

SET S_VALUE = 0;

SELECT current_value INTO S_VALUE FROM bs_sequence WHERE NAME = seq_name;

RETURN S_VALUE;

END//

DELIMITER ;


/**4.取完数将序列值加1*/

DROP FUNCTION IF EXISTS `nextval`;

DELIMITER //

CREATE  FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS INT(11)

    DETERMINISTIC

BEGIN

UPDATE bs_sequence SET current_value = current_value + increment WHERE NAME = seq_name;

RETURN currval(seq_name);

END//


DELIMITER ;

/***获取序列示例,user_code1是序列名*/
SELECT nextval("user_code1");