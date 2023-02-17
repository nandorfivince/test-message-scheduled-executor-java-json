import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MessageSchedulerEndpointJson {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Random rand = new Random();
        int minDelay = 250; // Minimum késleltetés 250 ms
        int maxDelay = 3500; // Maximum késleltetés 3500 ms
        int minPeriod = 250; // Minimum időköz 250 ms
        int maxPeriod = 3500; // Maximum időköz 3500 ms
        final int[] count = {0};
        final int[] delay = {minDelay + rand.nextInt(maxDelay - minDelay)}; // Random késleltetés az intervallumban
        final int[] period = {minPeriod + rand.nextInt(maxPeriod - minPeriod)}; // Random időköz az intervallumban

        System.out.println("Kezdő késleltetés: " + delay[0] + " ms");
        System.out.println("Kezdő időköz: " + period[0] + " ms");

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                count[0]++;
                String message = "{\"message\":\"Ez az üzenet " + count[0] + ". alkalommal lett elküldve.\"}";
                delay[0] = minDelay + rand.nextInt(maxDelay - minDelay); // Frissíti a késleltetést
                period[0] = minPeriod + rand.nextInt(maxPeriod - minPeriod); // Frissíti az időközt

                System.out.println("Küldési adatok:");
                System.out.println("Üzenet: " + message);
                System.out.println("Késleltetés: " + delay[0] + " ms");
                System.out.println("Időköz: " + period[0] + " ms");

                // Az időkorlát ellenőrzése
                if (count[0] >= (10000 / period[0])) {
                    timer.cancel(); // Megszünteti az időzítőt
                    System.out.println("Az időkorlát elérve, a program leállt.");
                }

                try {
                    sendPostRequest("https://example.com/api/messages", message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay[0], period[0]);
    }

    private static void sendPostRequest(String endpoint, String message) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = message.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        System.out.println("Válasz kód: " + responseCode);
    }

}
