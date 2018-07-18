/*
package com.test;

import com.alibaba.fastjson.JSONObject;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.utils.convert.DBUtils;
import com.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by nannan.li on 2018/7/16.
 *//*

public class ThirdLoginTest {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
//        String qq = "qq_1234";
        String qq = "E92A8DF534D9DB20A994A7C3B2213097";
        String wx = "";
        String uid = "9f0045b0aaa343e49fa7d26c0d23d435";
//        String qq_nickname = "qq_nick1234";
        String qq_nickname = "凯凯王";
        String qq_image_url = "http://thirdqq.qlogo.cn/qqapp/100735153/E92A8DF534D9DB20A994A7C3B2213097/100";
        String wx_nickname = "";
        String wx_image_url = "";
        DBUtils db = DBUtils.getInstance();
        db.getConnection();
        List listUid = new ArrayList();
        listUid.add(uid);
        String sql = "SELECT " +
                " a.id,a.login_name,a.nickname,t.mobile,a.icon,t.birthyear, " +
                " t.birthmonth,t.birthday,t.prov,t.gender,a.serialno " +
                " FROM " +
                " f_app_user a, " +
                " f_tpad_user t " +
                " WHERE " +
                " a.tpad_id = t.id " +
                " AND a.id = ? and app_id=1 ";
        try {
            List<Map<String, Object>> mapList = db.executeQuery(sql, listUid);
            Map<String,Object> map = mapList.get(0);
            com.tpadsz.uic.user.api.vo.AppUser appUser = new com.tpadsz.uic.user
                    .api.vo.AppUser();
            TpadUser tpadUser = new TpadUser();
            appUser.setTpadUser(tpadUser);
            appUser.setId((String) map.get("id"));
            if (StringUtils.isNotBlank(map.get("icon").toString())) {
                appUser.setIcon((String) map.get("icon"));
            }
            if (StringUtils.isNotBlank(map.get("nickname").toString())) {
                appUser.setNickname((String) map.get("nickname"));
            }
            if (StringUtils.isNotBlank(map.get("birthday").toString())) {
                appUser.getTpadUser().setBirthday(Integer.valueOf(map.get("birthday").toString()) );
            }
            if (StringUtils.isNotBlank(map.get("birthmonth").toString())) {
                appUser.getTpadUser().setBirthmonth((Integer) map.get
                        ("birthmonth"));
            }
            if (StringUtils.isNotBlank(map.get("birthyear").toString())) {
                appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
            }
            if (StringUtils.isNotBlank(map.get("login_name").toString())) {
                appUser.setLoginName(map.get("login_name").toString());
            }
            if (StringUtils.isNotBlank(map.get("mobile").toString())) {
                appUser.getTpadUser().setMobile((String) map.get("mobile"));
            }

            if (StringUtils.isNotBlank(map.get("prov").toString())) {
                appUser.getTpadUser().setProv((Integer) map.get("prov"));
            }
            if (StringUtils.isNotBlank(map.get("gender").toString())) {
                appUser.getTpadUser().setGender((Integer) map.get("gender"));
            }
            if (StringUtils.isNotBlank(map.get("serialno").toString())) {
                appUser.setSerialno((String) map.get("serialno"));
            }

//            String sql2="SELECT * FROM f_app_user_third WHERE id =?";
//            List<Map<String, Object>> getUserThidInfoById =
//                    db.executeQuery(sql2, listUid);
//            Map<String,Object> mapThirdInfo = getUserThidInfoById.get(0);
//            if (getUserThidInfoById==null || getUserThidInfoById.isEmpty()){
                String sql3 = " replace INTO f_app_user_third (id, qq, " +
                        " qq_nickname, qq_image_url, wx, wx_nickname, " +
                        " wx_image_url) " +
                        " VALUES(?,?,?,?,?,?,?) ";
                List list = new ArrayList();
                list.add(uid);
                list.add(qq);
                list.add(qq_nickname);
                list.add(qq_image_url);
                list.add(wx);
                list.add(wx_nickname);
                list.add(wx_image_url);

                db.executeUpdate(sql3, list);
//            }else {
//                if (StringUtils.isNotBlank(qq)){
//                    String sql4 = "UPDATE f_app_user_third set " +
//                            " qq = ?," +
//                            " qq_nickname = ?, " +
//                            " qq_image_url = ? " +
//                            " WHERE id = ? ";
//                    List list = new ArrayList();
//                    list.add(qq);
//                    list.add(qq_nickname);
//                    list.add(qq_image_url);
//                    list.add(uid);
//                    db.executeUpdate(sql4,list);
//                }else if (StringUtils.isNotBlank(wx)){
//                    String sql5 = "UPDATE f_app_user_third set " +
//                            " wx = ?, " +
//                            " wx_nickname = ?, " +
//                            " wx_image_url = ? " +
//                            " WHERE id = ? ";
//                    List list = new ArrayList();
//                    list.add(wx);
//                    list.add(wx_nickname);
//                    list.add(wx_image_url);
//                    list.add(uid);
//                    boolean flag2 = db.executeUpdate(sql5, list);
//                    System.out.println("flag2: "+flag2);
//                }
//            }

            String sql6="SELECT * FROM f_app_user_third WHERE id =?";
            List<Map<String, Object>> getUserThidInfoById2 =
                    db.executeQuery(sql6, listUid);
            Map<String,Object> mapThirdInfo2 = getUserThidInfoById2.get(0);
            if (StringUtils.isNotBlank(qq)) {
                if (StringUtils.isBlank(appUser.getIcon()) &&
                        StringUtils.isNotBlank(mapThirdInfo2.get("qq_image_url").toString())) {

                    appUser.setIcon(mapThirdInfo2.get("qq_image_url").toString());

                }
                if (StringUtils.isBlank(appUser.getNickname()) &&
                        StringUtils.isNotBlank(mapThirdInfo2.get("qq_nickname").toString())) {
                    appUser.setNickname(mapThirdInfo2.get("qq_nickname")
                            .toString());
                }
            } else if (StringUtils.isBlank(appUser.getIcon()) && StringUtils.isNotBlank(wx)) {
                if (StringUtils.isNotBlank(mapThirdInfo2.get("wx_image_url").toString())) {
                    appUser.setIcon(mapThirdInfo2.get("wx_image_url").toString());
                }
                if (StringUtils.isBlank(appUser.getNickname()) &&
                        StringUtils.isNotBlank(mapThirdInfo2.get("wx_nickname").toString())) {
                    appUser.setNickname(mapThirdInfo2.get("wx_nickname").toString());
                }
            }

            UserVo vo = UserVo.convert(appUser);
            System.out.println(JSONObject.toJSON(vo));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.closeDB();
        }
    }
}
*/
