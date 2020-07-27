package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dayne
 */
public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {

    private static Random rng; //Used for random number generation

    public Chromosome() { //no arg constructor

    }

    /**
     * Adds a copy of each of the items passed in to this Chromosome. Uses a
     * random number to decide whether each item’s included field is set to true
     * or false.
     *
     *
     * @param items
     */
    public Chromosome(ArrayList<Item> items) {
        rng = new Random();
        for (int i = 0; i < items.size(); i++) {
            int trueOrFalse = rng.nextInt(2) + 1;
            // copy each item
            Item copy = new Item(items.get(i));
            // add the copy of that item into this chromosome
            this.add(copy);
            if (trueOrFalse == 1) {
                this.get(i).setIncluded(true);
            }
        }

    }

    // copy constructor, the goal is to make this Chromosome have the same genes as the
    // one passed in (by "same", I mean a *copy* of them).
    public Chromosome(Chromosome other) {
        //this.genes = other.genes; // this is bad and wrong!
        // instead, make a separate arraylist and copy over the values from the other list
        // the there will be two independent lists, and changes to one won't impact the other
        this.clear();
        for (int i = 0; i < other.size(); i++) {
            this.add(new Item(other.get(i)));
        }
    }

    /**
     * Creates and returns a new child chromosome by performing the crossover
     * operation on this chromosome and the other one that is passed in (i.e.
     * for each item, use a random number to decide which parent’s item should
     * be copied and added to the child).
     *
     * @param other
     * @return
     */
    public Chromosome crossover(Chromosome other) {

        Chromosome child = new Chromosome();
        rng = new Random();
        // loop over all of the items in this Chromosome
        for (int i = 0; i < this.size(); i++) {

            int randomNumber = rng.nextInt(10) + 1;

            if (randomNumber <= 5) {
                child.add(new Item(this.get(i)));

            } else {
                child.add(new Item(other.get(i)));

            }
        }
        return child;
    }

    /**
     * Performs the mutation operation on this chromosome (i.e. for each item in
     * this chromosome, use a random number to decide whether or not to flip
     * it’s included field from true to false or vice versa).
     */
    public void mutate() {
        rng = new Random();

        for (int i = 0; i < this.size(); i++) {
            int randomNumber = rng.nextInt(10) + 1;
            if (randomNumber == 1 && this.get(i).isIncluded() == false) {
                this.get(i).setIncluded(true);
            } else if (randomNumber == 1 && this.get(i).isIncluded() == true) {
                this.get(i).setIncluded(false);
            }

        }

    }

    /**
     * Returns the fitness of this chromosome. If the sum of all of the included
     * items’ weights are greater than 10, the fitness is zero. Otherwise, the
     * fitness is equal to the sum of all of the included items’ values.
     *
     * @return
     */
    public int getFitness() {

        double totalWeight = 0;
        int totalValue = 0;

     

        for (Item i : this) {
            /*
            if i's included field is true
            increment totalWeight by this i's weight
            and increment totalValue by this i's value
             */
            if (i.isIncluded() == true) {
                totalWeight = totalWeight + i.getWeight();
                totalValue = totalValue + i.getValue();
            }
        }
        // determine overall fitness and return that value
        if (totalWeight > 10) {
            return 0;
        } else {
            return totalValue;
        }
    }

    /**
     * Returns -1 if this chromosome’s fitness is greater than the other’s
     * fitness, +1 if this chromosome’s fitness is less than the other one’s,
     * and 0 if their fitness is the same.
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Chromosome other) {
        if (this.getFitness() > other.getFitness()) {
            return -1;
        } else if (other.getFitness() > this.getFitness()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Displays the name, weight and value of all items in this chromosome whose
     * included value is true, followed by the fitness of this chromosome.
     *
     * @return
     */
    @Override
    public String toString() {
        String trueItems = "";

        for (Item e : this) {
            if (e.isIncluded() == true) {
                trueItems = trueItems + e.toString();
            }
        }
        return trueItems + " -> " + String.valueOf(this.getFitness());
    }

}
