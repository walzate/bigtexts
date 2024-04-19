/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtextsudfs.sandbox;

/**
 *
 * @author walzate
 */
public class StanfordMain {
    /**
     *
     * @param args
     */
    public static void main(String args[]){
        StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
        System.out.println(lemmatizer.lemmatize("Working with my girlfriend"));
    }
}
