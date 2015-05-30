package io.github.wapmorgan.onecloudmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by Сергей on 30.05.2015.
 */
public class LoginTask extends AsyncTask<Void, Void, Boolean> {
    protected Activity senderActivity;


    public LoginTask(Activity prevActivity) {
        senderActivity = prevActivity;
    }

    protected Boolean doInBackground(Void... params) {
        return OneCloudApi.checkKey();
    }

    protected void onPostExecute(Boolean result) {
        if (!result) {
            Intent intent = new Intent(senderActivity, LoginActivity.class);
            senderActivity.startActivity(intent);
        }
    }
}
