package com.example.ashish.attemptone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RotateActivity extends AppCompatActivity {
    ImageView imageView;
    TextView message;
    EditText angleValue;
    Button backButton, rotateButton;
    String path;
    Bitmap bmp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        imageView = (ImageView) findViewById(R.id.image_view);
        message = (TextView) findViewById(R.id.text_rotate);
        angleValue = (EditText) findViewById(R.id.edit_angle);
        backButton = (Button) findViewById(R.id.back_button);
        rotateButton = (Button) findViewById(R.id.rotate_button);

        File filename = new File(Environment.getExternalStorageDirectory() + "/smile.jpg");
        path = filename.getAbsolutePath();
        //final String value= angleValue.getText().toString();

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateButton.setBackgroundColor(Color.GREEN);
                new ImageRotate().execute(path);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton.setBackgroundColor(Color.DKGRAY);
                Intent back = new Intent(RotateActivity.this, MainActivity.class);
                startActivity(back);
            }
        });


    }

    public class ImageRotate extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                String value= angleValue.getText().toString();
                float percentage= Float.parseFloat(value);


                int width= result.getWidth();
                int height= result.getHeight();
                float scale= (1-(percentage/100));
                int newWidth;
                  newWidth = (int)(width*scale);
                int newHeight;
                newHeight = (int)(height*scale);
               // Matrix m = new Matrix();
               // m.postScale(newHeight,newWidth);

                Bitmap newMap;
                newMap = Bitmap.createScaledBitmap(result,newWidth, newHeight,false);
                imageView.setImageBitmap(newMap);


               // Matrix matrix = new Matrix();
                //String value = angleValue.getText().toString();
                //float degree = Float.parseFloat(value);
                //matrix.postRotate(degree)Bitmap newmap = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
                //imageview.setImageBitmap(newmap);
            /* stored in cache */
                File cacheDir = getCacheFolder(RotateActivity.this);
                File cacheFile = new File(cacheDir, "local2.jpg");
                try {
                    FileOutputStream out = null;
                    out = new FileOutputStream(cacheFile);
                    if (newMap != null) {
                       newMap.compress(Bitmap.CompressFormat.JPEG,100,out);

                        out.close();
                        // MediaStore.Images.Media.insertImage(getContentResolver(), getCacheDir().getAbsolutePath(), cacheFile.getName(), cacheFile.getName());
                        Toast.makeText(getApplicationContext(), "File is saved in" + cacheFile, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "newmap is null", Toast.LENGTH_LONG).show();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        public Bitmap downloadBitmap(String filename) {
            bmp = BitmapFactory.decodeFile(filename);
            return bmp;
        }

        public File getCacheFolder(Context context) {
            File cacheDir;
            cacheDir = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
                if (!cacheDir.isDirectory()) {
                    cacheDir.mkdirs();
                }
            }
            if (!cacheDir.isDirectory()) {
                cacheDir = context.getCacheDir(); //get system cache folder//
            }

            return cacheDir;
        }
    }
}
