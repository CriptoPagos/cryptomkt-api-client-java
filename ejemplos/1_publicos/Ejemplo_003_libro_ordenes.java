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
 * @example Ejemplo_003_libro_ordenes.java
 * Ejemplo para obtener el libro de ordenes de cierto mercado
 * @link https://developers.cryptomkt.com/es/#libro-de-ordenes
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */

import cl.criptopagos.cryptomkt.Client;
import cl.criptopagos.cryptomkt.Market;
import cl.criptopagos.cryptomkt.ClientException;

public class Ejemplo_003_libro_ordenes {

	public static void main(String[] args) {

		// crear cliente y mercado
		Client Client = new Client();
		Market Market = Client.getMarket("ETHCLP");

		// obtener libro de compra y de venta
		try {
			System.out.println(Market.getBuyBook());
			System.out.println(Market.getSellBook());
		} catch (ClientException e) {
			System.out.println(e.getMessage());
		}
	}

}
