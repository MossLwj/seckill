package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ����spring��jUnit�����ϣ�jUnit����ʱ����springIOC���� spring-test�� jUnit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ����jUnit��spring��Ӧ�������ļ�
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class RedisDaoTest {
	
	private long seckillId = 1001;
	
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testSeckill() throws Exception {
		//get and put
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill != null) {
				String result = redisDao.putSeckill(seckill);
				System.out.println(result);
				seckill = redisDao.getSeckill(seckillId);
				System.out.println(seckill);
			}
		}
	}

}
