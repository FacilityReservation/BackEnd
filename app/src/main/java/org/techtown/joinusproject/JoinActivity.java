package org.techtown.joinusproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    private TextView textViewResult;
    private RequestQueue mQueue;
    String content="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button joinIn = findViewById(R.id.PostButton);

        mQueue = Volley.newRequestQueue(this);

        joinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPOST();
            }
        });
    }

    private void jsonPOST()
    {
        String url = "http://3.34.53.201/users";
        String response = "error!";

        EditText p = findViewById(R.id.m_p);
        EditText id = findViewById(R.id.m_id);
        EditText pw = findViewById(R.id.m_pw);
        EditText name = findViewById(R.id.m_name);
        EditText company = findViewById(R.id.m_company);
        EditText type = findViewById(R.id.m_type);
        TextView text = findViewById(R.id.getText);

        url = "http://3.34.53.201/users";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mem_p", p.getText().toString());
        params.put("mem_id", id.getText().toString());
        params.put("mem_pw", pw.getText().toString());
        params.put("mem_name", name.getText().toString());
        params.put("mem_company", company.getText().toString());
        params.put("mem_type", type.getText().toString());

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Gson gson = new Gson();
                UserInfo userInfo = new UserInfo(p.getText().toString(), id.getText().toString(),
                        pw.getText().toString(), name.getText().toString(),
                        company.getText().toString(), type.getText().toString());

                String json = gson.toJson(userInfo);
                text.append("\n" + json.toString() + "\n");

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("mem_p", userInfo.getP());
                params.put("mem_id", userInfo.getID());
                params.put("mem_pw", userInfo.getPW());
                params.put("mem_name", userInfo.getNAME());
                params.put("mem_company", userInfo.getCOMPANY());
                params.put("mem_type", userInfo.getTYPE());

                return params;
            }
        };

        mQueue.add(postRequest);
    }
}
