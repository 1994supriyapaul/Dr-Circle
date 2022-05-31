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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import info.tech.drcircle.HospitalDoctorActivity;
import info.tech.drcircle.R;

import static info.tech.drcircle.common.Config.UPLOAD_URL;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> doc_id = new ArrayList<>();
    private ArrayList<String> doctor_name = new ArrayList<>();
    private ArrayList<String> department_name = new ArrayList<>();
    private ArrayList<String> place_id = new ArrayList<>();
    private ArrayList<String> timming = new ArrayList<>();
    private ArrayList<String> contact_number = new ArrayList<>();
    private ArrayList<String> contact_number2 = new ArrayList<>();
    private ArrayList<String> video_link = new ArrayList<>();
    private ArrayList<String> doctor_day = new ArrayList<>();
    private ArrayList<String> doctor_image = new ArrayList<>();
    private ArrayList<String> hospital_name = new ArrayList<>();
    private ArrayList<String> hospital_timming = new ArrayList<>();
    private ArrayList<String> hospital_day = new ArrayList<>();
    private Activity mContext;
    //    SQLiteDatabase mDatabase; ,s,s,s
//    List<Item> productsList;
//    SqLiteItemClass sqLiteItemClass;
    public DoctorAdapter(Activity context,ArrayList<String> doc_id,ArrayList<String> doctor_name,  ArrayList<String> department_name,  ArrayList<String> place_id,  ArrayList<String> timming,  ArrayList<String> contact_number, ArrayList<String> contact_number2, ArrayList<String> doctor_image,  ArrayList<String> video_link,  ArrayList<String> doctor_day,  ArrayList<String> hospital_name,  ArrayList<String> hospital_timming,  ArrayList<String> hospital_day) {
        this.doc_id = doc_id;
        this.doctor_name = doctor_name;
        this.department_name = department_name;
        this.timming = timming;
        this.place_id = place_id;
        this.contact_number = contact_number;
        this.contact_number2 = contact_number2;
        this.video_link = video_link;
        this.doctor_day = doctor_day;
        this.doctor_image = doctor_image;
        this.hospital_name = hospital_name;
        this.hospital_timming = hospital_timming;
        this.hospital_day = hospital_day;
        mContext = context;

//        productsList = new ArrayList<>();
//        mDatabase = context.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
//        sqLiteItemClass = new SqLiteItemClass(context,mDatabase,productsList);
//        sqLiteItemClass.createProductTable();
//        productsList = sqLiteItemClass.showProductFromDatabase();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        //holder.product_unit.setVisibility(View.GONE);
        holder.dr_name.setText(doctor_name.get(position));
        holder.special.setText(department_name.get(position));
        holder.location.setText(place_id.get(position));
        holder.day.setText(doctor_day.get(position));
        holder.time.setText(timming.get(position));
        holder.descrip.setText(doc_id.get(position));

        String ss = "";
        if(!contact_number2.get(position).equals(" ")){
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
//

        Picasso.with(mContext).load(UPLOAD_URL + "doctor_image/" + doctor_image.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.os_images);

        if(video_link.get(position).equals("")){
            holder.button_video.setVisibility(View.GONE);
        }  if(hospital_name.get(position).equals("")){
            holder.button_hospital.setVisibility(View.GONE);
        }

        holder.button_video.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!video_link.get(position).equals("")) {
                    String url = video_link.get(position);
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            }
        });

        holder.button_hospital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, HospitalDoctorActivity.class);
                in.putExtra("DOCTOR_NAME",hospital_name.get(position));
                in.putExtra("DEPT_ID",hospital_name.get(position));
                in.putExtra("TIMMING",hospital_timming.get(position));
                in.putExtra("DAY",hospital_day.get(position));
                in.putExtra("TYPE","H");
                mContext.startActivity(in);
            }
        });

    }


    @Override
    public int getItemCount() {
        return doc_id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        // CircleImageView image;
        TextView dr_name, descrip, call_num,call_num2, special, location, day, time;
        RelativeLayout button_video,button_hospital;
        Button print;
        Boolean flag = false;
        ImageView os_images,imageView_push,imageView_delete;
        CheckBox checkBoxItems;
        public ViewHolder(View itemView){
            super(itemView);
            call_num2 = itemView.findViewById(R.id.call_num2);
            dr_name = itemView.findViewById(R.id.dr_name);
            descrip = itemView.findViewById(R.id.descrip);
            call_num = itemView.findViewById(R.id.call_num);
            button_video = itemView.findViewById(R.id.button_video);
            button_hospital = itemView.findViewById(R.id.button_hospital);
            os_images = itemView.findViewById(R.id.os_images);
            special = itemView.findViewById(R.id.special);
            location = itemView.findViewById(R.id.location);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
        }
    }


}

