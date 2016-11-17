package com.example.jangbi.myapplication;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;


import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;

import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Movie extends AppCompatActivity {
    ListView listView;
    public List<String> listPath = new ArrayList<String>();
    String selectedVideo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);

        listView = (ListView) findViewById(R.id.listViewMovie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_gallery_item,updateVedioList());
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        selectedVideo = listPath.get(position);

                        VideoView videoView = (VideoView) findViewById(R.id.VideoView01);

                        videoView.setVideoPath(selectedVideo);
                        MediaController mediaController = new MediaController(Movie.this);

                        mediaController.setAnchorView(videoView);
                        videoView.setMediaController(mediaController);

                        videoView.start();
                    }
                }
        );



    }
    public List<String> updateVedioList() {

        List<String> listName = new ArrayList<String>();
        String[] cursorColumns = new String[] {
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA
        };
        Cursor cursor = (Cursor) getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                cursorColumns, null, null, null);

        if (cursor == null) {
            return listName;
        }
        if (cursor.moveToFirst()) {
            int moviePath = cursor.getColumnIndex( MediaStore.Video.Media.DATA);
            int movieColumn = cursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            do
            {
                String musicName = cursor.getString(movieColumn);
                String Path = cursor.getString(moviePath);

                listName.add(musicName);
                listPath.add(Path);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listName;
    }
}

class MyVideoView extends VideoView
{

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Display dis =((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        setMeasuredDimension(dis.getWidth(), dis.getHeight());
    }
}
