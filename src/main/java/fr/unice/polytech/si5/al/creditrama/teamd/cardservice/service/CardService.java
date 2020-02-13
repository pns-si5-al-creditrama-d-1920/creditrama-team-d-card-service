package fr.unice.polytech.si5.al.creditrama.teamd.cardservice.service;

import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.BankAccountInformation;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.Card;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {
    private CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
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
        card.setClientId(bankAccountInformation.getUserId());
        return cardRepository.save(card);
    }

    public List<Card> getCardsOfClient(Long userId) {
        return cardRepository.findAllByClientId(userId);
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
