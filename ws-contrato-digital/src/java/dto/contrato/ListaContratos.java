package dto.contrato;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author marlon.rosalio
 */
public class ListaContratos implements Serializable {
  @Getter @Setter String idCustomer;
  @Getter @Setter String nombre;
  @Getter @Setter String typeDoc;
  @Getter @Setter String nameDoc;
  @Getter @Setter String numDoc;
  @Getter @Setter String telefono;
  @Getter @Setter String date;
  @Getter @Setter String idStatus;
  @Getter @Setter String nameStatus;
}
