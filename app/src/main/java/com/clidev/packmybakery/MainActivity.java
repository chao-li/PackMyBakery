package com.clidev.packmybakery;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private int mVegemiteNumber;
    private int mBlueberryNumber;
    private int mCroissantNumber;

    @BindView(R.id.vegemite_scroll_et) EditText mVegemiteScrollEt;
    @BindView(R.id.blueberry_muffin_et) EditText mBlueberryMuffinEt;
    @BindView(R.id.croissant_et) EditText mCroissantEt;
    @BindView(R.id.place_order_button) Button mPlaceOrderButton;
    @BindView(R.id.loading_bar) ProgressBar mProgressBar;


    // On create is where the main logic of the program is ran.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // change actionbar title to Pack my bakery
        getSupportActionBar().setTitle("Pack My Bakery");

        // Wire up connections between the views and their variables
        ButterKnife.bind(this);

        // This function is called when the place order button is clicked
        placeOrderClicked();

    }

    // Get the inputted order number
    private void placeOrderClicked() {
        mPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, try getting the number in each field. If failed, give the variable
                // a value of 0
                try {
                    mVegemiteNumber = Integer.parseInt(mVegemiteScrollEt.getText().toString());
                } catch (Exception e) {
                    mVegemiteNumber = 0;
                }

                try {
                    mBlueberryNumber = Integer.parseInt(mBlueberryMuffinEt.getText().toString());
                } catch (Exception e) {
                    mBlueberryNumber = 0;
                }

                try {
                    mCroissantNumber = Integer.parseInt(mCroissantEt.getText().toString());
                } catch (Exception e) {
                    mCroissantNumber = 0;
                }

                // Turn on the loading icon
                mProgressBar.setVisibility(View.VISIBLE);


                // Perform the calculation in a background thread.
                new CalculateTask().execute();

            }
        });

    }


    public class CalculateTask extends AsyncTask<Void, Void,  ArrayList<ArrayList<Integer>>> {


        @Override
        protected ArrayList<ArrayList<Integer>> doInBackground(Void... voids) {

            // Call the PackageLooper class to perform calculations for the best package combinations
            ArrayList<Integer> vegemitePackage = PackageLooper.optimalVegemite(mVegemiteNumber);
            //ArrayList<Integer> blueberryPackage = PackageLooper.calculateBlueberry(mBlueberryNumber);
            //ArrayList<Integer> croissantPackage = PackageLooper.calculateCroissant(mCroissantNumber);

            // testing knapsack algorithm
            ArrayList<Integer> blueberryPackage = PackageLooper.optimalNumberBlueberry(mBlueberryNumber);
            ArrayList<Integer> croissantPackage = PackageLooper.optimalNumberCroissant(mCroissantNumber);



            // package these results together so that they may all be returned to the main thread.
            ArrayList<ArrayList<Integer>> allPackage = new ArrayList<>();
            allPackage.add(vegemitePackage);
            allPackage.add(blueberryPackage);
            allPackage.add(croissantPackage);

            return allPackage;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<Integer>> arrayLists) {
            // back on the main thread, turn off the loading icon.
            mProgressBar.setVisibility(View.INVISIBLE);


            // unpack the results that just got passed in.
            ArrayList<Integer> vegemitePackage = arrayLists.get(0);
            ArrayList<Integer> blueberryPackage = arrayLists.get(1);
            ArrayList<Integer> croissantPackage = arrayLists.get(2);

            // create the intent to take us to the results screen
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);

            // pack the results to be sent to the results screen.
            intent.putIntegerArrayListExtra("VEGEMITE", vegemitePackage);
            intent.putIntegerArrayListExtra("BLUEBERRY", blueberryPackage);
            intent.putIntegerArrayListExtra("CROISSANT", croissantPackage);

            // navigate to the results screen
            startActivity(intent);

        }
    }
}
