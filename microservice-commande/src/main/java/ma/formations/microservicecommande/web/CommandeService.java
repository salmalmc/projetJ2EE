package ma.formations.microservicecommande.web;

import ma.formations.microservicecommande.dto.ProduitDTO;
import ma.formations.microservicecommande.entities.Commande;
import ma.formations.microservicecommande.feign.ProduitFeignClient;
import ma.formations.microservicecommande.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Value("${mes-config-ms.commandes-last}")
    private int commandesLastDays;

    private final CommandeRepository commandeRepository;
    private final ProduitFeignClient produitFeignClient; // Injecter le client Feign

    public CommandeService(CommandeRepository commandeRepository, ProduitFeignClient produitFeignClient) {
        this.commandeRepository = commandeRepository;
        this.produitFeignClient = produitFeignClient;
    }

    // Récupérer les commandes récentes
    public List<Commande> getRecentCommandes() {
        LocalDate startDate = LocalDate.now().minusDays(commandesLastDays);
        return commandeRepository.findByDateAfter(startDate);
    }

    public Commande createCommande(Commande commande) {
        // Valider l'existence du produit via Feign
        ProduitDTO produit = produitFeignClient.getProduitById(commande.getId_produit());
        if (produit == null) {
            throw new RuntimeException("Produit avec ID " + commande.getId_produit() + " introuvable.");
        }

        // Sauvegarder la commande après validation
        return commandeRepository.save(commande);
    }

    // Lire une commande par ID
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    // Lire toutes les commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // Mettre à jour une commande existante
    public Commande updateCommande(Long id, Commande updatedCommande) {
        return commandeRepository.findById(id)
                .map(existingCommande -> {
                    existingCommande.setDescription(updatedCommande.getDescription());
                    existingCommande.setQuantite(updatedCommande.getQuantite());
                    existingCommande.setDate(updatedCommande.getDate());
                    existingCommande.setMontant(updatedCommande.getMontant());
                    existingCommande.setId_produit(updatedCommande.getId_produit());
                    return commandeRepository.save(existingCommande);
                })
                .orElseThrow(() -> new RuntimeException("Commande not found with ID: " + id));
    }

    // Supprimer une commande par ID
    public void deleteCommande(Long id) {
        if (commandeRepository.existsById(id)) {
            commandeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Commande not found with ID: " + id);
        }
    }
}
