package com.android.gerajjjj.dailyselfie;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Gerajjjj on 1/24/2017.
 */
public class MainActivity extends AppCompatActivity {

    // array of supported extensions
    static final String[] EXTENSIONS = new String[]{
            "gif", "png", "bmp", "jpeg", "jpg"
    };
    public static final String PATH = "PATH";
    public static final String NAME = "NAME";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int THUMBNAIL_SIZE = 256;
    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent;
    public static final long INTERVAL_TWO_MINUTES = 2 * 60 * 1000;
    public static final int MY_PERMISSIONS_REQUEST = 1;
    private SelfieViewAdapter selfieViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_main);
        selfieViewAdapter = new SelfieViewAdapter(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(selfieViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelfieRecord record = (SelfieRecord) selfieViewAdapter.getItem(position);
                record.getPhotoUrl();
                Intent intent = new Intent(MainActivity.this, ShowSelfieActivity.class);
                intent.putExtra(PATH, record.getPhotoUrl());
                intent.putExtra(NAME, record.getExtraInfo());
                startActivity(intent);
            }
        });
        File dir = new File(getGalleryPath());
        //create directory if not exists
        if (dir == null || !dir.isDirectory()) {
            dir.mkdirs();
        }
        boolean b = dir.isDirectory();
        File[] filelist = dir.listFiles(IMAGE_FILTER);
        if (filelist != null) {
            for (File f : filelist) { // load files in the directiory
                Bitmap imageBitmap = BitmapFactory.decodeFile(f.getPath());
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
                SelfieRecord record = new SelfieRecord();
                record.setPhotoBitMap(imageBitmap);
                record.setPhotoUrl(f.getPath());
                record.setExtraInfo(f.getName());
                selfieViewAdapter.add(record);
            }
        }

        // Get the AlarmManager Service
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Create an Intent to broadcast to the AlarmNotificationReceiver
        mNotificationReceiverIntent = new Intent(MainActivity.this,
                SelfieAlarmReceiver.class);
        // Create an PendingIntent that holds the NotificationReceiverIntent
        PendingIntent mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, mNotificationReceiverIntent, 0);
        // Set repeating alarm
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + INTERVAL_TWO_MINUTES,
                INTERVAL_TWO_MINUTES,
                mNotificationReceiverPendingIntent);

    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    /**
     * Get path to galery
     *
     * @return
     */
    private static String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/Selfie/";
    }

    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            FileOutputStream out = null;
            try {
                // save and add file to list
                Date date = new Date();
                String name = "Selfie" + date.getTime() + ".png";
                out = new FileOutputStream(getGalleryPath() + name);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
                SelfieRecord record = new SelfieRecord();
                record.setPhotoUrl(getGalleryPath() + name);
                record.setExtraInfo(name);
                record.setPhotoBitMap(imageBitmap);
                selfieViewAdapter.add(record);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
