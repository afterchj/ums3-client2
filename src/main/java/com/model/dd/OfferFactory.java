package com.model.dd;

import com.tpadsz.cic.coin.vo.CoinsAccountOffer;
import com.tpadsz.ctc.vo.ShelfOffer;
import com.tpadsz.ctc.vo.UserCommitOffer;
import com.tpadsz.ctc.vo.UserOffer;
import com.tpadsz.img.vo.ImageOffer;
import com.tpadsz.uic.user.api.vo.LoginedMobileValidateOffer;
import com.tpadsz.uic.user.api.vo.LoginedOffer;
import com.tpadsz.uic.user.api.vo.MobileValidateOffer;
import com.tpadsz.uic.user.api.vo.MobileVerifyOffer;
import com.tpadsz.uic.user.api.vo.MobileVerifyType;
import com.tpadsz.uic.user.api.vo.UserLoginOffer;
import com.tpadsz.vo.Offer;

public class OfferFactory {
	
	public static String APP_ID = "1";
	
	public static LoginedOffer generateLoginedOffer(String uid, String token){
		return new LoginedOffer(uid, token, APP_ID);
	}
	
	public static Offer generateOffer(){
		return new Offer(APP_ID);
	}
	
	public static MobileValidateOffer generateMobileValidateOffer(String mobile){
		return new MobileValidateOffer(mobile, APP_ID);
	}
	
	public static MobileVerifyOffer generateMobileVerifyOffer(String mobile, MobileVerifyType type, String code){
		return new MobileVerifyOffer(mobile, type, code, APP_ID);
	}
	
	public static ShelfOffer generateShelfOffer(String sid){
		return new ShelfOffer(APP_ID, sid);
	}
	
	public static UserLoginOffer generateUserLoginOffer(String loginName, String password){
		return new UserLoginOffer(loginName, password, APP_ID);
	}
	
	public static LoginedMobileValidateOffer generateLoginedMobileValidateOffer(String mobile, String code, String uid, String token){
		return new LoginedMobileValidateOffer(mobile, code, uid, token, APP_ID);
	}
	
	public static ImageOffer generateImageOffer(String type, String name){
		return new ImageOffer(APP_ID, "fun", type, name);
	}
	
	public static UserCommitOffer generateUserCommitOffer(String tid, String uid, String token, String check){
		return new UserCommitOffer(APP_ID, tid, uid, token, check);
	}
	
	public static UserOffer generateUserOffer(String tid, String uid){
		return new UserOffer(APP_ID, tid, uid);
	}
	
	public static CoinsAccountOffer generateCoinsAccountOffer(String uid, String appid) {
		return new CoinsAccountOffer(appid, uid);
	}

}
