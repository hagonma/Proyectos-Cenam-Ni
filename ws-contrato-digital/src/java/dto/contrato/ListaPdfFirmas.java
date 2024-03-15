package dto.contrato;

import java.io.Serializable;
import java.sql.Blob;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author marlon.rosalio
 */
public class ListaPdfFirmas implements Serializable {
  @Getter @Setter String idDoc;
  @Getter @Setter String tipoDoc;
  @Getter @Setter String pdf;
  @Getter @Setter String nombreCli;
  @Getter @Setter String ordeNum;
  @Getter @Setter String idServc;
  @Getter @Setter String tipoServc;
}
