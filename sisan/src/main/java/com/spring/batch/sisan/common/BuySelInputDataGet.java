package com.spring.batch.sisan.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.batch.sisan.dto.KaiIdoDto;
import com.spring.batch.sisan.entities.BsIdEntity;
import com.spring.batch.sisan.entities.ThNrEntity;

@Component
public class BuySelInputDataGet {

	public List<KaiIdoDto> getBaibaiIdo(String innoInf, Integer idoKey) {
		List<KaiIdoDto> listKaiIdoDto = new ArrayList<KaiIdoDto>();
		for (int i = 0; i < 3; i ++) {
			BsIdEntity bsIdEntity = createBsIdEntity();
			ThNrEntity thNrEntity = createThNrEntity();
			KaiIdoDto kaiIdoDto = new KaiIdoDto();
			kaiIdoDto.setBsIdEntity(bsIdEntity);
			kaiIdoDto.setThNrEntity(thNrEntity);
			listKaiIdoDto.add(kaiIdoDto);
		}
		return listKaiIdoDto;
	}

	private BsIdEntity createBsIdEntity() {
		BsIdEntity bsIdEntityRlt = new BsIdEntity();

		bsIdEntityRlt.setIdoKey(1111);
		bsIdEntityRlt.setKjnYmd(20200101);
		bsIdEntityRlt.setUpdZnKey(123456789);
		bsIdEntityRlt.setUpdZnKeyTouitsu(987654321);
		bsIdEntityRlt.setInnoinf("Innoinf01");
		bsIdEntityRlt.setBunkatuMotoInnoinf("BunkatuMotoInnoinf01");
		bsIdEntityRlt.setItkCmpCd("100");
		bsIdEntityRlt.setFndCd("FndCd01");
		bsIdEntityRlt.setMegCd("MegCd01");
		bsIdEntityRlt.setKaiYmd(20200202);
		bsIdEntityRlt.setSiteiNo(111);
		bsIdEntityRlt.setBuySelKbn("BuySelKbn01");
		bsIdEntityRlt.setFifoFlg("FifoFlg01");
		bsIdEntityRlt.setKekaRskKbn("KekaRskKbn01");
		bsIdEntityRlt.setKazeiKbn("KazeiKbn01");
		bsIdEntityRlt.setAgainstKbn("AgainstKbn01");
		bsIdEntityRlt.setHokanBnkCd("HokanBnkCd01");
		bsIdEntityRlt.setErrorFlg("1");
		bsIdEntityRlt.setYkjYmdLcl(20200303);
		bsIdEntityRlt.setUkeYmdLcl(20200808);
		bsIdEntityRlt.setUkeYmdJpn(20200505);
		bsIdEntityRlt.setNrykYmd(20200606);
		bsIdEntityRlt.setYkjYmdKjy(20200707);
		bsIdEntityRlt.setSikinUkeKanrYmdK(20200808);
		bsIdEntityRlt.setSecUkeKanrYmdKjy(20200909);
		bsIdEntityRlt.setFace(123);
		bsIdEntityRlt.setOrgFace(321);
		bsIdEntityRlt.setYkjTnk(222);
		bsIdEntityRlt.setYkjKn(333);
		bsIdEntityRlt.setKekaRsk(444);
		bsIdEntityRlt.setUkeKn(555);

		bsIdEntityRlt.setDelKbn("DelKbn01");
		bsIdEntityRlt.setNrykUserId("NrykUserId01");

		return bsIdEntityRlt;
	}

	private ThNrEntity createThNrEntity() {
		ThNrEntity thNrEntity = new ThNrEntity();
		thNrEntity.setDelKbn("DelKbn02");
		thNrEntity.setNrykUserId("NrykUserId02");

		return thNrEntity;
	}
}
