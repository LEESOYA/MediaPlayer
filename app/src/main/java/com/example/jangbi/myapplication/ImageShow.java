package com.example.jangbi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImageShow extends Activity implements OnClickListener{

    private Context mContext = null;
    private final int imgWidth = 320;
    private final int imgHeight = 320;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_show);
        mContext = this;


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String imgPath = extras.getString("filename");

        BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Bitmap bitmapm = BitmapFactory.decodeFile(imgPath, bfo);
        Bitmap resized = Bitmap.createScaledBitmap(bitmapm, imgWidth, imgHeight, true);
        imageView.setImageBitmap(resized);

        Button btn = (Button)findViewById(R.id.btn_back);
        btn.setOnClickListener(this);


    }
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back:

                Intent intent = new Intent("MainActivity");
                intent.putExtra("activity", "ImageList");

                startActivity( intent );




                break;


        }
    }
}