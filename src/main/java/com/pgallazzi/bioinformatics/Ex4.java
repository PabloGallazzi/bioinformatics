package com.pgallazzi.bioinformatics;

import org.apache.commons.lang3.StringUtils;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import static com.pgallazzi.bioinformatics.Constants.*;

public class Ex4 {

    public static void main(String[] args) {

        System.out.println("Running Ex4!!!");

        final File[] listOfFiles = new File(INPUT_FOLDER).listFiles() != null ? new File(INPUT_FOLDER).listFiles() : new File[0];

        Arrays.stream(listOfFiles).filter(file -> file.isFile() && file.getName().startsWith(EX4) && file.getName().endsWith(XML)).forEach(file -> {
            try {

                final String blastOutput;
                final StringBuilder fileContents = new StringBuilder((int) file.length());
                final Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
                while (scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine());
                }
                blastOutput = fileContents.toString();
                scanner.close();
                String[] hits = StringUtils.substringsBetween(blastOutput, "<Hit>", "</Hit>");

                Arrays.stream(hits).filter(it -> it.contains(args[0])).forEach(
                        it -> {
                            System.out.println("Match found for hit " + it);
                            String accession = StringUtils.substringBetween(blastOutput, "<Hit_accession>", "</Hit_accession>");
                            System.out.println("Match's accession " + accession);
                            try {
                                final URL uniprotFasta = new URL(String.format("http://www.uniprot.org/uniprot/%s.fasta", accession));
                                final ProteinSequence seq = FastaReaderHelper.readFastaProteinSequence(uniprotFasta.openStream()).get(accession);
                                System.out.println("Full sequence for accession " + accession + " " + seq);
                            } catch (final Exception exception) {
                                System.out.println("Unable to get sequence for accession " + accession);
                                exception.printStackTrace();
                            }
                        }
                );
            } catch (final Exception exception) {
                System.out.println("Error running Ex4 for file " + file.getName());
                exception.printStackTrace();
                System.exit(100);
            }
        });
    }
}