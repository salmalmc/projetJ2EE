package ma.formations.microservicecommande;

import ma.formations.microservicecommande.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class CommandeHealthIndicator implements HealthIndicator {

    private final CommandeRepository commandeRepository;

    @Value("${mes-config-ms.commandes-last:20}")
    private int commandesLast;

    public CommandeHealthIndicator(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Health health() {
        LocalDate dateSeuil = LocalDate.now().minusDays(commandesLast);
        long count = commandeRepository.countByDateAfter(dateSeuil);

        if (count > 0) {
            return Health.up()
                    .withDetail("message", "Table COMMANDE contient " + count + " commandes depuis " + commandesLast + " jours.")
                    .build();
        } else {
            return Health.down()
                    .withDetail("message", "Aucune commande dans la table COMMANDE depuis " + commandesLast + " jours.")
                    .build();
        }
    }
}

