package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ����spring��jUnit�����ϣ�jUnit����ʱ����springIOC����
 * spring-test�� jUnit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����jUnit��spring��Ӧ�������ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() {
		long seckillId = 1001L;
		long userPhone = 18267566890L;
		int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println(insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() throws Exception {
		long seckillId = 1001L;
		long userPhone = 18267566890L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}

}
