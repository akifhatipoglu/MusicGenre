package com.genremusic.genremusic;

import java.util.List;

import com.mp3.mp3scanner.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Activity_Main extends Activity {
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__main);

		MusicLibraryScanner scanner = new MusicLibraryScanner();
		List<String> musiclist = scanner.getMusicFromStorage(this);
		List<String> genreslist=scanner.getMusicGenresFromStorage(this);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, musiclist);
		final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, genreslist);
		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		
		/*Button btn_genres=(Button)findViewById(R.id.button_genres);
		btn_genres.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				list.setAdapter(adapter2);
			}
		});*/
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity__main, menu);
		return true;
	}

}
