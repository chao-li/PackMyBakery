package com.clidev.packmybakery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public final class PackageCalculator {
    public static List<Integer> calculateBlueberry(int number) {
        // package of 2, 5, 8
        int pack2;
        int pack5;
        int pack8;

        List<Integer> totalPack = new ArrayList<Integer>();
        List<Integer> smallList = new ArrayList<Integer>();
        List<Integer> mediumList = new ArrayList<Integer>();
        List<Integer> largeList = new ArrayList<Integer>();

        /////////////////////////////////////////////////////////////
        // Try pack starting with the large size ////////////////////
        /////////////////////////////////////////////////////////////

        // Find the modulo of 8
        int remainderFrom8 = number%8;

        // if divisible by 8, use only large package
        if (remainderFrom8 == 0) {
            pack2 = 0;
            pack5 = 0;
            pack8 = number/8;

            Timber.d("Divisible by 8, use only large package.");

        } else { // if not divisble by 8, try using smaller package to pack the remainder
            // find the modulo of 5 with the remainder
            int remainderFrom5 = remainderFrom8 % 5;

            // if divisible by 5, use large and medium package
            if (remainderFrom5 == 0) {

                pack2 = 0;
                pack5 = remainderFrom8/5;
                pack8 = number/8;

                Timber.d("Divisible by 8 and 5, use only large and medium.");
            } else {

                // find the modulo of 2 with the remainder
                int remainderFrom2 = remainderFrom5 % 2;

                // if remainderFrom2 is equal to 0 then package works
                if (remainderFrom2 == 0) {

                    pack8 = number / 8;
                    pack5 = remainderFrom8 / 5;
                    pack2 = remainderFrom5 / 2;

                    Timber.d("Divisible by 8, 5 and 2, use all 3 package.");
                } else {

                    pack2 = 0;
                    pack5 = 0;
                    pack8 = 0;
                }
            }
        }

        // Add total package into the totalPack list
        if (pack2 + pack5 + pack8 != 0) {
            smallList.add(pack2);
            mediumList.add(pack5);
            largeList.add(pack8);
            totalPack.add(pack2 + pack5 + pack8);
        }

        //Try pack starting with large then small
        /////////////////////////////////////////////////////////
        pack2 = 0;
        pack5 = 0;
        pack8 = 0;



        remainderFrom8 = number%8;

        int remainderFrom2 = remainderFrom8 % 2;

        if (remainderFrom2 == 0) {
            // package everything with medium and small package
            pack2 = remainderFrom8/2;
            pack5 = 0;
            pack8 = number/8;

        } else {

            pack2 = 0;
            pack5 = 0;
            pack8 = 0;
        }

        // Add total package into the totalPack list
        if (pack2 + pack5 + pack8 != 0) {
            smallList.add(pack2);
            mediumList.add(pack5);
            largeList.add(pack8);
            totalPack.add(pack2 + pack5 + pack8);
        }


        //Try pack starting with medium sized package
        /////////////////////////////////////////////////////////
        pack2 = 0;
        pack5 = 0;
        pack8 = 0;


        int remainderFrom5 = number%5;

        if (remainderFrom5 == 0) {
            // pack using medium size only
            pack5 = number/5;
        } else {
            // if not divisible by 5, try package remainder with smaller package

            remainderFrom2 = remainderFrom5 % 2;

            if (remainderFrom2 == 0) {
                // package everything with medium and small package
                pack2 = remainderFrom5/2;
                pack5 = number/5;
                pack8 = 0;

            } else {

                pack2 = 0;
                pack5 = 0;
                pack8 = 0;
            }

        }

        // add to totalPack list
        if (pack2 + pack5 + pack8 != 0) {
            smallList.add(pack2);
            mediumList.add(pack5);
            largeList.add(pack8);
            totalPack.add(pack2 + pack5 + pack8);
        }


        //Try starting with small sized package
        /////////////////////////////////////////////////////////
        pack2 = 0;
        pack5 = 0;
        pack8 = 0;

        remainderFrom2 = number%2;

        if (remainderFrom2 == 0) {
            // package using small size only
            pack2 = number/2;
            pack5 = 0;
            pack8 = 0;
        } else {
            pack2 = 0;
            pack5 = 0;
            pack8 = 0;
        }

        if (pack2 + pack5 + pack8 != 0) {
            smallList.add(pack2);
            mediumList.add(pack5);
            largeList.add(pack8);
            totalPack.add(pack2 + pack5 + pack8);
        }


        // Try compare all options, and find the option with the smallest number of packages.
        if (totalPack.isEmpty() != false) {
            int minIndex = totalPack.indexOf(Collections.min(totalPack));
            int minValue = totalPack.get(minIndex);
            int minValueFreq = Collections.frequency(totalPack, minValue);

            Timber.d("minValueFreq: " + minValueFreq);

            if (minValueFreq > 1) {
                // compare price and get the cheapest solution
                // get all occurence of the index
                ArrayList<Integer> indexList = new ArrayList<>();
                for (int i = 0; i < totalPack.size(); i++) {
                    if(minValue == totalPack.get(i)) {
                        indexList.add(i);
                    }
                }

                // loop thru each combination and calculate the price

            }

            List<Integer> finalPackage = new ArrayList<>();
            finalPackage.add(smallList.get(minIndex));
            finalPackage.add(mediumList.get(minIndex));
            finalPackage.add(largeList.get(minIndex));
        } else {
            return new ArrayList<>();
        }
    }
}
