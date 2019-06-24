package com.enriquejimenez.animationplayground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView headerImageView;
    private TextView titleTextView;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt(Constants.EXTRA_ID, 0);
        item = Item.getItem(id);

        titleTextView = findViewById(R.id.textview_item_title);
        headerImageView = findViewById(R.id.imageview_item_header);

        //Conexión de elementos que están compartidos
        ViewCompat.setTransitionName(headerImageView, Constants.SHARED_VIEW_PHOTO);
        ViewCompat.setTransitionName(titleTextView, Constants.SHARED_VIEW_TITLE);

        loadItem();


    }

    private void loadItem() {
        titleTextView.setText(item.getName());
        Picasso.with(this)
                .load(item.getPhotoUrl())
                .noFade()
                .into(headerImageView);
    }
}
