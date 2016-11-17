package com.example.jangbi.myapplication;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;


public class audio extends ListActivity {

    // ROOT 경로를 지정합니다.
    private List<String> songs = new ArrayList<String>();
    public List<String> listPath = new ArrayList<String>();
    private MediaPlayer mp = new MediaPlayer();
    private int currentPosition = 0;
    Button btn,btnStart;
    TextView status;

// 재생할 곡의 위치입니다.
    /** Called when the activity is first created. */
    @Override    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);


        btn = (Button) findViewById(R.id.btnStop);
        btnStart = (Button) findViewById(R.id.btnPause);
        // Toast.makeText(this, "리스트 불러오는 중", Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item,  updateSongList());
        setListAdapter(songList);

        Log.v("data", "로딩성공");


        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mp.stop();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(mp.isPlaying() == true){
                    mp.pause();
                    btnStart.setText("재생");
                }
                else{
                    mp.start();
                    btnStart.setText("일시정지");
                }
            }
        });


    }
    // SD카드에서 파일 불러오기
    public List<String> updateSongList() {

        List<String> listName = new ArrayList<String>();
        String[] cursorColumns = new String[] {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = (Cursor) getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                cursorColumns, null, null, null);

        if (cursor == null) {
            return listName;
        }
        if (cursor.moveToFirst()) {
            int musicPath = cursor.getColumnIndex( MediaStore.Audio.Media.DATA);
            int musicColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            do
            {
                String musicName = cursor.getString(musicColumn);
                String Path = cursor.getString(musicPath);

                listName.add(musicName);
                listPath.add(Path);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listName;

    }

    // 리스트 클릭 시 이벤트 처리

    protected void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        Log.v("path", listPath.get(position));
        playSong(listPath.get(position));
    }




    private void playSong(String songPath) {
        try {
            mp.reset();
            mp.setDataSource(songPath);
            mp.prepare();
            mp.start();
            Toast.makeText(this, "재생 : " + songPath, Toast.LENGTH_SHORT).show();
            status = (TextView)findViewById(R.id.playStatus);
            status.setText("재생 중 : " + songPath );

            //한 곡의 재생이 끝나면 다음 곡 재생
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer arg0) {
                    nextSong();
                }
            });

        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void nextSong() {
        if (++currentPosition >= songs.size()) {

            // 마지막 곡 종료시 리스트 초키화
            currentPosition = 0;
            TextView status = (TextView)findViewById(R.id.playStatus);
            status.setText("노래를 선택하세요");
        } else {
            // 다음 곡을 재생
            Toast.makeText(getApplicationContext(), "다음 곡 재생", Toast.LENGTH_SHORT).show();
            playSong(listPath.get(currentPosition));
        }
    }
}
