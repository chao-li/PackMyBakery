package com.clidev.packmybakery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

public final class PackageLooper {

    //////////////////////////////////////////////////////////////
    // VEGEMITE SCROLLS CALCULATIONS BELOW
    //////////////////////////////////////////////////////////////
    public static ArrayList<Integer> calculateVegemite(int number) {
        // set the pack size
        Integer mediumPackSize = 5;
        Integer smallPackSize = 3;

        // set the pricing
        Double smallPrice = 6.99;
        Double mediumPrice = 8.99;

        // create empty lists to house the collection of possible pack numbers.
        List<Integer> smallPackNo = new ArrayList<>();
        List<Integer> mediumPackNo = new ArrayList<>();
        List<Integer> totalPackNo = new ArrayList<>();

        // run the function to fill the collection of combinations
        compileAllPossibleCombinationsVegemite(number, mediumPackSize, smallPackSize,
                smallPackNo, mediumPackNo, totalPackNo);


        ArrayList<Integer> finalPackage = new ArrayList<>();

        // run the function to find the combination with the least number of packaging, and at the same time, ensuring the lowest cost.
        findMinimumPackageCombinationVegemite(smallPrice, mediumPrice, smallPackNo, mediumPackNo, totalPackNo, finalPackage);

        return finalPackage;


    }

    // this function finds all the possible combinations to obtain the number of products requested.
    private static void compileAllPossibleCombinationsVegemite(int number, int mediumPackSize, int smallPackSize, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> totalPackNo) {
        // define the limit for the x and y. x is the number of small package, and y is the number of large package.
        int xlim = number / smallPackSize;
        int ylim = number / mediumPackSize;

        // for each possible combinations.
        for (int x = 0; x <= xlim; x++) {
            for (int y = 0; y <= ylim; y++) {

                // calculate total number of product.
                int totalProduct = smallPackSize * x + mediumPackSize * y;

                // if the number of product matches the number of product requested, save this combination in the lists.
                if (totalProduct == number && totalProduct != 0) {
                    smallPackNo.add(x);
                    mediumPackNo.add(y);

                    totalPackNo.add(x + y);
                }

            }
        }
    }

