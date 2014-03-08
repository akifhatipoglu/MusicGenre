package com.genremusic.genremusic;

import java.util.List;

import com.mp3.mp3scanner.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Activity_Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__main);

		MusicLibraryScanner scanner = new MusicLibraryScanner();
		List<String> musiclist = scanner.getMusicFromStorage(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, musiclist);
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity__main, menu);
		return true;
	}

}
