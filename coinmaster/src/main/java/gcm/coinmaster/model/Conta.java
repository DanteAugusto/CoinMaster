package gcm.coinmaster.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Conta {
    
    @Id
    private String numero;
    private double saldo;
}