    // this function finds the combination with the lowest number of package and price
    private static void findMinimumPackageCombinationVegemite(Double smallPrice, Double mediumPrice, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> totalPackNo, List<Integer> finalPackage) {
        // if the totalPackNo isn't empty.
        if (totalPackNo.size() >= 1) {
            // find the value for the minimum number of totalPackNo.
            Integer minPackNo = Collections.min(totalPackNo);
            Integer minPackFreq = Collections.frequency(totalPackNo, minPackNo);

            // find how many time this number is achieved, if more than one, find the cheapest option
            Integer minIndex = totalPackNo.indexOf(Collections.min(totalPackNo));

            // if multiple minimum pack number combination is found...
            if (minPackFreq > 1) {
                // Timber.d("MORE THAN 1 MINIMUM PACKAGE METHOD DETECTED.");

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

                    // Timber.d("Price: " + price);
                }

                // find the index that corresponds to the lowest price
                Set<Double> priceSet = priceIndexMap.keySet();
                Double minPrice = Collections.min(priceSet);
                minIndex = priceIndexMap.get(minPrice);

            }

            // package the combinations into the final result
            finalPackage.add(smallPackNo.get(minIndex));
            finalPackage.add(mediumPackNo.get(minIndex));
        }
    }


    //////////////////////////////////////////////////////////////
    // BLUEBERRY MUFFIN AND CROISSANT CALCULATIONS BELOW
    //////////////////////////////////////////////////////////////


    // method for calculating order for blueberry muffins
    public static ArrayList<Integer> calculateBlueberry(int number) {
        // Define the pack size
        int largePackSize = 8;
        int mediumPackSize = 5;
        int smallPackSize = 2;


        return calculateCombinations(number, largePackSize, mediumPackSize, smallPackSize);
    }

    // method for calculating order for croissant
    public static ArrayList<Integer> calculateCroissant(int number) {
        // Define the pack size
        int largePackSize = 9;
        int mediumPackSize = 5;
        int smallPackSize = 3;


        return calculateCombinations(number, largePackSize, mediumPackSize, smallPackSize);
    }

    // function used to find all combination of packaging for blueberry and croissant
    private static ArrayList<Integer> calculateCombinations(int number,
                                              int largePackSize,
                                              int mediumPackSize,
                                              int smallPackSize) {


        // get all possible combinations
        ArrayList<Integer> combResult = compileAllPossibleCombinationsBestOptimized(number, smallPackSize, mediumPackSize, largePackSize);


        return combResult;
        //return finalPackage;
    }

    private static int max(int i, int j) {
        return (i > j) ? i : j;
    }

    private static int[] knackPackAlgorithm(int number, int smallPackSize, int mediumPackSize, int largePackSize) {
        int W = number;
        int wt[] = {smallPackSize, mediumPackSize, largePackSize};
        int val[] = {1, 1, 1};
        int n = val.length;


        // dp[i] is going to store maximum value
        // with knapsack capacity i.
        int dp[] = new int[W + 1];

        // wt = 3,5,9
        // Fill dp[] using above recursive formula
        for (int i = W; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                if (wt[j] <= i) {
                    dp[i] = max(dp[i], dp[i - wt[j]] +
                            val[j]);
                }
            }
        }
        Timber.d("dp list");
        for (int item : dp) {
            Timber.d("dp: " + item);
        }
        return dp;
    }


    // function for finding all possible combination to get the number of products requested.
    private static ArrayList<Integer> compileAllPossibleCombinationsBestOptimized(int number, int smallPackSize, int mediumPackSize, int largePackSize) {
        //int xlim = number/smallPackSize;
        //int ylim = number/mediumPackSize;
        int zlim = number / largePackSize;
        boolean conditionMet = false;
        ArrayList<Integer> combResult = new ArrayList<>();

        // loop thru all possibility
        for (int z = zlim; z >= 0; z--) {

            int ylim = number - z * largePackSize;

            for (int y = ylim; y >= 0; y--) {

                int xlim = ylim - (y * mediumPackSize);

                for (int x = xlim; x >= 0; x--) {
                    int totalProduct = smallPackSize * x + mediumPackSize * y + largePackSize * z;

                    // if total product number equals what we requested, save this combination.
                    if (totalProduct == number && totalProduct != 0) {
                        combResult.add(x);
                        combResult.add(y);
                        combResult.add(z);

                        conditionMet = true;
                        break;
                    }

                }
                if (conditionMet) {
                    break;
                }
            }
            if (conditionMet) {
                break;
            }
        }

        return combResult;
    }

}



    /*
    private static void compileAllPossibleCombinationsOptimized(int number, int largePackSize, int mediumPackSize, int smallPackSize, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> largePackNo, List<Integer> totalPackNo) {
       //int xlim = number/smallPackSize;
        //int ylim = number/mediumPackSize;
        int zlim = number/largePackSize;

        // loop thru all possibility
        for (int z = zlim; z >= 0; z--) {

            int ylim = number - z*largePackSize;

            for (int y = ylim; y >= 0; y--) {

                int xlim = ylim - (y*mediumPackSize);

                for (int x = xlim; x >= 0; x--) {
                    int totalProduct = smallPackSize*x + mediumPackSize*y + largePackSize*z;

                    // if total product number equals what we requested, save this combination.
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



    private static void compileAllPossibleCombinations(int number, int largePackSize, int mediumPackSize, int smallPackSize, List<Integer> smallPackNo, List<Integer> mediumPackNo, List<Integer> largePackNo, List<Integer> totalPackNo) {
        int xlim = number/smallPackSize;
        int ylim = number/mediumPackSize;
        int zlim = number/largePackSize;

        // loop thru all possibility
        for (int x = 0; x <= xlim; x++) {
            for (int y = 0; y <= ylim; y++) {
                for (int z = 0; z <= zlim; z++) {
                    int totalProduct = smallPackSize*x + mediumPackSize*y + largePackSize*z;

                    // if total product number equals what we requested, save this combination.
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



    // function to calculate the combination with the lowest number of packages and the lowest price
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

            // package the results
            finalPackage.add(smallPackNo.get(minIndex));
            finalPackage.add(mediumPackNo.get(minIndex));
            finalPackage.add(largePackNo.get(minIndex));

        }
    }

    */

//}
