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
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import org.json.simple.JSONObject;

/**
 * Clase que representa una orden de pago de CryptoMKT
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */
public class PaymentOrder {

	// atributos obligatorios para crear una orden
	protected double to_receive; ///< Monto a cobrar de la orden de pago. ARS soporta 2 decimales. CLP no soporta decimales. "." como separador de decimales.
	protected String to_receive_currency; ///< Tipo de moneda con la cual recibirá el pago.
	protected String payment_receiver; ///< Email del usuario o comercio que recibirá el pago. Debe estar registrado en CryptoMarket.

	// atributos opcionales para crear una orden
	protected String external_id = null; /// ID externo. Permite asociar orden interna de comercio con orden de pago. Max. 64 caracteres.
	protected String callback_url = null; ///< Url a la cual se notificarán los cambios de estado de la orden. Max. 256 caracteres.
	protected String error_url = null; ///< Url a la cual se rediccionará en caso de error. Max. 256 caracteres.
	protected String success_url = null; ///< Url a la cual se rediccionará en caso de éxito. Max. 256 caracteres.
	protected String refund_email = null; ///< correo para hacer devolución en caso de problemas

	// atributos que se asignan a través del servicio web de CryptoMKT
	protected String id; ///< ID interno de la orden de pago
	protected int status; ///< Estado de la orden de pago
	protected String deposit_address; ///< Dirección de la orden de pago
	protected String expected_currency; ///< Tipo de moneda que espera la orden para ser aceptada
	protected double expected_amount; ///< Cantidad que espera la orden para ser aceptada
	protected Date created_at; ///< Fecha de creación de la orden de pago
	protected Date updated_at; ///< Fecha de actualización de la orden de pago
	protected Date server_at; ///< cuando se creo la orden en el servidor
	protected String qr; ///< Url de la imagen QR de la orden de pago
	protected String payment_url; ///< Url de voucher de orden de pago
	protected String obs; ///< Observaciones
	protected double remaining; ///< tiempo que queda para hacer el pago
	protected String token; ///< token para url de pago en CryptoMKT

	protected HashMap<Integer, String> statuses;

	public PaymentOrder() {
		this.statuses = new HashMap<Integer, String>();
		this.statuses.put(-4, "Pago múltiple");
		this.statuses.put(-3, "Monto pagado no concuerda");
		this.statuses.put(-2, "Falló conversión");
		this.statuses.put(-1, "Expiró orden de pago");
		this.statuses.put(0, "Esperando pago");
		this.statuses.put(1, "Esperando bloque");
		this.statuses.put(2, "Esperando procesamiento");
		this.statuses.put(3, "Pago exitoso");
	}

	public HashMap getData() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("to_receive", String.valueOf(to_receive));
		data.put("to_receive_currency", to_receive_currency);
		data.put("payment_receiver", payment_receiver);
		if (this.external_id != null) {
			data.put("external_id", external_id);
		}
		if (this.callback_url != null) {
			data.put("callback_url", callback_url);
		}
		if (this.error_url != null) {
			data.put("error_url", error_url);
		}
		if (this.success_url != null) {
			data.put("success_url", success_url);
		}
		if (this.refund_email != null) {
			data.put("refund_email", refund_email);
		}
		return data;
	}

	public void set(JSONObject data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.to_receive = Double.valueOf(data.get("to_receive").toString());
		this.to_receive_currency = data.get("to_receive_currency").toString();
		if (data.get("payment_receiver") != null) {
			this.payment_receiver = data.get("payment_receiver").toString();
		}
		this.external_id = data.get("external_id").toString();
		this.callback_url = data.get("callback_url").toString();
		this.error_url = data.get("error_url").toString();
		this.success_url = data.get("success_url").toString();
		this.refund_email = data.get("refund_email").toString();
		this.id = data.get("id").toString();
		this.status = Integer.valueOf(data.get("status").toString());
		this.deposit_address = data.get("deposit_address").toString();
		this.expected_currency = data.get("expected_currency").toString();
		this.expected_amount = Double.valueOf(data.get("expected_amount").toString());
		try {
			this.created_at = dateFormat.parse(data.get("created_at").toString());
			this.updated_at = dateFormat.parse(data.get("updated_at").toString());
			this.server_at = dateFormat.parse(data.get("server_at").toString());
		} catch (ParseException e) {
		}
		this.qr = data.get("qr").toString();
		this.payment_url = data.get("payment_url").toString();
		this.obs = data.get("obs").toString();
		this.remaining = Double.valueOf(data.get("remaining").toString());
		this.token = data.get("token").toString();
	}

	public String getStatusMessage() {
		return (String)this.statuses.get(this.status);
	}

	public void setToReceive(double to_receive) {
		this.to_receive = to_receive;
	}

	public double getToReceive() {
		return this.to_receive;
	}

	public void setToReceiveCurrency(String to_receive_currency) {
		this.to_receive_currency = to_receive_currency;
	}

	public String getToReceiveCurrency() {
		return this.to_receive_currency;
	}

	public void setPaymentReceiver(String payment_receiver) {
		this.payment_receiver = payment_receiver;
	}

	public String getPaymentReceiver() {
		return this.payment_receiver;
	}

	public void setExternalId(String external_id) {
		this.external_id = external_id;
	}

	public String getExternalId() {
		return this.external_id;
	}

	public void setCallbackUrl(String callback_url) {
		this.callback_url = callback_url;
	}

	public String getCallbackUrl() {
		return this.callback_url;
	}

	public void setErrorUrl(String error_url) {
		this.error_url = error_url;
	}

	public String getErrorUrl() {
		return this.error_url;
	}

	public void setSuccessUrl(String success_url) {
		this.success_url = success_url;
	}

	public String getSucessUrl() {
		return this.success_url;
	}

	public void setRefundEmail(String refund_email) {
		this.refund_email = refund_email;
	}

	public String getRefundEmail() {
		return this.refund_email;
	}

	public String getId() {
		return this.id;
	}

	public int getStatus() {
		return this.status;
	}

	public String getDepositAddress() {
		return this.deposit_address;
	}

	public double getExpectedAmount() {
		return this.expected_amount;
	}

	public Date getCreatedAt() {
		return this.created_at;
	}

	public Date getUpdatedAt() {
		return this.updated_at;
	}

	public Date getServerAt() {
		return this.server_at;
	}

	public String getQr() {
		return this.qr;
	}

	public String getPaymentUrl() {
		return this.payment_url;
	}

	public String getObs() {
		return this.obs;
	}

	public double getRemaining() {
		return this.remaining;
	}

	public String getToken() {
		return this.token;
	}

}
