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
 * @example Ejemplo_004_transacciones.java
 * Ejemplo para obtener las transacciones de cierto mercado
 * @link https://developers.cryptomkt.com/es/#obtener-trades
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */

import cl.criptopagos.cryptomkt.Client;
import cl.criptopagos.cryptomkt.Market;
import cl.criptopagos.cryptomkt.ClientException;

public class Ejemplo_004_transacciones {

	public static void main(String[] args) {

		// crear cliente y mercado
		Client Client = new Client();
		Market Market = Client.getMarket("ETHCLP");

		// obtener transacciones en rango de fechas
		try {
			System.out.println(Market.getTrades("2017-10-01", "2017-10-02"));
		} catch (ClientException e) {
			System.out.println(e.getMessage());
		}
	}

}
