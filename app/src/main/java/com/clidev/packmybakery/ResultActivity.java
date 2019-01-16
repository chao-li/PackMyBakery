package com.clidev.packmybakery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ResultActivity extends AppCompatActivity {
    ArrayList<Integer> mVegemitePackage;
    ArrayList<Integer> mBlueberryPackage;
    ArrayList<Integer> mCroissantPackage;

    @BindView(R.id.vegemite_tv) TextView mVegemiteTv;
    @BindView(R.id.blueberry_tv) TextView mBlueberryTv;
    @BindView(R.id.croissant_tv) TextView mCroissantTv;

    // This is where the logic of the result screen is ran
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // change actionbar title to pack my bakery, and also add in a back button.
        getSupportActionBar().setTitle("Pack My Bakery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // make connections between views and their corresponding variables.
        ButterKnife.bind(this);

        // grab the results that were passed through
        Intent intent = getIntent();
        mVegemitePackage = intent.getIntegerArrayListExtra("VEGEMITE");
        mBlueberryPackage = intent.getIntegerArrayListExtra("BLUEBERRY");
        mCroissantPackage = intent.getIntegerArrayListExtra("CROISSANT");

        // following three functions populates the 3 text view with the required results.
        setVegemiteTv(mVegemitePackage);
        setBlueberryTv(mBlueberryPackage);
        setCroissantTv(mCroissantPackage);
        Timber.d(mVegemitePackage.size() + "");
    }

    // This block indicates that if back button is pressed, we move back to the previous screen.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Set the text view for the vegemite scroll result
    private void setVegemiteTv(ArrayList<Integer> vegemitePackage) {
        if (vegemitePackage.size() >= 1) {

            // Get the results and compile the strings that will be used in the result.
            Integer small = vegemitePackage.get(0);
            Integer medium = vegemitePackage.get(1);
            Integer total = small * 3 + medium * 5;
            Double price = small * 6.99 + medium * 8.99;

            String line0 = total + " X Vegemite Scroll: $" + String.format ("%.2f", price) + "\n";
            String line1 = small + " X 3Pack $6.99\n";
            String line2 = medium + " X 5pack $8.99\n";

            // set the strings into the text view.
            mVegemiteTv.setText(line0 + line1 + line2);
        } else {
            // if no results were passed in, that means it isn't possible to get that combination.
            mVegemiteTv.setText("Cannot pack vegemite scrolls to this exact number, try a different amount.");
        }
    }

    // Set the text view for the blueberry muffin result
    private void setBlueberryTv(ArrayList<Integer> blueberryPackage) {
        if (blueberryPackage.size() >= 1) {

            // Get the results and compile the strings that will be used in the result.
            Integer small = blueberryPackage.get(0);
            Integer medium = blueberryPackage.get(1);
            Integer large = blueberryPackage.get(2);
            Integer total = small * 2 + medium * 5 + large * 8;
            Double price = small * 9.95 + medium * 16.95 + large * 24.95;

            String line0 = total + " X Blueberry Muffin: $" + String.format ("%.2f", price) + "\n";
            String line1 = small + " X 2pack $9.95\n";
            String line2 = medium + " X 5pack $16.95\n";
            String line3 = large + " X 8pack $24.95\n";

            // set the strings into the text view.
            mBlueberryTv.setText(line0 + line1 + line2 + line3);
        } else {
            // if no results were passed in, that means it isn't possible to get that combination.
            mBlueberryTv.setText("Cannot pack blueberry muffins to this exact number, try a different amount.");
        }
    }

    // Set the text view for the blueberry muffin result
    private void setCroissantTv(ArrayList<Integer> croissantPackage) {
        if (croissantPackage.size() >= 1) {
            Integer small = croissantPackage.get(0);
            Integer medium = croissantPackage.get(1);
            Integer large = croissantPackage.get(2);
            Integer total = small * 3 + medium * 5 + large * 9;
            Double price = small * 5.95 + medium * 9.95 + large * 16.99;

            String line0 = total + " X Croissant: $" + String.format ("%.2f", price) + "\n";
            String line1 = small + " X 3pack $9.95\n";
            String line2 = medium + " X 5pack $16.95\n";
            String line3 = large + " X 9pack $24.95\n";

            // set the strings into the text view.
            mCroissantTv.setText(line0 + line1 + line2 + line3);
        } else {
            // if no results were passed in, that means it isn't possible to get that combination.
            mCroissantTv.setText("Cannot pack croissant to this exact number, try a different amount.");
        }
    }
}
