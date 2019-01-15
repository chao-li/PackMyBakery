package com.clidev.packmybakery;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

public final class PackageCalculator {

    public static List<Integer> calculateBlueberry(int number) {
        int largePackSize = 8;
        int mediumPackSize = 5;
        int smallPackSize = 2;

        Double smallPrice = 9.95;
        Double mediumPrice = 16.95;
        Double largePrice = 24.95;

        return calculatePackage(number,
                largePackSize,
                mediumPackSize,
                smallPackSize,
                smallPrice,
                mediumPrice,
                largePrice);


    }

    public static List<Integer> calculatePackage(int number,
                                                 int largePackSize,
                                                 int mediumPackSize,
                                                 int smallPackSize,
                                                 Double smallPrice,
                                                 Double mediumPrice,
                                                 Double largePrice) {

        // package of 2, 5, 8

        // initializing the collection of packing option
        List<Integer> totalPack = new ArrayList<>();
        List<Integer> smallList = new ArrayList<>();
        List<Integer> mediumList = new ArrayList<>();
        List<Integer> largeList = new ArrayList<>();

        Map<String, Integer> sizeToNumberMap;

        // Try large, medium, small combination
        sizeToNumberMap = largeMediumSmallCombo(number, largePackSize, mediumPackSize, smallPackSize);

        // save total pack number and individual pack numbers to list
        saveCombo(totalPack, smallList, mediumList, largeList, sizeToNumberMap);

        // Large, small combination
        sizeToNumberMap = LargeSmallCombo(number, largePackSize, smallPackSize);

        // save total pack number and individual pack numbers to list
        saveCombo(totalPack, smallList, mediumList, largeList, sizeToNumberMap);

        // Medium, small combinations
        sizeToNumberMap = mediumSmallCombo(number, mediumPackSize, smallPackSize);

        // save total pack number and individual pack numbers to list
        saveCombo(totalPack, smallList, mediumList, largeList, sizeToNumberMap);

        // Small combinations
        sizeToNumberMap = smallCombo(number, smallPackSize);

        // save total pack number and individual pack numbers to list
        saveCombo(totalPack, smallList, mediumList, largeList, sizeToNumberMap);


        // Looking at all the various combinations, find combination that yields the cheapest and minimum number of package.
        List<Integer> finalPackage = findComboForMinimumTotalPackage(totalPack,
                smallList,
                mediumList,
                largeList,
                smallPrice,
                mediumPrice,
                largePrice);


        return finalPackage;

    }





    // Additional helper function
    @NonNull
    private static Map<String, Integer> largeMediumSmallCombo(int number, int largePackSize, int mediumPackSize, int smallPackSize) {
        /////////////////////////////////////////////////////////////
        // Try pack starting with the large size ////////////////////
        /////////////////////////////////////////////////////////////
        int smallPack = 0;
        int mediumPack = 0;
        int largePack = 0;

        // Find the modulo of 8
        int remainderFromLarge = number%largePackSize;

        // if divisible by 8, use only large package
        if (remainderFromLarge == 0) {
            smallPack = 0;
            mediumPack = 0;
            largePack = number/largePackSize;

            Timber.d("Divisible by 8, use only large package.");

        } else { // if not divisble by 8, try using smaller package to pack the remainder
            // find the modulo of 5 with the remainder
            int remainderFromMedium = remainderFromLarge % mediumPackSize;

            // if divisible by 5, use large and medium package
            if (remainderFromMedium == 0) {

                smallPack = 0;
                mediumPack = remainderFromLarge/mediumPackSize;
                largePack = number/largePackSize;

                Timber.d("Divisible by 8 and 5, use only large and medium.");
            } else {

                // find the modulo of 2 with the remainder
                int remainderFromSmall = remainderFromMedium % smallPackSize;

                // if remainderFromSmall is equal to 0 then package works
                if (remainderFromSmall == 0) {

                    largePack = number / largePackSize;
                    mediumPack = remainderFromLarge / mediumPackSize;
                    smallPack = remainderFromMedium / smallPackSize;

                    Timber.d("Divisible by 8, 5 and 2, use all 3 package.");
                } else {

                    smallPack = 0;
                    mediumPack = 0;
                    largePack = 0;
                }
            }
        }

        Map<String, Integer> sizeToNumberMap = new HashMap<>();
        sizeToNumberMap.put("small", smallPack);
        sizeToNumberMap.put("medium", mediumPack);
        sizeToNumberMap.put("large", largePack);
        return sizeToNumberMap;
    }

    @NonNull
    private static Map<String, Integer> LargeSmallCombo(int number, int largePackSize, int smallPackSize) {
        int remainderFromLarge;
        int smallPack;
        int mediumPack;
        int largePack;//Try pack starting with large then small
        /////////////////////////////////////////////////////////
        remainderFromLarge = number%largePackSize;

        int remainderFrom2 = remainderFromLarge % smallPackSize;

        if (remainderFrom2 == 0) {
            // package everything with medium and small package
            smallPack = remainderFromLarge/smallPackSize;
            mediumPack = 0;
            largePack = number/largePackSize;

        } else {

            smallPack = 0;
            mediumPack = 0;
            largePack = 0;
        }

        Map<String, Integer> sizeToNumberMap = new HashMap<>();
        sizeToNumberMap.put("small", smallPack);
        sizeToNumberMap.put("medium", mediumPack);
        sizeToNumberMap.put("large", largePack);
        return sizeToNumberMap;
    }


