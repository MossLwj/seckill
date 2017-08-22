package org.seckill.service.Impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatesEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//注入Service依赖
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
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

	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			 throws SeckillException, RepeatKillException, SeckillCloseException {
		Date killTime = new Date();
		try {
			//减库存
			int updateCount = seckillDao.reduceNumber(seckillId, killTime);
			if(updateCount <= 0){
				//没有更新记录，说明秒杀结束
				throw new SeckillCloseException("seckill is closed");
			}else{
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//唯一：seckillId, userPhone
				if(insertCount<=0){
					throw new RepeatKillException("seckill repeated");
				}else{
					//秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatesEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译期异常 转化为运行期异常
			throw new SeckillException("seckill inner error");
		}
	}

}
