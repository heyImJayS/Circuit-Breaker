
import java.util.Date;
import java.util.Random;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class WebService {
    public String request(String sleepTimeMilli){
        Date requestReceived = new Date();
        Integer randomTime = randomTime(sleepTimeMilli);
        if(randomTime >= 500) {
            System.out.println("Long Sleep (" + randomTime + ") Request for: " + sleepTimeMilli);
        }
        return "Response: Sleep(" + randomTime + ") Start(" + requestReceived + ") (End: " + new Date() + ")";
    }
    private Integer randomTime(String bound) {
        Random random = new Random();
        return random.nextInt(Integer.valueOf(bound));
    }
}
