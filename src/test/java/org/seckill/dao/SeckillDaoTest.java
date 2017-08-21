package org.seckill.dao;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ����spring��jUnit�����ϣ�jUnit����ʱ����springIOC����
 * spring-test�� jUnit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����jUnit��spring��Ӧ�������ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//ע��Daoʵ��������
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testReduceNumber() throws Exception{
		
	}

	@Test
	public void testQueryAll() throws Exception{
		
	}

}
