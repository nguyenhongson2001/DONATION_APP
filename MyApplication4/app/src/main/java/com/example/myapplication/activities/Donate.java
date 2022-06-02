package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class Donate extends Base {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button donateButton;
    private RadioGroup paymentMethod;
    public static ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    public static TextView amountTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void reset(MenuItem item)
    {
        onDonationReset();
    }
//
//    private class InsertTask extends AsyncTask<Object, Void, String> {
//        protected ProgressDialog dialog;
//        protected Context context;
//        public InsertTask(Context context)
//        {
//            this.context = context;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            this.dialog = new ProgressDialog(context, 1);
//            this.dialog.setMessage("Saving Donation....");
//            this.dialog.show();
//        }
//        @Override
//        protected String doInBackground(Object... params) {
//            String res = null;
//            try {
//                Log.v("donate", "Donation App Inserting");
//            }
//            catch(Exception e)
//            {
//                Log.v("donate","ERROR : " + e);
//                e.printStackTrace();
//            }
//            return res;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//    }
//
//    public class ResetTask extends AsyncTask<Object, Void, String> {
//        protected ProgressDialog dialog;
//        protected Context context;
//
//        public ResetTask(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            this.dialog = new ProgressDialog(context, 1);
//            this.dialog.setMessage("Resetting Donation...");
//            this.dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(Object... strings) {
//            try {
//                return (String) DonationApi.deleteAll((String) strings[0]);
//            } catch (Exception e) {
//                Log.v("donate", "Error: " + e);
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            app.totalDonated = 0;
//            FirstFragment.progressBar.setProgress(app.totalDonated);
//            String totalDonatedStr = "$" + app.totalDonated;
//            FirstFragment.amountTotal.setText(totalDonatedStr);
//            if (dialog.isShowing()) dialog.dismiss();
//        }
//    }

}