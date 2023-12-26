package nuricanozturk.dev.service.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
public class PaymentServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
