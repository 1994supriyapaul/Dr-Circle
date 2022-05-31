package info.tech.drcircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import info.tech.drcircle.common.AppSingleton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static info.tech.drcircle.common.Config.VERSION_CODE;

public class MainActivity extends AppCompatActivity {
    Snackbar snackbar;
    RelativeLayout splash_lay;
    String versionCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash_lay = findViewById(R.id.splash_lay);

        pinList();
    }

    public void pinList(){
        String cancel_req_tag = "login";
        // progressDialog.setMessage("Loading...");
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, VERSION_CODE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("report", "Register Response: " + response.toString());
                //  hideDialog();
                if (!response.toString().equals("null")) {
                    //               recyclerView.setVisibility(View.VISIBLE);
                    try {
                        if(snackbar!=null){
                            snackbar.dismiss();
                        }
                        boolean error = new JSONObject(response).getBoolean("error");
                        if(!error) {
                            try {
                                JSONObject response1 = new JSONObject(response).getJSONObject("result");
                                versionCode = response1.getString("version_code");
                                PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                int versionNumber = pinfo.versionCode;
                                String versionName = pinfo.versionName;
                                if (versionName.equals(versionCode)){
                                    Runnable r = new Runnable() {
                                        @Override
                                        public void run() {
               /* SharedPreferences userPrefs = getSharedPreferences("prefName", MODE_PRIVATE);
                String userName = userPrefs.getString("USER_ID_KEY", "");
                String USER_ADD_KEY = userPrefs.getString("USER_ADD_KEY", "");
                // Toast.makeText(getApplicationContext(),userName ,Toast.LENGTH_LONG).show();
                if(userName.isEmpty()) {
                    startActivity(new Intent(Splash.this, LoginActivity.class));
                    // Toast.makeText(getApplicationContext(),MyProfile.username1,Toast.LENGTH_LONG).show();
                    finish();
                }
                else{*/
                                            startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
                                            finish();
                                            //   }

                                        }
                                    };


                                    Handler h = new Handler();
                                    // The Runnable will be executed after the given delay time
                                    h.postDelayed(r, 3000);
                                }else {
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setMessage("You have to update new version from play store.")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=info.tech.drcircle"));
                                                    startActivity(myIntent);

                                                }
                                            })
                                            .show();
                                }
                            }catch (PackageManager.NameNotFoundException e){

                            }

                        }else{
//                            String mess = new JSONObject(response).getString("message");
                            //Toast.makeText(getApplicationContext(),
                            //      mess, Toast.LENGTH_LONG).show();
                            //                     recyclerView.setVisibility(View.GONE);

                            showSnack();
                            //      hideDialog();
                        }


                    } catch (JSONException e) {
                        showSnack();
                        e.printStackTrace();
                    }
                }else{
                    showSnack();
                    //           recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "Login Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                //      error.getMessage(), Toast.LENGTH_LONG).show();
                showSnack();
                //  hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                SharedPreferences userPrefs = getSharedPreferences("prefName", MODE_PRIVATE);
                String userId = userPrefs.getString("USER_ID_KEY", "");
                Map<String, String> params = new HashMap<String, String>();
                // params.put("staffUID",userId);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);

    }

    /*private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }*/


    public void showSnack(){
        snackbar = Snackbar
                .make(splash_lay, "Try again! No internet connection or slow internet.", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        pinList();
                        snackbar.dismiss();
                    }   });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}