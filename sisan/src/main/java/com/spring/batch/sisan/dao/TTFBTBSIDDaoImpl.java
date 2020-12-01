package com.spring.batch.sisan.dao;

import org.springframework.stereotype.Component;

import com.spring.batch.sisan.dao.TTFBTBSIDIF;

@Component
public class TTFBTBSIDDaoImpl implements TTFBTBSIDIF {

	@Override
	public void write() {
		// nothing
		System.out.println("DAO excute");
	}

}
