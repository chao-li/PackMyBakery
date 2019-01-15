package com.clidev.packmybakery;

import java.util.ArrayList;
import java.util.List;

public final class PackageCalculator {
    public static List<Integer> calculateBlueberry(int number) {
        // package of 2, 5, 8
        int pack2;
        int pack5;
        int pack8;

        List<Integer> totalPack = new ArrayList<Integer>();

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

        } else { // if not divisble by 8, try using smaller package to pack the remainder
            // find the modulo of 5 with the remainder
            int remainderFrom5 = remainderFrom8 % 5;

            // if divisible by 5, use large and medium package
            if (remainderFrom5 == 0) {
                pack2 = 0;
                pack5 = remainderFrom8/5;
                pack8 = number/8;
            } else {

                // find the modulo of 2 with the remainder
                int remainderFrom2 = remainderFrom5 % 2;

                // if remainderFrom2 is equal to 0 then package works
                if (remainderFrom2 == 0) {
                    pack8 = number / 8;
                    pack5 = remainderFrom8 / 5;
                    pack2 = remainderFrom5 / 2;
                } else {

                    pack2 = 0;
                    pack5 = 0;
                    pack8 = 0;
                }
            }
        }

        totalPack.add(pack2 + pack5 + pack8);

        //Try pack starting with medium sized package
        pack2 = 0;
        pack5 = 0;
        pack8 = 0;


        int remainderFrom5 = number%5;

        if (remainderFrom5 == 0) {
            // pack using medium size only
            pack5 = number/5;
        } else {
            // if not divisible by 5, try package remainder with smaller package

            int remainderFrom2 = remainderFrom5 % 2;

        }

    }
}
