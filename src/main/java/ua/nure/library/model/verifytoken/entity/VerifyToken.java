package ua.nure.library.model.verifytoken.entity;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.library.model.user.entity.Reader;

/**
 * @author Artem Kudria
 */
@Data
@NoArgsConstructor
public class VerifyToken {

  private Long id;
  private String token;
  private Reader user;
  private LocalDate expiryDate;

  public VerifyToken(Reader user) {
    this.user = user;
    this.token = UUID.randomUUID().toString();
    this.expiryDate = LocalDate.now();
  }
}
