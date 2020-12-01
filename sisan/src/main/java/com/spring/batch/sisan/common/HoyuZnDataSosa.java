package com.spring.batch.sisan.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HoyuZnDataSosa {
	
	public Integer getZnKey(String fndCd, String megCd, Integer kaiYmd, Integer siteiNo) {
		if (kaiYmd > 20201212) {
			return null;
		}
		return 3303;
	}
	
	public List<String> checkKaiZnJufuku(Integer znKey) {
		List<String> result = new ArrayList<>();
		if (znKey > 4000) {
			result.add("znKey");
		}
		return result;
	}
}
