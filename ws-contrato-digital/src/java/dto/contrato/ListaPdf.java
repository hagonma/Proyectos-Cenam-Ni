package dto.contrato;

import java.io.Serializable;
import java.sql.Blob;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author marlon.rosalio
 */
public class ListaPdf implements Serializable {
  @Getter @Setter String idDoc;
  @Getter @Setter String tipoDoc;
  @Getter @Setter String pdf;
}
