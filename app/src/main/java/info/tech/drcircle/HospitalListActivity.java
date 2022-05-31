package info.tech.drcircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import info.tech.drcircle.adapter.AmbulanceAdapter;
import info.tech.drcircle.adapter.BannerSliderAdapter;
import info.tech.drcircle.adapter.DoctorAdapter;
import info.tech.drcircle.adapter.HospitalAdapter;
import info.tech.drcircle.adapter.PathLabAdapter;
import info.tech.drcircle.common.AppSingleton;
import me.relex.circleindicator.CircleIndicator;

import android.app.ProgressDialog;
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
import static info.tech.drcircle.common.Config.HOSPITAL_LIST;

public class HospitalListActivity extends AppCompatActivity {
    ArrayList personNames1 = new ArrayList<>(Arrays.asList("SATYANARAYAN KATHA", "BHUMI PUJA", "DURGA PUJA", "GANPATI PUJA"));
    //ArrayList personImages1 = new ArrayList<>(Arrays.asList(R.drawable.service_img1, R.drawable.service_img2, R.drawable.service_img3, R.drawable.service_img4));
    RecyclerView recyclerView2;
    HospitalAdapter hospitalAdapter;
    RelativeLayout back_button,button_login;
    ProgressDialog progressDialog;
    private ArrayList<String>  hospital_id = new ArrayList<String>();
    private ArrayList<String>  hospital_name = new ArrayList<String>();
    private ArrayList<String>  place = new ArrayList<String>();
    private ArrayList<String> contact_number = new ArrayList<String>();
    private ArrayList<String> contact_number2 = new ArrayList<String>();
    private ArrayList<String> hospital_image = new ArrayList<String>();
    private ArrayList<String>  hospital_video = new ArrayList<String>();
    private ArrayList<String>  doctor_name = new ArrayList<String>();
    private ArrayList<String>  department_id = new ArrayList<String>();
    private ArrayList<String>  timming = new ArrayList<String>();
    private ArrayList<String>  doctor_day = new ArrayList<String>();

    private ArrayList<String>  hospital_ids = new ArrayList<String>();
    private ArrayList<String>  hospital_names = new ArrayList<String>();
    private ArrayList<String>  places = new ArrayList<String>();
    private ArrayList<String>  contact_numbers = new ArrayList<String>();
    private ArrayList<String>  contact_numbers2 = new ArrayList<String>();
    private ArrayList<String>  hospital_images = new ArrayList<String>();
    private ArrayList<String>   hospital_videos = new ArrayList<String>();
    private ArrayList<String>  doctor_names = new ArrayList<String>();
    private ArrayList<String>  department_ids = new ArrayList<String>();
    private ArrayList<String>  timmings = new ArrayList<String>();
    private ArrayList<String>  doctor_days = new ArrayList<String>();

    private ArrayList<String>   id = new ArrayList<String>();
    private ArrayList<String>   place_name = new ArrayList<String>();
    EditText search_text;
    Spinner location_text;

    Timer swipeTimer;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        search_text = findViewById(R.id.search_text);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView2.setNestedScrollingEnabled(true);
        location_text = findViewById(R.id.location_text);
        back_button = findViewById(R.id.back_button);
        button_login = findViewById(R.id.button_login);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                hospital_ids = new ArrayList<String>();
                hospital_names = new ArrayList<String>();
                places = new ArrayList<String>();
                contact_numbers = new ArrayList<String>();
                contact_numbers2 = new ArrayList<String>();
                hospital_images = new ArrayList<String>();
                hospital_videos = new ArrayList<String>();

