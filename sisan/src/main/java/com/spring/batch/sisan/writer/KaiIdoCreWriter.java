package com.spring.batch.sisan.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.batch.sisan.common.LoginUserInfoGet;
import com.spring.batch.sisan.dao.TTFBTBSIDDaoImpl;
import com.spring.batch.sisan.entities.BsIdEntity;
import com.spring.batch.sisan.entities.LoginUserInfo;

@Component
public class KaiIdoCreWriter extends MyListItemWriter<BsIdEntity> {

	@Autowired 
	private LoginUserInfoGet loginUserInfoGet;
	
	@Autowired
	private TTFBTBSIDDaoImpl tTFBTBSIDDao;
	
	@Override
	protected void writeTargetData(BsIdEntity bsIdEntity) {
		LoginUserInfo loginUserInfo = loginUserInfoGet.getLoginUserInfo();
		bsIdEntity.setUpdLoginCmpCd(loginUserInfo.getLoginUserKaishaCd());
		bsIdEntity.setUpdSid(loginUserInfo.getLoginUserUserId());

		tTFBTBSIDDao.write();
	}
	
}
