package com.xdf.kb.alipaydemo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.xdf.kb.R;
import com.xdf.kb.alipaydemo.utils.OrderInfoUtil2_0;
import com.xdf.kb.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/1 0001.
 * 支付宝Demo
 */

@ContentView(R.layout.activity_alipay_demo_main)
public class AlipayDemoMainActivity extends BaseActivity {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016072900115009";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088102180941790";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "2016102400754147";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK1CCiTVjwRs5SWI\n" +
            "hCRf8EQBK4dcgkEKNeAO2nqNYfjo5pv9NR3/9F6WECogJwmiHS7CeD5NDlgNbTPs\n" +
            "P9ASLQ/UP/YsTEkFPDwEV4P9Ioq4BxYrOARr1D9j75W4Bq1hkIiuT5DZ3GsgGLl+\n" +
            "WjgBlhYI7Cyw/kJbOfNFLUSyNHVFAgMBAAECgYEAmAFqmOpssXtAh2+AT9GEIIXO\n" +
            "RNIazSlCHzOo5wCHxjO/r3e1VH6tTRbnU64l0cPTSqT/jdlZDlOpTXamfcyUju6e\n" +
            "9LnGrG+PfiQz5cP9W2bOgqe34hdMgsORTzO5WgvNIDZyhng5qQXRxfxyCji6MRT1\n" +
            "r2mT/Un3snYtD7aULHkCQQDeBIxzQRYv5uKUngTUJ99HOak3KmQSSrAn2G4qKSn3\n" +
            "w8V6EaPsUHlGSqHCUCVso37597elU4ODklXk8L/OuHJDAkEAx8boSA4i23pZe3g8\n" +
            "EGEn9m9eO6BgP683jyKMjhpuXc8YcPx2mXfiyVPwgi+IEQOt0EfP4qDJRRs9ZKe9\n" +
            "lg8V1wJAbrj+Aoy3L/SJk2yrI9Muuyq6hVSSy7yL1ZNOfaBB2dq8pYAerEAF+1RF\n" +
            "D89Amsl/DdLIt57Ku9dDFwYp4kKCWQJACllYgRsSXTJKXmYNHoAZ9N8yD36wXbwy\n" +
            "8ZxFkbr2yq9GwduiPTMDsZBCnkveDZuWLnr53vCbXJQbqxIxdQuVAwJAJ/oD5IbU\n" +
            "UtwijcGejWLOFjKrGNLL/CchmABq4HLd0Y8dRevMm90Iha/VmNd1oX3oJUIxTESW\n" +
            "J7BPl0QfoAq+Ug==";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(AlipayDemoMainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AlipayDemoMainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(AlipayDemoMainActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(AlipayDemoMainActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * 支付宝支付业务
     * 返回ali20247 错误的原因是没有签约APP支付功能
     *
     * @param v 被点击的视图
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(AlipayDemoMainActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v 被点击的视图
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, RSA_PRIVATE);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(AlipayDemoMainActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    @SuppressWarnings("unused")
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v 被点击的视图
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.taobao.com";
        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setLogo(getResources().getDrawable(R.drawable.msp_demo_title, getTheme()));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.alipay_demo)
    private void aliPay(View v) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        payV2(v);
    }

    @Event(R.id.ali_impower_demo)
    private void aliImpower(View v) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        authV2(v);
    }

    @Event(type = View.OnClickListener.class, value = R.id.ali_web_to_native_demo)
    private void aliWeb2Native(View v) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        h5Pay(v);
    }
}
