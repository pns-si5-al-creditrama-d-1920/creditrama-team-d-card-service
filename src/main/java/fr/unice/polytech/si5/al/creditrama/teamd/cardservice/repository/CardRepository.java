package fr.unice.polytech.si5.al.creditrama.teamd.cardservice.repository;

import fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByClientId(Long clientId);
}
