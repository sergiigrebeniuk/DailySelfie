package com.prehistoricprograms.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class SelfieRecord {
    private String mPath;

    public SelfieRecord(String path) {
        mPath = path;
    }

    public String getName() {
        File file = new File(mPath);
        Date lastModDate = new Date(file.lastModified());

        return DateFormat.getDateTimeInstance().format(lastModDate);
    }

    public String getPath() {
        return mPath;
    }

    public Bitmap getScaledSelfie(int width, int height) {
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mPath), width, height);
    }
}
