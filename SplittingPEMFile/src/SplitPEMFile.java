import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

public class SplitPEMFile {
	public static void main(String args[]) {
		String inputFileName = "C:\\Users\\Admin\\Downloads\\cert_chain.PEM";
		String certFileName = "C:\\Users\\Admin\\Downloads\\cert.PEM";
		String chainFileName = "C:\\Users\\Admin\\Downloads\\chain.PEM";
		StringBuffer stringbuffer = new StringBuffer();
		File inputFile = new File(inputFileName);
		if (!inputFile.exists()) {
			System.err.println("Error: " + inputFileName + " does not exist.");
			System.exit(1);
		} else {
			String extension = "";
			int index = inputFile.getName().lastIndexOf('.');
			if (index > 0) {
				extension = inputFile.getName().substring(index + 1);
			}
			if (extension.equals("PEM")) {
				try {
					FileReader filereader = new FileReader(inputFile);
					BufferedReader bufferedReader = new BufferedReader(filereader);
					String line;
					Boolean flag = false;
					while ((line = bufferedReader.readLine()) != null) {
						if (line.equalsIgnoreCase("-----END CERTIFICATE-----") && flag.equals(false)) {
							stringbuffer.append(line).append("\n");
							certificateSplitting(stringbuffer.toString(), certFileName);
							stringbuffer.setLength(0);
							flag = true;
						} else {
							stringbuffer.append(line).append("\n");
						}
					}
					if (flag == true) {
						certificateSplitting(stringbuffer.toString(), chainFileName);

					}
					filereader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Provide PEM Files only..");
			}
		}
	}

	public static void certificateSplitting(String stringbuffer, String filename) {
		try {
			FileWriter filewrite = new FileWriter(filename);
			filewrite.write(stringbuffer);
			filewrite.close();
		} catch (Exception e) {

		}
		System.out.println("Success...");
	}
}
