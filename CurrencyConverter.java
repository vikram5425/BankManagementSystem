import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter amount: - CurrencyConverter.java:12");
        double amount = scanner.nextDouble();

        System.out.print("Enter base currency (e.g. USD, INR): - CurrencyConverter.java:15");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter target currency (e.g. EUR, INR): - CurrencyConverter.java:18");
        String targetCurrency = scanner.next().toUpperCase();

        try {
            String apiURL = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String json = response.toString();

            // Simple JSON parsing (without external libraries)
            String search = "\"" + targetCurrency + "\":";
            int index = json.indexOf(search);

            if (index == -1) {
                System.out.println("Invalid target currency. - CurrencyConverter.java:44");
                return;
            }

            int start = index + search.length();
            int end = json.indexOf(",", start);
            double rate = Double.parseDouble(json.substring(start, end));

            double convertedAmount = amount * rate;

            System.out.println("Converted Amount: - CurrencyConverter.java:54" + convertedAmount + " " + targetCurrency);

        } catch (Exception e) {
            System.out.println("Error during conversion: - CurrencyConverter.java:57" + e.getMessage());
        }

        scanner.close();
    }
}
