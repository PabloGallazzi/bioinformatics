package com.pgallazzi.bioinformatics;

import org.apache.commons.lang3.StringUtils;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.util.IOUtils;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import static com.pgallazzi.bioinformatics.Constants.*;

public class Ex4 {

    public static void main(final String[] args) {

        System.out.println("Running Ex4!!!");

        if (args.length > 1 || (args.length == 1 && args[0].length() < 10)) {
            System.out.println("You must provide one and only one pattern with a minimum of ten characters to search for. Try this one QHEDFGDEGAANHNRLGGDYDSPGVQVFVYGRSVF or this one +K+++PR+KL");
            System.exit(100);
        }

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
                final String[] hits = StringUtils.substringsBetween(blastOutput, "<Hit>", "</Hit>");
                if (args.length == 0) {
                    System.out.println("Searching at input file " + file.getName());
                    System.out.println("Insert a pattern to search for, the minimum length is ten (no spaces) . Try this one QHEDFGDEGAANHNRLGGDYDSPGVQVFVYGRSVF or this one +K+++PR+KL or this one QYHDYSDDGWVNLNRQGFSYQCPQGQVIVAVRSIF");
                    System.out.println("Type \"exit\" is you want to leave");
                    while (true) {
                        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        final String pattern = br.readLine().trim().replace(" ", "");
                        if ("exit".equals(pattern)) {
                            break;
                        }
                        if (pattern.length() < 10) {
                            System.out.println("The minimum length of the pattern to search for is ten (no spaces). Try this one QHEDFGDEGAANHNRLGGDYDSPGVQVFVYGRSVF or this one +K+++PR+KL or this one QYHDYSDDGWVNLNRQGFSYQCPQGQVIVAVRSIF");
                        } else {
                            doSearch(hits, pattern, file.getName());
                            System.out.println("Done you can see the output at " + OUTPUT_FOLDER + file.getName().replace(XML, pattern + TXT));
                        }
                    }
                } else {
                    doSearch(hits, args[0], file.getName());
                }
            } catch (final Exception exception) {
                System.out.println("Error running Ex4 for file input file " + file.getName());
                exception.printStackTrace();
                System.exit(100);
            }
        });
    }

    private static void doSearch(final String[] hits, final String pattern, final String fileName) throws Exception {
        final FileWriter writer = new FileWriter(new File(OUTPUT_FOLDER + fileName.replace(XML, pattern + TXT)));
        try {
            writer.write("Results for input " + fileName + " and pattern " + pattern + System.getProperty(LINE_SEPARATOR));
            Arrays.stream(hits).filter(it -> it.contains(pattern)).forEach(
                    it -> {
                        try {
                            writer.write("Match found for hit " + it + System.getProperty(LINE_SEPARATOR));
                            String accession = StringUtils.substringBetween(it, "<Hit_accession>", "</Hit_accession>");
                            writer.write("Match's accession " + accession + System.getProperty(LINE_SEPARATOR));
                            try {
                                final URL uniprotFasta = new URL(String.format("http://www.uniprot.org/uniprot/%s.fasta", accession));
                                System.out.println("Searching a sequence for accession " + accession + " this can take a while!");
                                final ProteinSequence seq = FastaReaderHelper.readFastaProteinSequence(uniprotFasta.openStream()).get(accession);
                                writer.write("Full sequence for accession " + accession + " " + seq + System.getProperty(LINE_SEPARATOR));
                            } catch (final Exception exception) {
                                writer.write("Unable to get sequence for accession " + accession + System.getProperty(LINE_SEPARATOR));
                                exception.printStackTrace();
                            }
                        } catch (IOException e) {
                            System.out.println("Unable to write some results for input " + fileName + " and pattern " + pattern + ", CONTINUING BUT RESULTS ARE PARTIAL!!!");
                        }
                    }
            );
        } catch (IOException e) {
            System.out.println("Unable to write results for input " + fileName + " and pattern " + pattern);
        } finally {
            IOUtils.close(writer);
        }
    }
}