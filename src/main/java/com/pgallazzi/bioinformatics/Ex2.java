package com.pgallazzi.bioinformatics;

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.*;

import java.io.*;
import java.util.Arrays;

import static com.pgallazzi.bioinformatics.Constants.*;

public class Ex2 {

    public static void main(String[] args) {

        System.out.println("Running Ex2!!!");

        final NCBIQBlastService service = new NCBIQBlastService();
        final File[] listOfFiles = new File(INPUT_FOLDER).listFiles() != null ? new File(INPUT_FOLDER).listFiles() : new File[0];

        Arrays.stream(listOfFiles).filter(file -> file.isFile() && file.getName().startsWith(EX2) && file.getName().endsWith(FASTA)).forEach(file -> {
            FileWriter writer = null;
            BufferedReader reader = null;
            String requestId = null;

            try {
                //The protein sequence which is the one that belongs to the only valid ORF (this was validated by the local blastp script)
                final ProteinSequence sequence = (ProteinSequence) FastaReaderHelper.readFastaProteinSequence(file).values().toArray()[0];

                NCBIQBlastAlignmentProperties alignmentProperties = new NCBIQBlastAlignmentProperties();
                alignmentProperties.setBlastProgram(BlastProgramEnum.blastp);
                alignmentProperties.setBlastDatabase(SWISSPROT);

                NCBIQBlastOutputProperties outputProperties = new NCBIQBlastOutputProperties();
                outputProperties.setOutputFormat(BlastOutputFormatEnum.XML);

                //Send the alignment request to NCBI service
                requestId = service.sendAlignmentRequest(sequence.getSequenceAsString(), alignmentProperties);
                System.out.println("Waiting for NCBI service to return results for " + file.getName() + " , this can take a while...");

                while (!service.isReady(requestId)) {
                    Thread.sleep(1000);
                }
                System.out.println("Done!");

                //Get the results
                final InputStream inputStream = service.getAlignmentResults(requestId, outputProperties);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                writer = new FileWriter(new File(OUTPUT_FOLDER + file.getName().replace(FASTA, XML)));
                String line;

                //Write each line into an XML file
                while ((line = reader.readLine()) != null) {
                    writer.write(line + System.getProperty(LINE_SEPARATOR));
                }

            } catch (final Exception exception) {
                System.out.println("Error running Ex2!");
                exception.printStackTrace();
                System.exit(100);
            } finally {
                IOUtils.close(writer);
                IOUtils.close(reader);
                service.sendDeleteRequest(requestId);
            }
        });
    }
}
