package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author marlon.rosalio
 */
public class Conectar implements Serializable{
  
  private static final Logger LOGGER = Logger.getLogger(Conectar.class.getName());

  public Connection obtener(String jdni) {
    try {
      Connection con = null;
      Context ctx = new InitialContext();
      DataSource ds = (DataSource) ctx.lookup(jdni);
      if (ds != null) {
        con = ds.getConnection();
      }
      return con;
    } catch (NamingException | SQLException ex) {
      LOGGER.log(Level.WARNING, ex.getMessage());
      return null;
    }
  }

  public void cerrarConexcion(Connection con) {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException ex) {
        LOGGER.log(Level.WARNING, ex.getMessage());
      }
    }
  }

  public void cerrarResult(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException ex) {
        LOGGER.log(Level.WARNING, ex.getMessage());
      }
    }
  }

  public void cerrarPS(PreparedStatement ps) {
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException ex) {
        LOGGER.log(Level.WARNING, ex.getMessage());
      }
    }
  }
  
  public Connection conectarGaia(String valoresTns) {
    String ip = "";
    String port = "";
    String bd = "";
    String usr = "";
    String pass = "";
    
    String[] tns = valoresTns.split(";");
    
    try {
      for (int i = 0; i < tns.length; i++) {
        switch (i) {
          case 0:
            ip = tns[i];
          break;
          case 1:
            port = tns[i];
          break;
          case 2:
            bd = tns[i];
          break;
          case 3:
            usr = tns[i];
          break;
          case 4:
            pass = tns[i];
          break;
        }
      }
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "Error en parseo de conexion: " + ex.getMessage());
      ip = "192.168.3.103";
      port = "1525";
      bd = "GAIASV";
      usr = "CRMP";
      pass = "CrRQ_jA51069GwZy";
    }
        
    try {
      //LEER PARAMETROS DE CONFIGURACION DE CONEXION
      String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = "+ip+")(PORT = "+port+"))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = "+bd+")))";
      Connection conexion = DriverManager.getConnection(url, usr, pass);

      try {
        conexion = DriverManager.getConnection(url, usr, pass);
        LOGGER.log(Level.WARNING, "Conexion a la base de datos: " + url);
        
        return conexion;
      } catch (Exception ex) {
        LOGGER.log(Level.WARNING, "Error en la conexion: " + url + "error: " + ex.getMessage());
        return null;
      }
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "Error en la conexion: " + ex.getMessage());
      return null;
    }
  }
}
