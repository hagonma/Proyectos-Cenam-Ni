package com.claro.pa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.claro.pa.controller.ConexionOPEN;
import com.claro.pa.controller.ConexionOracle;
import com.claro.pa.controller.ConexionCRM;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataAccessObject {

	protected Connection conNew;
        protected Connection conNew2;
        protected Connection conNew3;
	protected PreparedStatement stm;
	protected ResultSet rst;
        protected ResultSet rst2;
        protected ResultSet rst3;
        protected ResultSet rst4;
        protected ResultSet rst5;
	protected String sql;
	protected String sqNextval;
	protected int p;
	
	protected int idPais;
	//BSCS
	protected void connect() throws SQLException{
            try {
                conNew = ConexionOPEN.Conectar("506");
            } catch (Exception ex) {
                Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
            }
		p = 1;
	}
        
        //CDN
        protected void connect2() throws SQLException{
            try {
                conNew2= ConexionOracle.Conectar();
            } catch (Exception ex) {
                Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
            }
		p = 1;
	}
        //CRM
         protected void connect3() throws SQLException{
            try {
                conNew3= ConexionCRM.Conectar("506");
            } catch (Exception ex) {
                Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
            }
		p = 1;
	}
	
	protected void disconnect(Connection con, Statement stm, ResultSet rst) {
		if(rst!=null)try {rst.close();rst=null;}catch(Exception e) {};
		if(stm!=null)try {stm.close();stm=null;}catch(Exception e) {};
		if(con!=null)try {con.close();con=null;}catch(Exception e) {};
	
	}
	protected void disconnect() {
		if(rst!=null)try {rst.close();rst=null;}catch(Exception e) {};
		if(stm!=null)try {stm.close();stm=null;}catch(Exception e) {};
		if(conNew!=null)try {conNew.close();conNew=null;}catch(Exception e) {};
	
	}
}
