package fr.unice.polytech.si5.al.creditrama.teamd.cardservice.controller;

import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.BankAccountInformation;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.Card;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "content-type")
@RestController
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("cards/{cardNumber}")
    public ResponseEntity<Card> getCard(@PathVariable String cardNumber) {
        Optional<Card> optionalCard = cardService.getCard(cardNumber);
        return optionalCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("cards")
    public ResponseEntity<Card> createCard(@RequestBody BankAccountInformation bankAccountInformation) {
        return new ResponseEntity<>(cardService.createCard(bankAccountInformation), HttpStatus.CREATED);
    }

    @GetMapping("clients/{userId}/cards")
    public ResponseEntity<List<Card>> getCards(@PathVariable String userId) {
        return ResponseEntity.ok(cardService.getCardsOfClient(userId));
    }

}

