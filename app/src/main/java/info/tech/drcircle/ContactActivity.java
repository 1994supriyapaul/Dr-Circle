package info.tech.drcircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import info.tech.drcircle.adapter.BannerSliderAdapter;
import info.tech.drcircle.common.AppSingleton;
import me.relex.circleindicator.CircleIndicator;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static info.tech.drcircle.common.Config.CONTACT_FROM;
import static info.tech.drcircle.common.Config.HOME_DETAILS;

public class ContactActivity extends AppCompatActivity {
    EditText name_text,email_text,message_text,phone_text,subject_text;
    ProgressDialog progressDialog;
    RelativeLayout menu_button,button_login;
    TextView text_main;

    Timer swipeTimer;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        menu_button = findViewById(R.id.back_button);
        text_main = findViewById(R.id.text_main);

        name_text = findViewById(R.id.name_text);
        email_text = findViewById(R.id.email_text);
        phone_text = findViewById(R.id.phone_text);
        subject_text = findViewById(R.id.subject_text);
        button_login = findViewById(R.id.button_login);
        message_text = findViewById(R.id.message_text);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        menu_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });


        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (name_text.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Type name", Toast.LENGTH_LONG).show();
                } else if (email_text.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Type email id", Toast.LENGTH_LONG).show();
                } else if (phone_text.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Type phone number", Toast.LENGTH_LONG).show();
                }else{
                    add_notice();
                }
            }
        });
        categoryList();
    }

    public void add_notice(){
        String cancel_req_tag = "login";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                CONTACT_FROM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LOGIN", "Register Response: " + response.toString());
                hideDialog();

                if (!response.toString().equals("null")){
                    try {
                        boolean error = new JSONObject(response).getBoolean("error");
                        if(!error){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContactActivity.this);

                            // Setting Dialog Title
                            alertDialog.setTitle("Contact");

                            // Setting Dialog Message
                            alertDialog.setMessage("Your message submitted successfully");
                            // Setting Icon to Dialog
                           // alertDialog.setIcon(R.drawable.logo1);
                            // Setting Positive "Yes" Button DELETE FROM employees WHERE id = ?
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // deleteItem(mId.get(position))
                                    dialog.cancel();
                                    finish();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        }else{

                            hideDialog();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "error", Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LogIn", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                SharedPreferences userPrefs = getSharedPreferences("prefName", MODE_PRIVATE);
                String USER_ID_KEY = userPrefs.getString("USER_ID_KEY", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",name_text.getText().toString());
                params.put("phone",phone_text.getText().toString());
                params.put("email",email_text.getText().toString());
               // params.put("contact_subject",subject_text.getText().toString());
                params.put("message",message_text.getText().toString());
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);

    }


    public void categoryList(){
        String cancel_req_tag = "login";
        progressDialog.setMessage("Loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, HOME_DETAILS, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                Log.d("report", "Register Response: " + response.toString());
                hideDialog();
                if (!response.toString().equals("null")) {
                    //               recyclerView.setVisibility(View.VISIBLE);
                    try {
                        boolean error = new JSONObject(response).getBoolean("error");
                        XMENArray = new ArrayList<String>();
                        if(!error) {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");
                            JSONArray advertisement_image = jsonObject.getJSONArray("video_list");

                            text_main.setText("Email id: "+advertisement_image.getJSONObject(0).getString("email_address"));

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
                params.put("user_id",userId);
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
            mPager.setAdapter(new BannerSliderAdapter(ContactActivity.this, XMENArray));
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

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


}