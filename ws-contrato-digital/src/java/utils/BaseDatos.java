package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.contrato.ListaContratos;
import dto.contrato.ListaPdf;
import dto.contrato.ListaPdfFirmas;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marlon.rosalio
 */
public class BaseDatos {
  
  private String pool;
  
  public BaseDatos (String vpool){
    this.pool = vpool;
  }
  
  public String consulta(String consulta) {
    String valor = "";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    try {
      conexion = con.obtener(this.pool);
      String query = consulta;
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      if (rs.next()) {
        valor = rs.getString("RESULTADO");
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      valor = "{\"resultado\":\"Error\", \"comentario\":\"Ocurrió un error al consultar la información\", \"codigo\":\""+ex.getMessage().replace("\n", "")+"\", \"transaccion\":\"-1\"}";
    } finally {
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return valor;
  }
  
  public String Ejecutar(String query) {
    PreparedStatement ps = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    String resultado = "";
    
    try {
      conexion = con.obtener(this.pool);
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Ejecutando transaccion: {0}", new Object[]{query});
      ps = conexion.prepareStatement(query);
      ps.executeUpdate();
      
      resultado = "true";
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error: {0} - motivo: {1}",new Object[]{query, ex.getMessage()});
      resultado = ex.getMessage().replace("\n", "");
    } finally {
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return resultado;
  }
  
  public String getTipoDocs() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_TIPO_DOC, NAME_TIPO_DOC FROM CD_CAT_TIPO_DOC ORDER BY 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idDoc\": \""+rs.getString("ID_TIPO_DOC")+"\", \"nameDoc\": \""+rs.getString("NAME_TIPO_DOC")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }

    return "["+Docs+"]";
  }
  
  public String getProvincias() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_PROVINCIA, NAME_PROVINCIA FROM CD_CAT_PROVINCIA WHERE STATUS = 1 ORDER BY 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idProvincia\": \""+rs.getString("ID_PROVINCIA")+"\", \"nameProvincia\": \""+rs.getString("NAME_PROVINCIA")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getCantones(String cadena) {
    JsonParser parser= new JsonParser();
    JsonObject root = parser.parse(cadena).getAsJsonObject();
    
    int provincia  = Integer.valueOf(root.get("provincia").getAsInt());
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_CANTON, NAME_CANTON FROM CD_CAT_CANTON WHERE ID_PROVINCIA = "+provincia+" AND STATUS = 1 ORDER BY NAME_CANTON";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idCanton\": \""+rs.getString("ID_CANTON")+"\", \"nameCanton\": \""+rs.getString("NAME_CANTON")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getDistritos(String cadena) {
    JsonParser parser= new JsonParser();
    JsonObject root = parser.parse(cadena).getAsJsonObject();
    
    int canton  = Integer.valueOf(root.get("canton").getAsInt());
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_DISTRITO, NAME_DISTRITO FROM CD_CAT_DISTRITO WHERE ID_CANTON = "+canton+" AND STATUS = 1 ORDER BY NAME_DISTRITO";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idDistrito\": \""+rs.getString("ID_DISTRITO")+"\", \"nameDistrito\": \""+rs.getString("NAME_DISTRITO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getDirRef() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_REF, NAME_REF FROM CD_CAT_DIR_REF WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idRef\": \""+rs.getString("ID_REF")+"\", \"nameRef\": \""+rs.getString("NAME_REF")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getDirLado() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_LADO, NAME_LADO FROM CD_CAT_DIR_LADO WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idLado\": \""+rs.getString("ID_LADO")+"\", \"nameLado\": \""+rs.getString("NAME_LADO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getMarcaTerminal() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_MARCA, NAME_MARCA FROM CD_CAT_MARCA_TERMINAL WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idMarca\": \""+rs.getString("ID_MARCA")+"\", \"nameMarca\": \""+rs.getString("NAME_MARCA")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getModeloTerminal(String cadena) {
    JsonParser parser= new JsonParser();
    JsonObject root = parser.parse(cadena).getAsJsonObject();
    
    int marca  = root.get("marca").getAsInt();
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_MODELO, NAME_MODELO FROM CD_CAT_MODELO_TERMINAL WHERE ID_MARCA = "+marca+" AND STATUS = 1 ORDER BY 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idModelo\": \""+rs.getString("ID_MODELO")+"\", \"nameModelo\": \""+rs.getString("NAME_MODELO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getTipoServicio() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_SERVICIO, NAME_SERVICIO FROM CD_CAT_SERVICIO WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idServicio\": \""+rs.getString("ID_SERVICIO")+"\", \"nameServicio\": \""+rs.getString("NAME_SERVICIO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getPlanes(String cadena) {
    JsonParser parser= new JsonParser();
    JsonObject root = parser.parse(cadena).getAsJsonObject();
    
    int servicio  = root.get("servicio").getAsInt();
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT P.ID_PLAN, P.NAME_PLAN FROM CD_CAT_SERVICIO_PLAN SP\n" +
                     "INNER JOIN CD_CAT_PLAN P ON P.ID_PLAN = SP.ID_PLAN \n" +
                     "WHERE SP.ID_SERVICIO = "+servicio+" AND SP.STATUS = 1 AND P.STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idPlan\": \""+rs.getString("ID_PLAN")+"\", \"namePlan\": \""+rs.getString("NAME_PLAN")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getDetallePlan(String cadena) {
    JsonParser parser= new JsonParser();
    JsonObject root = parser.parse(cadena).getAsJsonObject();
    
    int plan  = root.get("plan").getAsInt();
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT INTERNET, MINUTOS, MSJ, TV, MONTO, VELOCIDAD, DESCARGAV, DESCARGAF, EXCEDENTE"+
                     " FROM CD_CAT_PLAN WHERE ID_PLAN = "+plan;
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"internet\": \""+rs.getString("INTERNET")+"\", \"minutos\": \""+rs.getString("MINUTOS")+"\", \"msj\": \""+rs.getString("MSJ")+"\", \"tv\": \""+rs.getString("TV")+"\", \"monto\": \""+rs.getString("MONTO")+"\", \"velocidad\": \""+rs.getString("VELOCIDAD")+"\", \"descargav\": \""+rs.getString("DESCARGAV")+"\", \"descargaf\": \""+rs.getString("DESCARGAF")+"\", \"excedente\": \""+rs.getString("EXCEDENTE")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getCicloFact() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_CICLO, NAME_CICLO FROM CD_CAT_CICLO_FACT WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idCiclo\": \""+rs.getString("ID_CICLO")+"\", \"nameCiclo\": \""+rs.getString("NAME_CICLO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getFinancing() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_FINANCING, NAME_FINANCING FROM CD_CAT_FINANCING WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idFinancing\": \""+rs.getString("ID_FINANCING")+"\", \"nameFinancing\": \""+rs.getString("NAME_FINANCING")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getPlazo() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_PLAZO, NAME_PLAZO FROM CD_CAT_PLAZO WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idPlazo\": \""+rs.getString("ID_PLAZO")+"\", \"namePlazo\": \""+rs.getString("NAME_PLAZO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getMedioPago() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_PAGO, NAME_PAGO FROM CD_CAT_MEDIO_PAGO WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idPago\": \""+rs.getString("ID_PAGO")+"\", \"namePago\": \""+rs.getString("NAME_PAGO")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public String getOperador() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    String Docs = "";

    try {
      conexion = con.obtener(this.pool);      
      String query = "SELECT ID_OPERADOR, NAME_OPERADOR FROM CD_CAT_OPERADOR WHERE STATUS = 1";
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        Docs = Docs+"{ \"idOperador\": \""+rs.getString("ID_OPERADOR")+"\", \"nameOperador\": \""+rs.getString("NAME_OPERADOR")+"\" },";
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }

    if(Docs.length()>0){
      Docs = Docs.substring(0, Docs.length()-1);
    }
    
    return "["+Docs+"]";
  }
  
  public ArrayList<ListaContratos> getCasosAsesor(String consulta, String user) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    ArrayList<ListaContratos> listaContratos = new ArrayList<ListaContratos>();

    try {
      conexion = con.obtener(this.pool);      
      String query = consulta;
      ps = conexion.prepareStatement(query);
      ps.setString(1, user);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        ListaContratos registro = new ListaContratos();
        
        registro.setIdCustomer(rs.getString("ID_CUSTOMER"));
        registro.setNombre(rs.getString("NOMBRE"));
        registro.setTypeDoc(rs.getString("TYPE_DOC"));
        registro.setNameDoc(rs.getString("NAME_TIPO_DOC"));
        registro.setNumDoc(rs.getString("NUM_DOC"));
        registro.setTelefono(rs.getString("TELEFONO"));
        registro.setDate(rs.getString("FECHA_MODIFICACION"));
        registro.setIdStatus(rs.getString("STATUS"));
        registro.setNameStatus(rs.getString("NAME_STATUS"));
        
        listaContratos.add(registro);
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return listaContratos;
  }

  public ArrayList<ListaContratos> getCasosBackoffice(String consulta) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    ArrayList<ListaContratos> listaContratos = new ArrayList<ListaContratos>();

    try {
      conexion = con.obtener(this.pool);      
      String query = consulta;
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        ListaContratos registro = new ListaContratos();
        
        registro.setIdCustomer(rs.getString("ID_CUSTOMER"));
        registro.setNombre(rs.getString("NOMBRE"));
        registro.setTypeDoc(rs.getString("TYPE_DOC"));
        registro.setNameDoc(rs.getString("NAME_TIPO_DOC"));
        registro.setNumDoc(rs.getString("NUM_DOC"));
        registro.setTelefono(rs.getString("TELEFONO"));
        registro.setDate(rs.getString("FECHA_MODIFICACION"));
        registro.setIdStatus(rs.getString("STATUS"));
        registro.setNameStatus(rs.getString("NAME_STATUS"));
        
        listaContratos.add(registro);
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return listaContratos;
  }
  
  public ArrayList<ListaContratos> getCasosSupervisor(String consulta) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    ArrayList<ListaContratos> listaContratos = new ArrayList<ListaContratos>();

    try {
      conexion = con.obtener(this.pool);      
      String query = consulta;
      ps = conexion.prepareStatement(query);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        ListaContratos registro = new ListaContratos();
        
        registro.setIdCustomer(rs.getString("ID_CUSTOMER"));
        registro.setNombre(rs.getString("NOMBRE"));
        registro.setTypeDoc(rs.getString("TYPE_DOC"));
        registro.setNameDoc(rs.getString("NAME_TIPO_DOC"));
        registro.setNumDoc(rs.getString("NUM_DOC"));
        registro.setTelefono(rs.getString("TELEFONO"));
        registro.setDate(rs.getString("FECHA_MODIFICACION"));
        registro.setIdStatus(rs.getString("STATUS"));
        registro.setNameStatus(rs.getString("NAME_STATUS"));
        
        listaContratos.add(registro);
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return listaContratos;
  }
  
  public String insertPdf(String customer, String doc, String pdf) {
    PreparedStatement ps = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    String resultado = "";
    
    try {
      conexion = con.obtener(this.pool);
      
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Insertando pdf");
      
      ps = conexion.prepareStatement("INSERT INTO CD_DOC_PDF (ID_CUSTOMER, ID_DOC, PDF, STATUS) VALUES (?, ?, ?, 1)");
      ps.setString(1, customer);
      ps.setString(2, doc);
      ps.setString(3, pdf);
      ps.executeUpdate();
      resultado = "true";
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error al insertar pdf - motivo: {0}",new Object[]{ex.getMessage()});
      resultado = ex.getMessage().replace("\n", "");
    } finally {
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return resultado;
  }
  
  public String updatePdf(String customer, String doc, String pdf) {
    PreparedStatement ps = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    String resultado = "";
    
    try {
      conexion = con.obtener(this.pool);
      
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Actualizando pdf");
      
      ps = conexion.prepareStatement("UPDATE CD_DOC_PDF SET PDF = ?, STATUS = 1 WHERE ID_CUSTOMER = ? AND ID_DOC = ?");
      ps.setString(1, pdf);
      ps.setString(2, customer);
      ps.setString(3, doc);
      ps.executeUpdate();
      resultado = "true";
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error al insertar pdf - motivo: {0}",new Object[]{ex.getMessage()});
      resultado = ex.getMessage().replace("\n", "");
    } finally {
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return resultado;
  }
  
  public ArrayList<ListaPdf> getPdf(String consulta, String customer) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    ArrayList<ListaPdf> listaPdf = new ArrayList<ListaPdf>();

    try {
      byte[] imgData = null;
      Blob img = null;
      String text = null;
      conexion = con.obtener(this.pool);      
      String query = consulta;
      ps = conexion.prepareStatement(query);
      ps.setString(1, customer);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        ListaPdf registro = new ListaPdf();
        
        registro.setIdDoc(rs.getString("ID_DOC"));
        registro.setTipoDoc(rs.getString("NOMBRE"));
        registro.setPdf(rs.getString("PDF"));
       
        listaPdf.add(registro);
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return listaPdf;
  }
  
  public ArrayList<ListaPdfFirmas> getPdfFirmas(String consulta, String customer) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    
    ArrayList<ListaPdfFirmas> listaPdf = new ArrayList<ListaPdfFirmas>();

    try {
      byte[] imgData = null;
      Blob img = null;
      String text = null;
      conexion = con.obtener(this.pool);      
      String query = consulta;
      ps = conexion.prepareStatement(query);
      ps.setString(1, customer);
      rs = ps.executeQuery();
      
      while (rs.next()) {
        ListaPdfFirmas registro = new ListaPdfFirmas();
        
        registro.setIdDoc(rs.getString("ID_DOC"));
        registro.setTipoDoc(rs.getString("NOMBRE"));
        registro.setPdf(rs.getString("PDF"));
        registro.setNombreCli(rs.getString("NOMBRECLI"));
        registro.setOrdeNum(rs.getString("ORDENUM"));
        registro.setIdServc(rs.getString("IDSERVC"));
        registro.setTipoServc(rs.getString("TIPOSERVC"));
        
        listaPdf.add(registro);
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }finally{  
      con.cerrarResult(rs);
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return listaPdf;
  }
  
  public String insertFirma(String customer, String acepta, String firma, String frente, String dorso, String comentario) {
    PreparedStatement ps = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    String resultado = "";
    
    try {
      conexion = con.obtener(this.pool);
      
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Insertando firma");
      
      ps = conexion.prepareStatement("INSERT INTO CD_DOC_FIRMA (ID_CUSTOMER, ACEPTA, FIRMA, DOC_FRENTE, DOC_DORSO, COMENTARIO) VALUES (?, ?, ?, ?, ?, ?)");
      ps.setString(1, customer);
      ps.setString(2, acepta);
      ps.setString(3, firma);
      ps.setString(4, frente);
      ps.setString(5, dorso);
      ps.setString(6, comentario);
      ps.executeUpdate();
      resultado = "true";
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error al insertar firma - motivo: {0}",new Object[]{ex.getMessage()});
      resultado = ex.getMessage().replace("\n", "");
    } finally {
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return resultado;
  }
  
  public String updateFirma(String customer, String acepta, String firma, String frente, String dorso, String comentario) {
    PreparedStatement ps = null;
    Connection conexion = null;
    Conectar con = new Conectar();
    String resultado = "";
    
    try {
      conexion = con.obtener(this.pool);
      
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Actualizando firma");
      
      ps = conexion.prepareStatement("UPDATE CD_DOC_FIRMA SET ACEPTA=?, FIRMA=?, DOC_FRENTE=?, DOC_DORSO=?, COMENTARIO=? WHERE ID_CUSTOMER = ?");
      ps.setString(1, acepta);
      ps.setString(2, firma);
      ps.setString(3, frente);
      ps.setString(4, dorso);
      ps.setString(5, comentario);
      ps.setString(6, customer);
      ps.executeUpdate();
      resultado = "true";
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,"Error al actualizar firma - motivo: {0}",new Object[]{ex.getMessage()});
      resultado = ex.getMessage().replace("\n", "");
    } finally {
      con.cerrarPS(ps);
      con.cerrarConexcion(conexion);
    }
    
    return resultado;
  }
}
