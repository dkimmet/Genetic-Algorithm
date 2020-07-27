/*
 * Project 1: Genetic Algorithms based off of the concept of natural 
 */
package geneticalgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * CS1181
 *
 * @author Dayne
 */
public class GeneticAlgorithm {

    public static final int POP_SIZE = 100;
    public static final int NUM_EPOCHS = 100;
    public static final int NUM_THREADS = 1;

    /**
     * Reads in a data file with the format shown below and creates and returns
     * an ArrayList of Item objects. item1_label, item1_weight, item1_value
     * item2_label, item2_weight, item2_value ...
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Item> readData(String filename) throws
            FileNotFoundException {
        ArrayList<Item> items = new ArrayList<Item>();
        Scanner fileIn = new Scanner(new File(filename));
        fileIn.useDelimiter(",|\\n");
        double weight; //in pounds
        int value; //dollar amount each item is worth 
        while (fileIn.hasNext()) {
            String label = fileIn.next();
            weight = Double.parseDouble(fileIn.next());
            value = Integer.parseInt(fileIn.next().trim());
            items.add(new Item(label, weight, value));
        }
        fileIn.close();//close scanner 
        return items;
    }

    /**
     * Creates and returns an ArrayList of populationSize Chromosome objects
     * that each contain the items, with their included field randomly set to
     * (T)rue or (F)alse
     *
     * @param items
     * @param populationSize
     * @return
     */
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items,
            int populationSize) {
        ArrayList<Chromosome> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome(items));
        }

        return population;
    }

    /**
     * Reads the data about the items in from a file called items.txt and
     * performs the steps described in the Running the Genetic Algorithm section
     * above.
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Item> items = new ArrayList<>(readData("items.txt"));
        ArrayList<Chromosome> population = initializePopulation(items, POP_SIZE);//start population
        ArrayList<Chromosome> nextGeneration = new ArrayList<>();//start next Generation

        // evolution
        for (int i = 0; i < NUM_EPOCHS; i++) { // each generation

            for (Chromosome e : population) {//add from current population to next generation
                nextGeneration.add(new Chromosome(e));
            }

            // randomly pair off the existing Chromosomes in the population
            Collections.shuffle(population);

            // in a loop, take two at a time from the population arraylist and make them the parents
            for (int j = 0; j < POP_SIZE; j = j + 2) {//interation is +2 since two parents are paired each time the loop is run
                Chromosome parent1 = population.get(j);
                Chromosome parent2 = population.get(j + 1);
                Chromosome child = parent1.crossover(parent2);
                nextGeneration.add(child);
            }

            // Mutation: generate a random number between 1 and 10 and if it's 
            // equal to 1 call population.get(j).mutate();
            Random random = new Random();
            for (int k = 0; k < nextGeneration.size(); k++) {
                int tenPercent = random.nextInt(10) + 1;
                if (tenPercent == 1) {
                    nextGeneration.get(k).mutate();
                }
            }

            Collections.sort(nextGeneration);//sorts the next gen
            population.clear();//step six clear current population

            for (int h = 0; h < POP_SIZE; h++) {//step 6 adds the top 10 back into population
                population.add(nextGeneration.get(h));
            }
            System.out.println("------------------------------------------------------------");
            System.out.println("Generation " + (i + 1));
            System.out.println(population.get(0));//displays fittest individual from each gerneration 

        }
    }
}
