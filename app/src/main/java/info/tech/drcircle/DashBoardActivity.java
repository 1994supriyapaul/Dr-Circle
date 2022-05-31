package info.tech.drcircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import info.tech.drcircle.adapter.BannerSliderAdapter;
import info.tech.drcircle.adapter.CustomRecyclerViewAdapter;
import info.tech.drcircle.adapter.DoctorAdapter;
import info.tech.drcircle.adapter.VideoAdapter;
import info.tech.drcircle.common.AppSingleton;
import me.relex.circleindicator.CircleIndicator;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class DashBoardActivity extends AppCompatActivity {
    ArrayList personNames = new ArrayList<>(Arrays.asList("Booking", "Calendar", "Pujan Vidhi", "Review"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.dr_list, R.drawable.hospital, R.drawable.path_lab, R.drawable.ambulance));
    RecyclerView recyclerView;
    CustomRecyclerViewAdapter customRecyclerViewAdapter;
    ProgressDialog progressDialog;
  //  WebView web_view1;
    RelativeLayout button_login,button_all,button_login2,cart_button;
    EditText search_text;
    String video_link = "";
    Spinner department_text;
    Timer swipeTimer;
    private static ViewPager mPager;
    private static int currentPage = 0;

    Timer swipeTimer2;
    private static ViewPager mPager2;
    private static int currentPage2 = 0;

    private ArrayList<String> XMENArray = new ArrayList<String>();
    private ArrayList<String> XMENArray2 = new ArrayList<String>();

    private ArrayList<String>   dept_id = new ArrayList<String>();
    private ArrayList<String>  department_name1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        department_text = findViewById(R.id.department_text);
        button_login = findViewById(R.id.button_login);
        button_all = findViewById(R.id.button_all);
        button_login2 = findViewById(R.id.button_login2);
       // web_view1 = findViewById(R.id.web_view1);
        search_text = findViewById(R.id.search_text);
        cart_button = findViewById(R.id.cart_button);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2); // you can change grid columns to 3 or more
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(DashBoardActivity.this, personNames,personImages);
        recyclerView.setAdapter(customRecyclerViewAdapter); // set the Adapter to RecyclerView
        recyclerView.setNestedScrollingEnabled(true);

       // ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.location_slot, R.layout.spinner_item);
       // location_text.setAdapter(adapter);

      //  ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.department_slot, R.layout.spinner_item);
       // department_text.setAdapter(adapter2);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent in = new Intent(DashBoardActivity.this,ContactActivity.class);
                    startActivity(in);

            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_text.getText().toString().equals("")){
                    Toast.makeText(DashBoardActivity.this,"Type text.",Toast.LENGTH_LONG).show();
                }else{
                    Intent in = new Intent(DashBoardActivity.this,DoctorListActivity.class);
                    in.putExtra("SEARCH_TEXT",search_text.getText().toString());
                    startActivity(in);
                }
            }
        });

        button_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(department_text.getSelectedItemPosition() == 0){
                    Toast.makeText(DashBoardActivity.this,"Select department.",Toast.LENGTH_LONG).show();
                }else{
                    Intent in = new Intent(DashBoardActivity.this,DoctorListActivity.class);
                    in.putExtra("SEARCH_TEXT1",department_text.getSelectedItem().toString());
                    startActivity(in);
                }
            }
        });

        button_all.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!video_link.equals("")) {
                    String url = video_link;
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        homeList();
    }

    public void homeList() {
        String cancel_req_tag = "login";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, HOME_DETAILS, new Response.Listener<String>() {

            @SuppressLint("SetJavaScriptEnabled")
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
                        dept_id = new ArrayList<String>();
                        department_name1 = new ArrayList<String>();

                        XMENArray = new ArrayList<String>();
                        XMENArray2 = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");

                            JSONArray sub_list1 = jsonObject.getJSONArray("video_list");
                            //String html = sub_list1.getJSONObject(0).getString("video");
                            video_link = sub_list1.getJSONObject(0).getString("video_link");
                            if(!sub_list1.getJSONObject(0).getString("video").equals("")){
                                XMENArray2.add(sub_list1.getJSONObject(0).getString("video"));
                            }if(!sub_list1.getJSONObject(0).getString("video2").equals("")){
                                XMENArray2.add(sub_list1.getJSONObject(0).getString("video2"));
                            }if(!sub_list1.getJSONObject(0).getString("video3").equals("")){
                                XMENArray2.add(sub_list1.getJSONObject(0).getString("video3"));
                            }
                            init2();

//                            web_view1.setWebViewClient(new MyBrowser());
//                            web_view1.getSettings().setLoadsImagesAutomatically(true);
//                            web_view1.getSettings().setJavaScriptEnabled(true);
//                            web_view1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//                            web_view1.loadData(html, "text/html", null);

                            dept_id.add("");
                            department_name1.add("Select department");
                            JSONArray sub_list = jsonObject.getJSONArray("department_list");
                            for (int i = 0; i< sub_list.length() ; i ++ ){
                                dept_id.add(sub_list.getJSONObject(i).getString("id"));
                                department_name1.add(sub_list.getJSONObject(i).getString("department_name"));
                            }
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(DashBoardActivity.this, android.R.layout.simple_spinner_dropdown_item, department_name1);
                            department_text.setAdapter(adapter1);

                            JSONArray adsense_list = jsonObject.getJSONArray("adsense_list");
                            for (int i = 0; i< adsense_list.length() ; i ++ ){
                                XMENArray.add(adsense_list.getJSONObject(i).getString("adsense_image"));
                             }
                            init();

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
            mPager.setAdapter(new BannerSliderAdapter(DashBoardActivity.this, XMENArray));
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

    private void init2(){
        // for(int i=0;i<XMENArray.length;i++)
        //XMENArray.add(XMEN[i]);
        if(swipeTimer2 == null){
            mPager2 = (ViewPager) findViewById(R.id.slider_pager1);
            mPager2.setAdapter(new VideoAdapter(DashBoardActivity.this, XMENArray2));
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.slider_indicator1);
            indicator.setViewPager(mPager2);
            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable(){
                public void run() {
                    if (currentPage2 == XMENArray2.size()) {
                        currentPage2 = 0;
                    }
                    mPager2.setCurrentItem(currentPage2++, true);
                }
            };
            swipeTimer2 = new Timer();
            swipeTimer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);
        }
    }

    /*private class MyBrowser extends WebViewClient {

        //private ProgressBar progressBar;

        public MyBrowser() {
            //  this.progressBar=progressBar;
            // progressBar.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Loading...");
            showDialog();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            // progressBar.setVisibility(View.GONE);
            hideDialog();
        }
        private void showDialog() {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
        private void hideDialog() {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }

    }*/

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



}