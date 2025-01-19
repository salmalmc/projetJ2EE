package ma.formations.microservicecommande.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProduitDTO {
    private Long id;
    private String nom;
    private String description;
    private double prix;
}
