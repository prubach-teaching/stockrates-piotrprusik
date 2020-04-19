package sgh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StockData {

    public static void getAndProcessChange(String stock) throws IOException {
        String filePath = "data_in/" + stock + ".csv";
        File datain = new File(filepath);
        
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
        dataout.write(line + ",% Change" + "\n");
        
        for (line : dataout) {
            
            if (scanner.hasNextLine()) {
            
            String[] values = line.split(",");
            
            double open = Double.valueOf(lineElem[1]);
            double close = Double.valueOf(lineElem[4]);
            double change = (close - open) / open;
            
            dataout.write(line + "," + change * 100 + "\n");
            
            } 
            
        }
        
        dataout.close();
        
    }
        
      
       
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
