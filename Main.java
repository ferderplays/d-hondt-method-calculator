import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        /**
         * D'hondt method calculator
         * 
         * By Ferdis
         * 
         * I made this calculator becuz I was interested in how elections work, especially the 2025 czech parliamentary elections
         * 
         * The parties in this calculator right now are ANO, SPOLU, STAN, Pirates, SPD, and AUTO which are all czech parties that won over 5% of the vote nationwide
         * 
         * To make this actually work for whatever elections you have, 
         * you need to first change the values of the region_mandates list, to the actual regional mandate counts of your country
         * then you need to rename the party regional results lists (ano_results, ...) to the parties running in your election, 
         * then you need to replace the values in the lists to match the results each party gets per-region,
         * important: make sure that the results are listed in order with the region_mandates list, otherwise you will get wierd results
         * then you need to change the stuff in the system println to replace the czech parties with your parties,
         * next up change the mandate variable names from the names they got rn, this is to make it easy to understand
         * and not make the code messy for you
         * 
         * SUMMARY OF THE TEXT ABOVE: 
         * change the names of the party variables and put in the actual results (in order with the regional mandates list) and regional mandates
         * 
         * i might be overexplaining some stuff here, so if u dont like it, just ignore it pls
         */

        int[] region_mandates = new int[] { 4, 13, 6, 11, 11, 11, 12, 12, 26, 23, 24, 12, 13, 22 }, // { region1, region2, region3, ... } 
            ano_results = new int[] { 57555, 173864, 79181, 101557, 98797, 103972, 121501, 113063, 234681, 125996, 215596, 110814, 131683, 272073 }, // { region1, region2, region3, ... } until u reach all ur regions
            spolu_results = new int[] { 20871, 59706, 41567, 71268, 67422, 67037, 82278, 63598, 183391, 214826, 181892, 77049, 68520, 112649 },
            stan_results = new int[] { 14828, 37252, 37609, 36872, 31535, 33976, 37512, 32179, 103451, 84665, 64307, 32444, 31830, 52457 },
            pirates_results = new int[] { 8801, 27012, 18891, 24333, 22015, 20373, 26467, 23582, 68571, 106657, 63119, 23967, 24942, 45022 },
            spd_results = new int[] { 13837, 35408, 19914, 23404, 21938, 21099, 27650, 26908, 53242, 33188, 50344, 27784, 32084, 50707 },
            auto_results = new int[] { 9868, 26324, 16933, 22981, 22155, 19588, 24535, 21701, 58574, 32712, 40952, 21352, 22586, 40261 };

        int[] nationalTotals = new int[6]; // ANO, SPOLU, STAN, Piráti, SPD, AUTO

        for (int i = 0; i < 14; i++) { // 14 is the region count in czechia, replace it with the number of regions your country has
            int[] regional = runMethod(
                ano_results[i], spolu_results[i], stan_results[i],
                pirates_results[i], spd_results[i], auto_results[i],
                region_mandates[i]
            );

            // adds regional results to national results
            for (int i2 = 0; i2 < nationalTotals.length; i2++) {
                nationalTotals[i2] += regional[i2];
            }
        }

        System.out.println("======================");
        System.out.println("NATIONAL-LEVEL RESULTS");
        System.out.println("======================");
        System.out.println("ANO | " + nationalTotals[0]);
        System.out.println("SPOLU | " + nationalTotals[1]);
        System.out.println("STAN | " + nationalTotals[2]);
        System.out.println("Piráti | " + nationalTotals[3]);
        System.out.println("SPD | " + nationalTotals[4]);
        System.out.println("AUTO | " + nationalTotals[5]);
    }

    public static int[] runMethod(int ano_current, int spolu_current, int stan_current, int pirates_current, int spd_current, int auto_current, int size) {
        int freemandates = size,
            ano_mandates = 0,
            spolu_mandates = 0,
            stan_mandates = 0,
            pirates_mandates = 0,
            auto_mandates = 0,
            spd_mandates = 0;

        do {
            /**
             * The D'hondt method works like this:
             * 1. find the party with the largest votes value
             * 2. assign a mandate to that party
             * 3. divide that party's votes value by (1 + the ammount of mandates they already got at that point in the loop)
             * 4. repeat the loop
             * 
             * it does this until all seats have been assigned to the parties
             * in this case combined_currents is the list of the current votes values,
             * it is made each itteration of the loop, so the values are not outdated
             * whatever value is the highest in this list is also the highest value overall as of that itteration of the loop
             * 
             * important advice here: do not put it outside of the loop, unless you want absolutely outrageous results
             */
            ArrayList<Double> combined_currents = new ArrayList<>();

            combined_currents.add(ano_current / (double) (1 + ano_mandates));
            combined_currents.add(spolu_current / (double) (1 + spolu_mandates));
            combined_currents.add(stan_current / (double) (1 + stan_mandates));
            combined_currents.add(pirates_current / (double) (1 + pirates_mandates));
            combined_currents.add(spd_current / (double) (1 + spd_mandates));
            combined_currents.add(auto_current / (double) (1 + auto_mandates));

            double highest_current = Collections.max(combined_currents); // the highest current value in the list
            
            if (highest_current == combined_currents.get(0)) {
                ano_mandates++;
                freemandates--;
            } else if (highest_current == combined_currents.get(1)) {
                spolu_mandates++;
                freemandates--;
            } else if (highest_current == combined_currents.get(2)) {
                stan_mandates++;
                freemandates--;
            } else if (highest_current == combined_currents.get(3)) {
                pirates_mandates++;
                freemandates--;
            } else if (highest_current == combined_currents.get(4)) {
                spd_mandates++;
                freemandates--;
            } else if (highest_current == combined_currents.get(5)) {
                auto_mandates++;
                freemandates--;
            }

        } while (freemandates > 0);

        // this is where the "code above" starts, everything above this comment is a core part of the code
    
        System.out.println("----------------");
        System.out.println("PARTY | MANDATES");
        System.out.println("----------------");
        System.out.println("ANO | " + ano_mandates);
        System.out.println("SPOLU | " + spolu_mandates);
        System.out.println("STAN | " + stan_mandates);
        System.out.println("Piráti | " + pirates_mandates);
        System.out.println("SPD | " + spd_mandates);
        System.out.println("AUTO | " + auto_mandates);

        // the code above is just a regional explainer, u can remove it if u just want to see the overall results

        return new int[] { ano_mandates, spolu_mandates, stan_mandates, pirates_mandates, spd_mandates, auto_mandates };
    }
}