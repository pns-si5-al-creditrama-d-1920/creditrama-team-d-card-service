package fr.unice.polytech.si5.al.creditrama.teamd.cardservice.service;

import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.client.ClientServiceClient;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.BankAccountInformation;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.Card;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {
    private CardRepository cardRepository;
    private ClientServiceClient clientServiceClient;

    @Autowired
    public CardService(CardRepository cardRepository, ClientServiceClient clientServiceClient) {
        this.cardRepository = cardRepository;
        this.clientServiceClient = clientServiceClient;
    }

    public Optional<Card> getCard(String cardNumber) {
        return cardRepository.findById(Long.valueOf(cardNumber));
    }

    public Card createCard(BankAccountInformation bankAccountInformation) {
        Card card = Card.builder().build();
        card.setNumber(generateRandomDigits(16).longValue());
        card.setCode(generateRandomDigits(4).intValue());
        card.setCvc(generateRandomDigits(3).intValue());
        card.setOwner(String.format("%s %s", bankAccountInformation.getFirstName(), bankAccountInformation.getLastName()));
        card.setIban(bankAccountInformation.getIban());
        card.setExpiryDate(LocalDate.now().plusYears(2L));
        return cardRepository.save(card);
    }

    public List<Card> getCardsOfClient(String userId) {
        List<Card> cardsNumber = clientServiceClient.getCardsOfClientBankAccount(userId);
        List<Card> cards = new ArrayList<>();
        for (Card card : cardsNumber) {
            Optional<Card> optionalCard = cardRepository.findById(card.getNumber());
            optionalCard.ifPresent(cards::add);
        }
        return cards;
    }

    /**
     * Generate a random BigInteger of `numbersOfDigits` digits
     *
     * @param numbersOfDigits int
     * @return BigInteger
     */
    private BigInteger generateRandomDigits(int numbersOfDigits) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numbersOfDigits; i++) {
            builder.append(random.nextInt(9) + 1);
        }
        return new BigInteger(builder.toString());
    }
}
