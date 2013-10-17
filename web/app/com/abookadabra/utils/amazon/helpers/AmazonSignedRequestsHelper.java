package com.abookadabra.utils.amazon.helpers;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import play.libs.WS;
import play.libs.WS.WSRequestHolder;

public class AmazonSignedRequestsHelper {
  private static final String UTF8_CHARSET = "UTF-8";
  private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
  public static final String REQUEST_URI = "/onca/xml";
  private static final String REQUEST_METHOD = "GET";
  private static final String SIGNATURE_PARAM = "Signature";
  private static final String ACCESS_KEY_ID_PARAM = "AWSAccessKeyId";
  private static final String TIMESTAMP_PARAM = "Timestamp";

  public static String endpoint = "webservices.amazon.fr"; // must be lowercase
  public String awsAccessKeyId = AmazonSecrets.getAwsAccessKeyId();
  private String awsSecretKey = AmazonSecrets.getAwsSecretKey();

  private SecretKeySpec secretKeySpec = null;
  private Mac mac = null;
  
  private class AmazonSignedRequest {
	  private Map<String, String> initialParams;
	  private Map<String, String> fullListOfParams;
	  private SortedMap<String, String> sortedFullListOfParams;
	  
	  protected AmazonSignedRequest(final Map<String, String> initialParams) {
		  this.initialParams = getACopyOfParams(initialParams);
		  initialise();
	  }
	  
	  private void initialise() {
		  fullListOfParams = getACopyOfParams(initialParams);
		  addNeededParamsForSigning(fullListOfParams);
		  sortedFullListOfParams = new TreeMap<String, String>(fullListOfParams);
	  }
	  
	  public String getCanonicalQS() {
		  return canonicalize(sortedFullListOfParams);
	  }

	  public String getSignature() {
		  return hmac(getToSign());
	  }
	  
	  public String getEncodedSignature() {
		  return percentEncodeRfc3986(hmac(getToSign()));
	  }
	  
	  public SortedMap<String, String> getSortedFullListOfParamsExceptSignature() {
		  return sortedFullListOfParams;
	  }
	  
	  public String getStartingURI() {
		  return "http://" + endpoint + REQUEST_URI;
	  }

	  private String getToSign() {
		  return getStringToSign(getCanonicalQS());
	  }
	  
	  private void addNeededParamsForSigning(Map<String, String> params) {
		  params.put(ACCESS_KEY_ID_PARAM, awsAccessKeyId);
		  params.put(TIMESTAMP_PARAM, timestamp());
	  }
	  
	  private String getStringToSign(final String canonicalQS) {
		  return getHTTPCallWhithMethodBeforeParams() + canonicalQS;
	  }
	  
	  private String getHTTPCallWhithMethodBeforeParams() {
		  return REQUEST_METHOD + "\n"
			      + endpoint + "\n"
			      + REQUEST_URI + "\n";
	  }
	  
	  private Map<String, String> getACopyOfParams(final Map<String, String> params) {
		  Map<String, String> copied = new ConcurrentHashMap<String, String>();
		  for (String key : params.keySet()) {
			  copied.put(key, params.get(key));
		  }
		  return copied;
	  }
  }


  public AmazonSignedRequestsHelper() throws Throwable {
    byte[] secretyKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
    secretKeySpec =
      new SecretKeySpec(secretyKeyBytes, HMAC_SHA256_ALGORITHM);
    mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
    mac.init(secretKeySpec);
  }

  
  public WSRequestHolder getUrl(Map<String, String> params) {
	  AmazonSignedRequest toSignRequest = new AmazonSignedRequest(params);

	  WSRequestHolder ws = WS.url(toSignRequest.getStartingURI());
	  addParametersToRequestHolder(ws, toSignRequest.getSortedFullListOfParamsExceptSignature());
	  addSignatureToRequestHolder(ws, toSignRequest.getSignature());

	  return ws;
  }
  
  private void addParametersToRequestHolder(WSRequestHolder ws, SortedMap<String, String> params) {
	  Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
	  while (iter.hasNext()) {
		  Map.Entry<String, String> kvpair = iter.next();
		  ws.setQueryParameter(kvpair.getKey(), kvpair.getValue());
	  }
  }
  
  private void addSignatureToRequestHolder(WSRequestHolder ws, String signature) {
	  ws.setQueryParameter(SIGNATURE_PARAM, signature);
  }
  
  public String getFullUrlAsString(final Map<String, String> params) {
	  AmazonSignedRequest toSignRequest = new AmazonSignedRequest(params);

	  String canonicalQS = toSignRequest.getCanonicalQS();
	  String signature = toSignRequest.getEncodedSignature();

	  return toSignRequest.getStartingURI() + "?" + canonicalQS + getSubsequentParamInQueryString(SIGNATURE_PARAM, signature);
  }
  
  private String getSubsequentParamInQueryString(String paramName, String paramValue) {
	  return "&" + paramName + "=" + paramValue;
  }  
  
  private String hmac(String stringToSign) {
    String signature = null;
    byte[] data;
    byte[] rawHmac;
    try {
      data = stringToSign.getBytes(UTF8_CHARSET);
      rawHmac = mac.doFinal(data);
      Base64 encoder = new Base64();
      signature = new String(encoder.encode(rawHmac));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
    }
    return signature;
  }

  private String timestamp() {
    String timestamp = null;
    Calendar cal = Calendar.getInstance();
    DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
    timestamp = dfm.format(cal.getTime());
    return timestamp;
  }

  private String canonicalize(SortedMap<String, String> sortedParamMap)
{
    if (sortedParamMap.isEmpty()) {
      return "";
    }

    StringBuffer buffer = new StringBuffer();
    Iterator<Map.Entry<String, String>> iter =
      sortedParamMap.entrySet().iterator();

    while (iter.hasNext()) {
      Map.Entry<String, String> kvpair = iter.next();
      buffer.append(percentEncodeRfc3986(kvpair.getKey()));
      buffer.append("=");
      buffer.append(percentEncodeRfc3986(kvpair.getValue()));
      if (iter.hasNext()) {
        buffer.append("&");
      }
    }
    String canonical = buffer.toString();
    return canonical;
  }

  private String percentEncodeRfc3986(String s) {
    String out;
    try {
      out = URLEncoder.encode(s, UTF8_CHARSET)
      .replace("+", "%20")
      .replace("*", "%2A")
      .replace("%7E", "~");
    } catch (UnsupportedEncodingException e) {
      out = s;
    }
    return out;
  }

}