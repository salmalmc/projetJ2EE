package ma.formations.microserviceproduit;

import ma.formations.microserviceproduit.entities.Produit;
import ma.formations.microserviceproduit.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroserviceProduitApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceProduitApplication.class, args);
    }

    // CommandLineRunner bean pour créer 5 produits de matériel informatique
    @Bean
    public CommandLineRunner run(ProduitRepository produitRepository) {
        return args -> {
            // Créer 5 produits de matériel informatique
            Produit produit1 = Produit.builder()
                    .nom("Ordinateur Portable HP")
                    .description("Ordinateur portable avec processeur Intel Core i5, 8GB RAM, 512GB SSD")
                    .prix(699.99)
                    .build();
            Produit produit2 = Produit.builder()
                    .nom("Clavier Mécanique Logitech")
                    .description("Clavier mécanique avec switches Cherry MX, rétroéclairé RGB")
                    .prix(129.99)
                    .build();
            Produit produit3 = Produit.builder()
                    .nom("Souris Gaming Razer")
                    .description("Souris ergonomique avec capteur de 16000 DPI et boutons programmables")
                    .prix(79.99)
                    .build();
            Produit produit4 = Produit.builder()
                    .nom("Écran Dell 27 pouces")
                    .description("Écran LED 27 pouces avec résolution 4K, temps de réponse de 4ms")
                    .prix(329.99)
                    .build();
            Produit produit5 = Produit.builder()
                    .nom("Casque Audio Sans Fil Bose")
                    .description("Casque audio sans fil avec réduction de bruit active et 20 heures d'autonomie")
                    .prix(299.99)
                    .build();

            // Sauvegarder les produits dans la base de données
            produitRepository.save(produit1);
            produitRepository.save(produit2);
            produitRepository.save(produit3);
            produitRepository.save(produit4);
            produitRepository.save(produit5);
        };
    }
}
