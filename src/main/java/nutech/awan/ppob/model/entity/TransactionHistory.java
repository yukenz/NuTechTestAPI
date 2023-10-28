package nutech.awan.ppob.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    public static enum TransactionType {

        PAYMENT("PAYMENT"),
        TOPUP("TOPUP");

        private final String value;

        TransactionType(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    private String invoice;
    private String memberEmail;
    private String description;
    private Long amount;
    private TransactionType type;
    private Instant createdOn;

}
