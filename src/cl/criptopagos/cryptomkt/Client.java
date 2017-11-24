/**
 * Cliente para servicios web de CryptoMKT
 * Copyright (C) SASCO SpA (https://sasco.cl)
 *
 * Este programa es software libre: usted puede redistribuirlo y/o modificarlo
 * bajo los términos de la GNU Lesser General Public License (LGPL) publicada
 * por la Fundación para el Software Libre, ya sea la versión 3 de la Licencia,
 * o (a su elección) cualquier versión posterior de la misma.
 *
 * Este programa se distribuye con la esperanza de que sea útil, pero SIN
 * GARANTÍA ALGUNA; ni siquiera la garantía implícita MERCANTIL o de APTITUD
 * PARA UN PROPÓSITO DETERMINADO. Consulte los detalles de la GNU Lesser General
 * Public License (LGPL) para obtener una información más detallada.
 *
 * Debería haber recibido una copia de la GNU Lesser General Public License
 * (LGPL) junto a este programa. En caso contrario, consulte
 * <http://www.gnu.org/licenses/lgpl.html>.
 */

package cl.criptopagos.cryptomkt;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Date;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.SortedSet;
import java.util.TreeSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Clase principal con el cliente de CryptoMKT
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */
public class Client {

	private String _url = "https://api.cryptomkt.com"; ///< URL base para las llamadas a la API
	private String _version = "v1"; ///< Versión de la API con la que funciona este SDK
	protected int default_limit = 100; ///< Límite por defecto a usar en consultas paginadas (se usa el máximo por defecto)
	protected String api_key = null; ///< API key para autenticación
	protected String api_secret = null; ///< API secret para autenticación
	protected ClientResponse response; ///< Objeto con la respuesta del servicio web de CryptoMKT

	public Client() {
	}

	public Client(String api_key, String api_secret) {
		this.api_key = api_key;
		this.api_secret = api_secret;
	}

	public Market getMarket(String market) {
		return new Market(market, this);
	}

