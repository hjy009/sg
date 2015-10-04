package fuzhu.gwee.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class gweeCSV {

	public void readpassport(){
		Reader in;
		Writer out;
		try {
			in = new FileReader("res/passport.csv");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
			for (CSVRecord record : records) {
			    String username = record.get("username");
			    String password = record.get("password");
			    System.out.println(username+":"+password);
			}		
			out = new FileWriter("res/passport.csv");
			CSVPrinter printer = CSVFormat.DEFAULT.withHeader("H1", "H2").print(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
