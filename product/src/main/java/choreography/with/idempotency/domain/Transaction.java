package choreography.with.idempotency.domain;
import javax.persistence.Entity;
import javax.persistence.Id;

import choreography.with.idempotency.ProductApplication;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    Long orderId;
    Integer stockOrdered;
    String customerId;

    public static TransactionRepository repository(){
        return ProductApplication.applicationContext.getBean(TransactionRepository.class);
    }
}