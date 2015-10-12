package com.example.ashish.attemptone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class FinalActivity extends AppCompatActivity {

    ImageView beforeRotateView, afterrotateview;

    Button originalimagebutton, rotateimagebutton;
    String originalpath, rotatepath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        beforeRotateView =(ImageView)findViewById(R.id.imageone);
        afterrotateview=(ImageView)findViewById(R.id.imagetwo);
        originalimagebutton=(Button)findViewById(R.id.orgbutton);
        rotateimagebutton=(Button)findViewById(R.id.rotbutton);
        File cacheDir= getCacheFolder(FinalActivity.this);
        File cacheFile= new File(cacheDir,"local.jpg");
        //File filename= new File(Environment.getRootDirectory()+"/cachefolder/local.jpg");
        originalpath= cacheFile.getAbsolutePath();
        File cacheDire= getCacheFolder(FinalActivity.this);
        File cacheFilefinal= new File(cacheDir,"local2.jpg");
       // File file= new File(Environment.getRootDirectory()+"/cachefolder/local2.jpg");
        rotatepath= cacheFilefinal.getAbsolutePath();

        originalimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalimagebutton.setBackgroundColor(Color.BLUE);
              new OriginalDownloader().execute(originalpath);
            }
        });

        rotateimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                rotateimagebutton.setBackgroundColor(Color.GREEN);
               new RotateDownloader().execute(rotatepath);
            }
        });

    }
    public class OriginalDownloader extends AsyncTask <String, Void,Bitmap >{
        @Override
        protected Bitmap doInBackground(String... params) {
            return DownloadOriginal(params[0]);
        }
        @Override
        protected void onPostExecute(Bitmap results){
            if(results !=null) {
                beforeRotateView.setImageBitmap(results);
            }
        }
    }

    public Bitmap DownloadOriginal(String pathname){
        Bitmap originalbitmap;
        originalbitmap= BitmapFactory.decodeFile(pathname);
        return originalbitmap;
    }

    public class RotateDownloader extends AsyncTask <String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadRotate(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result){
            if(result !=null) {
                afterrotateview.setImageBitmap(result);
            }
        }
    }

    public Bitmap downloadRotate(String pathname){
        Bitmap rotateresult;
        rotateresult=BitmapFactory.decodeFile(pathname);
        return rotateresult;
    }

    public File getCacheFolder(Context context){
        File cacheDir= null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            cacheDir=  new File(Environment.getExternalStorageDirectory(),"CacheFolder");
            if(!cacheDir.isDirectory())
            {  cacheDir.mkdirs();
            }
        }
        if (cacheDir != null) {
            if(!cacheDir.isDirectory()){
                cacheDir=context.getCacheDir();
            }
        }
        return cacheDir;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
