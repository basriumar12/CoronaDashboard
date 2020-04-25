package com.basbas.lawanqfid.utama.ui.setting;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.basbas.lawanqfid.R;
import com.basbas.lawanqfid.service.NotificationJobServiceJava;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Setting");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String preference) {

            addPreferencesFromResource(R.xml.preferences);

            }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            final String notifyKey = getString(R.string.pref_key_notification);
            if (preference.getKey().equals(notifyKey)) {
                // Check if notification setting is no then schedule notification
                boolean on = ((SwitchPreferenceCompat) preference).isChecked();
                JobScheduler jobScheduler = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    jobScheduler = getContext().getSystemService(JobScheduler.class);
                }

                if (on == true){
                    ComponentName componentName = new ComponentName(getContext(), NotificationJobServiceJava.class);
                    JobInfo info = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        info = new JobInfo.Builder(123, componentName)
                                .setPersisted(true)
                                .setPeriodic(24 * 60 * 60 * 1000L)
                                .build();
                    }

                    int resultCode = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        resultCode = jobScheduler.schedule(info);
                    }
                    if (resultCode == JobScheduler.RESULT_SUCCESS) {
                        System.out.println("sudah terjadwal");
                    } else {
                        System.out.println("gagal terjadwal");
                    }

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        jobScheduler.cancel(123);
                    }
                    System.out.println("terjadwal di cancel");
                }

            }

            return super.onPreferenceTreeClick(preference);
        }

    }




    }

