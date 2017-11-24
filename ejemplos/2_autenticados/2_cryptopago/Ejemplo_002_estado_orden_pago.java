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

/**
 * @example Ejemplo_002_estado_orden_pago.java
 * Ejemplo para obtener el estado de una orden de pago
 * @link https://developers.cryptomkt.com/es/#estado-de-orden-de-pago
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */


import cl.criptopagos.cryptomkt.Client;
import cl.criptopagos.cryptomkt.PaymentOrder;
import cl.criptopagos.cryptomkt.ClientException;

public class Ejemplo_002_estado_orden_pago {

	public static void main(String[] args) {

		// credenciales
		String api_key = "";
		String api_secret = "";

		// crear cliente
		Client Client = new Client(api_key, api_secret);

		// obtener estado de la orden de pago
		try {
			PaymentOrder PaymentOrder = Client.getPaymentOrder("P14155");
			System.out.println(PaymentOrder.getPaymentUrl());
		} catch (ClientException e) {
			System.out.println(e.getMessage());
		}
	}

}
