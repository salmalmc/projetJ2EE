package ma.formations.microservicecommande.web;

import ma.formations.microservicecommande.dto.ProduitDTO;
import ma.formations.microservicecommande.entities.Commande;
import ma.formations.microservicecommande.feign.ProduitFeignClient;
import ma.formations.microservicecommande.web.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private final CommandeService commandeService;
    private final ProduitFeignClient produitClient;

    public CommandeController(CommandeService commandeService, ProduitFeignClient produitClient) {
        this.commandeService = commandeService;
        this.produitClient = produitClient;
    }

    // Récupérer toutes les commandes
    @GetMapping("all")
    public List<Commande> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        commandes.forEach(commande -> {
            ProduitDTO produit = produitClient.getProduitById(commande.getId_produit());
            commande.setProduit(produit);
        });
        return commandes;
    }

    // Récupérer les commandes récentes
    @GetMapping("/recentes")
    public List<Commande> getRecentCommandes() {
        List<Commande> commandes = commandeService.getRecentCommandes();
        commandes.forEach(commande -> {
            ProduitDTO produit = produitClient.getProduitById(commande.getId_produit());
            commande.setProduit(produit);
        });
        return commandes;
    }

    // Récupérer une commande par ID
    @GetMapping("/{id}")
    public Commande getCommandeById(@PathVariable Long id) {
        Commande commande = commandeService.getCommandeById(id)
                .orElseThrow(() -> new RuntimeException("Commande not found with ID: " + id));
        ProduitDTO produit = produitClient.getProduitById(commande.getId_produit());
        commande.setProduit(produit);
        return commande;
    }

    // Créer une nouvelle commande
    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeService.createCommande(commande);
    }

    // Mettre à jour une commande existante
    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande updatedCommande) {
        return commandeService.updateCommande(id, updatedCommande);
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
    }
}
