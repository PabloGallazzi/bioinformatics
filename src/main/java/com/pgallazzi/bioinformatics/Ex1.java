package com.pgallazzi.bioinformatics;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;
import org.biojava.nbio.core.sequence.transcription.Frame;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.pgallazzi.bioinformatics.Constants.*;
import static java.util.stream.Collectors.toList;

public class Ex1 {

    public static void main(String[] args) {

        File[] listOfFiles = new File(INPUT_FOLDER).listFiles();
        listOfFiles = listOfFiles != null ? listOfFiles : new File[0];

        Arrays.stream(listOfFiles).filter(file -> file.isFile() && file.getName().startsWith(EX1) && file.getName().endsWith(GB)).forEach(file -> {
            try {
                //Obtain the DNA sequence from the genbank file
                final DNASequence dnaSequence = (DNASequence) GenbankReaderHelper.readGenbankDNASequence(file).values().toArray()[0];
                final Frame[] frames = {Frame.ONE, Frame.REVERSED_ONE, Frame.TWO, Frame.REVERSED_TWO, Frame.THREE, Frame.REVERSED_THREE};

                //Translate to RNA once for each available open reading frame
                final List<RNASequence> rnaSequences = Arrays.stream(frames).map(dnaSequence::getRNASequence).collect(toList());

                //RNA transcript to amino acids for each ORF
                final List<ProteinSequence> proteinSequences = rnaSequences.stream().map(RNASequence::getProteinSequence).collect(toList());

                //Write results to fasta file
                final File outputFASTA = new File(OUTPUT_FOLDER + file.getName().replace(GB, FASTA));
                outputFASTA.createNewFile();
                FastaWriterHelper.writeProteinSequence(outputFASTA, proteinSequences);

            } catch (Exception e) {
                System.out.println("Error getting info from genbank file!" + file.getName());
                e.printStackTrace();
                System.exit(100);
            }
        });
    }
}
