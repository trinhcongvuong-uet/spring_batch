package com.spring.batch.sisan.reader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.batch.sisan.common.BuySelInputDataGet;
import com.spring.batch.sisan.dto.KaiIdoDto;

@Component
public class KaiIdoCreReader extends MyListItemReader<KaiIdoDto> {

	@Autowired
	private BuySelInputDataGet buySelInputDataGet;

	@Override
	protected List<KaiIdoDto> getTargetData() {
		// get innoinf and keyIdo from jobExecutionContext
		String innoInf = (String) jobExecutionContext.get("INNOINF");
		Integer keyIdo = (Integer) jobExecutionContext.get("IDO_KEY");
		// defined listKaiIdoDto
		List<KaiIdoDto> listKaiIdoDto = (List<KaiIdoDto>) buySelInputDataGet.getBaibaiIdo(innoInf, keyIdo);
		return (List<KaiIdoDto>) listKaiIdoDto;
	}
}
