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
import info.tech.drcircle.R;

import static info.tech.drcircle.common.Config.UPLOAD_URL;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> ambulance_id = new ArrayList<String>();
    private ArrayList<String> ambulance_name = new ArrayList<String>();
    private ArrayList<String> ambulance_type = new ArrayList<String>();
    private ArrayList<String> place = new ArrayList<String>();
    private ArrayList<String> contact_number = new ArrayList<String>();
    private ArrayList<String> contact_number2 = new ArrayList<String>();
    private ArrayList<String> ambulance_image = new ArrayList<String>();
    private Activity mContext;
    //    SQLiteDatabase mDatabase;
//    List<Item> productsList;
//    SqLiteItemClass sqLiteItemClass;
    public AmbulanceAdapter(Activity context,ArrayList<String> ambulance_id,ArrayList<String> ambulance_name,  ArrayList<String> ambulance_type,  ArrayList<String> place,  ArrayList<String> contact_number,  ArrayList<String> contact_number2,  ArrayList<String> ambulance_image) {
        this.ambulance_id = ambulance_id;
        this.ambulance_name = ambulance_name;
        this.ambulance_type = ambulance_type;
        this.place = place;
        this.contact_number = contact_number;
        this.contact_number2 = contact_number2;
        this.ambulance_image = ambulance_image;
        mContext = context;
//
//        productsList = new ArrayList<>();
//        mDatabase = context.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
//        sqLiteItemClass = new SqLiteItemClass(context,mDatabase,productsList);
//        sqLiteItemClass.createProductTable();
//        productsList = sqLiteItemClass.showProductFromDatabase();
    }

    @Override
    public AmbulanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_item, parent, false);
        AmbulanceAdapter.ViewHolder holder = new AmbulanceAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AmbulanceAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        //holder.product_unit.setVisibility(View.GONE);
     holder.dr_name.setText(ambulance_name.get(position));
        final String[] DOCTOR_NAME1 = contact_number.get(position).split(",");

     holder.type.setText(ambulance_type.get(position));
     holder.place_id.setText(place.get(position));



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
         Picasso.with(mContext).load(UPLOAD_URL+ "ambulance_image/" + ambulance_image.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.os_images);

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Intent intent115 = new Intent(mContext, PujaVidhiDetailsActivity.class);
//               // intent115.putExtra("PUJA_ID",id.get(position));
//              //  mContext.startActivity(intent115);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return ambulance_id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        // CircleImageView image;
        TextView type, dr_name, call_num, place_id, call_num2, imageName6, imageName7;
        RelativeLayout parentLayout;
        Button print;
        Boolean flag = false;
        ImageView os_images,imageView_push,imageView_delete,product_img;
        CheckBox checkBoxItems;
        public ViewHolder(View itemView){
            super(itemView);
            os_images = itemView.findViewById(R.id.os_images);
            dr_name = itemView.findViewById(R.id.dr_name);
            call_num2 = itemView.findViewById(R.id.call_num2);
            type = itemView.findViewById(R.id.type);
            place_id = itemView.findViewById(R.id.place_id);
            call_num = itemView.findViewById(R.id.call_num);
        }
    }


}


