package edu.zut.weatherforcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import edu.zut.weatherforcast.bean.DayWeatherBean;
import edu.zut.weatherforcast.bean.WeatherBean;
import edu.zut.weatherforcast.utils.NetworkUtil;
import edu.zut.weatherforcast.utils.WeatherImgUtil;

public class MainActivity extends AppCompatActivity {

    TextView tvCity,tvTime,tvWeather,tvWeek,tvTem,tvTemLowHigh,tvWin,tvAir;
    ImageView ivWeather;//天气图标

    private DayWeatherBean dayWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = (TextView)findViewById(R.id.tv_city);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvWeather = (TextView)findViewById(R.id.tv_weather);
        tvWeek = (TextView)findViewById(R.id.tv_week);
        tvTem = (TextView)findViewById(R.id.tv_tem);
        tvTemLowHigh = (TextView)findViewById(R.id.tv_tem_low_high);
        tvWin = (TextView)findViewById(R.id.tv_win);
        tvAir = (TextView)findViewById(R.id.tv_air);

        ivWeather=(ImageView)findViewById(R.id.iv_weather);

        getWeather();

    }


    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("Main", ">>>>>>主线程收到了天气数据>>>" + weather);
                if (TextUtils.isEmpty(weather)) {
                    Toast.makeText(MainActivity.this, "天气数据为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                if (weatherBean != null) {
                    Log.d("Main", ">>>>>>解析后的数据>>>" + weatherBean.toString());
                }

                tvCity.setText(weatherBean.getCity());
                tvTime.setText(weatherBean.getUpdate_time());
                /**
                 * 当天天气
                 */
                dayWeather = weatherBean.getData().get(0);
                tvWeather.setText(dayWeather.getWea());
                tvTem.setText(dayWeather.getTem());
                tvTemLowHigh.setText(dayWeather.getTem2()+"/"+dayWeather.getTem1());
                tvWeek.setText(dayWeather.getWeek());
                tvWin.setText(dayWeather.getWin()[0]+dayWeather.getWin_speed());
                tvAir.setText("空气:"+dayWeather.getAir()+" | "+dayWeather.getAir_level()+"\n"+dayWeather.getAir_tips());
                ivWeather.setImageResource(WeatherImgUtil.getImgResOfWeather(dayWeather.getWea_img()));

            }

        }
    };

    private void getWeather() {
        // 开启子线程，请求网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 请求网络
                String weatherJson = NetworkUtil.getWeather();
                // 使用handler将数据传递给主线程
                Message message = Message.obtain();
                message.what = 0;
                message.obj = weatherJson;
                mHandler.sendMessage(message);

            }
        }).start();

    }

}