package Model;

import Controller.Conector;
import Controller.ConexionOPEN;
import Controller.ConexionOracle;
import Controller.Helper;
import Model.Select;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;


public class ConsultasOPEN
{
  int pantalla;
  int rol;
  int estado;
  int id;
  public ResultSet rs;
  public PreparedStatement ps;
  public JSONArray responseArray;
/*   32 */   public Helper hp = new Helper();
/*   33 */   public ConexionOPEN con = new ConexionOPEN();
/*   34 */   public ConexionOracle conO = new ConexionOracle();
/*   35 */   public ConexionOracle conO2 = new ConexionOracle();
  public String pais;
  
  public ConsultasOPEN(String idpais) {
/*   39 */     this.pais = idpais;
    try {
/*   41 */       this.con.Conectar(this.pais);
/*   42 */     } catch (Exception exception) {}
  }


  
  public ResultSet obtenerInformacion() throws SQLException, Exception {
/*   48 */     String id_cliente = "";
/*   49 */     String cliente = "";
/*   50 */     String servicio_adquirido = "";
/*   51 */     String cuota_mensual = "";
/*   52 */     String fecha_pago = "";
/*   53 */     String fecha_contratacion = "";
/*   54 */     String vigencia_contrato = "";
/*   55 */     String fecha_instalacion = "";
/*   56 */     String equipo = "";
/*   57 */     String email = "";
/*   58 */     String television = "";
/*   59 */     String linea_fija = "";
/*   60 */     String internet = "";
/*   61 */     String claro_video = "";
/*   62 */     String fox = "";
/*   63 */     String hbo = "";
/*   64 */     String query = "";
/*   65 */     String update = "";
/*   66 */     String paisAb = "";
/*   67 */     String contrato = "";
/*   68 */     String numeroReferencia = "";
/*   69 */     String basico = "";
/*   70 */     String avanzado = "";
/*   71 */     String velocidadInternet = "";
    try {
/*   73 */       if (this.pais.equals("504")) {

        
/*   76 */         query = "select id_cliente, cliente\n    , case when count(distinct servicio) = 1 then 'Casa Claro Simple'\n           when count(distinct servicio) = 2 then 'Casa Claro Doble'\n           when count(distinct servicio) >= 3 then 'Casa Claro Triple'\n      end servicio\n    ,nvl(sum(distinct(select count(distinct sesuserv) from smart.servsusc where sesuserv = 6042 and sesususc=xx.sesususc and sesuesco not in (96,110))),0) television\n    ,nvl(sum(distinct( case when (select count(distinct sesuserv) from smart.servsusc where sesuserv = 24 and sesususc=xx.sesususc and sesuesco not in (96,110)) =1 then 1\n                        when (select count(distinct sesuserv) from smart.servsusc,SMART.PR_COMPONENT\n                                where sesunuse=product_id\n                                and sesususc = xx.sesususc\n                                and sesuserv = 9013\n                                and class_service_id = 5082\n                                and component_status_id != 9 and sesuesco not in (96,110)) =1 then 1 else 0 end)\n              ),0) internet\n     ,nvl(sum(distinct( case when (select count(distinct sesuserv) from smart.servsusc where sesuserv = 185 and sesususc=xx.sesususc and sesuesco not in (96,110)) =1 then 1\n                        when (select count(distinct sesuserv) from smart.servsusc,SMART.PR_COMPONENT\n                                where sesunuse=product_id\n                                and sesususc = xx.sesususc\n                                and sesuserv = 9013\n                                and class_service_id = 5081\n                                and component_status_id != 9 and sesuesco not in (96,110)) =1 then 1 else 0 end)\n              ),0) llamadas\n    ,fecha_instalacion ,min(fecha_registro) fecha_registro,fecha_pago\n    ,round(MONTHS_BETWEEN(max(fecha_fin_vigencia),(min(fecha_registro))))||' meses' vigencia_contrato\n    ,fina,e_mail,sum(renta) renta, sum(hbo) hbo,sum(fox) fox,sum(claro_video) claro_video,sum(basico) basico,sum(avanzado) avanzado\n    ,sum(llamada) llamada, sum(vel_iba)||' '||max(vel_cla) vel_iba,sesususc contrato\nfrom (\nselect subscriber_id id_cliente,sesususc\n    ,(select suscnomb from smart.suscripc where susccodi=sesususc) Cliente\n    , case when sesuserv = 9013 then (select description from smart.pr_component aa, smart.ps_class_service bb\n                                        where aa.class_service_id=bb.class_service_id\n                                        and component_type_id = 7010 and component_status_id != 9\n                                        and product_id = sesunuse)\n        else servdesc end servicio\n    , trunc(sesufein) fecha_instalacion\n    ,(select trunc(request_date) from smart.mo_packages where package_type_id = 587 and product_id = sesunuse) fecha_registro, '20 de cada mes' fecha_pago\n    ,(select trunc(expiration_of_plan) from smart.pr_product where product_id = sesunuse) fecha_fin_vigencia\n    ,nvl((select pldidesc\n            from smart.diferido\n                ,smart.plandife\n                ,smart.concepto\n                ,smart.suscripc\n            where pldicodi=difepldi\n            and conccodi=difeconc\n            and susccodi=difesusc\n            and difecupa < difenucu\n            and difesusc = sesususc\n            and rownum <=1),'N/A') fina\n     ,(SELECT dccodeco fROM SMART.SUSCRIPC sp    ,smart.dicocobr dc where dccocodi=suscdcco and susccodi = sesususc)e_mail\n     ,nvl((select sum(retavaba)\n                From smart.pr_component aa,\n                            (SELECT plsucodi , d.servcodi,\n                                   a.tacoconc \"Id Concepto\"--, g.concdesc \"Concepto\",\n                                   ,j.class_service_id ,\n                                   k.retavaba,\n                                   c.geograp_location_id,\n                                   a.tacocate,\n                                   a.tacosuca \n                              FROM smart.tariconc a,\n                                   smart.ge_geogra_location b,\n                                   smart.ge_geogra_location c,\n                                   smart.servicio d,\n                                   smart.categori e,\n                                   smart.subcateg f,\n                                   smart.concepto g,\n                                   smart.gst_tipomone h,\n                                   smart.ps_component_type i,\n                                   smart.ps_class_service j,\n                                   smart.regltari k,\n                                   smart.plansusc l\n                            WHERE a.tacodepa = b.geograp_location_id\n                               AND a.tacoloca = c.geograp_location_id\n                               AND a.tacoserv = d.servcodi\n                               AND a.tacocate = e.catecodi\n                               AND a.tacocate = f.sucacate\n                               AND a.tacosuca = f.sucacodi\n                               AND a.tacoconc = g.conccodi\n                               AND a.tacotimo = h.timocodi\n                               AND a.tacotcom = i.component_type_id\n                               AND a.tacoclse = j.class_service_id\n                               AND a.tacocodi = k.retataco\n                               AND k.retaplsu = l.plsucodi\n                               AND k.retavige LIKE 'S'\n                               AND a.tacosesu = -1\n                               and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=plsucodi and retavige like 'S')\n                            and a.tacoconc not in (65) ) pp --\n                where 3=3\n                and pp.class_service_id= aa.class_service_id \n                and aa.product_id=aa.sesunuse\n                and pp.servcodi = sesuserv\n                and component_status_id !=9\n                and pp.servcodi in (24,6042)\n                and pp.geograp_location_id = nvl((select tacoloca\n                                                FROM smart.tariconc a,\n                                                       smart.ps_component_type i,\n                                                       smart.regltari k\n                                                WHERE  a.tacotcom = i.component_type_id\n                                                   AND a.tacocodi = k.retataco\n                                                   AND k.retavige LIKE 'S'\n                                                   AND a.tacosesu = -1\n                                                   and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=k.retaplsu and retavige like 'S')\n                                                   and retaplsu = pp.plsucodi\n                                                   and tacoclse = aa.class_service_id\n                                                   and tacoloca = (select sesuloca from smart.servsusc where sesunuse = aa.sesunuse)\n                                                   and rownum <= 1),-1)\n                and pp.tacocate = nvl((select tacocate\n                                                FROM smart.tariconc a,\n                                                       smart.ps_component_type i,\n                                                       smart.regltari k\n                                                WHERE  a.tacotcom = i.component_type_id\n                                                   AND a.tacocodi = k.retataco\n                                                   AND k.retavige LIKE 'S'\n                                                   AND a.tacosesu = -1\n                                                   and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=k.retaplsu and retavige like 'S')\n                                                   and retaplsu = pp.plsucodi\n                                                   and tacoclse = aa.class_service_id\n                                                   and tacocate = (select sesucate from smart.servsusc where sesunuse= aa.sesunuse)  --------------\n                                                   and rownum <= 1),-1)\n                and pp.tacosuca = nvl((select tacosuca\n                                                FROM smart.tariconc a,\n                                                       smart.ps_component_type i,\n                                                       smart.regltari k\n                                                WHERE  a.tacotcom = i.component_type_id\n                                                   AND a.tacocodi = k.retataco\n                                                   AND k.retavige LIKE 'S'\n                                                   AND a.tacosesu = -1\n                                                   and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=k.retaplsu and retavige like 'S')\n                                                   and retaplsu = pp.plsucodi\n                                                   and tacoclse = aa.class_service_id\n                                                   and tacosuca = (select sesusuca from smart.servsusc where sesunuse= aa.sesunuse)  --------------\n                                                   and rownum <= 1),-1)                \n                and pp.plsucodi=aa.sesuplfa--\n                ),0)+   --------------\n                nvl((select sum(retavaba)\n                From smart.pr_component aa,\n                            (SELECT plsucodi , d.servcodi,\n                                   a.tacoconc \"Id Concepto\"--, g.concdesc \"Concepto\",\n                                   ,j.class_service_id ,\n                                   k.retavaba \n                              FROM smart.tariconc a,\n                                   smart.ge_geogra_location b,\n                                   smart.ge_geogra_location c,\n                                   smart.servicio d,\n                                   smart.categori e,\n                                   smart.subcateg f,\n                                   smart.concepto g,\n                                   smart.gst_tipomone h,\n                                   smart.ps_component_type i,\n                                   smart.ps_class_service j,\n                                   smart.regltari k,\n                                   smart.plansusc l\n                            WHERE a.tacodepa = b.geograp_location_id\n                               AND a.tacoloca = c.geograp_location_id\n                               AND a.tacoserv = d.servcodi\n                               AND a.tacocate = e.catecodi\n                               AND a.tacocate = f.sucacate\n                               AND a.tacosuca = f.sucacodi\n                               AND a.tacoconc = g.conccodi\n                               AND a.tacotimo = h.timocodi\n                               AND a.tacotcom = i.component_type_id\n                               AND a.tacoclse = j.class_service_id\n                               AND a.tacocodi = k.retataco\n                               AND k.retaplsu = l.plsucodi\n                               AND k.retavige LIKE 'S'\n                               AND a.tacosesu = -1\n                               and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=plsucodi and retavige like 'S')\n                            and a.tacoconc not in (65) ) pp --\n                where 3=3\n                and pp.class_service_id= -1\n                and aa.class_service_id is null\n                and component_status_id != 9\n                and aa.product_id=aa.sesunuse\n                and component_type_id in (51,82)\n                and pp.servcodi in (185,11) --\n                and pp.servcodi = sesuserv --\n                and pp.plsucodi =aa.sesuplfa --                \n                ),0)+ --------------\n                nvl((select sum(retavaba)\n                From smart.pr_component aa,\n                            (SELECT plsucodi , d.servcodi,\n                                   a.tacoconc \"Id Concepto\"--, g.concdesc \"Concepto\",\n                                   ,j.class_service_id ,\n                                   k.retavaba \n                              FROM smart.tariconc a,\n                                   smart.ge_geogra_location b,\n                                   smart.ge_geogra_location c,\n                                   smart.servicio d,\n                                   smart.categori e,\n                                   smart.subcateg f,\n                                   smart.concepto g,\n                                   smart.gst_tipomone h,\n                                   smart.ps_component_type i,\n                                   smart.ps_class_service j,\n                                   smart.regltari k,\n                                   smart.plansusc l\n                            WHERE a.tacodepa = b.geograp_location_id\n                               AND a.tacoloca = c.geograp_location_id\n                               AND a.tacoserv = d.servcodi\n                               AND a.tacocate = e.catecodi\n                               AND a.tacocate = f.sucacate\n                               AND a.tacosuca = f.sucacodi\n                               AND a.tacoconc = g.conccodi\n                               AND a.tacotimo = h.timocodi\n                               AND a.tacotcom = i.component_type_id\n                               AND a.tacoclse = j.class_service_id\n                               AND a.tacocodi = k.retataco\n                               AND k.retaplsu = l.plsucodi\n                               AND k.retavige LIKE 'S'\n                               AND a.tacosesu = -1\n                               and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=plsucodi and retavige like 'S')\n                                ) pp --\n                where 3=3\n                and pp.class_service_id= aa.class_service_id \n                and aa.product_id=aa.sesunuse\n                and component_status_id != 9\n                and pp.servcodi = sesuserv --\n                and pp.servcodi in (9013)\n                and pp.plsucodi = aa.sesuplfa --\n                ),0) renta\n    ,(select count(*) from smart.pr_component aa, smart.ps_class_service bb\n        where aa.class_service_id=bb.class_service_id\n        and component_type_id = 98\n        and component_status_id != 9\n        and lower(description) like '%hbo%'\n        and product_id = sesunuse\n        and rownum <= 1) HBO  \n    ,(select count(*) from smart.pr_component aa, smart.ps_class_service bb\n        where aa.class_service_id=bb.class_service_id\n        and component_type_id = 98\n        and component_status_id != 9\n        and lower(description) like '%fox%'\n        and product_id = sesunuse\n        and rownum <= 1) fox    \n    ,nvl((select case when aplica_promo_bts = 'SI' then 1 else 0 end from smart.VW_CV_PROMO_BTS_TEST where id_producto= sesunuse),0) claro_video \n    ,(select count(*) from smart.pr_component aa\n        where component_status_id != 9\n        and product_id = sesunuse\n        and class_service_id in (3154,100,5020)\n        and rownum <= 1) basico  \n    ,(select count(*) from smart.pr_component aa\n        where component_status_id != 9\n        and product_id = sesunuse\n        and class_service_id in (8033,5091,5090,5056,5054,5053,5052,5051,5035,3157,3156,3155,2501,2205,2204,2203)\n        and rownum <= 1) avanzado \n    ,(SELECT case when sum(LR.raftvaun) = 0 then 1 else 0 end \n        FROM smart.LE_PLANES LP, smart.LE_TARIFAS LT, smart.LE_VIGETARI LV, smart.LE_FRANHOTL LH, smart.LE_RANGFHTL LR\n            ,smart.pr_product gg, smart.cc_commercial_plan hh\n        WHERE LP.PLANCODI = LT.TARIPLLI\n        AND LT.TARICONS = LV.VITACOTA\n        AND LV.VITACONS = LH.FRHTCOVT\n        AND LH.FRHTCONS = LR.RAFTCOFH\n        and taritiev = 1\n        and gg.commercial_plan_id=hh.commercial_plan_id and product_id = sesunuse\n        AND  LP.PLANCODI = rating_plan) llamada\n    ,(SELECT case \n        when bb.class_service_id in (709) then 0.12\n        when bb.class_service_id in (710) then 0.25\n        when bb.class_service_id in (711) then 0.51\n        when bb.class_service_id in (728) then 1\n        when bb.class_service_id in (713) then 2\n        when bb.class_service_id in (2225,2241,5159) then 3        \n        when bb.class_service_id in (2242,5034,5160) then 5\n        when bb.class_service_id in (8035) then 8\n        when bb.class_service_id in (5043,8021,5161) then 10\n        when bb.class_service_id in (5171) then 12\n        when bb.class_service_id in (5029,5162) then 15\n        when bb.class_service_id in (5030,5042) then 20\n        when bb.class_service_id in (5031) then 25\n        when bb.class_service_id in (5114) then 30\n        when bb.class_service_id in (5115) then 40\n        when bb.class_service_id in (5116,5075,5123) then 50\n        when bb.class_service_id in (5172) then 60\n        when bb.class_service_id in (5173,5076,5124) then 80\n        when bb.class_service_id in (5158) then 100\n        when bb.class_service_id in (5078,5125) then 120\n        when bb.class_service_id in (5108) then 200\n        when bb.class_service_id in (5077,5126) then 250\n        else 0\n       end velocidad \n        FROM smart.PR_COMPONENT aa, smart.PS_CLASS_SERVICE bb\n        WHERE aa.class_service_id=bb.class_service_id\n        AND component_type_id in (8,7007)\n        AND component_status_id != 9\n        AND product_id = sesunuse\n        AND ROWNUM <= 1) vel_iba  \n    ,(SELECT case when bb.class_service_id in (709,710,711,728,713,2225,2241,2242,5034,8035,5043,8021,5171,5029,5030,5042,5031,5114,5115,5116,5172,5173,5158) then 'MB'\n        when bb.class_service_id in (5159,5160,5161,5162,5075,5123,5076,5124,5078,5125,5108,5077,5126) then 'GB'\n        when bb.class_service_id in (5073,5074,5121) then ''\n        else 'MB'\n       end vel\n        FROM smart.PR_COMPONENT aa, smart.PS_CLASS_SERVICE bb\n        WHERE aa.class_service_id=bb.class_service_id\n        AND component_type_id in (8,7007)\n        AND component_status_id != 9\n        AND product_id = sesunuse\n        AND ROWNUM <= 1) vel_cla                                            \nfrom smart.servsusc aa ,smart.pr_subscription bb, smart.servicio ss\nwhere sesususc = subscription_id\nand servcodi= sesuserv\nand subscriber_id in (\n--\n    select  subscriber_id from (\n        select subscriber_id,subscription_name,min(trunc(sesufein)) fec_min_produ \n        from smart.servsusc aa ,smart.pr_subscription bb\n        where subscriber_id in (select subscriber_id from smart.servsusc aa, smart.pr_subscription bb\n                                    where 1=1\n                                    and sesususc = subscription_id\n                                    and trunc(sysdate-1) = trunc(sesufein)\n                                    and sesuesco not in (96,110)\n                                    and sesususc = aa.sesususc\n                                )\n        and sesususc = subscription_id\n        and sesuesco not in (96,110)  \n--        and subscriber_id in (1000998900)\n        having min(trunc(sesufein)) = trunc(sysdate-1)\n        group by subscriber_id ,subscription_name          \n    )\n--\n)\nand sesuesco not in (96,110)  \n) xx\ngroup by id_cliente, cliente,fecha_instalacion ,fecha_pago,fina,e_mail ,sesususc";






















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































        
/* 1252 */         this.conO2.ip = "172.18.125.180";
/* 1253 */         this.conO2.port = "3871";
/* 1254 */         this.conO2.bd = "CDNHN";
/* 1255 */         this.conO2.usr = "CDNHN";
/* 1256 */         this.conO2.pass = "C1@r0cdn#";
        
/* 1258 */         paisAb = "HN";
      }
/* 1260 */       else if (this.pais.equals("505")) {
        
/* 1262 */         query = "SELECT id_cliente, CLIENTE\n    , CASE WHEN COUNT(DISTINCT SERVICIO) = 1 THEN 'Casa Claro Simple'\n           WHEN COUNT(DISTINCT SERVICIO) = 2 THEN 'Casa Claro Doble'\n           WHEN COUNT(DISTINCT SERVICIO) >= 3 THEN 'Casa Claro Triple'\n      END SERVICIO\n    ,NVL(SUM(DISTINCT(SELECT COUNT(DISTINCT sesuserv) FROM smart.SERVSUSC A WHERE A.sesuserv = 6042 AND A.sesususc=xx.sesususc AND A.sesuesco NOT IN (96,110))),0) television\n    ,NVL(SUM(DISTINCT(SELECT COUNT(DISTINCT sesuserv) FROM smart.SERVSUSC A WHERE A.sesuserv = 24 AND A.sesususc=xx.sesususc AND A.sesuesco NOT IN (96,110))),0) internet\n     ,NVL(SUM(DISTINCT(SELECT COUNT(DISTINCT sesuserv) FROM smart.SERVSUSC A WHERE A.sesuserv IN(1, 185) AND A.sesususc=xx.sesususc AND A.sesuesco NOT IN (96,110))),0) llamadas\n    ,MIN(fecha_instalacion) fecha_instalacion ,MIN(fecha_registro) fecha_registro,fecha_pago\n--    ,round(MONTHS_BETWEEN(max(fecha_fin_vigencia),(MIN(fecha_registro))))||' meses' vigencia_contrato\n    ,avg((xx.permanence)/30)||' meses' vigencia_contrato\n    ,fina,e_mail,'N/A' renta, SUM(hbo) hbo,SUM(fox) fox,SUM(claro_video) claro_video,SUM(basico) basico,SUM(avanzado) avanzado\n    ,SUM(llama_ili) llama_ili, sum(vel_iba) vel_iba, sesususc contrato\nFROM (\nSELECT subscriber_id id_cliente,sesususc\n    ,(SELECT suscnomb FROM smart.SUSCRIPC WHERE susccodi=sesususc) CLIENTE\n    ,(select max(permanence) from smart.pr_product where subscription_id = sesususc) permanence\n    , servdesc SERVICIO, TRUNC(sesufein) fecha_instalacion\n    ,(SELECT DISTINCT TRUNC(request_date) FROM smart.OR_EXTERN_SYSTEMS_ID aa, smart.MO_PACKAGES bb\n        WHERE aa.package_id=bb.package_id AND aa.package_type_id = 587 AND aa.product_id = sesunuse) fecha_registro\n    ,(SELECT TO_CHAR(pefafepa,'dd') FROM smart.PERIFACT WHERE pefacodi = (SELECT TO_CHAR(SYSDATE,'yymm')+1||LPAD(sesucicl,2,'0') FROM dual)) fecha_pago\n    ,(SELECT TRUNC(expiration_of_plan) FROM smart.PR_PRODUCT WHERE product_id = sesunuse) fecha_fin_vigencia\n    ,NVL((SELECT pldidesc\n            FROM smart.DIFERIDO\n                ,smart.PLANDIFE\n                ,smart.CONCEPTO\n                ,smart.SUSCRIPC\n            WHERE pldicodi=difepldi\n            AND conccodi=difeconc\n            AND susccodi=difesusc\n            AND difecupa < difenucu\n            AND difesusc = sesususc\n            AND ROWNUM <=1),'N/A') fina\n     ,(SELECT e_mail FROM smart.GE_SUBSCRIBER WHERE subscriber_id = bb.subscriber_id)e_mail\n     ,'N/A' renta\n     ,(SELECT COUNT(*) FROM smart.PR_COMPONENT aa, smart.PS_CLASS_SERVICE bb\n        WHERE aa.class_service_id=bb.class_service_id\n        AND component_type_id = 98\n        AND component_status_id != 9\n        AND LOWER(description) LIKE '%hbo%'\n        AND product_id = sesunuse\n        AND ROWNUM <= 1) HBO  \n    ,(SELECT COUNT(*) FROM smart.PR_COMPONENT aa, smart.PS_CLASS_SERVICE bb\n        WHERE aa.class_service_id=bb.class_service_id\n        AND component_type_id = 98\n        AND component_status_id != 9\n        AND LOWER(description) LIKE '%fox%'\n        AND product_id = sesunuse\n        AND ROWNUM <= 1) fox    \n    ,(SELECT COUNT(*) FROM smart.PR_COMPONENT aa, smart.PS_CLASS_SERVICE bb\n        WHERE aa.class_service_id=bb.class_service_id\n        AND component_type_id = 98\n        AND component_status_id != 9\n        AND aa.class_service_id = 3600\n        AND product_id = sesunuse\n        AND ROWNUM <= 1) claro_video  \n    ,(SELECT COUNT(*) FROM smart.PR_COMPONENT aa\n        WHERE component_status_id != 9\n        AND product_id = sesunuse\n        AND class_service_id IN (100,3645,3646,3725,3154,3162,3184,3507,3512,3513,3523,3597,3601)\n        AND ROWNUM <= 1) basico  \n    ,(SELECT COUNT(*) FROM smart.PR_COMPONENT aa\n        WHERE component_status_id != 9\n        AND product_id = sesunuse\n        AND class_service_id IN (2203,3510,3602,3603)\n        AND ROWNUM <= 1) avanzado \n    ,(SELECT COUNT(*)\n                FROM SMART.LE_OPCICOND OC,\n                     SMART.LE_OPCIPAQU OP,\n                     SMART.LE_PAUNINSE PU,\n                     SMART.LE_CONDUNIN CO\n               WHERE     OP.OPPAPAUI = PU.PUISPAUI\n                     AND OP.OPPAOPCO = OC.OPCOCONS\n                     AND PU.PUISSESU = sesunuse\n                     AND OC.OPCOCOUI = CO.COUICONS\n                     AND UPPER(couidesc) LIKE '%ILIMITADO%'\n                     AND OC.OPCOVAME != 0\n                     AND PU.PUISFEFI > SYSDATE\n                     AND ROWNUM <=1) llama_ili\n    ,(SELECT case \n        when bb.class_service_id in (3697,9008) then 0\n        when bb.class_service_id in (709) then 0.12\n        when bb.class_service_id in (710,2249) then 0.25\n        when bb.class_service_id in (711,3559,3560,3563) then 0.51\n        when bb.class_service_id in (2238) then 0.76\n        when bb.class_service_id in (712,728,2244,2251,3552,3564,3571,3574,3642,3643) then 1\n        when bb.class_service_id in (2298,3504) then 1.5\n        when bb.class_service_id in (713,3537,3553,3565,3572,3575,3582,3586) then 2\n        when bb.class_service_id in (3371,3538,3566,3576,3583,3587,3671) then 3\n        when bb.class_service_id in (3187,3368,3539,3567,3577,3584,3588,3672) then 4\n        when bb.class_service_id in (2242,3568,3578,3728) then 5\n        when bb.class_service_id in (3404,3554,3585,3589) then 6\n        when bb.class_service_id in (3794) then 8\n        when bb.class_service_id in (3408) then 9\n        when bb.class_service_id in (3515,3570,3580,3650,3673,3687,3795) then 10\n        when bb.class_service_id in (3657) then 12\n        when bb.class_service_id in (3659,3698) then 15\n        when bb.class_service_id in (3660,3710,3796) then 20\n        when bb.class_service_id in (3605,3662) then 25\n        when bb.class_service_id in (3699,3726,3797) then 30\n        when bb.class_service_id in (3700) then 35\n        when bb.class_service_id in (3701,3727,3798) then 40\n        when bb.class_service_id in (3702) then 45\n        when bb.class_service_id in (3674,3736,3799) then 50\n        when bb.class_service_id in (3675) then 55\n        when bb.class_service_id in (3676) then 70\n        when bb.class_service_id in (3708) then 100\n        when bb.class_service_id in (3678) then 120\n        when bb.class_service_id in (8507) then 130\n        else 0\n       end velocidad \n        FROM smart.PR_COMPONENT aa, smart.PS_CLASS_SERVICE bb\n        WHERE aa.class_service_id=bb.class_service_id\n        AND component_type_id = 8\n        AND component_status_id != 9\n        AND product_id = sesunuse\n        AND ROWNUM <= 1) vel_iba                                                          \nFROM smart.SERVSUSC aa ,smart.PR_SUBSCRIPTION bb, smart.SERVICIO ss\nWHERE sesususc = subscription_id\nAND servcodi= sesuserv\nAND subscriber_id IN (\n--\n    SELECT  subscriber_id FROM (\n        SELECT subscriber_id,subscription_name,MIN(TRUNC(sesufein)) fec_min_produ \n        FROM smart.SERVSUSC aa ,smart.PR_SUBSCRIPTION bb\n        WHERE subscriber_id IN (SELECT subscriber_id FROM smart.SERVSUSC aa, smart.PR_SUBSCRIPTION bb\n                                    WHERE 1=1\n                                    AND sesususc = subscription_id\n                                    AND TRUNC(SYSDATE-1) = TRUNC(sesufein)\n                                    AND sesufein IS NOT NULL\n                                    AND sesuesco NOT IN (96,110)\n                                    AND sesucate IN (19,20,12,5,8,1,7,21) --categoria cliente\n                                    AND sesususc = aa.sesususc\n                                )\n        AND sesususc = subscription_id\n        AND sesuesco NOT IN (96,110)  \n--        and subscriber_id in (2877958)--\n        HAVING MIN(TRUNC(sesufein)) = TRUNC(SYSDATE-1)\n        GROUP BY subscriber_id ,subscription_name          \n    )\n--\n)\nAND sesuesco NOT IN (96,110)) xx\nWHERE fecha_registro IS NOT NULL  \nGROUP BY id_cliente, CLIENTE ,fecha_pago,fina,e_mail,sesususc";


































































































































































































































        
/* 1490 */         this.conO2.ip = "172.18.125.180";
/* 1491 */         this.conO2.port = "3871";
/* 1492 */         this.conO2.bd = "CDNNI";
/* 1493 */         this.conO2.usr = "CDNNI";
/* 1494 */         this.conO2.pass = "C1@r0cdn#";
        
/* 1496 */         paisAb = "NI";
      }
/* 1498 */       else if (this.pais.equals("507")) {

        
/* 1501 */         query = "select id_cliente, cliente\n    , case when count(distinct servicio) = 1 then 'Claro TV'\n           when count(distinct servicio) = 2 then 'Casa Claro Doble'\n           when count(distinct servicio) >= 3 then 'Casa Claro Triple'\n      end servicio\n    ,nvl(sum(distinct(select count(distinct sesuserv) from smart.servsusc where sesuserv = 6042 and sesususc=xx.sesususc and sesuesco not in (96,110))),0) television\n    ,fecha_instalacion ,min(fecha_registro) fecha_registro,fecha_pago\n--    ,round(MONTHS_BETWEEN(fecha_fin_vigencia,(min(fecha_registro))))||' meses' vigencia_contrato\n    ,'12 meses' vigencia_contrato\n    ,fina,e_mail,round((((sum(renta))*1.12))+((sum(zz.mnt))*1.07),2) renta, sum(hbo) hbo,sum(fox) fox,sum(claro_video) claro_video,sum(basico) basico,sum(avanzado) avanzado,null vel_iba\n    ,sesususc contrato, (select phone from smart.ge_subscriber where subscriber_id=id_cliente) telefono\nfrom (\nselect subscriber_id id_cliente,sesususc,sesuplfa,sesunuse    \n    ,(select suscnomb from smart.suscripc where susccodi=sesususc) Cliente\n    ,(select distinct trunc(request_date) from smart.OR_EXTERN_SYSTEMS_ID aa, smart.mo_packages bb\n        where aa.package_id=bb.package_id and aa.package_type_id = 587 and aa.product_id = sesunuse) fecha_registro\n    , (select to_char(pefafepa,'dd') from smart.perifact where pefacodi = (select to_char(sysdate,'yymm')+1||LPAD(sesucicl,2,'0') from dual))||' de cada mes' fecha_pago\n    , servdesc servicio, trunc(sesufein) fecha_instalacion\n    ,(select trunc(expiration_of_plan) from smart.pr_product where product_id = sesunuse) fecha_fin_vigencia\n    ,'N/A' fina\n     ,(SELECT dccodeco fROM SMART.SUSCRIPC sp    ,smart.dicocobr dc where dccocodi=suscdcco and susccodi = sesususc)e_mail\n     ,nvl((select sum(retavaba)\n                From smart.pr_component aa,\n                            (SELECT plsucodi , d.servcodi,\n                                   a.tacoconc \"Id Concepto\"--, g.concdesc \"Concepto\",\n                                   ,j.class_service_id ,\n                                   k.retavaba,\n                                   c.geograp_location_id,\n                                   a.tacocate,\n                                   a.tacosuca \n                              FROM smart.tariconc a,\n                                   smart.ge_geogra_location b,\n                                   smart.ge_geogra_location c,\n                                   smart.servicio d,\n                                   smart.categori e,\n                                   smart.subcateg f,\n                                   smart.concepto g,\n                                   smart.gst_tipomone h,\n                                   smart.ps_component_type i,\n                                   smart.ps_class_service j,\n                                   smart.regltari k,\n                                   smart.plansusc l\n                            WHERE a.tacodepa = b.geograp_location_id\n                               AND a.tacoloca = c.geograp_location_id\n                               AND a.tacoserv = d.servcodi\n                               AND a.tacocate = e.catecodi\n                               AND a.tacocate = f.sucacate\n                               AND a.tacosuca = f.sucacodi\n                               AND a.tacoconc = g.conccodi\n                               AND a.tacotimo = h.timocodi\n                               AND a.tacotcom = i.component_type_id\n                               AND a.tacoclse = j.class_service_id\n                               AND a.tacocodi = k.retataco\n                               AND k.retaplsu = l.plsucodi\n                               AND k.retavige LIKE 'S'\n                               AND a.tacosesu = -1\n                               and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=plsucodi and retavige like 'S')\n                            and a.tacoconc not in (65) ) pp --\n                where 3=3\n                and pp.class_service_id= aa.class_service_id \n                and aa.product_id=aa.sesunuse\n                and pp.servcodi = sesuserv\n                and component_status_id !=9\n                and pp.servcodi in (24,6042)\n                and pp.geograp_location_id = nvl((select tacoloca\n                                                FROM smart.tariconc a,\n                                                       smart.ps_component_type i,\n                                                       smart.regltari k\n                                                WHERE  a.tacotcom = i.component_type_id\n                                                   AND a.tacocodi = k.retataco\n                                                   AND k.retavige LIKE 'S'\n                                                   AND a.tacosesu = -1\n                                                   and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=k.retaplsu and retavige like 'S')\n                                                   and retaplsu = pp.plsucodi\n                                                   and tacoclse = aa.class_service_id\n                                                   and tacoloca = (select sesuloca from smart.servsusc where sesunuse = aa.sesunuse)\n                                                   and rownum <= 1),-1)\n                and pp.tacocate = nvl((select tacocate\n                                                FROM smart.tariconc a,\n                                                       smart.ps_component_type i,\n                                                       smart.regltari k\n                                                WHERE  a.tacotcom = i.component_type_id\n                                                   AND a.tacocodi = k.retataco\n                                                   AND k.retavige LIKE 'S'\n                                                   AND a.tacosesu = -1\n                                                   and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=k.retaplsu and retavige like 'S')\n                                                   and retaplsu = pp.plsucodi\n                                                   and tacoclse = aa.class_service_id\n                                                   and tacocate = (select sesucate from smart.servsusc where sesunuse= aa.sesunuse)  --------------\n                                                   and rownum <= 1),-1)\n                and pp.tacosuca = nvl((select tacosuca\n                                                FROM smart.tariconc a,\n                                                       smart.ps_component_type i,\n                                                       smart.regltari k\n                                                WHERE  a.tacotcom = i.component_type_id\n                                                   AND a.tacocodi = k.retataco\n                                                   AND k.retavige LIKE 'S'\n                                                   AND a.tacosesu = -1\n                                                   and k.retafein=(select max(retafein) from smart.regltari where retataco=a.tacocodi and retaplsu=k.retaplsu and retavige like 'S')\n                                                   and retaplsu = pp.plsucodi\n                                                   and tacoclse = aa.class_service_id\n                                                   and tacosuca = (select sesusuca from smart.servsusc where sesunuse= aa.sesunuse)  --------------\n                                                   and rownum <= 1),-1)                \n                and pp.plsucodi=aa.sesuplfa--\n                ),0) renta\n    ,(select count(*) from smart.pr_component aa, smart.ps_class_service bb\n        where aa.class_service_id=bb.class_service_id\n        and component_type_id = 98\n        and component_status_id != 9\n        and lower(description) like '%hbo%'\n        and product_id = sesunuse\n        and rownum <= 1) HBO  \n    ,(select count(*) from smart.pr_component aa, smart.ps_class_service bb\n        where aa.class_service_id=bb.class_service_id\n        and component_type_id = 98\n        and component_status_id != 9\n        and lower(description) like '%movie%city%'\n        and product_id = sesunuse\n        and rownum <= 1) fox\n    ,'0' claro_video  \n    ,(select count(*) from smart.pr_component aa\n        where component_status_id != 9\n        and product_id = sesunuse\n        and class_service_id in (100,3423,3154,9077)\n        and rownum <= 1) basico  \n    ,(select count(*) from smart.pr_component aa\n        where component_status_id != 9\n        and product_id = sesunuse\n        and class_service_id in (3155,3422,3424)\n        and rownum <= 1) avanzado                      \nfrom smart.servsusc aa ,smart.pr_subscription bb, smart.servicio ss\nwhere sesususc = subscription_id\nand servcodi= sesuserv\nand subscriber_id in (\n--\n    select  subscriber_id from (\n        select subscriber_id,subscription_name,min(trunc(sesufein)) fec_min_produ \n        from smart.servsusc aa ,smart.pr_subscription bb\n        where subscriber_id in (select subscriber_id from smart.servsusc aa, smart.pr_subscription bb\n                                    where 1=1\n                                    and sesususc = subscription_id\n                                    and trunc(sysdate-1) = trunc(sesufein)\n                                    and sesuesco not in (96,110)\n                                    and sesususc = aa.sesususc\n                                )\n        and sesususc = subscription_id\n        and sesuesco not in (96,110)  \n--        and subscriber_id in (414412)\n        having min(trunc(sesufein)) = trunc(sysdate-1)\n        group by subscriber_id ,subscription_name          \n    )\n--\n)\nand sesuesco not in (96,110)  \n) xx\n,\n(select suscclie,class_service_id, nvl((count(*)-1)*gg.retavaba,0) mnt\nfrom smart.suscripc,smart.servsusc,smart.pr_component\n    ,(select tacoclse,retavaba\n        from smart.regltari, smart.tariconc\n        where tacocodi = retataco\n        and tacoconc = 59\n        and trunc(retafefi)>trunc(sysdate)) gg\nwhere susccodi = sesususc\nand sesunuse=product_id\nand component_type_id = 107\nand component_status_id != 9\nand class_service_id=gg.tacoclse\ngroup by suscclie,class_service_id,gg.retavaba) zz\nwhere xx.id_cliente=zz.suscclie\ngroup by id_cliente, cliente,fecha_instalacion ,fecha_pago,fecha_fin_vigencia,fina,e_mail,sesususc";





































































































































































































































































































































































































































































































































































































































































        
/* 2148 */         this.conO2.ip = "172.18.125.180";
/* 2149 */         this.conO2.port = "3871";
/* 2150 */         this.conO2.bd = "CDNPA";
/* 2151 */         this.conO2.usr = "CDNPA";
/* 2152 */         this.conO2.pass = "C1@r0cdn#";
        
/* 2154 */         paisAb = "PA";
      } 
      
/* 2157 */       ResultSet resultado = this.con.Consultar(query);
      
/* 2159 */       this.conO.Conectar();





      
/* 2166 */       while (resultado.next()) {
/* 2167 */         id_cliente = resultado.getString("ID_CLIENTE");
/* 2168 */         cliente = resultado.getString("CLIENTE");
/* 2169 */         servicio_adquirido = resultado.getString("SERVICIO");
/* 2170 */         cuota_mensual = resultado.getString("RENTA");
/* 2171 */         fecha_pago = resultado.getString("FECHA_PAGO");
/* 2172 */         fecha_contratacion = resultado.getString("FECHA_REGISTRO");
/* 2173 */         vigencia_contrato = resultado.getString("VIGENCIA_CONTRATO");
/* 2174 */         fecha_instalacion = resultado.getString("FECHA_INSTALACION");
/* 2175 */         equipo = resultado.getString("FINA");
/* 2176 */         email = resultado.getString("E_MAIL");
/* 2177 */         television = resultado.getString("TELEVISION");
        
/* 2179 */         claro_video = resultado.getString("CLARO_VIDEO");
/* 2180 */         hbo = resultado.getString("HBO");
/* 2181 */         fox = resultado.getString("FOX");
/* 2182 */         contrato = resultado.getString("CONTRATO");
/* 2183 */         velocidadInternet = resultado.getString("VEL_IBA");

        
/* 2186 */         basico = resultado.getString("BASICO");
/* 2187 */         avanzado = resultado.getString("AVANZADO");
/* 2188 */         String tipo_tv = "";
        
/* 2190 */         if (this.pais.equals("507")) {
/* 2191 */           numeroReferencia = resultado.getString("TELEFONO");
        } else {
/* 2193 */           linea_fija = resultado.getString("LLAMADAS");
/* 2194 */           internet = resultado.getString("INTERNET");
/* 2195 */           numeroReferencia = "";
        } 






        
/* 2204 */         if (basico == null || basico.isEmpty()) {
/* 2205 */           basico = "0";
        }
        
/* 2208 */         if (avanzado == null || avanzado.isEmpty()) {
/* 2209 */           avanzado = "0";
        }
        
/* 2212 */         if (velocidadInternet == null || velocidadInternet.isEmpty()) {
/* 2213 */           velocidadInternet = "0";
        }

        
/* 2217 */         if (basico.equals("1") || avanzado.equals("1")) {
/* 2218 */           tipo_tv = "1";
        } else {
/* 2220 */           tipo_tv = "0";
        } 


        
/* 2225 */         if (id_cliente == null || id_cliente.isEmpty()) {
/* 2226 */           id_cliente = "0";
        }
/* 2228 */         if (numeroReferencia == null || numeroReferencia.isEmpty()) {
/* 2229 */           numeroReferencia = "0";
        }
        
/* 2232 */         if (contrato == null || contrato.isEmpty()) {
/* 2233 */           contrato = "0";
        }
        
/* 2236 */         if (cliente == null || cliente.isEmpty()) {
/* 2237 */           cliente = "0";
        }
/* 2239 */         if (servicio_adquirido == null || servicio_adquirido.isEmpty()) {
/* 2240 */           servicio_adquirido = "0";
        }
/* 2242 */         if (cuota_mensual == null || cuota_mensual.isEmpty()) {
/* 2243 */           cuota_mensual = "0";
        }
/* 2245 */         if (fecha_pago == null || fecha_pago.isEmpty()) {
/* 2246 */           fecha_pago = "0";
        }
/* 2248 */         if (fecha_contratacion == null || fecha_contratacion.isEmpty()) {
/* 2249 */           fecha_contratacion = "0";
        }
/* 2251 */         if (vigencia_contrato == null || vigencia_contrato.isEmpty()) {
/* 2252 */           vigencia_contrato = "0";
        }
/* 2254 */         if (fecha_instalacion == null || fecha_instalacion.isEmpty()) {
/* 2255 */           fecha_instalacion = "0";
        }
/* 2257 */         if (equipo == null || equipo.isEmpty()) {
/* 2258 */           equipo = "0";
        }
/* 2260 */         if (email == null || email.isEmpty()) {
/* 2261 */           email = "0";
        }
/* 2263 */         if (television == null || television.isEmpty()) {
/* 2264 */           television = "0";
        }
/* 2266 */         if (linea_fija == null || linea_fija.isEmpty()) {
/* 2267 */           linea_fija = "0";
        }
/* 2269 */         if (internet == null || internet.isEmpty()) {
/* 2270 */           internet = "0";
        }
/* 2272 */         if (claro_video == null || claro_video.isEmpty()) {
/* 2273 */           claro_video = "0";
        }
/* 2275 */         if (fox == null || fox.isEmpty()) {
/* 2276 */           fox = "0";
        }
/* 2278 */         if (hbo == null || hbo.isEmpty()) {
/* 2279 */           hbo = "0";
        }





        
/* 2287 */         Conector validador = new Conector("172.18.125.181", "CDNRG", "C1@r0cdn#", "3871", "CDNRG");
        
/* 2289 */         validador.conectar();
        
/* 2291 */         String[][] resultado1 = validador.consultas("SELECT FECHA_ENVIO FROM CDN_MAIL WHERE ID = '" + id_cliente + "' AND PAIS_ID = '" + this.pais + "'");
        
/* 2293 */         if (resultado1.length == 0) {
/* 2294 */           update = "INSERT INTO CDNRG.CDN_MAIL (ID, NOMBRE_CLIENTE, SERVICIO, CUOTA, FECHA_PAGO, FECHA_CONTRATACION, VIGENCIA_CONTRATO, FECHA_INSTALACION, EQUIPO, CORREO_CLIENTE, PAIS_ID, FECHA_ENVIO, INTERNET, LLAMADA, TELEVISION, CLARO_VIDEO, FOX, HBO, CONTRATO, NUMERO_REFERENCIA, TIPO_PLAN_TV, VELOCIDAD)\nVALUES (" + id_cliente + ",'" + cliente + "','" + servicio_adquirido + "','" + cuota_mensual + "','" + fecha_pago + "',TO_DATE('" + fecha_contratacion + "','YYYY-MM-DD HH24:MI:SS.SSSSS'),'" + vigencia_contrato + "',TO_CHAR(TO_DATE('" + fecha_instalacion + "','YYYY-MM-DD HH24:MI:SS.SSSSS'),'DD/MM/YYYY'),'" + equipo + "','" + email + "'," + this.pais + ",SYSDATE," + internet + "," + linea_fija + "," + television + "," + claro_video + "," + fox + "," + hbo + "," + contrato + ",'" + numeroReferencia + "'," + tipo_tv + ",'" + velocidadInternet + "')";
          
/* 2296 */           this.conO.Consultar(update);
/* 2297 */           validador.desconectar();
/* 2298 */           if (email.equals("0")) {
            continue;
          }
/* 2301 */           Select param = new Select();
/* 2302 */           param.selectFijo(id_cliente, this.pais);
        } 
      } 











      
/* 2317 */       this.con.CerrarConsulta();
/* 2318 */       this.con.CerrarConexion();
/* 2319 */       this.conO.CerrarConsulta();
/* 2320 */       this.conO2.CerrarConsulta();
/* 2321 */       return this.rs;
/* 2322 */     } catch (SQLException e) {
/* 2323 */       this.con.CerrarConsulta();
/* 2324 */       this.conO.CerrarConsulta();
      
/* 2326 */       this.con.CerrarConexion();

      
/* 2329 */       return null;
    } 
  }

  
  public String[][] DesgloseFijo(String contrato) throws SQLException, Exception {
/* 2335 */     String query1 = null;
    
/* 2337 */     String[][] datos = (String[][])null;
    
    try {
/* 2340 */       if (this.pais.equals("504")) {
/* 2341 */         query1 = "SELECT CONTRATO,\n       TO_CHAR(MAX(FECHA_LIMITE_PAGO), 'DD/MM/YYYY') FECHA_LIMITE_PAGO,\n       (CASE WHEN TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99'))\n             ELSE TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99')) \n        END) TOTAL_FACTURA,\n       (CASE WHEN TRIM(TO_CHAR(CUOTA_MENSUAL_FINANCIAMIENTO, '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(CUOTA_MENSUAL_FINANCIAMIENTO, '9999999.99'))\n             ELSE TRIM(TO_CHAR(CUOTA_MENSUAL_FINANCIAMIENTO, '9999999.99')) \n        END) CUOTA_MENSUAL_FINANCIAMIENTO,\n       (CASE WHEN TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99'))  LIKE '.%' THEN '0'||TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99'))\n             ELSE TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99')) \n        END) SALDO_PENDIENTE\nFROM (SELECT DISTINCT SESUSUSC CONTRATO,\n                      (SELECT TRUNC(MAX(CUCOFEVE)) \n                       FROM SMART.CUENCOBR \n                       WHERE CUCONUSE = SESUNUSE \n                       AND CUCOSACU > 0) FECHA_LIMITE_PAGO,\n                      (SELECT SUM(CUCOSACU) \n                       FROM SMART.CUENCOBR \n                       WHERE CUCOSUSC = SESUSUSC) TOTAL_FACTURA,\n                      NVL((SELECT SUM(CARGVALO) CARG \n                           FROM SMART.CARGOS\n                           WHERE CARGCUCO = (SELECT MAX(CUCOCODI) \n                                             FROM SMART.CUENCOBR AA,\n                                                  SMART.CARGOS\n                                             WHERE CUCOSUSC = SESUSUSC\n                                             AND CUCOSACU > 0\n                                             AND CARGCUCO = CUCOCODI\n                                             AND CARGCONC IN (SELECT CONCCODI \n                                                              FROM SMART.CONCEPTO \n                                                              WHERE UPPER(CONCDESC) LIKE '%FINA%'))\n                           AND CARGCONC IN (SELECT CONCCODI \n                                            FROM SMART.CONCEPTO \n                                            WHERE UPPER(CONCDESC) LIKE '%FINA%')), 0) CUOTA_MENSUAL_FINANCIAMIENTO,\n                      NVL((SELECT SUM(CUCOSACU) \n                           FROM SMART.CUENCOBR \n                           WHERE CUCONUSE = SESUNUSE \n                           AND CUCOFEVE < SYSDATE),0) SALDO_PENDIENTE\n      FROM SMART.SERVSUSC, \n           SMART.SUSCRIPC, \n           SMART.SERVICIO\n      WHERE SESUSUSC = SUSCCODI\n      AND SESUSERV = SERVCODI\n      AND SESUSAPE > 0) \nWHERE CONTRATO = " + contrato + "\n" + "GROUP BY CONTRATO, TOTAL_FACTURA, CUOTA_MENSUAL_FINANCIAMIENTO";











































        
/* 2386 */         ResultSet consulta = this.con.ConsultarDesglose(query1);
/* 2387 */         if (consulta != null) {
/* 2388 */           datos = new String[1][5];
/* 2389 */           while (consulta.next()) {
/* 2390 */             datos[0][0] = consulta.getString("CONTRATO");
/* 2391 */             datos[0][1] = consulta.getString("FECHA_LIMITE_PAGO");
/* 2392 */             if (consulta.getString("TOTAL_FACTURA").substring(0, 1).equals(".")) {
/* 2393 */               datos[0][2] = "0" + consulta.getString("TOTAL_FACTURA");
            } else {
/* 2395 */               datos[0][2] = consulta.getString("TOTAL_FACTURA");
            } 
/* 2397 */             if (consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO").substring(0, 1).equals(".")) {
/* 2398 */               datos[0][3] = "0" + consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO");
            } else {
/* 2400 */               datos[0][3] = consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO");
            } 
/* 2402 */             if (consulta.getString("SALDO_PENDIENTE").substring(0, 1).equals(".")) {
/* 2403 */               datos[0][4] = "0" + consulta.getString("SALDO_PENDIENTE"); continue;
            } 
/* 2405 */             datos[0][4] = consulta.getString("SALDO_PENDIENTE");
          }
        
        } 
/* 2409 */       } else if (this.pais.equals("505")) {
/* 2410 */         String queryContrato = "SELECT SESUSUSC CONTRATO\nFROM SMART.ELMESESU,\n     SMART.SERVSUSC\nWHERE EMSSFERE >= SYSDATE\nAND EMSSSESU = SESUNUSE\nAND EMSSCOEM = '" + contrato + "'";





        
/* 2417 */         ResultSet consultaContrato = this.con.ConsultarDesglose(queryContrato);
        
/* 2419 */         if (consultaContrato != null) {
/* 2420 */           while (consultaContrato.next()) {
/* 2421 */             contrato = consultaContrato.getString("CONTRATO");
          }
        }
        
/* 2425 */         query1 = "SELECT CONTRATO,\n       FECHA_LIMITE_PAGO,\n       (CASE WHEN TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99'))\n             ELSE TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99')) \n        END) TOTAL_FACTURA,\n       (CASE WHEN TRIM(TO_CHAR(CUOTA_MENSUAL_FINANCIAMIENTO, '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(CUOTA_MENSUAL_FINANCIAMIENTO, '9999999.99'))\n             ELSE TRIM(TO_CHAR(CUOTA_MENSUAL_FINANCIAMIENTO, '9999999.99')) \n        END) CUOTA_MENSUAL_FINANCIAMIENTO,\n       (CASE WHEN TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99'))\n             ELSE TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99')) \n        END) SALDO_PENDIENTE\nFROM (SELECT DISTINCT SESUSUSC CONTRATO,\n                      TO_CHAR((SELECT TRUNC(MAX(CUCOFEVE)) \n                               FROM SMART.CUENCOBR \n                               WHERE CUCONUSE = SESUNUSE \n                               AND CUCOSACU > 0), 'DD/MM/YYYY') FECHA_LIMITE_PAGO,\n                      (SELECT SUM(CUCOSACU) \n                              FROM SMART.CUENCOBR \n                              WHERE CUCOSUSC = SESUSUSC)  TOTAL_FACTURA,\n                       NVL((SELECT SUM(CARGVALO) CARG \n                            FROM SMART.CARGOS\n                            WHERE CARGCUCO = (SELECT MAX(CUCOCODI) \n                                              FROM SMART.CUENCOBR AA,\n                                                   SMART.CARGOS\n                                              WHERE CUCOSUSC = SESUSUSC\n                                              AND CUCOSACU > 0\n                                              AND CARGCUCO = CUCOCODI\n                                              AND CARGCONC IN (SELECT CONCCODI \n                                                               FROM SMART.CONCEPTO \n                                                               WHERE UPPER(CONCDESC) LIKE '%FINA%' OR UPPER(CONCDESC) LIKE '%DIFE%'))\n                            AND CARGCONC IN (SELECT CONCCODI \n                                             FROM SMART.CONCEPTO \n                                             WHERE UPPER(CONCDESC) LIKE '%FINA%' OR UPPER(CONCDESC) LIKE '%DIFE%')), 0) CUOTA_MENSUAL_FINANCIAMIENTO,\n                      NVL((SELECT SUM(CUCOSACU) \n                           FROM SMART.CUENCOBR \n                           WHERE CUCONUSE = SESUNUSE \n                           AND CUCOFEVE < SYSDATE),0) SALDO_PENDIENTE\n      FROM SMART.SERVSUSC, \n           SMART.SUSCRIPC, \n           SMART.SERVICIO\n      WHERE SESUSUSC = SUSCCODI\n      AND SESUSERV = SERVCODI\n      AND SESUSAPE > 0) \nWHERE CONTRATO = " + contrato + "\n" + "GROUP BY CONTRATO, TOTAL_FACTURA, FECHA_LIMITE_PAGO, CUOTA_MENSUAL_FINANCIAMIENTO";











































        
/* 2470 */         ResultSet consulta = this.con.ConsultarDesglose(query1);
/* 2471 */         if (consulta != null) {
/* 2472 */           datos = new String[1][5];
/* 2473 */           while (consulta.next()) {
/* 2474 */             datos[0][0] = consulta.getString("CONTRATO");
/* 2475 */             datos[0][1] = consulta.getString("FECHA_LIMITE_PAGO");
/* 2476 */             if (consulta.getString("TOTAL_FACTURA").substring(0, 1).equals(".")) {
/* 2477 */               datos[0][2] = "0" + consulta.getString("TOTAL_FACTURA");
            } else {
/* 2479 */               datos[0][2] = consulta.getString("TOTAL_FACTURA");
            } 
/* 2481 */             if (consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO").substring(0, 1).equals(".")) {
/* 2482 */               datos[0][3] = "0" + consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO");
            } else {
/* 2484 */               datos[0][3] = consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO");
            } 
/* 2486 */             if (consulta.getString("SALDO_PENDIENTE").substring(0, 1).equals(".")) {
/* 2487 */               datos[0][4] = "0" + consulta.getString("SALDO_PENDIENTE"); continue;
            } 
/* 2489 */             datos[0][4] = consulta.getString("SALDO_PENDIENTE");
          }
        
        } 
/* 2493 */       } else if (this.pais.equals("507")) {
/* 2494 */         query1 = "SELECT CONTRATO,\n       FECHA_LIMITE_PAGO,\n       (CASE WHEN TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99'))\n             ELSE TRIM(TO_CHAR(TOTAL_FACTURA, '9999999.99')) \n        END) TOTAL_FACTURA,\n       (CASE WHEN TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99')) LIKE '.%' THEN '0'||TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99'))\n             ELSE TRIM(TO_CHAR(SUM(SALDO_PENDIENTE), '9999999.99')) \n        END) SALDO_PENDIENTE\nFROM (SELECT DISTINCT SESUSUSC CONTRATO,\n                      TO_CHAR((SELECT TRUNC(MAX(CUCOFEVE)) \n                               FROM SMART.CUENCOBR \n                               WHERE CUCONUSE = SESUNUSE \n                               AND CUCOSACU > 0), 'DD/MM/YYYY') FECHA_LIMITE_PAGO,\n                      (SELECT SUM(CUCOSACU) \n                              FROM SMART.CUENCOBR \n                              WHERE CUCOSUSC = SESUSUSC)  TOTAL_FACTURA,\n                      NVL((SELECT SUM(CUCOSACU) \n                           FROM SMART.CUENCOBR \n                           WHERE CUCONUSE = SESUNUSE \n                           AND CUCOFEVE < SYSDATE),0) SALDO_PENDIENTE\n      FROM SMART.SERVSUSC, \n           SMART.SUSCRIPC, \n           SMART.SERVICIO\n      WHERE SESUSUSC = SUSCCODI\n      AND SESUSERV = SERVCODI\n      AND SESUSAPE > 0) \nWHERE CONTRATO = " + contrato + "\n" + "GROUP BY CONTRATO, TOTAL_FACTURA, FECHA_LIMITE_PAGO";


























        
/* 2522 */         ResultSet consulta = this.con.ConsultarDesglose(query1);
/* 2523 */         if (consulta != null) {
/* 2524 */           datos = new String[1][4];
/* 2525 */           while (consulta.next()) {
/* 2526 */             datos[0][0] = consulta.getString("CONTRATO");
/* 2527 */             datos[0][1] = consulta.getString("FECHA_LIMITE_PAGO");
/* 2528 */             if (consulta.getString("TOTAL_FACTURA").substring(0, 1).equals(".")) {
/* 2529 */               datos[0][2] = "0" + consulta.getString("TOTAL_FACTURA");
            } else {
/* 2531 */               datos[0][2] = consulta.getString("TOTAL_FACTURA");
            } 
/* 2533 */             if (consulta.getString("SALDO_PENDIENTE").substring(0, 1).equals(".")) {
/* 2534 */               datos[0][3] = "0" + consulta.getString("SALDO_PENDIENTE"); continue;
            } 
/* 2536 */             datos[0][3] = consulta.getString("SALDO_PENDIENTE");
          } 
        } 
      } 


      
/* 2543 */       if (datos == null || datos[0][0] == null) {

        
/* 2546 */         String consultaExistenciaSQL = "SELECT /*+ INDEX(A IX_SESU_SUSC) */ *\nFROM SMART.SERVSUSC A\nWHERE A.SESUSUSC = " + contrato + "\n" + "AND ROWNUM  = 1";


        
        try {
/* 2551 */           ResultSet consultaExistencia = this.con.ConsultarDesglose(consultaExistenciaSQL);
/* 2552 */           if (consultaExistencia == null || !consultaExistencia.next()) {
/* 2553 */             throw new Exception("Servicio no encontrado: " + contrato);
          }
/* 2555 */         } catch (Exception ex) {
/* 2556 */           throw new Exception(ex.toString());
        } 
      } 


      
/* 2562 */       this.con.CerrarConsulta();
/* 2563 */       this.con.CerrarConexion();
/* 2564 */       return datos;
    }
/* 2566 */     catch (SQLException e) {
/* 2567 */       this.con.CerrarConsulta();
/* 2568 */       this.con.CerrarConexion();
/* 2569 */       datos = new String[1][2];
/* 2570 */       datos[0][0] = "ERROR";
/* 2571 */       datos[0][1] = e.toString();
/* 2572 */       return datos;
    } 
  }
}


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ConsultasOPEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */