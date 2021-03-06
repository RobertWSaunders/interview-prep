import java.util.*;

class NodeBounds {
    int upperBound;
    int lowerBound;
    BinaryTreeNode node;

    NodeBounds(BinaryTreeNode node, int upperBound, int lowerBound) {
        this.node = node;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
}


/**
 * Cake structure for cake thief question.
 * See interview cake, "The Cake Thief" question.
 */
class CakeType {

    int value;
    int weight;

    CakeType(int value, int weight) {
        this.weight = weight;
        this.value = value;
    }
}

class InfinityException extends RuntimeException {
    public InfinityException() {
        super("Max value is infinity!");
    }
}

/**
 * Implementation of different dynamic programming problems.
 * Has recursive and tabulation implementations.
 * Author: Robert Saunders
 */
class DynamicProgramming {

    // number of islands
    // Given a chess-like board where some spaces don't exist and others are blocked by another piece, output all the possible places where a knight could move with one step, two steps, and so on.  

    ////////////////
    /* Fibonacci */
    //////////////

    private Map<Integer, Integer> fibMemo = new HashMap<>();

    /////////////////////
    /* The Cake Thief */
    ///////////////////

    /**
     * Computes the maximum value in cakes that can fit in the bag.
     * @param cakes Array of cakes to choose from.
     * @param weightCapacity The capacity of the bag.
     * @return The maximum value in cakes that could be put in the bag.
     */
    public long maxDuffelBagValue(CakeType[] cakes, int weightCapacity) {

        long[] maxValuesAtCapacities = new long[weightCapacity+1];

        for (int currentCapacity = 0; currentCapacity <= weightCapacity; currentCapacity++) {

            long maxValueForCapacity = 0;

            for (CakeType cake : cakes) {

                if (cake.weight == 0 && cake.value != 0) {
                    throw new InfinityException();
                }

                if (cake.weight < currentCapacity) {
                    long maxValueUsingCake = cake.weight + maxValuesAtCapacities[currentCapacity-cake.weight];

                    maxValueForCapacity = Math.max(maxValueForCapacity, maxValueUsingCake);
                }
            }

            maxValuesAtCapacities[currentCapacity] = maxValueForCapacity;

        }

        return maxValuesAtCapacities[weightCapacity];
    }

    ////////////////////
    /* Making Change */
    //////////////////

    /**
     * Calculates number of ways to group coins to equal given amount.
     * @param amount The amount to add the coins up to.
     * @param denominations The coin denominations available.
     * @return The number of ways the coins can be grouped together.
     */
    public static int computeCoinCollectionWays(int amount, int[] denominations) {
        return computeCoinCollectionWays(amount, denominations, 0);
    }

    /**
     *
     * @param amountLeft
     * @param denominations
     * @param currentIndex
     * @return
     */
    private static int computeCoinCollectionWays(int amountLeft, int[] denominations, int currentIndex) {
        if (amountLeft == 0) {
            return 1;
        }

        if (amountLeft < 0) {
            return 0;
        }

        if (currentIndex == denominations.length) {
            return 0;
        }

        int currentCoin = denominations[currentIndex];

        int numPossibilities = 0;
        while (amountLeft >= 0) {
            numPossibilities += computeCoinCollectionWays(amountLeft, denominations, ++currentIndex);
            amountLeft -= currentCoin;
        }

        return numPossibilities;
    }

    /**
     *
     * @param amount
     * @param denominations
     * @return
     */
    private static int computeCoinCollectionWaysBottomUp(int amount, int[] denominations) {
        int[] waysOfDoingNCents = new int[amount +1];

        waysOfDoingNCents[0] = 1; // one way to make zero

        for (int coin : denominations) {
            for (int higherAmount = coin; higherAmount <= amount; higherAmount++) {
                int higherAmountRemainder = higherAmount - coin;
                waysOfDoingNCents[higherAmount] += waysOfDoingNCents[higherAmountRemainder];
            }
        }

        return waysOfDoingNCents[amount];
    }

    ///////////////////
    /* Apple Stocks */
    /////////////////

    /**
     * Gets the max profit that could have been earned from a sorted list of stock prices.
     * @param stockPrices The stock prices, sorted by time.
     * @return The max profit that could have been earned.
     */
    public static int getMaxProfit(int[] stockPrices) {

        // first check if we can even calculate a profit
        if (stockPrices.length < 2) {
            throw new IllegalArgumentException("To calculate a profit we need at lease two stock prices.");
        }

        // initialize the min price to be first price
        // initialize max profit to be first pair
        int minPrice = stockPrices[0];
        int maxProfit = stockPrices[1] - stockPrices[0];

        // greedily iterate through each stock price
        // check potential profit against current min
        // update maxProfit if needed
        // update minPrice if needed
        for(int i = 1; i < stockPrices.length; i++) {

            int currentPrice = stockPrices[i];

            int potentialProfit = currentPrice - minPrice;

            maxProfit = Math.max(maxProfit, potentialProfit);

            minPrice = Math.min(minPrice, currentPrice);
        }

        // return the max profit
        return maxProfit;
    }

