package com.gc.basicpublicdoctor.tecent;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.gc.basicpublicdoctor.tecent.sign.YoutuSign;
import com.gc.basicpublicdoctor.tecent.util.BitMapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class TecentIdentification {

    private String appId;       // 创建应用时的应用id
    private String secretId;    // 创建应用时生成的密钥ID
    private String secretKey;   // 创建应用时生成的密钥KEY
    private String expired;     // 有效期 大于当前时间的秒值 UNIX Epoch 时间戳
    private String userId;      // 创建账号的userId 就是qq号

    private int EXPIRED_SECONDS = 2592000;  // 过期时间戳 30天

    private String baseUrl = "http://api.youtu.qq.com/youtu/ocrapi/idcardocr";

    public static final String CARD_TYPE_FRONT = "0";
    public static final String CARD_TYPE_BACK = "1";

    public interface IndentificationCallBack{
        void onSuccess(String result);
        void onFiled(String msg);
    }

    private static TecentIdentification tecentIdentification;
    public static TecentIdentification newInstance(String appId,String secretId,String secretKey,
                                                   String userId){
        if (tecentIdentification == null){
            tecentIdentification = new TecentIdentification(appId,secretId,secretKey,userId);
        }
        return tecentIdentification;
    }

    private TecentIdentification(String appId, String secretId, String secretKey, String userId){
        this.appId = appId;
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.userId = userId;
    }

    public void uploadIDCardByPath(String path,String card_type,IndentificationCallBack callBack){
        Bitmap bitmap = BitMapUtils.getBitmapFromSDCard(path);
        if (bitmap != null) {
            String bitmapStr = BitMapUtils.bitmapToBase64(bitmap);
            uploadIDCard("image", bitmapStr, card_type, callBack);
        }else{
            callBack.onFiled("bitmap为空");
        }
    }
    public void uploadIDCardByUrl(String imageUrl,String card_type,IndentificationCallBack callBack){
        uploadIDCard("url",imageUrl,card_type,callBack);
    }
    /**
     *
     * @param imageType 上传图片的类型  image：本地bitmap的base64二进制形式， url：网络图片url地址  默认为image
     * @param image     与imageType 对应的 base64 还是 网络url
     * @param card_type 0 正面  1 反面
     * @param callBack  回调
     */
    public void uploadIDCard(String imageType, String image, String card_type, final IndentificationCallBack callBack){
        StringBuffer mySign = new StringBuffer("");
        YoutuSign.appSign(appId,secretId,secretKey,System.currentTimeMillis() / 1000 + EXPIRED_SECONDS,
                userId,mySign);

        RequestParams params = new RequestParams(baseUrl);
        params.setAsJsonContent(true);
        params.addHeader("accept", "*/*");
        params.addHeader("Host", "api.youtu.qq.com");
        params.addHeader("user-agent", "youtu-java-sdk");
        params.addHeader("Authorization", mySign.toString());
        params.addHeader("Content-Type", "text/json");
        params.addParameter("card_type", Integer.valueOf(card_type));   // 0 正面 1 反面
        params.addBodyParameter(imageType, image);                       // image（二进制身份证图片） url（图片地址） 两者取其一
        params.addBodyParameter("app_id", appId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("onSuccess",result);
                if (TextUtils.isEmpty(result) == false) {
                    callBack.onSuccess(result);
                }else{
                    callBack.onFiled("查询返回为空");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("onError",ex.getMessage());
                callBack.onFiled(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("onCancelled", cex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }

}
