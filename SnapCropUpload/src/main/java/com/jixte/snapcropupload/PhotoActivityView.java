package com.jixte.snapcropupload;

import android.content.DialogInterface;

/**
 *
 * Created by Zeeshan A Zakaria on 2016-04-05.
 * www.zeeshanz.com
 */
public interface PhotoActivityView {
    void startCamera();
    void openGallery();
    void cancel(DialogInterface dialog);
    void finishActivity();
    void returnImagePath(String imagePath);
    String rescaleCropAndSave();
}