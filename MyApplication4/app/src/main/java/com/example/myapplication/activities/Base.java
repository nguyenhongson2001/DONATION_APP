package com.example.myapplication.activities;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.api.DonationApi;
import com.example.myapplication.main.DonationApp;

import com.example.myapplication.R;
import com.example.myapplication.models.Donation;

import java.util.List;

public class Base extends AppCompatActivity {
    public DonationApp app;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);

        app = (DonationApp) getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetAllTask(this).execute("/donations");
    }

    public class GetAllTask extends AsyncTask<String, Void, List<Donation>> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetAllTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations List");
            this.dialog.show();
        }

        @Override
        protected List<Donation> doInBackground(String...  params) {
            try {
                Log.v("donate", "Donation App Getting All Donations");
                return (List<Donation>) DonationApi.getAll((String)  params[0]);
            } catch (Exception e) {
                Log.v("donate", "Error: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);
            //use result to calculate the totalDonated amount here
            int sum = 0;
            app.donations.clear();
            for (int i = 0; i < result.size(); i++) {
                sum += result.get(i).amount;
                app.donations.add(result.get(i));
            }
            Log.v("Donate", String.valueOf(sum));
            app.totalDonated = sum;
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_content_main);
            if (fragment != null) {
                FragmentManager fragmentManager1 = fragment.getChildFragmentManager();
                Fragment fragment1 = fragmentManager1.getFragments().get(0);
                if (fragment1 != null) {
                    fragment1.onResume();
                }
            }

            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);
        MenuItem report = menu.findItem(R.id.menuReport);
        MenuItem donate = menu.findItem(R.id.menuDonate);
        MenuItem reset = menu.findItem(R.id.menuReset);
        if (app.donations.isEmpty()) {
            report.setEnabled(false);
            reset.setEnabled(false);
        } else {
            report.setEnabled(true);
            donate.setEnabled(true);
        }

        if (this instanceof Donate) {
            donate.setVisible(false);
            if (app.donations.isEmpty()) {
                report.setVisible(true);
                reset.setEnabled(true);
            }
        } else {
            report.setVisible(false);
            donate.setVisible(true);
            reset.setVisible(true);
        }
        return true;
    }
    public void settings(MenuItem item) {
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }
    public void report(MenuItem item) {
        startActivity (new Intent(this, Report.class));
    }
    public void donate(MenuItem item) {
        startActivity (new Intent(this, Donate.class));
    }
    public void reset(MenuItem item) {
//        onDonationReset();
//        new ResetTask(this).execute("/donations");
//        onResume();
//        Report.onDonationReset();
//        if (Report.check == 1) {
//
//            Report.check = 0;
//        }
//        startActivity (new Intent(this, Report.class));
    }

    private class ResetTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public ResetTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Resetting Donation...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... strings) {
            try {
                return (String) DonationApi.deleteAll((String) strings[0]);
            } catch (Exception e) {
                Log.v("donate", "Error: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            app.totalDonated = 0;
            FirstFragment.progressBar.setProgress(app.totalDonated);
            String totalDonatedStr = "$" + app.totalDonated;
            FirstFragment.amountTotal.setText(totalDonatedStr);
            new GetAllTask(Base.this).execute("/donations");
            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    public void onDonationReset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Donation?");
        builder.setMessage("Are you sure you want to Delete All the Donations");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new ResetTask(Base.this).execute("/donations");
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
