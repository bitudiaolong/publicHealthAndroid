package com.gc.basicpublicdoctor.orc;

import android.app.Activity;
import android.content.Intent;


import java.io.File;

import tv.gagc.basecameraui.style1.camera.CameraActivity;


public class ORCOnLine {

    private boolean hasGotToken = false;
    private String token = "";

    private String TAG = "ORCOnLine";


    public static final int REQUEST_CODE_PICK_IMAGE_FRONT = 8180;
    public static final int REQUEST_CODE_PICK_IMAGE_BACK = 8181;
    public static final int REQUEST_CODE_CAMERA = 8182;


    public static final String ID_CARD_SIDE_FRONT = "front";
    public static final String ID_CARD_SIDE_BACK = "back";

    public interface ORCInterface {
        void onSuccess(String msg);

        void onError(String msg);
    }

    private static ORCOnLine orcOnLine;

    public static ORCOnLine newInstance() {
        if (orcOnLine == null) {
            orcOnLine = new ORCOnLine();
        }
        return orcOnLine;
    }

//    public boolean checkTokenStatus(Context context) {
//        if (hasGotToken == false) {
//            Toast.makeText(context, "token未获取成功", Toast.LENGTH_SHORT).show();
//        }
//        return hasGotToken;
//    }
//
//    public void initAccessTokenWithAKSK(Context applicationContext) {
//        initAccessTokenWithAKSK(applicationContext, null);
//    }
//
//    public void initAccessTokenWithAKSK(Context applicationContext, final ORCInterface orcInterface) {
//        if (hasGotToken == false) {
//            OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
//                @Override
//                public void onResult(AccessToken accessToken) {
//                    token = accessToken.getAccessToken();
//                    hasGotToken = true;
//                    Log.e(TAG, "onResult: " + token);
//                    if (orcInterface != null) {
//                        orcInterface.onSuccess(token);
//                    }
//                }
//
//                @Override
//                public void onError(OCRError ocrError) {
//                    Log.e(TAG, "onError: " + ocrError.getMessage());
//                    if (orcInterface != null) {
//                        orcInterface.onError(ocrError.getMessage());
//                    }
//                }
//            }, applicationContext, Cons.getOcrApikey(), Cons.getOcrSecretkey());
//        } else {
//            if (orcInterface != null) {
//                orcInterface.onSuccess(token);
//            }
//        }
//    }

    /**
     * 自动扫描
     *
     * @param activity
     */
    public void IDCardScan(Activity activity) {
        File fileUri = FileUtil.getSaveEnvFile(); //FileUtil.getSaveFile(activity.getApplicationContext());
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, fileUri.getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 手动点击
     *
     * @param activity
     */
    public void IDCardTouch(Activity activity) {

    }

}
