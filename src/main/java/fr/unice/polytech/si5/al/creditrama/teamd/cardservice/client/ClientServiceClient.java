package fr.unice.polytech.si5.al.creditrama.teamd.cardservice.client;

import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.Card;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "client", url = "${service.client}", configuration = FeignConfiguration.class)
public interface ClientServiceClient {

    @GetMapping("/clients/{userId}/cards")
    List<Card> getCardsOfClientBankAccount(@PathVariable("userId") String userId);
}
