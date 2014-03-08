package com.genremusic.genremusic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.GenresColumns;
import android.provider.MediaStore.MediaColumns;

public class MusicLibraryScanner {

	private static Cursor mediaCursor;
	private static Cursor genresCursor;
	//Song from Secret Garden
	private static String[] mediaProjection = { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TITLE };
	private static String[] genresProjection = { MediaStore.Audio.Genres.NAME,
			MediaStore.Audio.Genres._ID };
	static String info = "";

	public List<String> getMusicFromStorage(Context context) {
		info="";
		List<String> getinfo = new ArrayList<String>();
		mediaCursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaProjection,
				null, null, null);

		int artist_column_index = mediaCursor
				.getColumnIndexOrThrow(AudioColumns.ARTIST);
		int album_column_index = mediaCursor
				.getColumnIndexOrThrow(AudioColumns.ALBUM);
		int title_column_index = mediaCursor
				.getColumnIndexOrThrow(MediaColumns.TITLE);
		int id_column_index = mediaCursor
				.getColumnIndexOrThrow(BaseColumns._ID);

		if (mediaCursor.moveToFirst()) {
			do {
				info = "Song " + mediaCursor.getString(title_column_index)
						+ " ";
				info += "from album "
						+ mediaCursor.getString(album_column_index) + " ";
				info += "by " + mediaCursor.getString(artist_column_index)
						+ ". ";

				int musicId = Integer.parseInt(mediaCursor
						.getString(id_column_index));

				Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId(
						"external", musicId);
				genresCursor = context.getContentResolver().query(uri,
						genresProjection, null, null, null);
				int genre_column_index = genresCursor
						.getColumnIndexOrThrow(GenresColumns.NAME);

				if (genresCursor.moveToFirst()) {
					info += "Genres:";
					do {
						info += genresCursor.getString(genre_column_index)
								+ " ";
					} while (genresCursor.moveToNext());
				}
				getinfo.add(info);
				// Log.e("Audio scanner", "Song info: " + info);
			} while (mediaCursor.moveToNext());
		}
		return getinfo;
	}
	
	public List<String> getMusicGenresFromStorage(Context context){
		info="";
		List<String> getinfo = new ArrayList<String>();
		int id_column_index = mediaCursor
				.getColumnIndexOrThrow(BaseColumns._ID);
		int musicId = Integer.parseInt(mediaCursor.getString(id_column_index));
		Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId(
				"external", musicId);
		genresCursor = context.getContentResolver().query(uri,
				genresProjection, null, null, null);
		int genre_column_index = genresCursor
				.getColumnIndexOrThrow(GenresColumns.NAME);

		if (genresCursor.moveToFirst()) {
			do {
				info += "Genres:";
				info += genresCursor.getString(genre_column_index)
						+ " ";
				getinfo.add(info);
			} while (genresCursor.moveToNext());
		}
		
		return getinfo;
	}
}
