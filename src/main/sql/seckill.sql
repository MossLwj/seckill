-- 秒杀执行的存储过程
DELIMITER $$ -- console ; 转换为  $$
-- 定义存储过程
-- 参数： in 输入参数； out 输出参数 
-- ROW_COUNT(): 返回上一条修改类型sql（delete，insert，update）的影响行数
CREATE PROCEDURE `seckill`.`execute_seckill`
	(IN v_seckill_id BIGINT,IN v_phone BIGINT, IN v_kill_time TIMESTAMP, OUT r_result INT)
	BEGIN
		DECLARE insert_count INT DEFAULT 0;
		START TRANSACTION;
-- 执行插入秒杀记录
		INSERT IGNORE INTO success_killed
			(seckill_id,user_phone,create_time)
			VALUES(v_seckill_id,v_phone,v_kill_time);

		SELECT ROW_COUNT() INTO insert_count;
		-- 如果插入的数据条数小于1则回滚操作
		IF (insert_count = 0) THEN 
			ROLLBACK; 
			SET r_result = -1;
		-- 如果result小于0的情况下的话说明系统错误了
		ELSEIF(insert_count < 0) THEN 
			ROLLBACK; 
			SET r_result = -2;
		ELSE
			UPDATE seckill
			SET number = number - 1
			WHERE seckill_id = v_seckill_id
				AND end_time > v_kill_time
				AND start_time < v_kill_time
				AND number > 0;
			SELECT ROW_COUNT() INTO insert_count;
			IF (insert_count = 0) THEN 
				ROLLBACK;
				SET r_result = 0;
			ELSEIF(insert_count < 0) THEN 
				ROLLBACK; 
				SET r_result = -2;
			ELSE COMMIT;
				SET r_result = 1;
			END IF;
		END IF;
	END;
$$