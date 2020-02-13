package fr.unice.polytech.si5.al.creditrama.teamd.cardservice;

import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.BankAccountInformation;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.Card;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.repository.CardRepository;
import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.service.CardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = PropertyOverrideContextInitializer.class)
@Transactional
public class CardServiceTest {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    private Card card;

    @Before
    public void setUp() throws Exception {
        cardRepository.deleteAll();

        card = Card.builder()
                .code(444)
                .cvc(333)
                .iban("FRFRFR")
                .owner("Nate")
                .number(42L)
                .build();
        cardRepository.save(card);
    }

    @Test
    public void createACard() {
        cardRepository.deleteAll();

        BankAccountInformation information = BankAccountInformation.builder()
                .firstName("Nathan")
                .lastName("Strobbe")
                .iban("FR347YDFR43")
                .build();
        Card expected = cardService.createCard(information);

        Long cardNumber = expected.getNumber();
        Optional<Card> actual = cardRepository.findById(cardNumber);
        assertEquals(Optional.of(expected), actual);
        assertTrue(actual.isPresent());
        assertTrue(0 <= actual.get().getCvc() && actual.get().getCvc() <= 999);
        assertTrue(0 <= actual.get().getCode() && actual.get().getCode() <= 9999);
    }

    @Test
    public void retrieveCard() {
        Long cardNumber = card.getNumber();
        Optional<Card> actual = cardRepository.findById(cardNumber);
        assertEquals(Optional.of(card), actual);
    }

    @Test
    public void gettingCardOfClient() {
        BankAccountInformation information = BankAccountInformation.builder()
                .firstName("Nathan")
                .lastName("Strobbe")
                .iban("FR347YDFR43")
                .userId(42L)
                .build();

        Card card1 = cardService.createCard(information);
        Card card2 = cardService.createCard(information);
        Card card3 = cardService.createCard(information);
        List<Card> returnedCards = Arrays.asList(
                Card.builder().number(card1.getNumber()).build(),
                Card.builder().number(card2.getNumber()).build(),
                Card.builder().number(card3.getNumber()).build()
        );

        List<Card> cards = cardService.getCardsOfClient(information.getUserId());

        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));
        assertTrue(cards.contains(card3));
    }
}
