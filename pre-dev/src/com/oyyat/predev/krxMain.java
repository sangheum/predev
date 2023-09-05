package com.oyyat.predev;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyyat.predev.constant.ApiTag;
import com.oyyat.predev.vo.ApiHeaderVO;
import com.oyyat.predev.vo.krxPVO;
import com.oyyat.predev.vo.krxRVO;

public class krxMain {

	public static void main(String[] args) {

		//String auth = "00816:42BCD45E02474CED97189C81A13773AD95D97C54";
		//System.out.println("========================================");
		//System.out.println(encode64(auth));
		//System.out.println("========================================");
		//test();
		int test = 0 ;

		//System.out.println("1test  " +test);
		//System.out.println("2test  " +test++);
		//System.out.println("3test  " +test++);
		//System.out.println("4test  " +test++);

		System.out.println("sss");
		System.out.println("01 " + !Pattern.matches("^[0-9a-zA-Zㄱ-ㅎ가-힣-_•]*$", "/abc/dexdd/dfsf.jsp"));
		System.out.println("02 " + !Pattern.matches("^[0-9a-zA-Zㄱ-ㅎ가-힣-_•]*$", "../../abc/dexdd/dfsf.jsp"));
		System.out.println("03 " + !Pattern.matches("^[0-9a-zA-Zㄱ-ㅎ가-힣-_•]*$", "../abc/ddd.png"));
		System.out.println("04 " + !Pattern.matches("^[0-9a-zA-Zㄱ-ㅎ가-힣-_•]*$", "/../../dfs.png"));



	}

	public static String convert2Snake(String text) {


		if(null == text || text.equals("")) {
			return text;
		}

		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";

		return text.replaceAll(regex, replacement).toUpperCase();
	}

	public static String encode64(String text){
		byte[] targetBytes = text.getBytes();
		Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode(targetBytes);
		return new String(encodedBytes);
	}

	public static String decode64(String text){
//		byte[] targetBytes = text.getBytes();
//		Decoder decoder = Base64.getDecoder();
//		byte[] decodedBytes = decoder.decode(targetBytes);
		Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes = decoder.decode(text.getBytes());


		return new String(decodedBytes);
	}


	public static String test(){
		ObjectMapper mapper = new ObjectMapper();

		krxPVO pVO = new krxPVO();

		//TODO BODY SET
		pVO.setClient_id("00816");
		pVO.setClient_secret("42BCD45E02474CED97189C81A13773AD95D97C54");
		pVO.setGrant_type("client_credentials");
		System.out.println("1 pVO.getClass(); " + pVO.getClass());

		ApiHeaderVO hVO = pVO;
		hVO.setResult("TESTRESULT");
		System.out.println(" hVO " + hVO.toString());
		System.out.println(" pVO " + pVO.toString());
		System.out.println(" Result " + hVO.getResult());
		System.out.println("2 hVO.getClass(); " + hVO.getClass());
		System.out.println("2-1 hVO.getClass(); " + hVO.getClass());

		//Class clazz = hVO.getClass();
//		krxPVO  rrVO  = (krxPVO)hVO;

		ApiHeaderVO  rrVO  = hVO.getClass().cast(hVO);
		//hVO.getClass  rrVO  = hVO.getClass().cast(hVO);


		System.out.println("3-1 rVO.getClass(); " + rrVO.toString());

		krxRVO rVO = new krxRVO();
		System.out.println("3 rVO.getClass(); " + rVO.getClass());



		ApiHeaderVO rhVO = rVO;
		System.out.println("4 rhVO.getClass(); " + rhVO.getClass());
		System.out.println("5 hVO.getClass(); " + hVO.getClass());


		//System.out.println(" client_id " + rrVO.getClient_id());
		System.out.println(" rrVO " + rrVO.toString());

		String json = "";
		try {
			json = mapper.writeValueAsString(pVO);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {

			//TODO HAEDER SET
			URL url = new URL(ApiTag.KRX_OAUTH_DEV_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(ApiTag.KRX_METHOD_POST);
			con.setRequestProperty(ApiTag.KRX_AUTHORIZATION, "MDA4MTY6NDJCQ0Q0NUUwMjQ3NENFRDk3MTg5QzgxQTEzNzczQUQ5NUQ5N0M1NA==");
			con.setRequestProperty(ApiTag.KRX_CONTENT_TYPE, ApiTag.KRX_CONTENT_APPLICATION_JSON);
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			System.out.println("request >> " +json);
				try (OutputStream os = con.getOutputStream()) {
					byte[] input = json.getBytes("utf-8");
					os.write(input,0,input.length);
				}


				try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"))){
					StringBuilder response = new StringBuilder();
				    String responseLine = null;

				    while((responseLine = br.readLine()) != null) {

				    	response.append(responseLine.trim());
				    }
				    System.out.println("response >> " + response.toString());
				    ObjectMapper om = new ObjectMapper();

				    String jsonStr =  response.toString();
				    krxRVO rvo= om.readValue(jsonStr, krxRVO.class);

				    int status = con.getResponseCode();
				    if (status == HttpURLConnection.HTTP_OK ) {
				    	System.out.println(" 성공 ");
				    }

				    Map<String, List<String>> headerMap = new HashMap<String, List<String>>();
				    headerMap = con.getHeaderFields ();

				    System.out.println("--------------------------------------");
				    System.out.println("02. " +headerMap.size());

				    Iterator<String> keys = headerMap.keySet().iterator();
			        while( keys.hasNext() ){
			            String key = keys.next();
			            System.out.println( String.format("키 : %s, 값 : %s", key, headerMap.get(key)) );
			        }

				    //map으로 답기
				    //Map<String, Object> resultMap = new HashMap<String, Object>();
				    //JSONParser parser = new JSONParser();
				    //resultMap = (Map<String, Object>) parser.parser(jsonStr);


				    System.out.println("--------------------------------------");
				    System.out.println(rvo.toString());
				    System.out.println("--------------------------------------");
				    System.out.println(rvo.getAccess_token());
				    System.out.println(rvo.getExpires_in());
				    System.out.println(rvo.getMessage());
				    System.out.println(rvo.getToken_type());
				    br.close();
				}

		} catch (Exception e) {

		}

		return json;
	}



}
