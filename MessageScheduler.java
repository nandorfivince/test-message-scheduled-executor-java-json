import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MessageScheduler {

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
                System.out.println("Ez az üzenet " + count[0] + ". alkalommal lett elküldve a konzolra.");
                delay[0] = minDelay + rand.nextInt(maxDelay - minDelay); // Frissíti a késleltetést
                period[0] = minPeriod + rand.nextInt(maxPeriod - minPeriod); // Frissíti az időközt

                System.out.println("Késleltetés: " + delay[0] + " ms");
                System.out.println("Időköz: " + period[0] + " ms");

                // Az időkorlát ellenőrzése
                if (count[0] >= (10000 / period[0])) {
                    timer.cancel(); // Megszünteti az időzítőt
                    System.out.println("Az időkorlát elérve, a program leállt.");
                }
            }
        }, delay[0], period[0]);
    }

}
