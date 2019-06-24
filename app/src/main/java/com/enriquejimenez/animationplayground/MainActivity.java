package com.enriquejimenez.animationplayground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private GridAdapter mAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        // Setup the GridView and set the adapter
        mGridView = findViewById(R.id.grid);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        mGridView.setOnItemClickListener(this);
        mAdapter = new GridAdapter();
        mGridView.setAdapter(mAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlottieFragment flottieFragment = new FlottieFragment();

//                flottieFragment.setCancelable(false);
                flottieFragment.show(getSupportFragmentManager(), "FlottieDialogFragmet");


            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Item item = (Item) adapterView.getItemAtPosition(position);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.EXTRA_ID, item.getId());

        // Obtenemos una referencia a los elementos visuales que queremos transicionar
        ImageView photoImaveView = view.findViewById(R.id.imageview_item);
        TextView titleTextView = view.findViewById(R.id.textview_name);


        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                new Pair<View, String>(photoImaveView,Constants.SHARED_VIEW_PHOTO),
                new Pair<View, String>(titleTextView,Constants.SHARED_VIEW_TITLE)
        );

        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());


    }


    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Item.ITEMS.length;
        }

        @Override
        public Item getItem(int position) {
            return Item.ITEMS[position];
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
            }

            final Item item = getItem(position);

            // Cargar thumbnail
            ImageView image = view.findViewById(R.id.imageview_item);
            Picasso.with(image.getContext())
                    .load(item.getThumbnailUrl())
                    .into(image);

            // Setear el texto
            TextView name = view.findViewById(R.id.textview_name);
            name.setText(item.getName());

            return view;
        }
    }
}
