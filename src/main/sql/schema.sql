--���ݿ��ʼ���ű�

--����mysql���ݿ���﷨
mysql -root -p
--�������ݿ�
create DATABASE seckill;
--ʹ�����ݿ�
use seckill;


--������ɱ����
CREATE TABLE seckill (
	seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���ID',
	NAME VARCHAR (120) NOT NULL COMMENT '��Ʒ����',
	number INT NOT NULL COMMENT '�������',
	start_time TIMESTAMP NOT NULL NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '��ɱ����ʱ��',
	end_time TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	PRIMARY KEY (seckill_id),
	KEY idx_start_time (start_time),
	KEY idx_end_time (end_time),
	KEY idx_create_time (create_time)
) ENGINE = INNODB AUTO_INCREMENT = 1000 DEFAULT CHARSET = UTF8 COMMENT '��ɱ����';

--��ʼ����ɱ����
insert into 
	seckill (name,number,start_time,end_time)
values
	('1000Ԫ��ɱIphone8',100,'2017-08-14 00:00:00','2017-08-15 00:00:00'),
	('500Ԫ��ɱIphone6',200,'2017-08-14 00:00:00','2017-08-15 00:00:00'),
	('300Ԫ��ɱС��4',300,'2017-08-14 00:00:00','2017-08-15 00:00:00'),
	('200Ԫ��ɱ����NOTE',400,'2017-08-14 00:00:00','2017-08-15 00:00:00');
	
--��ɱ�ɹ���ϸ��
--�û���¼��ص���Ϣ

	
CREATE TABLE success_killed (
	seckill_id BIGINT NOT NULL COMMENT '��ƷId',
	user_phone BIGINT NOT NULL COMMENT '�û��ֻ���',
	state TINYINT NOT NULL DEFAULT - 1 COMMENT '״̬��ʾ��-1:��Ч;0:�ɹ�;1���Ѹ���;2:�ѷ���;',
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	PRIMARY KEY (seckill_id, user_phone),/* ��������������Ψһ�ԣ�ʹ��һ���û�ֻ����ɱһ����Ʒ */
	KEY idx_create_time (create_time)
) ENGINE = INNODB DEFAULT CHARSET = UTF8 COMMENT '��ɱ�ɹ���ϸ��';


