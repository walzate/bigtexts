/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtextsudfs.sandbox;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.List;

/**
 *
 * @author hduser
 */
public class NERDemo {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String serializedClassifier = "classifiers/spanish.ancora.distsim.s512.crf.ser.gz";
        //String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";

        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        String[] example = {"Wilson está en Bogotá"};
        for (String str : example) {
            System.out.println(classifier.classifyToString(str));
        }
        System.out.println("---");

        for (String str : example) {
            // This one puts in spaces and newlines between tokens, so just print not println.
            System.out.print(classifier.classifyToString(str, "slashTags", false));
        }
        System.out.println("---");

        for (String str : example) {
            System.out.println(classifier.classifyWithInlineXML(str));
        }
        System.out.println("---");

        for (String str : example) {
            System.out.println(classifier.classifyToString(str, "xml", true));
        }
        System.out.println("---");

        int i = 0;
        for (String str : example) {
            for (List<CoreLabel> lcl : classifier.classify(str)) {
                for (CoreLabel cl : lcl) {
                    System.out.print(i++ + ": ");
                    System.out.println(cl.toShorterString());
                }
            }
        }

    }
}
