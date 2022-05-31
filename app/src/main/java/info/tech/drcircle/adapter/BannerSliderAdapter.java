package info.tech.drcircle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;
import info.tech.drcircle.PopupImageActivity;
import info.tech.drcircle.R;

import static info.tech.drcircle.common.Config.UPLOAD_URL;

public class BannerSliderAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;
    // Config config = new Config();
    public BannerSliderAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        // myImage.setImageResource(images.get(position));
        Picasso.with(context).load(UPLOAD_URL +"adsense_image/"+ images.get(position)).into(myImage);
        view.addView(myImageLayout, 0);
        myImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, PopupImageActivity.class);
                in.putExtra("PIC",images.get(position));
                context.startActivity(in);
            }
        });

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}

