package ma.formations.microservicecommande.feign;

import ma.formations.microservicecommande.dto.ProduitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "microservice-produit", url = "http://localhost:8084/api/produits")
public interface ProduitFeignClient {
    @GetMapping("/{id}")
    ProduitDTO getProduitById(@PathVariable Long id);

    @GetMapping("/all")
    List<ProduitDTO> getAllProduits();
}

