package com.spring.batch.sisan.common;

import org.springframework.stereotype.Component;

import com.spring.batch.sisan.entities.LoginUserInfo;

@Component
public class LoginUserInfoGet {
	public LoginUserInfo getLoginUserInfo() {
		LoginUserInfo loginUserInfo = new LoginUserInfo();
		loginUserInfo.setLoginUserKaishaCd("LUVINA");
		loginUserInfo.setLoginUserUserId("20200808");
		return loginUserInfo;
	}
}
