package com.example.ashish.attemptone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   private ImageView image;
    private String imageurl= "http://media.tumblr.com/tumblr_m6v66lJ5K61r0taux.jpg";

    Bitmap bmp;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= (ImageView)findViewById(R.id.imageView);
        final Button saveimage= (Button)findViewById(R.id.savebutton);
        final Button imagedownload =(Button)findViewById(R.id.downloadButton);
        final Button rotate= (Button)findViewById(R.id.Roatatebutton);



        imagedownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagedownload.setBackgroundColor(Color.YELLOW);
                new ImageDownloader().execute(imageurl);
            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate.setBackgroundColor(Color.GREEN);
                Intent rotateintent= new Intent(MainActivity.this, RotateActivity.class);
                startActivity(rotateintent);
            }
        });

       saveimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimage.setBackgroundColor(Color.RED);
                Intent view = new Intent(MainActivity.this, FinalActivity.class);
                startActivity(view);
            }
        });



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





    public class ImageDownloader extends AsyncTask <String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... param) {
            return downloadBitmap(param[0]);
        }


      /*  @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Log.i("Async-Example", "onPreExecute Called");
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading image");
            dialog.show();
        }*/

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("Async-Example", "onPostExecute Called");
            if (result != null) {
                //Matrix m= new Matrix();
                //  m.postRotate(45);
                // image.setImageMatrix(m);
                image.setImageBitmap(result);
                /* stored in cache */
                 File cacheDir= getCacheFolder(MainActivity.this);
                File cacheFile= new File(cacheDir,"local.jpg");


               /* File filename;
                String path = Environment.getExternalStorageDirectory().toString();
                filename = new File(path + "/smile.jpg");
                filename.mkdirs();
               if (!filename.exists()) {
                } else {
                    filename.delete();
                }*/


                try {
                    FileOutputStream out = null;
                    out = new FileOutputStream(cacheFile);
                    if (bmp != null) {
                        bmp.compress(Bitmap.CompressFormat.JPEG, 50, out);

                        out.close();
                       // MediaStore.Images.Media.insertImage(getContentResolver(), getCacheDir().getAbsolutePath(), cacheFile.getName(), cacheFile.getName());
                        Toast.makeText(getApplicationContext(), "File is saved in" + cacheFile, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "bmp is null", Toast.LENGTH_LONG).show();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
              //  dialog.dismiss();
        }}



        private Bitmap downloadBitmap(String downloadurl) {
           // Bitmap bitmap=null;
            try{
                URL url= new URL(downloadurl);
                HttpURLConnection connection= (HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input= connection.getInputStream();
                  bmp= BitmapFactory.decodeStream(input);
                //bmp= bitmap;
                return bmp;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //return bitmap;



        }
    }}

/* public File getCacheFolder(Context context){
     File cacheDir= null;
     if(Environment.getExternalStorageState().equals(Environment.Media_Mounted)){
       cacheDir=  new File(Environment.getExternalStorageDirectory(), "cachefolder");
         if(!cacheDir.isDirectory())
         {  cacheDir.mkdirs();
         }
      }
      if(!cacheDir.isDirectory()){
      cacheDir=context.getCacheDir();
      }
      return cacheDir;

  }
 */

      /* void saveImage(){

          File filename;
           try{

               String path= Environment.getExternalStorageDirectory().toString();


               //new File(path + "/mvc/mvc").mkdirs();
               filename= new File(path + "/smile.jpg");
               filename.mkdirs();


               if(filename.exists()){filename.delete();
                   FileOutputStream out= new FileOutputStream(filename);
               if(bmp!=null) {
                   bmp.compress(Bitmap.CompressFormat.JPEG, 1000, out);
                   out.flush();
                   out.close();
                   MediaStore.Images.Media.insertImage(getContentResolver(), filename.getAbsolutePath(), filename.getName(), filename.getName());
                   Toast.makeText(getApplicationContext(), "File is saved in" + filename, Toast.LENGTH_LONG).show();
               }
               else{
                   Toast.makeText(getApplicationContext(),"bmp is null", Toast.LENGTH_LONG).show();
               }



           }catch(Exception e){
               e.printStackTrace();
           }*/





