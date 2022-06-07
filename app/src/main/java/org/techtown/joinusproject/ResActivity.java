package org.techtown.joinusproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ResActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        mQueue = Volley.newRequestQueue(this);
        TextView Name_And_Region = (TextView) findViewById(R.id.name_and_region);
        Button go_resv = (Button) findViewById(R.id.go_reservation);

        Intent intent = getIntent();
        String my_facility = intent.getStringExtra("fac_title");
        String my_p_string = intent.getStringExtra("fac_p");
        String my_p;

        if (Integer.parseInt(my_p_string) / 1000 == 1)
            my_p = "서울";
        else if (Integer.parseInt(my_p_string) / 1000 == 2)
            my_p = "경기";
        else
            my_p = "충청";

        Name_And_Region.setText(my_facility + "\n" + my_p);

        go_resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_intent;

                new_intent = new Intent(ResActivity.this, ResActivity2.class);
                new_intent.putExtra("fac_title", my_facility);
                new_intent.putExtra("fac_p", my_p_string);
                startActivity(new_intent);
            }
        });
    }
}
