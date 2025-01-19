package ma.formations.microservicecommande.repository;

import ma.formations.microservicecommande.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    List<Commande> findByDateAfter(LocalDate date);
    long countByDateAfter(LocalDate date);
}
