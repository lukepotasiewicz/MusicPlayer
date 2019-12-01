package com.example.musicplayer.song;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.media.MediaMetadataRetriever;

import java.util.ArrayList;
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
    public static List<SongItem> ITEMS = new ArrayList<>();
    public static List<SongItem> ALL_SONGS = new ArrayList<>();

    /**
     * A map of sample (song) items, by ID.
     */
    public static final Map<String, SongItem> ITEM_MAP = new HashMap<String, SongItem>();

    static {
        File[] files;
        try {
            File directory = new File("/sdcard/Music");
            files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                MediaMetadataRetriever metaRetriever;
                metaRetriever = new MediaMetadataRetriever();
                String url = "/sdcard/Music/" + files[i].getName();
                metaRetriever.setDataSource(url);
                byte[] art;
                art = metaRetriever.getEmbeddedPicture();
                Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
                String title =  metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artist =  metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                int duration = Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))/1000;
                SongItem song = new SongItem(
                        title + artist,
                        title,
                        artist,
                        duration,
                        songImage,
                        url
                );
                addItem(song);
                ALL_SONGS.add(song);
            }
        } catch (Throwable error) {
            Log.e("ERROR", "", error);
            Log.e("ERROR", "No music Files found.");
        }
    }

    public static void addItem(SongItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);
    }

    public static void clearSongs() {
        ITEMS.clear();
    }

    /**
     * A song item representing a piece of content.
     */
    public static class SongItem {
        public final String id;
        public final String name;
        public final String artist;
        public final String url;
        final int length;
        public final Bitmap albumArt;

        SongItem(String id, String name, String artist, int length, Bitmap art, String url) {
            this.id = id;
            this.name = name;
            this.artist = artist;
            this.length = length;
            this.albumArt = art;
            this.url = url;
        }

        @Override
        public String toString() {
            return artist;
        }

        public String getFormattedLength() {
            if (length % 60 < 10) {
                return length / 60 + ":0" + length % 60;
            }
            return length / 60 + ":" + length % 60;
        }
    }
}
