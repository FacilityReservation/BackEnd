package org.techtown.joinusproject;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class IdentifyActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        resIdentification();
    }

    private void resIdentification()
    {
        String url = "http://3.34.53.201/review";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // 토큰(쿠키) 가져오기
                            SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                            String user_value= sf.getString("mem_id", "");
                            String user_p = sf.getString("mem_p", "");
                            JSONArray jsonArray = response.getJSONArray("review");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject review = jsonArray.getJSONObject(i);

                                String reviewP = review.getString("review_p");
                                String reviewItem = review.getString("review_item");
                                String reviewMem = review.getString("review_mem");
                                String reviewTitle = review.getString("review_title");
                                String reviewCon = review.getString("review_con");
                                String reviewDate = review.getString("review_date");

                                if (user_value.equals(reviewMem) && reviewP.charAt(0) == '1')
                                {
                                    Button new_btn;

                                    LocalDate now = LocalDate.now();
                                    String nowTime
                                            = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

                                    if (Long.parseLong(nowTime) > Long.parseLong(reviewDate))
                                        deletRes(reviewP);

                                    new_btn = new Button(getApplicationContext());
                                    new_btn.setText("예약자 : " + review.getString("review_mem")
                                            + "    시설 이름 : " + review.getString("reviewTitle"));
                                    new_btn.setTextSize(15);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            // 실패시
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void deletRes(String reviewP)
    {
        String delurl = "http://3.34.53.201/review";
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String user_value = sf.getString("mem_id", "");

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, delurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), user_value, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("review_p", reviewP);
                return map;
            }
        };
        mQueue.add(delRequest);
    }
}
