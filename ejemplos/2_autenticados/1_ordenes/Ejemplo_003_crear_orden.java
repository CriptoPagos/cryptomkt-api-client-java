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
 * @example Ejemplo_003_crear_orden.java
 * Ejemplo para crear una orden de compra o venta de un usuario en cierto mercado
 * @link https://developers.cryptomkt.com/es/#crear-orden
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */

import cl.criptopagos.cryptomkt.Client;
import cl.criptopagos.cryptomkt.Market;
import cl.criptopagos.cryptomkt.ClientException;

public class Ejemplo_003_crear_orden {

	public static void main(String[] args) {

		// credenciales
		String api_key = "";
		String api_secret = "";

		// crear cliente y mercado
		Client Client = new Client(api_key, api_secret);
		Market Market = Client.getMarket("ETHCLP");

		// crear ordenes
		try {

			// crear una orden de compra para el usuario en el mercado
			// poco ETH y precio muy bajo para evitar comprar por error si se ejecuta el ejemplo sin modificar
			System.out.println(Market.createBuyOrder(0.01, 1000)); // 0.01 ETH a 1.000 pesos cada 1 ETH

			// crear una orden de venta para el usuario en el mercado
			// poco ETH y precio muy alto para evitar vender por error si se ejecuta el ejemplo sin modificar
			System.out.println(Market.createSellOrder(0.01, 100000000)); // 0.01 ETH a 100.000.000 pesos cada 1 ETH

		} catch (ClientException e) {
			System.out.println(e.getMessage());
		}
	}

}
