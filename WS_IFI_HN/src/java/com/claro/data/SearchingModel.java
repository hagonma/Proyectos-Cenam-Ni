/*
 * Clase utilizada para la consulta de cargas de precios
 */
package com.claro.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marvin Urias
 */
public class SearchingModel {

    private DbModel base;

    public SearchingModel() {
        this.base = new DbModel();
    }

    public String consultarFactibilidad(double lat, double lng, int radio, int amount) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "Sin cobertura";

        conn = this.base.getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_GET_NEAR_CELL_V3 (?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lng);
            pstmt.setInt(3, radio);
            pstmt.setInt(4, amount);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            System.err.println("CONSULTA IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                System.err.println("CONSULTA IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Error al cerrar conexion a la base de datos" + e.getMessage());
            }
        }

        return respuesta;
    }

}
