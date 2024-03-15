package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marlon.rosalio
 */
public class Parametros implements Serializable{
  
  private String pool;
  
  public Parametros(String vpool) {
    this.pool = vpool;
  }
  
  public String consulta(String parametro) {
    String valor = "";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    try {
      conexion = con.obtener(this.pool);
      String query = "SELECT VALOR RESULTADO FROM CD_PARAMETROS WHERE PARAMETRO = '" +parametro+ "'";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      if (rs.next()) {
        valor = rs.getString("RESULTADO");
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());    
    } finally {
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return valor;
  }
  
  public boolean Ejecutar(String valor, String parametro) {
    PreparedStatement ps = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    String query = "UPDATE CD_PARAMETROS SET VALOR = '"+valor+"' WHERE PARAMETRO = '"+parametro+"'";
    
    try {
      conexion = con.obtener(this.pool);
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Ejecutando transaccion: {0}", new Object[]{query});
      ps = conexion.prepareStatement(query);
      ps.executeUpdate();
      
      return true;
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error: {0} - motivo: {1}",new Object[]{query, ex.getMessage()});
      return false;
    } finally {
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
  }
}
