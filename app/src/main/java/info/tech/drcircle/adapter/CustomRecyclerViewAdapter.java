package info.tech.drcircle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import info.tech.drcircle.AmbulanceActivity;
import info.tech.drcircle.DoctorListActivity;
import info.tech.drcircle.HospitalListActivity;
import info.tech.drcircle.PathLabActivity;
import info.tech.drcircle.R;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter {
    ArrayList personNames;
    ArrayList personImages;
    Context context;
    public CustomRecyclerViewAdapter(Context context, ArrayList personNames, ArrayList personImages) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // set the data in items
        MyViewHolder viewHolder= (MyViewHolder)holder;
        //((MyViewHolder) holder).name.setText( personNames.get(position).toString());
        ((MyViewHolder) holder).image.setImageResource((Integer) personImages.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0 ){
                    Intent intent115 = new Intent(context, DoctorListActivity.class);
                   context.startActivity(intent115);
                }else if(position == 1){
                    Intent intent115 = new Intent(context, HospitalListActivity.class);
                    context.startActivity(intent115);
                }else if(position == 2){
                    Intent intent115 = new Intent(context, PathLabActivity.class);
                    context.startActivity(intent115);
                }else if(position == 3){
                    Intent intent115 = new Intent(context, AmbulanceActivity.class);
                    context.startActivity(intent115);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return personNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            //name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}

