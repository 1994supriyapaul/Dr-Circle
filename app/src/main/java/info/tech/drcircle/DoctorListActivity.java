package info.tech.drcircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import info.tech.drcircle.adapter.BannerSliderAdapter;
import info.tech.drcircle.adapter.DoctorAdapter;
import info.tech.drcircle.adapter.DoctorDetailsAdapter;
import info.tech.drcircle.common.AppSingleton;
import me.relex.circleindicator.CircleIndicator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static info.tech.drcircle.common.Config.DOCTOR_LIST;
import static info.tech.drcircle.common.Config.HOME_DETAILS;

public class DoctorListActivity extends AppCompatActivity {
    ArrayList personNames1 = new ArrayList<>(Arrays.asList("SATYANARAYAN KATHA", "BHUMI PUJA", "DURGA PUJA", "GANPATI PUJA"));
    //ArrayList personImages1 = new ArrayList<>(Arrays.asList(R.drawable.service_img1, R.drawable.service_img2, R.drawable.service_img3, R.drawable.service_img4));
    RecyclerView recyclerView2;
    DoctorAdapter doctorAdapter;
    RelativeLayout back_button,button_login;
    ProgressDialog progressDialog;
    EditText search_text;
    private ArrayList<String> doc_id = new ArrayList<String>();
    private ArrayList<String>  doctor_name = new ArrayList<String>();
    private ArrayList<String>  department_name = new ArrayList<String>();
    private ArrayList<String> place_id = new ArrayList<String>();
    private ArrayList<String>  timming = new ArrayList<String>();
    private ArrayList<String>  contact_number = new ArrayList<String>();
    private ArrayList<String>  contact_number2 = new ArrayList<String>();
    private ArrayList<String>  doctor_image = new ArrayList<String>();
    private ArrayList<String>  video_link = new ArrayList<String>();
    private ArrayList<String>  doctor_day = new ArrayList<String>();
    private ArrayList<String>  hospital_name = new ArrayList<String>();
    private ArrayList<String>  hospital_timming = new ArrayList<String>();
    private ArrayList<String>  hospital_day = new ArrayList<String>();

    private ArrayList<String> XMENArray = new ArrayList<String>();
    private ArrayList<String>  doc_ids = new ArrayList<String>();
    private ArrayList<String>  doctor_names = new ArrayList<String>();
    private ArrayList<String>  department_names = new ArrayList<String>();
    private ArrayList<String>  place_ids = new ArrayList<String>();
    private ArrayList<String>  timmings = new ArrayList<String>();
    private ArrayList<String>  contact_numbers = new ArrayList<String>();
    private ArrayList<String>  contact_numbers2 = new ArrayList<String>();
    private ArrayList<String>  doctor_images = new ArrayList<String>();
    private ArrayList<String>  video_links = new ArrayList<String>();
    private ArrayList<String>  doctor_days = new ArrayList<String>();
    private ArrayList<String>  hospital_names = new ArrayList<String>();
    private ArrayList<String>  hospital_timmings = new ArrayList<String>();
    private ArrayList<String>  hospital_days = new ArrayList<String>();

    private ArrayList<String>   id = new ArrayList<String>();
    private ArrayList<String>   place_name = new ArrayList<String>();

    private ArrayList<String>   dept_id = new ArrayList<String>();
    private ArrayList<String>  department_name1 = new ArrayList<String>();

    Timer swipeTimer;
    private static ViewPager mPager;
    private static int currentPage = 0;

    Spinner location_text,department_text;
    String SEARCH_TEXT ="",SEARCH_TEXT1 ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView2.setNestedScrollingEnabled(true);
        location_text = findViewById(R.id.location_text);
        department_text = findViewById(R.id.department_text);
        back_button = findViewById(R.id.back_button);
        button_login = findViewById(R.id.button_login);
        search_text = findViewById(R.id.search_text);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent in = getIntent();
        SEARCH_TEXT = in.getStringExtra("SEARCH_TEXT");
        SEARCH_TEXT1 = in.getStringExtra("SEARCH_TEXT1");
        if(SEARCH_TEXT != null) {
            search_text.setText(SEARCH_TEXT);
        }
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showDialog();

                    doc_ids = new ArrayList<String>();
                    doctor_names = new ArrayList<String>();
                    department_names = new ArrayList<String>();
                    place_ids = new ArrayList<String>();
                    timmings = new ArrayList<String>();
                    contact_numbers = new ArrayList<String>();
                    contact_numbers2 = new ArrayList<String>();
                    doctor_images = new ArrayList<String>();
                    video_links = new ArrayList<String>();
                    doctor_days = new ArrayList<String>();
                    hospital_names = new ArrayList<String>();
                    hospital_timmings = new ArrayList<String>();
                    hospital_days = new ArrayList<String>();

