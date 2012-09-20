package ee.elisa.kooditalgud092012;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateAsciiArt {


	public CreateAsciiArt() {
		try {
			FileInputStream fstream = new FileInputStream("input.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			FileWriter output = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(output);
			  
			String strLine;
			// Read File Line By Line
			try {
				while ((strLine = br.readLine()) != null) {
					String[] line = new String[3];
					line[0] = "";
					line[1] = "";
					line[2] = "";
					for (int i = 0; i < strLine.length(); i++) {
						int asciiCode = strLine.charAt(i);
						line = decorate(line, asciiCode);

					}
					System.out.println(line[0]);
					System.out.println(line[1]);
					System.out.println(line[2]);
					out.write(line[0]);
					out.write("\n");
					out.write(line[1]);
					out.write("\n");
					out.write(line[2]);
					out.write("\n");
					out.write("\n");
				}
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String[] decorate(String[] line, int asciiCode) {
		String ascii = Integer.toString(asciiCode);
		if (ascii.length() == 1)
			ascii = "00" + ascii;
		else if (ascii.length() == 2)
			ascii = "0" + ascii;

		for (int i = 0; i < 3; i++) {
			char chr = ascii.charAt(i);
			if (chr == '0') {
				line[0] += Constants.zero1;
				line[1] += Constants.zero2;
				line[2] += Constants.zero3;
			}
			if (chr == '1') {
				line[0] += Constants.one1;
				line[1] += Constants.one2;
				line[2] += Constants.one3;
			}
			if (chr == '2') {
				line[0] += Constants.two1;
				line[1] += Constants.two2;
				line[2] += Constants.two3;
			}
			if (chr == '3') {
				line[0] += Constants.three1;
				line[1] += Constants.three2;
				line[2] += Constants.three3;
			}
			if (chr == '4') {
				line[0] += Constants.four1;
				line[1] += Constants.four2;
				line[2] += Constants.four3;
			}
			if (chr == '5') {
				line[0] += Constants.five1;
				line[1] += Constants.five2;
				line[2] += Constants.five3;
			}
			if (chr == '6') {
				line[0] += Constants.six1;
				line[1] += Constants.six2;
				line[2] += Constants.six3;
			}
			if (chr == '7') {
				line[0] += Constants.seven1;
				line[1] += Constants.seven2;
				line[2] += Constants.seven3;
			}
			if (chr == '8') {
				line[0] += Constants.eight1;
				line[1] += Constants.eight2;
				line[2] += Constants.eight3;
			}
			if (chr == '9') {
				line[0] += Constants.nine1;
				line[1] += Constants.nine2;
				line[2] += Constants.nine3;
			}
		}

		return line;
	}

	public static void main(String args[]) {

		new CreateAsciiArt();

	}

}
