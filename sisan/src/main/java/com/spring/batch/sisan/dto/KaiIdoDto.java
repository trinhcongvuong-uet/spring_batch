package com.spring.batch.sisan.dto;

import com.spring.batch.sisan.entities.BsIdEntity;
import com.spring.batch.sisan.entities.ThNrEntity;

public class KaiIdoDto {
	private ThNrEntity thNrEntity;

	private BsIdEntity bsIdEntity;

	public ThNrEntity getThNrEntity() {
		return thNrEntity;
	}

	public void setThNrEntity(ThNrEntity thNrEntity) {
		this.thNrEntity = thNrEntity;
	}

	public BsIdEntity getBsIdEntity() {
		return bsIdEntity;
	}

	public void setBsIdEntity(BsIdEntity bsIdEntity) {
		this.bsIdEntity = bsIdEntity;
	}
}
