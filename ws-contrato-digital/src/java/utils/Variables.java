/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author marlon.rosalio
 */
public interface Variables {
  //DESARROLLO
  public static final String POOLTR = "CONTRATO-DIGITAL";
  /*public static final String POOLBSCS = "POOLBSCS";
  public static final String POOLGAIA = "POOLGAIA";
  public static final String POOLGEVENUE = "POOLGEVENUE";*/
  
  //PRODUCCION
  /*public static final String POOLTR = "jdbc/adinfo";
  public static final String POOLBSCS = "jdbc/bscs8wt";
  public static final String POOLGAIA = "jdbc/gaiaCRM";*/
  
  public static final String SEQCONTRACT = "SELECT SEQ_CD_CONTRACT.nextval RESULTADO FROM DUAL";
  public static final String UTF = "; charset=UTF-8";
}
