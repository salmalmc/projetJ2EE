package ma.formations.microserviceproduit.web;

import ma.formations.microserviceproduit.entities.Produit;
import ma.formations.microserviceproduit.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    // Récupérer tous les produits
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    // Récupérer un produit par son ID
    public Produit getProduitById(Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.orElse(null);
    }

    // Créer un nouveau produit
    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    // Mettre à jour un produit existant
    public Produit updateProduit(Long id, Produit produitDetails) {
        return produitRepository.findById(id).map(produit -> {
            produit.setNom(produitDetails.getNom());
            produit.setDescription(produitDetails.getDescription());
            return produitRepository.save(produit);
        }).orElse(null);
    }

    // Supprimer un produit
    public boolean deleteProduit(Long id) {
        if (produitRepository.existsById(id)) {
            produitRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
