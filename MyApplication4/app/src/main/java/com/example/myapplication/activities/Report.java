package com.example.myapplication.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.api.DonationApi;
import com.example.myapplication.main.DonationApp;
import com.example.myapplication.models.Donation;

import java.util.ArrayList;
import java.util.List;

public class Report extends Base implements AdapterView.OnItemClickListener {

    ListView listView;
    DonationAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        listView = findViewById(R.id.reportList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.report_swipe_refresh_layout);

        new GetAllTask(Report.this).execute("/donations");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetAllTask(Report.this).execute("/donations");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View row, int pos, long id) {
        Donation donation = adapter.donations.get(pos);
        Toast.makeText(Report.this, "Donation Data [" + donation.upvotes + "]\n" +
                "With ID of [" + donation.id + "]", Toast.LENGTH_LONG).show();

    }

    class GetAllTask extends AsyncTask<String, Void, List<Donation>> {
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
        protected List<Donation> doInBackground(String... params) {
            try {
                Log.v("donate", "Donation App Getting All Donations");
                return (List<Donation>) DonationApi.getAll((String) params[0]);
            }
            catch (Exception e) {
                Log.v("donate", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);
            //use result to calculate the totalDonated amount here
            app.donations = result;
            adapter = new DonationAdapter(context, app.donations);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(Report.this);
            mSwipeRefreshLayout.setRefreshing(false);
            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    class GetTask extends AsyncTask<String, Void, Donation> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations Details");
            this.dialog.show();
        }

        @Override
        protected Donation doInBackground(String...  params) {
            try {
                return (Donation) DonationApi.get((String) params[0], params[1]);
            } catch (Exception e) {
                Log.v("donate", "Error: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Donation result) {
            super.onPostExecute(result);
            //use result to calculate the totalDonated amount here
            Donation donation = result;

            Toast.makeText(Report.this, "Donation Data [" + donation.upvotes + "]\n" +
                    "With ID of [" + donation.id + "]", Toast.LENGTH_LONG).show();

            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    private class DeleteTask extends AsyncTask<String, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public DeleteTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Donation");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String...  params) {
            try {
                return (String) DonationApi.delete((String)  params[0],  params[1]);
            } catch (Exception e) {
                Log.v("donate", "Error: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String s = result;

            new GetAllTask(Report.this).execute("/donations");

            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    public void onDonationDelete(final Donation donation) {
        int stringId = donation.id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Donation?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure you want to Delete the \'Donation with ID\'\n ["
                + stringId + "] ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new DeleteTask(Report.this).execute("/donations", String.valueOf(donation.id));
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

    class DonationAdapter extends ArrayAdapter<Donation> {
        private Context context;
        public List<Donation> donations;

        public DonationAdapter(Context context, List<Donation> donations) {
            super(context, R.layout.row_donate, donations);
            this.context = context;
            this.donations = donations;
        }

        @Override
        public View getView(int position, View contextView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_donate, parent, false);
            Donation donation = donations.get(position);
            TextView  upvoteView = (TextView) view.findViewById(R.id.row_upvotes);
            TextView amountView = (TextView) view.findViewById(R.id.row_amount);
            TextView methodView = (TextView) view.findViewById(R.id.row_method);
            ImageView deleteView = (ImageView) view.findViewById(R.id.imgDelete);
            upvoteView.setText(String.valueOf(donation.upvotes));
            amountView.setText("$" + donation.amount);
            methodView.setText(donation.paymentmethod);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDonationDelete(donation);
                }
            });

            view.setTag(donation.id);
            return view;
        }

        @Override
        public int getCount() {
            return donations.size();
        }
    }

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
//
//    public void onDonationReset() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Delete Donation?");
//        builder.setMessage("Are you sure you want to Delete the \'Donation with ID");
//        builder.setCancelable(false);
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                new ResetTask(Report.this).execute("/donations");
//                check = 1;
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                check = 0;
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    @Override
    public void reset(MenuItem item)
    {
        onDonationReset();
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
        protected String doInBackground(Object...  params) {
            try {
                return (String) DonationApi.deleteAll((String) params[0]);
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
            new GetAllTask(Report.this).execute("/donations");
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
                new ResetTask(Report.this).execute("/donations");
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