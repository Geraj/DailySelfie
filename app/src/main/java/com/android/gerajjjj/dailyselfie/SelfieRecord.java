package com.android.gerajjjj.dailyselfie;

import android.graphics.Bitmap;

/**
 * Created by Gerajjjj on 1/24/2017.
 */

public class SelfieRecord {
    private String photoUrl;
    private String extraInfo;
    private Bitmap photoBitMap;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public Bitmap getPhotoBitMap() {
        return photoBitMap;
    }

    public void setPhotoBitMap(Bitmap photoBitMap) {
        this.photoBitMap = photoBitMap;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
