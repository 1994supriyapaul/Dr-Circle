package info.tech.drcircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import info.tech.drcircle.adapter.AmbulanceAdapter;
import info.tech.drcircle.adapter.BannerSliderAdapter;
import info.tech.drcircle.adapter.DoctorAdapter;
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

import static info.tech.drcircle.common.Config.HOME_DETAILS;

public class AmbulanceActivity extends AppCompatActivity {
    ArrayList personNames1 = new ArrayList<>(Arrays.asList("SATYANARAYAN KATHA", "BHUMI PUJA", "DURGA PUJA", "GANPATI PUJA"));
    //ArrayList personImages1 = new ArrayList<>(Arrays.asList(R.drawable.service_img1, R.drawable.service_img2, R.drawable.service_img3, R.drawable.service_img4));
    RecyclerView recyclerView2;
    AmbulanceAdapter ambulanceAdapter;
    RelativeLayout back_button,button_login;
    ProgressDialog progressDialog;
    private ArrayList<String>   ambulance_id = new ArrayList<String>();
    private ArrayList<String> ambulance_name = new ArrayList<String>();
    private ArrayList<String> ambulance_type = new ArrayList<String>();
    private ArrayList<String> place = new ArrayList<String>();
    private ArrayList<String> contact_number = new ArrayList<String>();
    private ArrayList<String> contact_number2 = new ArrayList<String>();
    private ArrayList<String> ambulance_image = new ArrayList<String>();

    private ArrayList<String>   ambulance_ids = new ArrayList<String>();
    private ArrayList<String> ambulance_names = new ArrayList<String>();
    private ArrayList<String> ambulance_types = new ArrayList<String>();
    private ArrayList<String> places = new ArrayList<String>();
    private ArrayList<String> contact_numbers = new ArrayList<String>();
    private ArrayList<String> contact_numbers2 = new ArrayList<String>();
    private ArrayList<String> ambulance_images = new ArrayList<String>();

    private ArrayList<String>   id = new ArrayList<String>();
    private ArrayList<String>   place_name = new ArrayList<String>();
    EditText search_text;
    Timer swipeTimer;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    Spinner location_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView2.setNestedScrollingEnabled(true);
        location_text = findViewById(R.id.location_text);
        back_button = findViewById(R.id.back_button);
        button_login = findViewById(R.id.button_login);
        search_text = findViewById(R.id.search_text);
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
                ambulance_ids = new ArrayList<String>();
                ambulance_names = new ArrayList<String>();
                ambulance_types = new ArrayList<String>();
                places = new ArrayList<String>();
                contact_numbers = new ArrayList<String>();
                contact_numbers2 = new ArrayList<String>();
                ambulance_images = new ArrayList<String>();

                if(!search_text.getText().toString().equals("")){
                    for(int i = 0; i < place.size(); i++){
                        if(ambulance_name.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || place.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") || ambulance_type.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") ||  contact_number.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ") ||  contact_number2.get(i).toLowerCase().contains(search_text.getText().toString().toLowerCase()+" ")){
                            ambulance_ids.add(ambulance_id.get(i));
                            ambulance_names.add(ambulance_name.get(i));
                            ambulance_types.add(ambulance_type.get(i));
                            places.add(place.get(i));
                            contact_numbers.add(contact_number.get(i));
                            contact_numbers2.add(contact_number2.get(i));
                            ambulance_images.add(ambulance_image.get(i));
                        }
                    }
                     ambulanceAdapter = new AmbulanceAdapter(AmbulanceActivity.this, ambulance_ids,ambulance_names,ambulance_types,places,contact_numbers,contact_numbers2,ambulance_images);
                     recyclerView2.setAdapter(ambulanceAdapter);
                }else if(search_text.getText().toString().equals("")){
                     ambulanceAdapter = new AmbulanceAdapter(AmbulanceActivity.this, ambulance_id,ambulance_name,ambulance_type,place,contact_number,contact_number2,ambulance_image);
                     recyclerView2.setAdapter(ambulanceAdapter);
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


        //homeList();
        ambulanceList();
    }

    /*public void homeList() {
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

//
//                        dept_id = new ArrayList<String>();
//                        department_name1 = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");



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

    }*/

    public void ambulanceList() {
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
                        ambulance_id = new ArrayList<String>();
                        ambulance_name = new ArrayList<String>();
                        ambulance_type = new ArrayList<String>();
                        place = new ArrayList<String>();
                        contact_number = new ArrayList<String>();
                        contact_number2 = new ArrayList<String>();
                        ambulance_image = new ArrayList<String>();
                        XMENArray = new ArrayList<String>();
                        id = new ArrayList<String>();
                        place_name = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");

                            JSONArray sub_list = jsonObject.getJSONArray("ambulance_list");
                            for (int i = 0; i< sub_list.length() ; i ++ ){
                                ambulance_id.add(sub_list.getJSONObject(i).getString("id"));
                                ambulance_name.add(sub_list.getJSONObject(i).getString("ambulance_name")+" ");
                                ambulance_type.add(sub_list.getJSONObject(i).getString("ambulance_type")+" ");
                                place.add(sub_list.getJSONObject(i).getString("place")+" ");
                                contact_number.add(sub_list.getJSONObject(i).getString("contact_number")+" ");
                                contact_number2.add(sub_list.getJSONObject(i).getString("contact_number2")+" ");
                                ambulance_image.add(sub_list.getJSONObject(i).getString("ambulance_image"));
                            }
                            ambulanceAdapter = new AmbulanceAdapter(AmbulanceActivity.this, ambulance_id,ambulance_name,ambulance_type,place,contact_number,contact_number2,ambulance_image);
                            recyclerView2.setAdapter(ambulanceAdapter);

                            JSONArray adsense_list = jsonObject.getJSONArray("adsense_list");
                            for (int i = 0; i< adsense_list.length() ; i ++ ){
                                XMENArray.add(adsense_list.getJSONObject(i).getString("adsense_image"));
                            }
                            init();
                           /* id.add("");
                            place_name.add("Select location");
                            JSONArray sub_list1 = jsonObject.getJSONArray("ambulance_place_list");
                            for (int i = 0; i< sub_list1.length() ; i ++ ){
                                id.add(sub_list1.getJSONObject(i).getString("id"));
                                place_name.add(sub_list1.getJSONObject(i).getString("place_name"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AmbulanceActivity.this, android.R.layout.simple_spinner_dropdown_item, place_name);
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

    private void init(){
        // for(int i=0;i<XMENArray.length;i++)
        //XMENArray.add(XMEN[i]);
        if(swipeTimer == null){
            mPager = (ViewPager) findViewById(R.id.slider_pager);
            mPager.setAdapter(new BannerSliderAdapter(AmbulanceActivity.this, XMENArray));
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