    ///////////////////////////////
    /* Highest Product of Three */
    /////////////////////////////

    /**
     * Gets the highest product from three integers in the input array.
     * Note: this implementation does not account for negative numbers.
     * @param nums The group of input integers.
     * @return The highest product of three numbers in the array.
     */
    public static int getHighestProductOfThree(int[] nums) {

        // there must be at least three integers in input array
        if (nums.length < 3) {
            throw new IllegalArgumentException("There needs to be at least three numbers in the input array!");
        }

        // initialize trackers to track three largest numbers
        // set to negative infinity because that isn't a value in array
        int first, second, third;
        first = second = third = Integer.MIN_VALUE;

        // iterate through the numbers
        // update flags to indicate largest numbers
        for(int current : nums) {
            if (current > first) {
                third = second;
                second = first;
                first = current;
            } else if (current > second) {
                third = second;
                second = current;
            } else if (current > third) {
                third = current;
            }
        }

        // return the product of the three largest values
        return first * second * third;
    }

    /**
     * Gets the highest product from integers in the input array.
     * Note: This is a better approach versus above solution because accounts for negative numbers.
     * @param nums The group of input integers.
     * @return The highest product of three numbers in the array.
     */
    public static int getHighestProductOfThreeGreedy(int[] nums) {

        if (nums.length < 3) {
            throw new IllegalArgumentException("There needs to be at least three numbers in the input array!");
        }

        int highest = Math.max(nums[0], nums[1]);
        int lowest = Math.min(nums[0], nums[1]);

        int highestProductOf2 = nums[0] * nums[1];
        int lowestProductOf2 = nums[0] * nums[1];

        int highestProductOf3 = nums[0] * nums[1] * nums[2];

        for(int i = 2; i < nums.length; i++) {

            int current = nums[i];

            highestProductOf3 = Math.max(Math.max(current * highestProductOf2, highestProductOf3), current * lowestProductOf2);

            highestProductOf2 = Math.max(Math.max(current * highest, highestProductOf2), current * lowest);

            lowestProductOf2 = Math.min(Math.min(current * highest, lowestProductOf2), current * lowest);

            highest = Math.max(current, highest);

            lowest = Math.min(current, lowest);
        }

        return highestProductOf3;
    }

    ///////////////////////////////////
    /* Product of All Other Numbers */
    /////////////////////////////////

    /**
     * Finds product of every integer at the given index excluding the index value from calculation.
     * Note, this is the brute force implementation.
     * @param nums The numbers to find the products of.
     * @return An array with the products.
     */
    public static int[] getProductsOfAllIntsExceptAtIndex(int[] nums) {

        // catch edge case
        if (nums.length < 2) {
            throw new IllegalArgumentException("Need at least two numbers in input array!");
        }

        // initialize array of products
        // equal to length of input array
        int[] products = new int[nums.length];

        // iterate through array calculating product for each
        for (int i = 0; i < nums.length; i++) {

            // initialize product to be one
            int product = 1;

            // calculate product of all other numbers in array
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }

            // add the product to the products array
            products[i] = product;

        }

        // return products
        return products;
    }

    /**
     * Finds product of every integer at the given index excluding the index value from calculation.
     * Note: This is an improved version of algorithm above, using a greedy approach.
     * @param nums The numbers to find the products of.
     * @return An array with the products.
     */
    public static int[] getProductsOfAllIntsExceptAtIndexGreedy(int[] nums) {

        // create array to hold products
        // flag to keep track of product
        int[] productsOfAllIntsExceptIndex = new int[nums.length];
        int productSoFar = 1;

        // find the products before the index
        for (int i = 0; i < nums.length; i++) {
            productsOfAllIntsExceptIndex[i] = productSoFar;
            productSoFar *= nums[i];
        }

        // find the products after the index
        // reset the product so far flag
        productSoFar = 1;
        for (int j = nums.length - 1; j >= 0; j--) {
            productsOfAllIntsExceptIndex[j] *= productSoFar;
            productSoFar *= nums[j];
        }

        // return the array of products
        return productsOfAllIntsExceptIndex;
    }

    //////////////
    /* Testing */
    ////////////

    /**
     * Main execution method used for testing problems.
     * @param args Arguments passed into execution.
     */
    public static void main(StringProblems[] args) {

        System.out.println(DynamicProgramming.computeCoinCollectionWays(4, new int[]{1, 2, 3, 4}));

//        DynamicProgramming.getProductsOfAllIntsExceptAtIndex(new int[]{1, 7, 3, 4});
//        DynamicProgramming.getProductsOfAllIntsExceptAtIndexGreedy(new int[]{1, 7, 3, 4});
//        System.out.println(DynamicProgramming.getHighestProductOfThree(new int[]{-10, -10, 1, 3, 2}));

    }
}
