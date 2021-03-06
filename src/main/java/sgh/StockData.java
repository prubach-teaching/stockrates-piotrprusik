package sgh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileWriter;

public class StockData {

    public static void getAndProcessChange(String stock) throws IOException {
        String filePath = "data_in/" + stock + ".csv";
        File datain = new File(filePath);
        
        // checking if file exists, download when not
        if (!datain.exists()) {        
        download("https://query1.finance.yahoo.com/v7/finance/download/" + stock +
                                "?period1=1554504399&period2=1586126799&interval=1d&events=history",
                        filePath); 
        }
        
        Scanner scanner = new Scanner(datain);        
        String line = scanner.nextLine();
        
        //new file in dataout folder        
        FileWriter dataout = new FileWriter("data_out/" + stock + ".csv");
        dataout.write(line + ",Change" + "\n");
        
        
            
            if (scanner.hasNextLine()) {
            
                line = scanner.nextLine();
                    
                String[] values = line.split(",");

                double open = Double.valueOf(values[1]);
                double close = Double.valueOf(values[4]);

                dataout.write(line + "," + ((close - open) / open) * 100 + "\n");

            
            
            }
            
        
        dataout.close();
        
    
        
      
       
    }

    public static void download(String url, String fileName) throws IOException {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }

    public static void main(String[] args) throws IOException {
        String[] stocks = new String[] { "IBM", "MSFT", "GOOG" };
        for (String s : stocks) {
            getAndProcessChange(s);
        }
    }
}
