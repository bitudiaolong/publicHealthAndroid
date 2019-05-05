package com.gc.basicpublicdoctor.cons;

public class Cons {
    /**
     * 身份识别 用到的key
     */
    public static final String APPID = "10132036";
    public static final String SECRETID = "AKID55uZ2cTEJF0d6U9hWAfyXyFLwQirixra";
    public static final String SECRETKEY = "tb8UGwmX8ZpRwGTVWMetzywjLqKlRB37";
    public static final String USERID = "3516471083";

    // 用户token
    public static String doctorToken;
    // 用户账号
    public static String account;
    // 用户密码
    public static String password;
    // 用户Uid标识
    public static String userUid;
    // 用户头像地址
    public static String userPicture;
    // 用户手机号
    public static String userPhone;
    // 用户姓名
    public static String userName;
    // 用户性别
    public static String userSex;
    // 服务机构签约卫生室
    public static String healthRoomName;
    // 医生团队成员姓名
    public static String teamMembers;
    // 医生团队名
    public static String docTeamName;
    //医生签约码字符串
    public static String doctorSignQRCode;
    // 设备号
    public static String SYS_DEVICEID;

    public static final int LOADING_DISMISS = 10;

    // 自升级接口
    public static final String UPDATE_URL = "http://package.gagctv.com/version/open/version/checkVersion?";
    public static final String APPKEYCODE = "459d377934c94f9b9d9a718d8e703e03";

    // base url
    public static String URL_BASE;
    public static String FILE_SERVER_URL;
    public static final String IMG_SUFFIX = ".jpg";
    public static String RongYunKey = "kj7swf8ok1m02";

    // 01.首页签约统计 √
    public static String SIGNING_STATISTICS() {
        return Cons.URL_BASE + "count/signingStatistics";
    }

    // 02.首页危机专线资讯分类
    public static String NEWS_CLASSES_LIST() {
        return Cons.URL_BASE + "appinformation/newsClassesList";
    }

    // 03.首页卫计专线 新闻资讯
    public static String NEWS_RECOMMAND_LIST() {
        return Cons.URL_BASE + "appinformation/newsRecommandList";
    }

    // 04.登录 √
    public static String LOGIN() {
        return Cons.URL_BASE + "sysaccount/login";
    }

    // 05.获取验证码 √
    public static String GET_CODE() {
        return Cons.URL_BASE + "sysaccount/getCode";
    }

    // 07.修改密码 √
    public static String MODIFY_PASSWORD() {
        return Cons.URL_BASE + "sysaccount/modifyPassword";
    }

    // 08.签约列表-个人 √
    public static String SIGN_PERSONAL_LIST() {
        return Cons.URL_BASE + "doctor/signPersonalList";
    }

    // 09.签约列表-家庭 √
    public static String SIGN_FAMILY_LIST() {
        return Cons.URL_BASE + "doctor/signFamilyList";
    }

    // 10.签约列表-家庭成员列表  确认签约页面
    public static String FAMILY_MEMBERS_LIST() {
        return Cons.URL_BASE + "doctor/familyMembersList";
    }

    // 11.签约状态/详情
    public static String SIGN_DETAIL() {
        return Cons.URL_BASE + "doctor/signDetail";
    }

    // 13.确认-提交签约
    public static String SUBMIT_SIGN() {
        return Cons.URL_BASE + "doctor/submitSign";
    }

    // 15.确认驳回
    public static String CONFIRM_REJECT() {
        return Cons.URL_BASE + "doctor/confirmReject";
    }

    // 16.确认提交
    public static String CONFIRM_SUBMIT() {
        return Cons.URL_BASE + "doctor/confirmSubmit";
    }

    // 17.随访计划统计 √
    public static String VISIT_PLAN() {
        return Cons.URL_BASE + "count/visitPlan";
    }

    // 18.随访计划人群列表
    public static String CROWD_LIST() {
        return Cons.URL_BASE + "count/crowdList";
    }

    // 19.获取当前登录账号医生融云token
    public static String GET_CHAT_TOKEN() {
        return Cons.URL_BASE + "doctor/getChatToken";
    }

    // 20.获取聊天目标列表
    public static String GET_USER_CHAT_LIST() {
        return Cons.URL_BASE + "doctor/getUserChatList";
    }

    // 21.获取当前聊天列表的头像和昵称
    public static String GET_CHAT_USER_INFO() {
        return Cons.URL_BASE + "doctor/getChatUserInfo";
    }

    public static String GET_IMAGE_UPLOAD() {
        return Cons.FILE_SERVER_URL + "file/uploadMore";
    }

    // 22.获取服务包
    public static String GET_SERVICE_PACKAGE_LIST() {
        return Cons.URL_BASE + "server/getServicePackageList";
    }
}
