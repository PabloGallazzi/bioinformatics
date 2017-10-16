package com.pgallazzi.bioinformatics;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.core.alignment.template.Profile;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.core.util.ConcurrencyTools;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.pgallazzi.bioinformatics.Constants.*;

public class Ex3 {

    public static void main(String[] args) {

        System.out.println("Running Ex3!!!");

        final File[] listOfFiles = new File(INPUT_FOLDER).listFiles() != null ? new File(INPUT_FOLDER).listFiles() : new File[0];

        Arrays.stream(listOfFiles).filter(file -> file.isFile() && file.getName().startsWith(EX3) && file.getName().endsWith(FASTA)).forEach(file -> {
            FileWriter writer = null;
            try {
                final LinkedHashMap<String, ProteinSequence> sequences = FastaReaderHelper.readFastaProteinSequence(file);
                final List<ProteinSequence> lst = new ArrayList<>();
                sequences.forEach((key, value) -> lst.add(value));
                System.out.println("Performing MSA for " + lst.size() + " sequences on file " + file.getName() + " , this can take a while...");
                final Profile<ProteinSequence, AminoAcidCompound> profile = Alignments.getMultipleSequenceAlignment(lst);
                writer = new FileWriter(new File(OUTPUT_FOLDER + file.getName()));
                writer.write(profile.toString(Profile.StringFormat.FASTA));
            } catch (final Exception exception) {
                System.out.println("Error running Ex3");
                exception.printStackTrace();
                System.exit(100);
            } finally {
                IOUtils.close(writer);
                ConcurrencyTools.shutdown();
            }
        });
    }
}
