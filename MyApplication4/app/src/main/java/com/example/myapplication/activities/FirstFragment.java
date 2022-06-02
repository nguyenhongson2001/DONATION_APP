package com.example.myapplication.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.api.DonationApi;
import com.example.myapplication.databinding.FragmentFirstBinding;
import com.example.myapplication.main.DonationApp;
import com.example.myapplication.models.Donation;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private Button donateButton;
    private RadioGroup paymentMethod;
    public static ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    public static TextView amountTotal;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        amountTotal.setText("$" +  ((Donate) getActivity()).app.totalDonated);
        progressBar.setProgress( ((Donate) getActivity()).app.totalDonated);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donateButton = binding.donateButton;
        paymentMethod = binding.paymentMethod;
        progressBar = binding.progressBar;
        amountPicker = binding.amountPicker;
        amountText = binding.paymentAmount;
        amountTotal = binding.totalSoFar;
        progressBar.setMax(10000);
        if (donateButton != null) {
            Log.v("Donate", "Really got the donate button");
        }

        donateButton.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int donatedAmount = amountPicker.getValue();
                int radioId = paymentMethod.getCheckedRadioButtonId();
                String method = radioId == R.id.Paypal ? "PayPal" : "Direct";
                if (donatedAmount == 0) {
                    String text = amountText.getText().toString();
                    if (!text.equals("")) {
                        donatedAmount = Integer.parseInt(text);
                    }
                }
                if (donatedAmount > 0) {
                    Donation donation = new Donation(0, donatedAmount, method);
                    boolean check = ((Donate) getActivity()).app.newDonation(donation);
                    if (!check) {
                        new InsertTask(getActivity()).execute("/donations", donation);
                        int totalDonated = ((Donate) getActivity()).app.totalDonated;
                        progressBar.setProgress(totalDonated);
                        amountTotal.setText("$" + totalDonated);
                    } else {
                        return;
                    }
                }
            }
        });

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class InsertTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public InsertTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Saving Donation...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object...  params) {
            try {
                return (String) DonationApi.insert((String)  params[0], (Donation)  params[1]);
            } catch (Exception e) {
                Log.v("donate", "Error: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int totalDonated =  ((Donate) getActivity()).app.totalDonated;
            progressBar.setProgress(totalDonated);
            amountTotal.setText("$" + totalDonated);
            if (dialog.isShowing()) dialog.dismiss();
        }
    }

}