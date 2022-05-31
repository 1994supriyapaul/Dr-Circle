package info.tech.drcircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.tech.drcircle.adapter.DoctorAdapter;
import info.tech.drcircle.adapter.DoctorDetailsAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class HospitalDoctorActivity extends AppCompatActivity {
    RecyclerView recyclerView2;
    DoctorDetailsAdapter doctorAdapter;
    RelativeLayout back_button,button_login;
    ProgressDialog progressDialog;

    private ArrayList<String> doc_id = new ArrayList<String>();
    private ArrayList<String>  doctor_name = new ArrayList<String>();
    private ArrayList<String>  department_name = new ArrayList<String>();
    private ArrayList<String> place_id = new ArrayList<String>();
    private ArrayList<String>  timming = new ArrayList<String>();
    private ArrayList<String>  contact_number = new ArrayList<String>();
    private ArrayList<String>  doctor_image = new ArrayList<String>();
    private ArrayList<String>  video_link = new ArrayList<String>();
    private ArrayList<String>  doctor_day = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_doctor);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent in = getIntent();
        String DOCTOR_NAME = in.getStringExtra("DOCTOR_NAME");
        String DEPT_ID = in.getStringExtra("DEPT_ID");
        String TIMMING = in.getStringExtra("TIMMING");
        String DAY = in.getStringExtra("DAY");
        String TYPE = in.getStringExtra("TYPE");

        final String[] DOCTOR_NAME1 = DOCTOR_NAME.split("###");
        final String[] DEPT_ID1 = DEPT_ID.split("###");
        final String[] TIMMING1 = TIMMING.split("###");
        final String[] DAY1 = DAY.split("###");

        doc_id = new ArrayList<>(Arrays.asList(DOCTOR_NAME1));
        doctor_name = new ArrayList<>(Arrays.asList(DOCTOR_NAME1));
        department_name = new ArrayList<>(Arrays.asList(DEPT_ID1));
        place_id = new ArrayList<>(Arrays.asList(DEPT_ID1));
        timming = new ArrayList<>(Arrays.asList(TIMMING1));
        contact_number = new ArrayList<>(Arrays.asList(DAY1));
        doctor_image = new ArrayList<>(Arrays.asList(DAY1));
        video_link = new ArrayList<>(Arrays.asList(DAY1));
        doctor_day = new ArrayList<>(Arrays.asList(DAY1));

        doctorAdapter = new DoctorDetailsAdapter(HospitalDoctorActivity.this, doc_id,doctor_name,department_name,place_id,timming,contact_number,doctor_image,video_link,doctor_day,TYPE);
        recyclerView2.setAdapter(doctorAdapter);

    }
}