	public JSONArray getMarkets() throws ClientException {
		String url = this.createUrl("/market");
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener el listado de mercados: " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public JSONArray getTicker(String market) throws ClientException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("market", market);
		String url = this.createUrl("/ticker", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener el ticker del mercado " + market + ": " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public JSONArray getBook(String market, String type, int page, int limit) throws ClientException {
		if (limit == 0) {
			limit = this.default_limit;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("market", market);
		params.put("type", type);
		params.put("page", String.valueOf(page));
		params.put("limit", String.valueOf(limit));
		String url = this.createUrl("/book", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener el libro de " + type + " del mercado " + market + ": " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public JSONArray getTrades(String market, String start, String end, int page, int limit) throws ClientException {
		if (start == null) {
			start = this.date();
		}
		if (end == null) {
			end = start;
		}
		if (limit == 0) {
			limit = this.default_limit;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("market", market);
		params.put("start", start);
		params.put("end", end);
		params.put("page", String.valueOf(page));
		params.put("limit", String.valueOf(limit));
		String url = this.createUrl("/trades", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener los intercambios del mercado " + market + ": " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public JSONArray getActiveOrders(String market, int page, int limit) throws ClientException {
		if (limit == 0) {
			limit = this.default_limit;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("market", market);
		params.put("page", String.valueOf(page));
		params.put("limit", String.valueOf(limit));
		String url = this.createUrl("/orders/active", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener las órdenes activas del mercado " + market + ": " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public JSONArray getExecutedOrders(String market, int page, int limit) throws ClientException {
		if (limit == 0) {
			limit = this.default_limit;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("market", market);
		params.put("page", String.valueOf(page));
		params.put("limit", String.valueOf(limit));
		String url = this.createUrl("/orders/executed", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener las órdenes activas del mercado " + market + ": " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public JSONObject createOrder(String market, String type, double amount, double price) throws ClientException {
		String url = this.createUrl("/orders/create");
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("market", market);
		data.put("type", type);
		data.put("amount", String.valueOf(amount));
		data.put("price", String.valueOf(price));
		this.setResponse(this.consume(url, data));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible crear la orden en el mercado " + market + ": " + body.get("message"));
		}
		return (JSONObject)body.get("data");
	}

	public JSONObject getOrder(String id) throws ClientException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		String url = this.createUrl("/orders/status", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener el balance de las billeteras: " + body.get("message"));
		}
		return (JSONObject)body.get("data");
	}

	public JSONObject cancelOrder(String id) throws ClientException {
		String url = this.createUrl("/orders/cancel");
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("id", id);
		this.setResponse(this.consume(url, data));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible cancelar la orden " + id + " en el mercado: " + body.get("message"));
		}
		return (JSONObject)body.get("data");
	}

	public JSONArray getBalance() throws ClientException {
		String url = this.createUrl("/balance");
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener el balance de las billeteras: " + body.get("message"));
		}
		return (JSONArray)body.get("data");
	}

	public PaymentOrder createPaymentOrder(PaymentOrder PaymentOrder) throws ClientException {
		String url = this.createUrl("/payment/new_order");
		this.setResponse(this.consume(url, PaymentOrder.getData()));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible realizar consulta a CryptoMKT: " + body.get("message"));
		}
		PaymentOrder.set((JSONObject)body.get("data"));
		return PaymentOrder;
	}

	public PaymentOrder getPaymentOrder(String id) throws ClientException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		String url = this.createUrl("/payment/status", params);
		this.setResponse(this.consume(url));
		JSONObject body = this.getResponse().getJSON();
		if (this.getResponse().getStatus() != 200) {
			throw new ClientException("No fue posible obtener la orden de pago " + id + " desde CryptoMKT: " + body.get("message"));
		}
		PaymentOrder PaymentOrder = new PaymentOrder();
		PaymentOrder.set((JSONObject)body.get("data"));
		return PaymentOrder;
	}

	private String createUrl(String recurso) {
		return this._url + "/" + this._version + recurso;
	}

	private String createUrl(String recurso, HashMap params) {
		String url = this.createUrl(recurso);
		return url + "?" + this.createQueryString(params);
	}

	private String createQueryString(HashMap params) {
		ArrayList<String> query = new ArrayList<String>();
		Set set = params.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry entry = (Map.Entry)iterator.next();
			query.add(entry.getKey() + "="+ entry.getValue());
		}
		return String.join("&", query);
	}

	private ClientResponse consume(String url) {
		return this.consume(url, null);
	}

	private ClientResponse consume(String url, HashMap data) {
		// preparar cabecera con autenticación
		HashMap<String, String> headers = new HashMap<String, String>();
		if (this.api_key != null && this.api_secret != null) {
			long timestamp = this.getTimestamp();
			String path = url.replaceFirst(this._url, "").split("\\?")[0];
			String msg = String.valueOf(timestamp) + path;
			if (data != null) {
				SortedSet<String> keys = new TreeSet<String>(data.keySet());
				for (String key : keys) {
					msg += data.get(key);
				}
			}
			String signature = "";
			try {
				SecretKeySpec signingKey = new SecretKeySpec(this.api_secret.getBytes(), "HmacSHA384");
				Mac mac = Mac.getInstance("HmacSHA384");
				mac.init(signingKey);
				signature = this.bytesToHex(mac.doFinal(msg.getBytes()));
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			}
			headers.put("X-MKT-APIKEY", this.api_key);
			headers.put("X-MKT-SIGNATURE", signature);
			headers.put("X-MKT-TIMESTAMP", String.valueOf(timestamp));
		}
		// realizar solicitud
		int status = 0;
		String body = "";
		byte[] bytes = null;
		URL uri = null;
		HttpURLConnection conn = null;
		try {
			// crear conexión
			uri = new URL(url);
			conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestProperty("User-Agent", "SASCO CryptoMKT Client");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept", "application/json");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if (!headers.isEmpty()) {
				Set set = headers.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry entry = (Map.Entry)iterator.next();
					conn.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
				}
			}
			// enviar datos
			if (data != null) {
				String dataString = this.createQueryString(data);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", "" + Integer.toString(dataString.getBytes().length));
				OutputStream os = conn.getOutputStream();
				os.write(dataString.getBytes());
				os.flush();
				os.close();
			} else {
				conn.setRequestMethod("GET");
			}
			// obtener respuesta
			status = conn.getResponseCode();
			InputStream is = conn.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] dataIs = new byte[4098];
			while ((nRead = is.read(dataIs, 0, dataIs.length)) != -1) {
				buffer.write(dataIs, 0, nRead);
			}
			buffer.flush();
			bytes = buffer.toByteArray();
			body = new String(bytes, "UTF-8");
			// cerrar conexión
			conn.disconnect();
		} catch (FileNotFoundException e) {
			// si el código de estado fue diferente a 200 se caerá en esta excepción y se añade el
			// resultado de salida del servicio web
			InputStream error = conn.getErrorStream();
			try {
				int code = error.read();
				while (code != -1) {
					body += (char)code;
					code = error.read();
				}
				error.close();
			} catch (IOException ignored) {
			}
		}
		catch (Exception e) {
			InputStream error = conn.getErrorStream();
			try {
				int code = error.read();
				while (code != -1) {
					body += (char)code;
					code = error.read();
				}
				error.close();
			} catch (IOException ignored) {
			}
		}
		// crear respuesta
		ClientResponse response = new ClientResponse();
		response.setStatus(status);
		response.setBody(body);
		return response;
	}

	private long getTimestamp() {
		return (long)(System.currentTimeMillis()/1000);
	}

	private String bytesToHex(final byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private String date() {
		String formato = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void setResponse(ClientResponse response) {
		this.response = response;
	}

	private ClientResponse getResponse() {
		return this.response;
	}

}