                doctor_names = new ArrayList<String>();
                department_ids = new ArrayList<String>();
               timmings = new ArrayList<String>();
                doctor_days = new ArrayList<String>();
                if(!search_text.getText().toString().equals("")){
                    for(int i = 0; i < place.size(); i++){
                        if(hospital_name.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || place.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") ||  contact_number.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ")||  contact_number2.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ")){
                            hospital_ids.add(hospital_id.get(i));
                            hospital_names.add(hospital_name.get(i));
                            places.add(place.get(i));
                            contact_numbers.add(contact_number.get(i));
                            contact_numbers2.add(contact_number2.get(i));
                            hospital_images.add(hospital_image.get(i));
                            hospital_videos.add(hospital_video.get(i));
                            doctor_names.add(doctor_name.get(i));
                            department_ids.add(department_id.get(i));
                            timmings.add(timming.get(i));
                            doctor_days.add(doctor_day.get(i));
                        }
                    }
                    hospitalAdapter = new HospitalAdapter(HospitalListActivity.this, hospital_ids,hospital_names,places,contact_numbers,contact_numbers2,hospital_images,hospital_videos,doctor_names,department_ids,timmings,doctor_days);
                    recyclerView2.setAdapter(hospitalAdapter);
                }else if(search_text.getText().toString().equals("")){
                    hospitalAdapter = new HospitalAdapter(HospitalListActivity.this, hospital_id,hospital_name,place,contact_number,contact_number2,hospital_image,hospital_video,doctor_name,department_id,timming,doctor_day);
                    recyclerView2.setAdapter(hospitalAdapter);
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
            }
        });
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2); // you can change grid columns to 3 or more
        // recyclerView2.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

       homeList();
        hospitalList();
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
//                        dept_id = new ArrayList<String>();
//                        department_name1 = new ArrayList<String>();
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
            mPager.setAdapter(new BannerSliderAdapter(HospitalListActivity.this, XMENArray));
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

    public void hospitalList() {
        String cancel_req_tag = "login";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, HOSPITAL_LIST, new Response.Listener<String>() {

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
                        hospital_id = new ArrayList<String>();
                        hospital_name = new ArrayList<String>();
                        place = new ArrayList<String>();
                        contact_number = new ArrayList<String>();
                        contact_number2 = new ArrayList<String>();
                        hospital_image = new ArrayList<String>();
                        hospital_video = new ArrayList<String>();

                        doctor_name = new ArrayList<String>();
                        department_id = new ArrayList<String>();
                        timming = new ArrayList<String>();
                        doctor_day = new ArrayList<String>();

                        id = new ArrayList<String>();
                        place_name = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");


                            JSONArray sub_list = jsonObject.getJSONArray("hospital_list");
                            for (int i = 0; i< sub_list.length() ; i ++ ){
                                hospital_id.add(sub_list.getJSONObject(i).getString("hospital_id"));
                                hospital_name.add(sub_list.getJSONObject(i).getString("hospital_name")+" ");
                                place.add(sub_list.getJSONObject(i).getString("place")+" ");
                                contact_number.add(sub_list.getJSONObject(i).getString("contact_number")+" ");
                                contact_number2.add(sub_list.getJSONObject(i).getString("contact_number2")+" ");
                                hospital_image.add(sub_list.getJSONObject(i).getString("hospital_image"));
                                hospital_video.add(sub_list.getJSONObject(i).getString("hospital_video")+" ");
                                doctor_name.add(sub_list.getJSONObject(i).getString("doctor_name"));
                                department_id.add(sub_list.getJSONObject(i).getString("department_id"));
                                timming.add(sub_list.getJSONObject(i).getString("timming"));
                                doctor_day.add(sub_list.getJSONObject(i).getString("doctor_day"));
                              }
                            hospitalAdapter = new HospitalAdapter(HospitalListActivity.this, hospital_id,hospital_name,place,contact_number,contact_number2,hospital_image,hospital_video,doctor_name,department_id,timming,doctor_day);
                            recyclerView2.setAdapter(hospitalAdapter);

                          /*  id.add("");
                            place_name.add("Select location");
                            JSONArray sub_list1 = jsonObject.getJSONArray("place_list");
                            for (int i = 0; i< sub_list1.length() ; i ++ ){
                                id.add(sub_list1.getJSONObject(i).getString("id"));
                                place_name.add(sub_list1.getJSONObject(i).getString("place_name"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(HospitalListActivity.this, android.R.layout.simple_spinner_dropdown_item, place_name);
                            location_text.setAdapter(adapter);*/

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

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}