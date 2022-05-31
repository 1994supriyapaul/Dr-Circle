package info.tech.drcircle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import static info.tech.drcircle.common.Config.UPLOAD_URL;

public class PopupImageActivity extends AppCompatActivity {
RelativeLayout button_cross;
    ImageView advertisement_image_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_image);
        advertisement_image_id = findViewById(R.id.advertisement_image_id);
        button_cross = findViewById(R.id.button_cross);

        button_cross.setOnClickListener(this::button_click);
        Intent in = getIntent();
        String ss = in.getStringExtra("PIC");
        Picasso.with(this).load(UPLOAD_URL +"adsense_image/"+ ss).into(advertisement_image_id);

    }

    private void button_click(View view) {
        finish();
    }


}