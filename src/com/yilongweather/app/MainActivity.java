package com.yilongweather.app;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yilongweather.R;
import com.yilongweather.app.surfaceview.weather.MySurfaceView;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        MySurfaceView animation = (MySurfaceView) findViewById(R.id.animation);
        animation.setCurrentWeatherStatus("下雨");
    }

    private void requestData() {
        String weatherUrl = "http://www.weather.com.cn/data/cityinfo/101010100.html";
        ByAsyncHttpClientGet(weatherUrl);

    }


    /**
     * 通过开源框架AsyncHttpClient的get请求处理
     */
    private void ByAsyncHttpClientGet(String url) {
        // 创建异步请求端对象
        AsyncHttpClient client = new AsyncHttpClient();

        // 发送get请求对象
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {

                Log.i("test", "response " + new String(bytes));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new String(bytes));
                    JSONObject weatherObject = jsonObject.getJSONObject("weatherinfo");
//                    msg.what = 1;
//                    msg.obj = weatherObject;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }



}
