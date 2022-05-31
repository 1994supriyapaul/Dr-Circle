package info.tech.drcircle.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import info.tech.drcircle.R;

import static info.tech.drcircle.common.Config.UPLOAD_URL;

public class DoctorDetailsAdapter extends RecyclerView.Adapter<DoctorDetailsAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> doc_id = new ArrayList<>();
    private ArrayList<String> doctor_name = new ArrayList<>();
    private ArrayList<String> department_name = new ArrayList<>();
    private ArrayList<String> place_id = new ArrayList<>();
    private ArrayList<String> timming = new ArrayList<>();
    private ArrayList<String> contact_number = new ArrayList<>();
    private ArrayList<String> video_link = new ArrayList<>();
    private ArrayList<String> doctor_day = new ArrayList<>();
    private ArrayList<String> doctor_image = new ArrayList<>();
    private Activity mContext;
    String TYPE = "";
    //    SQLiteDatabase mDatabase;
//    List<Item> productsList;
//    SqLiteItemClass sqLiteItemClass;
    public DoctorDetailsAdapter(Activity context,ArrayList<String> doc_id,ArrayList<String> doctor_name,  ArrayList<String> department_name,  ArrayList<String> place_id,  ArrayList<String> timming,  ArrayList<String> contact_number, ArrayList<String> doctor_image,  ArrayList<String> video_link,  ArrayList<String> doctor_day,String TYPE) {
        this.doc_id = doc_id;
        this.doctor_name = doctor_name;
        this.department_name = department_name;
        this.timming = timming;
        this.place_id = place_id;
        this.contact_number = contact_number;
        this.video_link = video_link;
        this.doctor_day = doctor_day;
        this.doctor_image = doctor_image;
        mContext = context;
        this.TYPE = TYPE;
//        productsList = new ArrayList<>();
//        mDatabase = context.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
//        sqLiteItemClass = new SqLiteItemClass(context,mDatabase,productsList);
//        sqLiteItemClass.createProductTable();
//        productsList = sqLiteItemClass.showProductFromDatabase();
    }

    @Override
    public DoctorDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_item, parent, false);
        DoctorDetailsAdapter.ViewHolder holder = new DoctorDetailsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DoctorDetailsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        //holder.product_unit.setVisibility(View.GONE);
        holder.dr_name.setText(doctor_name.get(position));
        holder.special.setText(department_name.get(position));
        holder.day.setText(doctor_day.get(position));
        holder.time.setText(timming.get(position));
        if(TYPE.equals("D")){

            holder.special.setVisibility(View.VISIBLE);
            holder.special1.setVisibility(View.VISIBLE);
        }else  if(TYPE.equals("H")){
            holder.special.setVisibility(View.GONE);
            holder.special1.setVisibility(View.GONE);
        }
      //  holder.call_num.setText(contact_number.get(position));
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

//        Picasso.with(mContext).load(UPLOAD_URL + "doctor_image/" + doctor_image.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.os_images);
//
//        holder.button_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!video_link.get(position).equals("")) {
//                    String url = video_link.get(position);
//                    Uri uri = Uri.parse(url);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    mContext.startActivity(intent);
//                }
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return doc_id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        // CircleImageView image;
        TextView dr_name, special,dr_name1, special1, call_num, location, day, time,day1, time1, imageName7;
        RelativeLayout button_video;
        Button print;
        Boolean flag = false;
        ImageView os_images,imageView_push,imageView_delete;
        CheckBox checkBoxItems;
        public ViewHolder(View itemView){
            super(itemView);
            dr_name = itemView.findViewById(R.id.dr_name);
            special = itemView.findViewById(R.id.special);
            dr_name1 = itemView.findViewById(R.id.dr_name1);
            special1 = itemView.findViewById(R.id.special1);
            location = itemView.findViewById(R.id.location);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            day1 = itemView.findViewById(R.id.day1);
            time1 = itemView.findViewById(R.id.time1);
            call_num = itemView.findViewById(R.id.call_num);
            button_video = itemView.findViewById(R.id.button_video);
            os_images = itemView.findViewById(R.id.os_images);
        }
    }


}


