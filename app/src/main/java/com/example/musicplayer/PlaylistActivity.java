package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musicplayer.song.SongContent;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class PlaylistActivity extends AppCompatActivity {
    public static String songToAddId = "";

    public static String playlistInputValue = "";
    public static ArrayList<String> playlistNames = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> playlists = new HashMap<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        recyclerView = (RecyclerView) findViewById(R.id.playlist_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(new ArrayList<>(playlistNames));
        for (String playlistName : playlistNames) {
            if (playlists.get(playlistName) == null) {
                playlists.put(playlistName, new ArrayList<String>());
            }
        }
        recyclerView.setAdapter(mAdapter);

        final EditText playlistInput = findViewById(R.id.playlist_text);
        playlistInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                playlistInputValue = s.toString();
            }
        });

        final Button button = findViewById(R.id.playlist_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playlistNames.add(playlistInputValue);
                playlists.put(playlistInputValue, new ArrayList<String>());
                mAdapter.notifyDataSetChanged();
                playlistInput.setText("");
            }
        });

        final TextView addSongText = findViewById(R.id.add_song_text);

        if (songToAddId.length() > 0) {
            button.setVisibility(View.GONE);
            playlistInput.setVisibility(View.GONE);
            addSongText.setText("Pick a playlist for: \n" + SongContent.ITEM_MAP.get(songToAddId).name);
        } else {
            addSongText.setVisibility(View.GONE);
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.playlist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.main:
                SongContent.clearSongs();
                SongContent.ITEMS = new ArrayList<>(SongContent.ALL_SONGS);
                Intent menuIntent = new Intent(PlaylistActivity.this, ItemListActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.playlists:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;

            public MyViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<String> myDataset) {
            playlistNames = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail, parent, false);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) v.getLayoutParams();
            lp.height = parent.getMeasuredHeight() / 6;
            v.setLayoutParams(lp);

            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String playlistName = ((TextView) v).getText().toString();
                    if (songToAddId.length() == 0) {
                        SongContent.clearSongs();
                        try {
                            ArrayList<String> playlist = playlists.get(playlistName);
                            for (String s : playlist) {
                                SongContent.addItem(SongContent.ITEM_MAP.get(s));
                            }
                        } catch (Exception e) {
                            Log.e("ERROR", "onClick: ", e);
                        }
                    } else {
                        playlists.get(playlistName).add(songToAddId);
                        songToAddId = "";
                    }
                    Intent menuIntent = new Intent(PlaylistActivity.this, ItemListActivity.class);
                    startActivity(menuIntent);
                }
            });

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(playlistNames.get(position));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return playlistNames.size();
        }
    }
}