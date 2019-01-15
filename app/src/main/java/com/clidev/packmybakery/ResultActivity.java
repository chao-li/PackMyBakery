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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mVegemitePackage = intent.getIntegerArrayListExtra("VEGEMITE");
        mBlueberryPackage = intent.getIntegerArrayListExtra("BLUEBERRY");
        mCroissantPackage = intent.getIntegerArrayListExtra("CROISSANT");

        setVegemiteTv(mVegemitePackage);
        setBlueberryTv(mBlueberryPackage);
        setCroissantTv(mCroissantPackage);
        Timber.d(mVegemitePackage.size() + "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setVegemiteTv(ArrayList<Integer> vegemitePackage) {
        if (vegemitePackage.size() >= 1) {
            Integer small = vegemitePackage.get(0);
            Integer medium = vegemitePackage.get(1);
            Integer total = small * 3 + medium * 5;
            Double price = small * 6.99 + medium * 8.99;

            String line0 = total + " X Vegemite Scroll: $" + String.format ("%.2f", price) + "\n";
            String line1 = small + " X 3Pack $6.99\n";
            String line2 = medium + " X 5pack $8.99\n";

            mVegemiteTv.setText(line0 + line1 + line2);
        } else {
            mVegemiteTv.setText("Cannot pack this exact number, try a different combination.");
        }
    }

    private void setBlueberryTv(ArrayList<Integer> blueberryPackage) {
        if (blueberryPackage.size() >= 1) {
            Integer small = blueberryPackage.get(0);
            Integer medium = blueberryPackage.get(1);
            Integer large = blueberryPackage.get(2);
            Integer total = small * 2 + medium * 5 + large * 8;
            Double price = small * 9.95 + medium * 16.95 + large * 24.95;

            String line0 = total + " X Blueberry Muffin: $" + String.format ("%.2f", price) + "\n";
            String line1 = small + " X 2pack $9.95\n";
            String line2 = medium + " X 5pack $16.95\n";
            String line3 = large + " X 8pack $24.95\n";

            mBlueberryTv.setText(line0 + line1 + line2 + line3);
        } else {
            mBlueberryTv.setText("Cannot pack this exact number, try a different combination.");
        }
    }

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

            mCroissantTv.setText(line0 + line1 + line2 + line3);
        } else {
            mCroissantTv.setText("Cannot pack this exact number, try a different combination.");
        }
    }
}
