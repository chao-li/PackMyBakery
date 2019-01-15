package com.clidev.packmybakery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

public final class PackageLooper {

    public static ArrayList<Integer> calculateVegemite(int number) {
        Integer mediumPackSize = 5;
        Integer smallPackSize = 3;

        Double smallPrice = 6.99;
        Double mediumPrice = 8.99;

        List<Integer> smallPackNo = new ArrayList<>();
        List<Integer> mediumPackNo = new ArrayList<>();
        List<Integer> totalPackNo = new ArrayList<>();

        compileAllPossibleCombinationsVegemite(number, mediumPackSize, smallPackSize,
                smallPackNo, mediumPackNo, totalPackNo);

        ArrayList<Integer> finalPackage = new ArrayList<>();

        findMinimumPackageCombinationVegemite(smallPrice, mediumPrice, smallPackNo, mediumPackNo, totalPackNo, finalPackage);

        return finalPackage;


    }

    private static void compileAllPossibleCombinationsVegemite(int number, int mediumPackSize, int smallPackSize, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> totalPackNo) {
        int xlim = number/smallPackSize;
        int ylim = number/mediumPackSize;

        for (int x = 0; x <= xlim; x++) {
            for (int y = 0; y <= ylim; y++) {

                int totalProduct = smallPackSize*x + mediumPackSize*y;

                if (totalProduct == number && totalProduct != 0) {
                    smallPackNo.add(x);
                    mediumPackNo.add(y);

                    totalPackNo.add(x + y);
                }

            }
        }
    }

    private static void findMinimumPackageCombinationVegemite(Double smallPrice, Double mediumPrice, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> totalPackNo, List<Integer> finalPackage) {
        if (totalPackNo.size() >= 1) {
            // find the value for the minimum number of totalPackNo.
            Integer minPackNo = Collections.min(totalPackNo);
            Integer minPackFreq = Collections.frequency(totalPackNo, minPackNo);

            // find how many time this number is achieved, if more than one, find the cheapest option
            Integer minIndex = totalPackNo.indexOf(Collections.min(totalPackNo));

            if (minPackFreq > 1) {
                Timber.d("MORE THAN 1 MINIMUM PACKAGE METHOD DETECTED.");
                // compare price and get the cheapest solution
                // get all occurrence of the index
                ArrayList<Integer> indexList = new ArrayList<>();
                for (int i = 0; i < totalPackNo.size(); i++) {
                    if (minPackNo == totalPackNo.get(i)) {
                        indexList.add(i);
                    }
                }

                // loop thru each combination and calculate the price
                Map<Double, Integer> priceIndexMap = new HashMap<>();
                for (int index : indexList) {
                    int smallNo = smallPackNo.get(index);
                    int mediumNo = mediumPackNo.get(index);

                    Double price = smallNo * smallPrice + mediumNo * mediumPrice;

                    priceIndexMap.put(price, index);

                    Timber.d("Price: " + price);
                }

                // find the index that corresponds to the lowest price
                Set<Double> priceSet = priceIndexMap.keySet();
                Double minPrice = Collections.min(priceSet);
                minIndex = priceIndexMap.get(minPrice);

            }

            finalPackage.add(smallPackNo.get(minIndex));
            finalPackage.add(mediumPackNo.get(minIndex));
        }
    }




    //////////////////////////////////////////////////////////////
    // BLUEBERRY MUFFIN AND CROISSANT CALCULATIONS BELOW
    //////////////////////////////////////////////////////////////



    public static ArrayList<Integer> calculateBlueberry(int number) {
        Integer largePackSize = 8;
        Integer mediumPackSize = 5;
        Integer smallPackSize = 2;

        Double smallPrice = 9.95;
        Double mediumPrice = 16.95;
        Double largePrice = 24.95;

        return calculateCombinations(number, largePackSize, mediumPackSize, smallPackSize,
                smallPrice, mediumPrice, largePrice);
    }

