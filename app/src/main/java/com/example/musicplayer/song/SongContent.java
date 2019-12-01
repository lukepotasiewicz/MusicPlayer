package com.example.musicplayer.song;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.media.MediaMetadataRetriever;

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
    public static List<SongItem> ITEMS = new ArrayList<>();
    public static List<SongItem> ALL_SONGS = new ArrayList<>();

    /**
     * A map of sample (song) items, by ID.
     */
    public static final Map<String, SongItem> ITEM_MAP = new HashMap<String, SongItem>();

    static {
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
        boolean foundFiles = false;
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
            if (files.length > 0) {
                foundFiles = true;
            }
        } catch (Throwable error) {
            Log.e("ERROR", "", error);
            Log.e("ERROR", "No music Files found.");
        }
        // Add some sample items.
//        if (!foundFiles) {
//            for (int i = 0; i < SONG_NAMES.size(); i++) {
//                addItem(new SongItem(String.valueOf(i), SONG_NAMES.get(i), ARTIST_NAMES.get(i), 265));
//            }
//        }
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

        SongItem(String id, String name, String artist, int length) {
            this.id = id;
            this.name = name;
            this.artist = artist;
            this.length = length;
            this.albumArt = null;
            this.url = null;
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