    @NonNull
    private static Map<String, Integer> mediumSmallCombo(int number, int mediumPackSize, int smallPackSize) {
        int smallPack;
        int mediumPack;
        int largePack;
        int remainderFrom2;
        Map<String, Integer> sizeToNumberMap;//Try pack starting with medium sized package
        /////////////////////////////////////////////////////////
        smallPack = 0;
        mediumPack = 0;
        largePack = 0;


        int remainderFrom5 = number%mediumPackSize;

        if (remainderFrom5 == 0) {
            // pack using medium size only
            mediumPack = number/5;
        } else {
            // if not divisible by 5, try package remainder with smaller package

            remainderFrom2 = remainderFrom5 % smallPackSize;

            if (remainderFrom2 == 0) {
                // package everything with medium and small package
                smallPack = remainderFrom5/smallPackSize;
                mediumPack = number/mediumPackSize;
                largePack = 0;

            } else {

                smallPack = 0;
                mediumPack = 0;
                largePack = 0;
            }

        }

        sizeToNumberMap = new HashMap<>();
        sizeToNumberMap.put("small", smallPack);
        sizeToNumberMap.put("medium", mediumPack);
        sizeToNumberMap.put("large", largePack);
        return sizeToNumberMap;
    }

    @NonNull
    private static Map<String, Integer> smallCombo(int number, int smallPackSize) {
        int smallPack;
        int mediumPack;
        int largePack;
        int remainderFrom2;
        Map<String, Integer> sizeToNumberMap;//Try starting with small sized package
        /////////////////////////////////////////////////////////
        smallPack = 0;
        mediumPack = 0;
        largePack = 0;

        remainderFrom2 = number%smallPackSize;

        if (remainderFrom2 == 0) {
            // package using small size only
            smallPack = number/smallPackSize;
            mediumPack = 0;
            largePack = 0;
        } else {
            smallPack = 0;
            mediumPack = 0;
            largePack = 0;
        }

        sizeToNumberMap = new HashMap<>();
        sizeToNumberMap.put("small", smallPack);
        sizeToNumberMap.put("medium", mediumPack);
        sizeToNumberMap.put("large", largePack);
        return sizeToNumberMap;
    }

    private static void saveCombo(List<Integer> totalPack, List<Integer> smallList, List<Integer> mediumList, List<Integer> largeList, Map<String, Integer> sizeToNumberMap) {
        int smallPack;
        int mediumPack;
        int largePack;
        smallPack = sizeToNumberMap.get("small");
        mediumPack = sizeToNumberMap.get("medium");
        largePack = sizeToNumberMap.get("large");
        if (smallPack + mediumPack + largePack != 0) {
            smallList.add(smallPack);
            mediumList.add(mediumPack);
            largeList.add(largePack);
            totalPack.add(smallPack + mediumPack + largePack);
        }
    }

    @NonNull
    private static List<Integer> findComboForMinimumTotalPackage(List<Integer> totalPack,
                                                                 List<Integer> smallList,
                                                                 List<Integer> mediumList,
                                                                 List<Integer> largeList,
                                                                 Double smallPrice,
                                                                 Double mediumPrice,
                                                                 Double largePrice) {

        // Try compare all options, and find the option with the smallest number of packages.
        List<Integer> finalPackage = new ArrayList<>();

        if (totalPack.size() > 1) {
            Integer minIndex = totalPack.indexOf(Collections.min(totalPack));
            int minValue = totalPack.get(minIndex);
            int minValueFreq = Collections.frequency(totalPack, minValue);

            Timber.d("minValueFreq: " + minValueFreq);

            if (minValueFreq > 1) {
                Timber.d("MORE THAN 1 MINIMUM PACKAGE METHOD DETECTED.");
                // compare price and get the cheapest solution
                // get all occurrence of the index
                ArrayList<Integer> indexList = new ArrayList<>();
                for (int i = 0; i < totalPack.size(); i++) {
                    if (minValue == totalPack.get(i)) {
                        indexList.add(i);
                    }
                }

                // loop thru each combination and calculate the price
                Map<Double, Integer> priceIndexMap = new HashMap<>();
                for (int index : indexList) {
                    int smallNo = smallList.get(index);
                    int mediumNo = mediumList.get(index);
                    int largeNo = largeList.get(index);

                    Double price = smallNo * smallPrice + mediumNo * mediumPrice + largeNo * largePrice;

                    priceIndexMap.put(price, index);

                    Timber.d("Price: " + price);
                }

                // find the index that corresponds to the lowest price
                Set<Double> priceSet = priceIndexMap.keySet();
                Double minPrice = Collections.min(priceSet);
                minIndex = priceIndexMap.get(minPrice);

            }


            finalPackage.add(smallList.get(minIndex));
            finalPackage.add(mediumList.get(minIndex));
            finalPackage.add(largeList.get(minIndex));
        }
        return finalPackage;
    }

}
