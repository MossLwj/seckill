package org.seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和jUnit的整合，jUnit启动时加载springIOC容器
 * spring-test， jUnit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉jUnit，spring对应的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() {
		long id = 1001;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception{
		int offset = 0;
		int limit = 100;
		List<Seckill> seckills = seckillDao.queryAll(offset, limit);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}
	
	@Test
	public void testReduceNumber() throws Exception{
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000, killTime);
		System.out.println("updateCount" + updateCount);
	}

}
