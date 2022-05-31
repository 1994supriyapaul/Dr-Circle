package info.tech.drcircle.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import info.tech.drcircle.HospitalDoctorActivity;
import info.tech.drcircle.R;

import static info.tech.drcircle.common.Config.UPLOAD_URL;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> hospital_id = new ArrayList<String>();
    private ArrayList<String> hospital_name = new ArrayList<String>();
    private ArrayList<String>  place = new ArrayList<String>();
    private ArrayList<String> contact_number = new ArrayList<String>();
    private ArrayList<String> contact_number2 = new ArrayList<String>();
    private ArrayList<String> hospital_image = new ArrayList<String>();
    private ArrayList<String> hospital_video = new ArrayList<String>();
    private ArrayList<String> doctor_name = new ArrayList<String>();
    private ArrayList<String> department_id = new ArrayList<String>();
    private ArrayList<String> timming = new ArrayList<String>();
    private ArrayList<String> doctor_day = new ArrayList<String>();
    private Activity mContext;
    //    SQLiteDatabase mDatabase;
//    List<Item> productsList;
//    SqLiteItemClass sqLiteItemClass;
    public HospitalAdapter(Activity context,ArrayList<String> hospital_id,ArrayList<String> hospital_name,  ArrayList<String> place,  ArrayList<String> contact_number, ArrayList<String> contact_number2,  ArrayList<String> hospital_image,  ArrayList<String> hospital_video,  ArrayList<String> doctor_name,  ArrayList<String> department_id,  ArrayList<String> timming,  ArrayList<String> doctor_day) {
        this.hospital_id = hospital_id;
        this.hospital_name = hospital_name;
        this.place = place;
        this.contact_number = contact_number;
        this.contact_number2 = contact_number2;
        this.hospital_image = hospital_image;
        this.hospital_video = hospital_video;
        this.doctor_name = doctor_name;
        this.department_id = department_id;
        this.timming = timming;
        this.doctor_day = doctor_day;
        mContext = context;
//
//        productsList = new ArrayList<>();
//        mDatabase = context.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
//        sqLiteItemClass = new SqLiteItemClass(context,mDatabase,productsList);
//        sqLiteItemClass.createProductTable();
//        productsList = sqLiteItemClass.showProductFromDatabase();
    }

    @Override
    public HospitalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitals_item, parent, false);
        HospitalAdapter.ViewHolder holder = new HospitalAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HospitalAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        //holder.product_unit.setVisibility(View.GONE);
        // holder.product_name.setText(p_name.get(position));
        //holder.product_img.setImageResource((Integer) p_image.get(position));
        //  holder.product_price.setText("₹" +p_price.get(position));
//        try{
//            String ss = Integer.toString(productsList.get(position).getQuantity());
//            String aaa = Double.toString((Double.parseDouble(p_price.get(position)) * Double.parseDouble(ss)));
//            holder.product_quantity.setText(ss);
//            if(p_free.get(position).equals("1")) {
//                holder.product_price.setText("₹" + p_price.get(position) + " X " + ss + " = ₹0.00(Inc AMC)");
//            }else{
//                holder.product_price.setText("₹" + p_price.get(position) + " X " + ss + " = ₹" + aaa);
//            }
//        }catch(Exception e){
//            //
//        }
           holder.dr_name.setText(hospital_name.get(position));
           holder.place_id.setText(place.get(position));
        final String[] DOCTOR_NAME1 = contact_number.get(position).split(",");

           Picasso.with(mContext).load(UPLOAD_URL + "hospital_image/" + hospital_image.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.os_images);

        String ss = "";
        if(!contact_number2.get(position).equals("")){
            ss = ",";
        }

        holder.call_num.setText(contact_number.get(position)+ss);
        holder.call_num2.setText(contact_number2.get(position));

        holder.call_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!contact_number.get(position).equals("")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                    callIntent.setData(Uri.parse("tel:" + contact_number.get(position)));
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //request permission from user if the app hasn't got the required permission
                        ActivityCompat.requestPermissions(mContext,
                                new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                                10);
                        //return true;
                    } else {     //have got permission
                        try {
                            mContext.startActivity(callIntent);  //call activity and make phone call
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mContext, "your Activity is not founded", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        holder.call_num2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!contact_number2.get(position).equals("")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                    callIntent.setData(Uri.parse("tel:" + contact_number2.get(position)));
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //request permission from user if the app hasn't got the required permission
                        ActivityCompat.requestPermissions(mContext,
                                new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                                10);
                        //return true;
                    } else {     //have got permission
                        try {
                            mContext.startActivity(callIntent);  //call activity and make phone call
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mContext, "your Activity is not founded", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        if(hospital_video.get(position).equals("")) {
            holder.button_login.setVisibility(View.GONE);
        }

        holder.button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hospital_video.get(position).equals("")) {
                    String url = hospital_video.get(position);
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            }
        });

        holder.button_doctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, HospitalDoctorActivity.class);
                in.putExtra("DOCTOR_NAME",doctor_name.get(position));
                in.putExtra("DEPT_ID",department_id.get(position));
                in.putExtra("TIMMING",timming.get(position));
                in.putExtra("DAY",doctor_day.get(position));
                in.putExtra("TYPE","D");
                mContext.startActivity(in);
            }
        });
    }


    @Override
    public int getItemCount() {
        return hospital_id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        // CircleImageView image;
        TextView dr_name, place_id, call_num, call_num2, imageName5, imageName6, imageName7;
        RelativeLayout button_doctor,button_login;
        Button print;
        Boolean flag = false;
        ImageView os_images,imageView_push,imageView_delete,product_img;
        CheckBox checkBoxItems;
        public ViewHolder(View itemView){
            super(itemView);
            os_images = itemView.findViewById(R.id.os_images);
            dr_name = itemView.findViewById(R.id.dr_name);
            call_num2 = itemView.findViewById(R.id.call_num2);
            place_id = itemView.findViewById(R.id.place_id);
            call_num = itemView.findViewById(R.id.call_num);
            button_doctor = itemView.findViewById(R.id.button_doctor);
            button_login = itemView.findViewById(R.id.button_login);
        }
    }


}


