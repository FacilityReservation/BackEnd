package org.techtown.joinusproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// 220531 추가
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResActivity2 extends AppCompatActivity {
    private RequestQueue mQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        mQueue = Volley.newRequestQueue(this);
        Button Res_btn = findViewById(R.id.res_btn);

        Res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resFacility();

                Intent new_intent;

                new_intent = new Intent(ResActivity2.this, ResEndActivity.class);
                startActivity(new_intent);
            }
        });
    }

    private void resFacility()
    {
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        // 달력 값 가져오기
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() == 1) { month = "0" + month; };
        String day = String.valueOf(calendar.get(Calendar.DATE));
        if (day.length() == 1) { day = "0" + day; };
        String yyyymmdd = year + month + day;

        String url;
        String response = "error!";

        EditText cont = findViewById(R.id.contents);

        // 토큰(쿠키) 가져오기
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String user_value= sf.getString("mem_id", "");
        String mem_user_p = sf.getString("mem_p", "");

        // 인텐트 값 가져오기
        Intent intent = getIntent();
        String fac_Title = intent.getStringExtra("fac_title");
        String Fac_P = intent.getStringExtra("fac_p");

        String inputTime = yyyymmdd + cont.getText().toString();

        url = "http://3.34.53.201/review";
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json; charset=utf-8");
        // 1붙이는 이유 : 데이터베이스 분리를 잘못함.
        // 1을 붙이면 예약 정보, 2를 붙이면 리뷰 정보로 접근함
        params.put("review_p", "1" + mem_user_p);
        params.put("review_item", fac_Title);
        params.put("review_id", user_value);
        params.put("review_con", cont.getText().toString());
        params.put("review_title", Fac_P);
        params.put("review_date", inputTime);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(getApplicationContext(), inputTime, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                // 1붙이는 이유 : 데이터베이스 분리를 잘못함.
                // 1을 붙이면 예약 정보, 2를 붙이면 리뷰 정보로 접근함
                params.put("review_p", "1" + mem_user_p);
                params.put("review_item", fac_Title);
                params.put("review_id", user_value);
                params.put("review_con", cont.getText().toString());
                params.put("review_title", Fac_P);
                params.put("review_date", inputTime);

                return params;
            }
        };

        mQueue.add(postRequest);
    }
}
