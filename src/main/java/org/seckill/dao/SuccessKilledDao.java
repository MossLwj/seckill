package org.seckill.dao;

import org.seckill.entity.Successkilled;

public interface SuccessKilledDao {
	
	/**
	 * ���빺����ϸ,�ɹ����ظ�
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccessKilled(long seckillId,long userPhone);
	
	/**
	 * ����id��ѯ������ϸ����Я����ɱ��Ʒʵ��
	 * @param seckillId
	 * @return
	 */
	Successkilled queryByIdWithSeckill(long seckillId);
	

}