                    if (!search_text.getText().toString().equals("")) {
                        for (int i = 0; i < doc_id.size(); i++) {
                            if (doctor_name.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || place_id.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || department_name.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || doc_id.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || contact_number.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || contact_number2.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || contact_number2.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || doctor_day.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()) || timming.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ")) {
                                doc_ids.add(doc_id.get(i));
                                doctor_names.add(doctor_name.get(i));
                                department_names.add(department_name.get(i));
                                place_ids.add(place_id.get(i));
                                timmings.add(timming.get(i));
                                contact_numbers.add(contact_number.get(i));
                                contact_numbers2.add(contact_number2.get(i));
                                doctor_images.add(doctor_image.get(i));
                                video_links.add(video_link.get(i));
                                doctor_days.add(doctor_day.get(i));
                                hospital_names.add(hospital_name.get(i));
                                hospital_timmings.add(hospital_timming.get(i));
                                hospital_days.add(hospital_day.get(i));
                            }
                        }
                        doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_ids, doctor_names, department_names, place_ids, timmings, contact_numbers,contact_numbers2, doctor_images, video_links, doctor_days, hospital_names, hospital_timmings, hospital_days);
                        recyclerView2.setAdapter(doctorAdapter);
                    }/*else  if(location_text.getSelectedItemPosition() != 0){
                   for(int i = 0; i < place_id.size(); i++){
                       if(place_id.get(i).equals(location_text.getSelectedItem().toString())){
                           doc_ids.add(doc_id.get(i));
                           doctor_names.add(doctor_name.get(i));
                           department_names.add(department_name.get(i));
                           place_ids.add(place_id.get(i));
                           timmings.add(timming.get(i));
                           contact_numbers.add(contact_number.get(i));
                           doctor_images.add(doctor_image.get(i));
                           video_links.add(video_link.get(i));
                           doctor_days.add(doctor_day.get(i));
                       }
                   }
                   doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_ids,doctor_names,department_names,place_ids,timmings,contact_numbers,doctor_images,video_links,doctor_days);
                   recyclerView2.setAdapter(doctorAdapter);
               }else  if(department_text.getSelectedItemPosition() != 0){
                   for(int i = 0; i < department_name.size(); i++){
                       if(department_name.get(i).equals(department_text.getSelectedItem().toString())){
                           doc_ids.add(doc_id.get(i));
                           doctor_names.add(doctor_name.get(i));
                           department_names.add(department_name.get(i));
                           place_ids.add(place_id.get(i));
                           timmings.add(timming.get(i));
                           contact_numbers.add(contact_number.get(i));
                           doctor_images.add(doctor_image.get(i));
                           video_links.add(video_link.get(i));
                           doctor_days.add(doctor_day.get(i));
                       }
                   }
                   doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_ids,doctor_names,department_names,place_ids,timmings,contact_numbers,doctor_images,video_links,doctor_days);
                   recyclerView2.setAdapter(doctorAdapter);
               }*/ else if (search_text.getText().toString().equals("")) {
                        doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_id, doctor_name, department_name, place_id, timming, contact_number, contact_number2, doctor_image, video_link, doctor_day, hospital_name, hospital_timming, hospital_day);
                        recyclerView2.setAdapter(doctorAdapter);
                    }

                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            hideDialog();
                        }
                    };

                    Handler h = new Handler();
                    // The Runnable will be executed after the given delay time
                    h.postDelayed(r, 3000);

                }catch(Exception e){
                    //
                }
            }
        });
       // GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2); // you can change grid columns to 3 or more
       // recyclerView2.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        homeList();
        doctorList();
    }

    public void doctorList(){
        String cancel_req_tag = "login";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, DOCTOR_LIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("report", "Register Response: " + response.toString());
                hideDialog();
                if (!response.toString().equals("null")) {
                    //               recyclerView.setVisibility(View.VISIBLE);
                    try {
                        //Toast.makeText(getApplicationContext(),
                        //      mess, Toast.LENGTH_LONG).show();
                        boolean error = new JSONObject(response).getBoolean("error");
                        doc_id = new ArrayList<String>();
                        doctor_name = new ArrayList<String>();
                        department_name = new ArrayList<String>();
                        place_id = new ArrayList<String>();
                        timming = new ArrayList<String>();
                        contact_number = new ArrayList<String>();
                        contact_number2 = new ArrayList<String>();
                        doctor_image = new ArrayList<String>();
                        video_link = new ArrayList<String>();
                        doctor_day = new ArrayList<String>();
                        hospital_name = new ArrayList<String>();
                        hospital_timming = new ArrayList<String>();
                        hospital_day = new ArrayList<String>();

                        id = new ArrayList<String>();
                        place_name = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");

                            JSONArray sub_list = jsonObject.getJSONArray("doctor_list");
                            for (int i = 0; i< sub_list.length() ; i ++ ){
                                doc_id.add(sub_list.getJSONObject(i).getString("doctor_description")+" ");
                                doctor_name.add(sub_list.getJSONObject(i).getString("doctor_name")+" ");
                                department_name.add(sub_list.getJSONObject(i).getString("department_id"));
                                place_id.add(sub_list.getJSONObject(i).getString("place_id")+" ");
                                timming.add(sub_list.getJSONObject(i).getString("timming")+" ");
                                contact_number.add(sub_list.getJSONObject(i).getString("contact_number")+" ");
                                contact_number2.add(sub_list.getJSONObject(i).getString("contact_number2")+" ");
                                doctor_image.add(sub_list.getJSONObject(i).getString("doctor_image"));
                                video_link.add(sub_list.getJSONObject(i).getString("video_link"));
                                doctor_day.add(sub_list.getJSONObject(i).getString("doctor_day"));
                                hospital_name.add(sub_list.getJSONObject(i).getString("hospital_name"));
                                hospital_timming.add(sub_list.getJSONObject(i).getString("hospital_timming"));
                                hospital_day.add(sub_list.getJSONObject(i).getString("hospital_day"));
                            }
                            doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_id,doctor_name,department_name,place_id,timming,contact_number,contact_number2,doctor_image,video_link,doctor_day,hospital_name,hospital_timming,hospital_day);
                            recyclerView2.setAdapter(doctorAdapter);

//                            id.add("");
//                            place_name.add("Select location");
//                            JSONArray sub_list1 = jsonObject.getJSONArray("place_list");
//                            for (int i = 0; i< sub_list1.length() ; i ++ ){
//                                id.add(sub_list1.getJSONObject(i).getString("id"));
//                                place_name.add(sub_list1.getJSONObject(i).getString("place_name"));
//                            }
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoctorListActivity.this, android.R.layout.simple_spinner_dropdown_item, place_name);
//                            location_text.setAdapter(adapter);

                            if(SEARCH_TEXT != null){
                                search_text.setText(SEARCH_TEXT);

                                doc_ids = new ArrayList<String>();
                                doctor_names = new ArrayList<String>();
                                department_names = new ArrayList<String>();
                                place_ids = new ArrayList<String>();
                                timmings = new ArrayList<String>();
                                contact_numbers = new ArrayList<String>();
                                contact_numbers2 = new ArrayList<String>();
                                doctor_images = new ArrayList<String>();
                                video_links = new ArrayList<String>();
                                doctor_days = new ArrayList<String>();
// hospital_day.get(i).toLowerCase().startsWith(search_text.getText().toString().toLowerCase()) || hospital_timming.get(i).toLowerCase().startsWith(search_text.getText().toString().toLowerCase()) || hospital_name.get(i).toLowerCase().startsWith(search_text.getText().toString().toLowerCase()) ||
                                if (!search_text.getText().toString().equals("")) {
                                    for (int i = 0; i < doc_id.size(); i++) {
                                        if (doctor_name.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || place_id.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || department_name.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || doc_id.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || contact_number.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || contact_number2.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || doctor_day.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()) || timming.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ")) {
                                            doc_ids.add(doc_id.get(i));
                                            doctor_names.add(doctor_name.get(i));
                                            department_names.add(department_name.get(i));
                                            place_ids.add(place_id.get(i));
                                            timmings.add(timming.get(i));
                                            contact_numbers.add(contact_number.get(i));
                                            contact_numbers2.add(contact_number2.get(i));
                                            doctor_images.add(doctor_image.get(i));
                                            video_links.add(video_link.get(i));
                                            doctor_days.add(doctor_day.get(i));
                                            hospital_names.add(hospital_name.get(i));
                                            hospital_timmings.add(hospital_timming.get(i));
                                            hospital_days.add(hospital_day.get(i));
                                        }
                                    }
                                    doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_ids, doctor_names, department_names, place_ids, timmings, contact_numbers,contact_numbers2, doctor_images, video_links, doctor_days, hospital_names, hospital_timmings, hospital_days);
                                    recyclerView2.setAdapter(doctorAdapter);
                                }

                            }


                            if(SEARCH_TEXT1 != null){

                                doc_ids = new ArrayList<String>();
                                doctor_names = new ArrayList<String>();
                                department_names = new ArrayList<String>();
                                place_ids = new ArrayList<String>();
                                timmings = new ArrayList<String>();
                                contact_numbers = new ArrayList<String>();
                                contact_numbers2 = new ArrayList<String>();
                                doctor_images = new ArrayList<String>();
                                video_links = new ArrayList<String>();
                                doctor_days = new ArrayList<String>();

                                for(int i = 0; i < department_name.size(); i++){
                                    if(department_name.get(i).equals(SEARCH_TEXT1+" ")){
                                        doc_ids.add(doc_id.get(i));
                                        doctor_names.add(doctor_name.get(i));
                                        department_names.add(department_name.get(i));
                                        place_ids.add(place_id.get(i));
                                        timmings.add(timming.get(i));
                                        contact_numbers.add(contact_number.get(i));
                                        contact_numbers2.add(contact_number2.get(i));
                                        doctor_images.add(doctor_image.get(i));
                                        video_links.add(video_link.get(i));
                                        doctor_days.add(doctor_day.get(i));
                                        hospital_names.add(hospital_name.get(i));
                                        hospital_timmings.add(hospital_timming.get(i));
                                        hospital_days.add(hospital_day.get(i));
                                    }
                                }
                                doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doc_ids, doctor_names, department_names, place_ids, timmings, contact_numbers,contact_numbers2, doctor_images, video_links, doctor_days, hospital_names, hospital_timmings, hospital_days);
                                recyclerView2.setAdapter(doctorAdapter);

                            }




                        }else{
                            String mess = new JSONObject(response).getString("message");
                            //Toast.makeText(getApplicationContext(),
                            //      mess, Toast.LENGTH_LONG).show();
                            //                     recyclerView.setVisibility(View.GONE);


                            hideDialog();
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }else{
                    //           recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                SharedPreferences userPrefs = getSharedPreferences("prefName", MODE_PRIVATE);
                String userId = userPrefs.getString("USER_ID_KEY", "");
                Map<String, String> params = new HashMap<String, String>();
                //params.put("driver_id",userId);
                // params.put("staffUID",userId);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);

    }

  public void homeList() {
        String cancel_req_tag = "login";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, HOME_DETAILS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("report", "Register Response: " + response.toString());
                hideDialog();
                if (!response.toString().equals("null")) {
                    //               recyclerView.setVisibility(View.VISIBLE);
                    try {
                        //Toast.makeText(getApplicationContext(),
                        //      mess, Toast.LENGTH_LONG).show();
                        boolean error = new JSONObject(response).getBoolean("error");

                        XMENArray = new ArrayList<String>();
                        //dept_id = new ArrayList<String>();
                       // department_name1 = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");

                            JSONArray adsense_list = jsonObject.getJSONArray("adsense_list");
                            for (int i = 0; i< adsense_list.length() ; i ++ ){
                                XMENArray.add(adsense_list.getJSONObject(i).getString("adsense_image"));
                            }
                            init();

//                            dept_id.add("");
//                            department_name1.add("Select department");
//                            JSONArray sub_list1 = jsonObject.getJSONArray("department_list");
//                            for (int i = 0; i< sub_list1.length() ; i ++ ){
//                                dept_id.add(sub_list1.getJSONObject(i).getString("id"));
//                                department_name1.add(sub_list1.getJSONObject(i).getString("department_name"));
//                            }
//                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(DoctorListActivity.this, android.R.layout.simple_spinner_dropdown_item, department_name1);
//                            department_text.setAdapter(adapter1);

                        }else{
                            String mess = new JSONObject(response).getString("message");
                            //Toast.makeText(getApplicationContext(),
                            //      mess, Toast.LENGTH_LONG).show();
                            //                     recyclerView.setVisibility(View.GONE);


                            hideDialog();
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }else{
                    //           recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                SharedPreferences userPrefs = getSharedPreferences("prefName", MODE_PRIVATE);
                String userId = userPrefs.getString("USER_ID_KEY", "");
                Map<String, String> params = new HashMap<String, String>();
                //params.put("driver_id",userId);
                // params.put("staffUID",userId);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);

    }


    private void init(){
        // for(int i=0;i<XMENArray.length;i++)
        //XMENArray.add(XMEN[i]);
        if(swipeTimer == null){
            mPager = (ViewPager) findViewById(R.id.slider_pager);
            mPager.setAdapter(new BannerSliderAdapter(DoctorListActivity.this, XMENArray));
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.slider_indicator);
            indicator.setViewPager(mPager);
            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable(){
                public void run() {
                    if (currentPage == XMENArray.size()) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);
        }
    }

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}