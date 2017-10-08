package com.pgallazzi.bioinformatics;

import java.io.File;
import java.util.Arrays;

import static com.pgallazzi.bioinformatics.Constants.*;

public class Ex2Local {

    public static void main(String[] args) {

        System.out.println("Running Ex2Local!!!");

        final File[] listOfFiles = new File(INPUT_FOLDER).listFiles() != null ? new File(INPUT_FOLDER).listFiles() : new File[0];

        Arrays.stream(listOfFiles).filter(file -> file.isFile() && file.getName().startsWith(EX2_LOCAL) && file.getName().endsWith(FASTA)).forEach(file -> {
            try {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command("./blast/ncbi-blast-2.6.0+/bin/blastp", "-db", "blast/swissprot", "-query", file.getPath(), "-outfmt", "5", "-out", OUTPUT_FOLDER + file.getName().replace(FASTA, XML));
                builder.directory(new File("."));
                Process process = builder.start();
                System.out.println("Waiting for local blastp to return results for " + file.getName() + " , this can take a while...");
                process.waitFor();
                System.out.println("Done!");
            } catch (final Exception exception) {
                System.out.println("Error running Ex2Local!");
                exception.printStackTrace();
                System.exit(100);
            }
        });
    }
}
