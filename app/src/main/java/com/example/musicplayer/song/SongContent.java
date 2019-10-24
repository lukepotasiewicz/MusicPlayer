package com.example.musicplayer.song;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class SongContent {

    /**
     * An array of sample (song) items.
     */
    public static final List<SongItem> ITEMS = new ArrayList<SongItem>();

    /**
     * A map of sample (song) items, by ID.
     */
    public static final Map<String, SongItem> ITEM_MAP = new HashMap<String, SongItem>();

    static {
        try {
            String path = "/mnt/sdcard/Download";
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            Log.v("Files",directory.exists()+"");
            Log.v("Files",directory.isDirectory()+"");
            Log.v("Files",directory.listFiles()+"");
            File[] files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            for (int i = 0; i < files.length; i++)
            {
                Log.d("Files", "FileName:" + files[i].getName());
            }
        } catch (Throwable error) {
            Log.e("ERROR", "", error);
            Log.e("ERROR", "No music Files found, falling back to mock data");
        }
        List<String> SONG_NAMES = Arrays.asList(
                "Song Name 1",
                "Song Name 2",
                "Song Name 3",
                "Song Name 4",
                "Song Name 5",
                "Song Name 6",
                "Song Name 7",
                "Song Name 8",
                "Song Name 9",
                "Song Name 10",
                "Song Name 11",
                "Song Name 12",
                "Song Name 13",
                "Song Name 14",
                "Song Name 15",
                "Song Name 16"
        );
        List<String> ARTIST_NAMES = Arrays.asList(
                "Artist Name 1",
                "Artist Name 2",
                "Artist Name 3",
                "Artist Name 4",
                "Artist Name 5",
                "Artist Name 6",
                "Artist Name 7",
                "Artist Name 8",
                "Artist Name 9",
                "Artist Name 10",
                "Artist Name 11",
                "Artist Name 12",
                "Artist Name 13",
                "Artist Name 14",
                "Artist Name 15",
                "Artist Name 16"
        );
        // Add some sample items.
        for (int i = 0; i < SONG_NAMES.size(); i++) {
            addItem(new SongItem(String.valueOf(i), SONG_NAMES.get(i), ARTIST_NAMES.get(i), 265));
        }
    }

    private static void addItem(SongItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);
    }

    /**
     * A song item representing a piece of content.
     */
    public static class SongItem {
        public final String id;
        public final String name;
        public final String artist;
        final int length;

        SongItem(String id, String name, String artist, int length ) {
            this.id = id;
            this.name = name;
            this.artist = artist;
            this.length = length;
        }

        @Override
        public String toString() {
            return artist;
        }

        public String getFormattedLength() {
            return length/60 + ":" + length%60;
        }
    }
}
