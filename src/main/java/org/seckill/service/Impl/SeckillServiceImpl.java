package org.seckill.service.Impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SeckillDao seckillDao;

	private SuccessKilledDao successKilledDao;
	
	//MD5盐值字符串，用于混淆生成的MD5
	private final String salt = "saldjaklhghda;!@%$&(*&#$%@#$!@";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getSeckillById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//系统当前时间
		Date nowTime = new Date();
		if (startTime.getTime() < nowTime.getTime() || endTime.getTime() <nowTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		
		String md5 = getMD5(seckillId);
		
		return new Exposer(true, md5, seckillId);

	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	public SecurityException executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		// TODO Auto-generated method stub
		return null;
	}

}
