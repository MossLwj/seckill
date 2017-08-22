package org.seckill.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("list={}",seckills);
	}

	@Test
	public void testGetSeckillById() {
		long seckillId = 1000L;
		Seckill seckill = seckillService.getSeckillById(seckillId);
		logger.info("seckill={}",seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteSeckill() {
		fail("Not yet implemented");
	}

}
