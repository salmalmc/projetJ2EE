package ma.formations.microserviceproduit.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ma.formations.microserviceproduit.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
}
