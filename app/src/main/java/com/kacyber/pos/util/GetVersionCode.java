package com.kacyber.pos.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.kacyber.pos.ui.base.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class GetVersionCode extends AsyncTask<Void, String, String> {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity baseActivity;
    String currentVersion;
    ProgressDialog progressDialog;

    public GetVersionCode(BaseActivity baseActivity, String currentVersion) {
        this.baseActivity = baseActivity;
        this.currentVersion = currentVersion;
        progressDialog = new ProgressDialog(baseActivity);
        progressDialog.setTitle("Please Wait..");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        String newVersion = null;

        try {
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + baseActivity.getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVersion;
    }


    @Override

    protected void onPostExecute(String onlineVersion) {
        super.onPostExecute(onlineVersion);
        progressDialog.dismiss();
        Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        if (onlineVersion != null && !onlineVersion.isEmpty()) {

            if (Float.valueOf(currentVersion.replace(".", "")) < Float.valueOf(onlineVersion.replace(".", ""))) {
                //show anything
                new AlertDialog.Builder(baseActivity)
                        .setTitle("Updated app available!")
                        .setMessage("Want to update app?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                final String appPackageName = baseActivity.getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    baseActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (ActivityNotFoundException anfe) {
                                    baseActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        })
                        .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else {
                Toast.makeText(baseActivity, "No Update Found", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(baseActivity, "No Update Found", Toast.LENGTH_SHORT).show();

        }

    }
}