    public static ArrayList<Integer> calculateCroissant(int number) {
        Integer largePackSize = 9;
        Integer mediumPackSize = 5;
        Integer smallPackSize = 3;

        Double smallPrice = 5.95;
        Double mediumPrice = 9.95;
        Double largePrice = 16.99;

        return calculateCombinations(number, largePackSize, mediumPackSize, smallPackSize,
                smallPrice, mediumPrice, largePrice);
    }

    private static ArrayList<Integer> calculateCombinations(int number,
                                                      Integer largePackSize,
                                                      Integer mediumPackSize,
                                                      Integer smallPackSize,
                                                      Double smallPrice,
                                                      Double mediumPrice,
                                                      Double largePrice) {

        List<Integer> smallPackNo = new ArrayList<>();
        List<Integer> mediumPackNo = new ArrayList<>();
        List<Integer> largePackNo = new ArrayList<>();
        List<Integer> totalPackNo = new ArrayList<>();

        compileAllPossibleCombinations(number, largePackSize, mediumPackSize, smallPackSize, smallPackNo, mediumPackNo, largePackNo, totalPackNo);

        ArrayList<Integer> finalPackage = new ArrayList<>();

        findMinimumPackageCombination(smallPrice, mediumPrice, largePrice, smallPackNo, mediumPackNo, largePackNo, totalPackNo, finalPackage);

        return finalPackage;
    }

    private static void compileAllPossibleCombinations(int number, int largePackSize, int mediumPackSize, int smallPackSize, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> largePackNo, List<Integer> totalPackNo) {
        int xlim = number/smallPackSize;
        int ylim = number/mediumPackSize;
        int zlim = number/largePackSize;

        for (int x = 0; x <= xlim; x++) {
            for (int y = 0; y <= ylim; y++) {
                for (int z = 0; z <= zlim; z++) {
                    int totalProduct = smallPackSize*x + mediumPackSize*y + largePackSize*z;

                    if (totalProduct == number && totalProduct != 0) {
                        smallPackNo.add(x);
                        mediumPackNo.add(y);
                        largePackNo.add(z);

                        totalPackNo.add(x + y + z);
                    }
                }
            }
        }
    }

    private static void findMinimumPackageCombination(Double smallPrice, Double mediumPrice, Double largePrice, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> largePackNo, List<Integer> totalPackNo, List<Integer> finalPackage) {
        if (totalPackNo.size() >= 1) {
            // find the value for the minimum number of totalPackNo.
            Integer minPackNo = Collections.min(totalPackNo);
            Integer minPackFreq = Collections.frequency(totalPackNo, minPackNo);

            // find how many time this number is achieved, if more than one, find the cheapest option
            Integer minIndex = totalPackNo.indexOf(Collections.min(totalPackNo));

            if (minPackFreq > 1) {
                Timber.d("MORE THAN 1 MINIMUM PACKAGE METHOD DETECTED.");
                // compare price and get the cheapest solution
                // get all occurrence of the index
                ArrayList<Integer> indexList = new ArrayList<>();
                for (int i = 0; i < totalPackNo.size(); i++) {
                    if (minPackNo == totalPackNo.get(i)) {
                        indexList.add(i);
                    }
                }

                // loop thru each combination and calculate the price
                Map<Double, Integer> priceIndexMap = new HashMap<>();
                for (int index : indexList) {
                    int smallNo = smallPackNo.get(index);
                    int mediumNo = mediumPackNo.get(index);
                    int largeNo = largePackNo.get(index);

                    Double price = smallNo * smallPrice + mediumNo * mediumPrice + largeNo * largePrice;

                    priceIndexMap.put(price, index);

                    Timber.d("Price: " + price);
                }

                // find the index that corresponds to the lowest price
                Set<Double> priceSet = priceIndexMap.keySet();
                Double minPrice = Collections.min(priceSet);
                minIndex = priceIndexMap.get(minPrice);

            }

            finalPackage.add(smallPackNo.get(minIndex));
            finalPackage.add(mediumPackNo.get(minIndex));
            finalPackage.add(largePackNo.get(minIndex));

        }
    }


}
