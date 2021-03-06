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
 * @example Ejemplo_002_ticker.java
 * Ejemplo para obtener el ticker de cierto mercado
 * @link https://developers.cryptomkt.com/es/#obtener-ticker
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */

import cl.criptopagos.cryptomkt.Client;
import cl.criptopagos.cryptomkt.Market;
import cl.criptopagos.cryptomkt.ClientException;

public class Ejemplo_002_ticker {

	public static void main(String[] args) {

		// crear cliente
		Client Client = new Client();

		// obtener ticker del mercado chileno de ether
		// si no se indica el mercado, se obtienen todos los tickers de los
		// mercados disponibles
		try {
			System.out.println(Client.getTicker("ETHCLP"));
		} catch (ClientException e) {
			System.out.println(e.getMessage());
		}

		// también se puede utilizar el método que obtiene el mercado y luego el ticker
		// en ejemplos futuros de mercados se usará sólo esta forma (creando el mercado
		// primero) a pesar que existen los mismos métodos en el cliente que se pueden
		// usar pasando los parámetros correspondientes
		Market Market = Client.getMarket("ETHCLP");
		try {
			System.out.println(Market.getTicker());
		} catch (ClientException e) {
			System.out.println(e.getMessage());
		}

	}

}
