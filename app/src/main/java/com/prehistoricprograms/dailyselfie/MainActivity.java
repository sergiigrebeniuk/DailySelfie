package com.prehistoricprograms.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final long ALARM_INTERVAL = 2 * 60 * 1000L;

    private String mCurrentPhotoPath;
    private SelfieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new SelfieAdapter(getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.selfies);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelfieRecord selfie = (SelfieRecord)mAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), SelfieActivity.class);
                intent.putExtra(SelfieActivity.EXTRA_FILE_PATH, selfie.getPath());
                startActivity(intent);
            }
        });

        // Get the AlarmManager Service
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create an Intent to broadcast to the AlarmNotificationReceiver
        Intent mNotificationReceiverIntent = new Intent(this, AlarmNotificationReceiver.class);

        // Create an PendingIntent that holds the NotificationReceiverIntent
        PendingIntent mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, mNotificationReceiverIntent, 0);

        // Set repeating alarm
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + ALARM_INTERVAL,
                ALARM_INTERVAL,
                mNotificationReceiverPendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir != null) {
            File[] files = storageDir.listFiles();
            mAdapter.clear();
            for (File file : files) {
                SelfieRecord selfieRecord = new SelfieRecord(file.getPath());
                mAdapter.add(selfieRecord);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_camera) {
            dispatchTakePictureIntent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mAdapter.add(new SelfieRecord(mCurrentPhotoPath));
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String imageFileName = "DS-" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
