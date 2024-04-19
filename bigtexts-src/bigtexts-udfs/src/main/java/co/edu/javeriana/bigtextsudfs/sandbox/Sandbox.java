/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtextsudfs.sandbox;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author hduser
 */
public class Sandbox {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        String taggingModel= "taggers/spanish-distsim.tagger";
        //String taggingModel= "taggers/english-left3words-distsim.tagger";
        MaxentTagger tagger = new MaxentTagger(taggingModel);
        // The sample string

        String sample = "Este es un texto de ejemplo";
        //String sample = "This is a sample text";

        // The tagged string
        String tagged = tagger.tagString(sample);

        // Output the result
        System.out.println(tagged);
    }
}
