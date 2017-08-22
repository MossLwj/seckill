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

	//ע��Service����
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	//MD5��ֵ�ַ��������ڻ������ɵ�MD5
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
		//ϵͳ��ǰʱ��
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
			//�����
			int updateCount = seckillDao.reduceNumber(seckillId, killTime);
			if(updateCount <= 0){
				//û�и��¼�¼��˵����ɱ����
				throw new SeckillCloseException("seckill is closed");
			}else{
				//��¼������Ϊ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//Ψһ��seckillId, userPhone
				if(insertCount<=0){
					throw new RepeatKillException("seckill repeated");
				}else{
					//��ɱ�ɹ�
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
			//���б������쳣 ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error");
		}
	}

}
