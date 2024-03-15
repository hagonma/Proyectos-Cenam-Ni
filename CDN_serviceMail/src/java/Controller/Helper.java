 package Controller;
 
 import Model.Usuario;
 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.net.HttpURLConnection;
 import java.net.InetSocketAddress;
 import java.net.MalformedURLException;
 import java.net.ProtocolException;
 import java.net.Proxy;
 import java.net.URL;
 import java.net.URLConnection;
 import java.util.StringTokenizer;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 public class Helper
 {
/*  36 */   JSONObject objJson = new JSONObject();
/*  37 */   String json = "";
   
   public JSONObject request(String urld, String method, String data) throws MalformedURLException, ProtocolException, IOException, JSONException {
/*  40 */     URL url = new URL(urld);
/*  41 */     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*  42 */     conn.setDoOutput(true);
/*  43 */     conn.setRequestMethod(method);
/*  44 */     conn.setRequestProperty("Content-Type", "application/json");
/*  45 */     if (method.equals("POST")) {
/*  46 */       String input = data;
/*  47 */       OutputStream os = conn.getOutputStream();
/*  48 */       os.write(input.getBytes());
/*  49 */       os.flush();
     } 
/*  51 */     if (conn.getResponseCode() != 200) {
/*  52 */       throw new RuntimeException("Failed : HTTP error code : " + conn
/*  53 */           .getResponseCode());
     }
/*  55 */     BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
     
/*  57 */     System.out.println("Output from Server .... \n"); String output;
/*  58 */     while ((output = br.readLine()) != null) {
/*  59 */       if (!output.equals("null"))
/*  60 */         this.json += output; 
     } 
/*  62 */     conn.disconnect();
/*  63 */     return new JSONObject(this.json);
   }
 
   
   public InputStream getStreamFromUrl(String urlParam) throws MalformedURLException, IOException {
/*  68 */     URL url = new URL(urlParam);
/*  69 */     Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("TIV-TLG-PRX-03", 4713));
     
/*  71 */     InputStream inputStream = null;
     try {
/*  73 */       URLConnection uc = url.openConnection(proxy);
       
/*  75 */       uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2");
/*  76 */       uc.connect();
/*  77 */       inputStream = uc.getInputStream();
/*  78 */     } catch (IOException e) {
/*  79 */       e.printStackTrace();
     } 
/*  81 */     return inputStream;
   }
   
   public void writeFile(JSONArray arr) throws JSONException, IOException {
     try {
/*  86 */       File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "..//..//..//web//file//LIMITE_CONSUMO_SUBSC.CSV");
/*  87 */       BufferedWriter writer = new BufferedWriter(new FileWriter(file));
/*  88 */       for (int i = 0; i < arr.length(); i++) {
/*  89 */         String line = arr.getJSONObject(i).getString("TELEFONO") + ";" + arr.getJSONObject(i).getString("LIMITE_CONSUMO") + ";" + arr.getJSONObject(i).getString("SEGMENTACION");
/*  90 */         writer.write(line);
/*  91 */         writer.newLine();
       } 
       
/*  94 */       writer.close();
/*  95 */     } catch (IOException e) {
/*  96 */       e.printStackTrace();
     } 
   }
   
   public void setResponse(int code, JSONObject response) throws JSONException {
/* 101 */     switch (code) {
       case 100:
/* 103 */         this.objJson.put("Codigo", 100);
/* 104 */         this.objJson.put("Response", response);
/* 105 */         this.objJson.put("Description", "Continue");
         return;
       case 200:
/* 108 */         this.objJson.put("Codigo", 200);
/* 109 */         this.objJson.put("Response", response);
/* 110 */         this.objJson.put("Description", "OK");
         return;
       default:
/* 113 */         this.objJson.put("Codigo", 400);
/* 114 */         this.objJson.put("Response", response);
/* 115 */         this.objJson.put("Description", "Bad Request"); return;
       case 401:
         break;
/* 118 */     }  this.objJson.put("Codigo", 401);
/* 119 */     this.objJson.put("Response", response);
/* 120 */     this.objJson.put("Description", "Unauthorized");
   }
 
 
   
   public void setArrayResponse(int code, JSONArray response) throws JSONException {
/* 126 */     switch (code) {
       case 100:
/* 128 */         this.objJson.put("Codigo", 100);
/* 129 */         this.objJson.put("Response", response);
/* 130 */         this.objJson.put("Description", "Continue");
         return;
       case 200:
/* 133 */         this.objJson.put("Codigo", 200);
/* 134 */         this.objJson.put("Response", response);
/* 135 */         this.objJson.put("Description", "OK");
         return;
       default:
/* 138 */         this.objJson.put("Codigo", 400);
/* 139 */         this.objJson.put("Response", response);
/* 140 */         this.objJson.put("Description", "Bad Request"); return;
       case 401:
         break;
/* 143 */     }  this.objJson.put("Codigo", 401);
/* 144 */     this.objJson.put("Response", response);
/* 145 */     this.objJson.put("Description", "Unauthorized");
   }
 
 
   
   public String getResponse() {
/* 151 */     return this.objJson.toString();
   }
   
   public boolean validateToken(String token) throws Exception {
/* 155 */     String tokenF = "";
/* 156 */     String idUser = "";
/* 157 */     Usuario user = new Usuario();
/* 158 */     StringTokenizer st = new StringTokenizer(token, "-");
/* 159 */     while (st.hasMoreTokens()) {
/* 160 */       tokenF = st.nextToken();
/* 161 */       idUser = st.nextToken();
     } 
/* 163 */     String tokenBack = user.getToken(Integer.parseInt(idUser));
     
/* 165 */     if (tokenF.equals(tokenBack)) {
/* 166 */       return true;
     }
/* 168 */     return false;
   }
 
   
   public boolean validateURL(String url, int rol) throws Exception {
/* 173 */     Usuario user = new Usuario();
/* 174 */     user.validateURL(url, rol);
/* 175 */     JSONObject job = new JSONObject(user.hp.getResponse());
/* 176 */     JSONArray job2 = job.getJSONArray("Response");
/* 177 */     JSONObject s = job2.getJSONObject(0);
     
/* 179 */     if (s.getString("URL").equals(url)) {
/* 180 */       return true;
     }
/* 182 */     return false;
   }
 
 
   
   public void downloadFromInputStream(InputStream in, String filename) throws MalformedURLException, IOException {
/* 188 */     FileOutputStream fout = null;
     try {
/* 190 */       fout = new FileOutputStream(filename);
       
/* 192 */       byte[] data = new byte[1024];
       int count;
/* 194 */       while ((count = in.read(data, 0, 1024)) != -1) {
/* 195 */         fout.write(data, 0, count);
       }
     } finally {
/* 198 */       if (in != null) {
/* 199 */         in.close();
       }
/* 201 */       if (fout != null) {
/* 202 */         fout.close();
       }
     } 
   }
   
   public boolean isNullOrEmpty(String str) {
/* 208 */     if (str != null && !str.isEmpty())
/* 209 */       return false; 
/* 210 */     return true;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\Helper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */