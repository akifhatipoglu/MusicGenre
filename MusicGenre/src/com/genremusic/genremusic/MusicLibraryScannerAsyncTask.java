package com.genremusic.genremusic;

import java.util.ArrayList;
import java.util.List;

import com.mp3.mp3scanner.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.GenresColumns;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MusicLibraryScannerAsyncTask extends
		AsyncTask<String, String, List<String>> {

	private static Cursor mediaCursor;
	private static Cursor genresCursor;

	private static String[] mediaProjection = { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TITLE };
	private static String[] genresProjection = { MediaStore.Audio.Genres.NAME,
			MediaStore.Audio.Genres._ID };
	static String info = "";
	private Context context;
	private ListView list;
	private ProgressDialog progressDialog;
	private ArrayAdapter<String> adapter;

	public MusicLibraryScannerAsyncTask(Context context) {
		super();
		this.context = context;
		list = (ListView) ((Activity) context).findViewById(R.id.listView1);
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "Lütfen Bekleyin...",
				"Ýþlem Yürütülüyor...", true);
	}

	@Override
	protected List<String> doInBackground(String... params) {
		return getMusicFromStorage(context);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		progressDialog.setMessage("Process finished...");
	}

	@Override
	protected void onPostExecute(List<String> result) {
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, result);
		list.setAdapter(adapter);
		progressDialog.cancel();
	}

	public List<String> getMusicFromStorage(Context context) {
		info = "";
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
		publishProgress();
		return getinfo;
	}
}
