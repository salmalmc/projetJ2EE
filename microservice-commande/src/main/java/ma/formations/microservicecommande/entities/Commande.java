package ma.formations.microservicecommande.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.formations.microservicecommande.dto.ProduitDTO;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int quantite;
    private LocalDate date;
    private double montant;

    private Long id_produit; // ID du produit lié à cette commande

    @Transient // Indique que ce champ ne sera pas stocké dans la base de données
    private ProduitDTO produit;

    // Getter pour `id_produit`
    public Long getId_produit() {
        return id_produit;
    }

    // Setter pour attribuer un produit (ProduitDTO)
    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }
}
