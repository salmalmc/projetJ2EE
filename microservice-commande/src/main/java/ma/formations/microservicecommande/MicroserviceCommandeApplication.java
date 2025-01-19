package ma.formations.microservicecommande;

import ma.formations.microservicecommande.dto.ProduitDTO;
import ma.formations.microservicecommande.entities.Commande;
import ma.formations.microservicecommande.feign.ProduitFeignClient;
import ma.formations.microservicecommande.repository.CommandeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceCommandeApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MicroserviceCommandeApplication.class, args);
        Random random = new Random();

        // Récupérer le bean CommandeRepository et ProduitFeignClient
        CommandeRepository commandeRepository = context.getBean(CommandeRepository.class);
        ProduitFeignClient produitFeignClient = context.getBean(ProduitFeignClient.class);

        // Récupérer tous les produits
        List<ProduitDTO> produits = produitFeignClient.getAllProduits();

        // Créer des commandes basées sur les produits
        produits.forEach(produit -> {
            // Générer une quantité aléatoire pour chaque produit
            int quantite = random.nextInt(20) + 1; // Quantité aléatoire entre 1 et 20

            // Calculer le montant en fonction de la quantité et du prix du produit
            double montant = produit.getPrix() * quantite; // Montant = prix * quantité

            Commande commande = Commande.builder()
                    .description("Commande pour " + produit.getNom())
                    .quantite(quantite) // Quantité générée aléatoirement
                    .date(LocalDate.now().minusDays(random.nextInt(40))) // Date aléatoire entre aujourd'hui et il y a 40 jours
                    .montant(montant) // Montant calculé
                    .id_produit((long) produit.getId()) // ID du produit
                    .build();

            // Sauvegarder la commande dans la base de données
            commandeRepository.save(commande);
        });

        System.out.println("Commandes créées pour tous les produits.");
    }
}
