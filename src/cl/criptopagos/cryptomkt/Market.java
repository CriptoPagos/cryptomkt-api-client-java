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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Clase que representa un mercado
 * @author Esteban De La Fuente Rubio, DeLaF (esteban[at]sasco.cl)
 * @version 2017-11-24
 */
public class Market {

	protected String market; ///< Mercado sobre el que se está operando
	protected Client client; ///< Cliente de CryptoMKT

	public Market(String market, Client client) {
		this.market = market;
		this.client = client;
	}

	public JSONArray getTicker() throws ClientException {
		return this.getClient().getTicker(this.getMarket());
	}

	public JSONArray getBuyBook() throws ClientException  {
		return this.getBuyBook(0, 0);
	}

	public JSONArray getBuyBook(int page) throws ClientException  {
		return this.getBuyBook(page, 0);
	}

	public JSONArray getBuyBook(int page, int limit) throws ClientException  {
		return this.getClient().getBook(this.getMarket(), "buy", page, limit);
	}

	public JSONArray getSellBook() throws ClientException {
		return this.getSellBook(0, 0);
	}

	public JSONArray getSellBook(int page) throws ClientException {
		return this.getSellBook(page, 0);
	}

	public JSONArray getSellBook(int page, int limit) throws ClientException {
		return this.getClient().getBook(this.getMarket(), "sell", page, limit);
	}

	public JSONArray getTrades() throws ClientException {
		return this.getTrades(null, null, 0, 0);
	}

	public JSONArray getTrades(String start) throws ClientException {
		return this.getTrades(start, null, 0, 0);
	}

	public JSONArray getTrades(String start, String end) throws ClientException {
		return this.getTrades(start, end, 0, 0);
	}

	public JSONArray getTrades(String start, String end, int page) throws ClientException {
		return this.getTrades(start, end, page, 0);
	}

	public JSONArray getTrades(String start, String end, int page, int limit) throws ClientException {
		return this.getClient().getTrades(this.getMarket(), start, end, page, limit);
	}

	public JSONArray getActiveOrders() throws ClientException {
		return this.getActiveOrders(0, 0);
	}

	public JSONArray getActiveOrders(int page) throws ClientException {
		return this.getActiveOrders(page, 0);
	}

	public JSONArray getActiveOrders(int page, int limit) throws ClientException {
		return this.getClient().getActiveOrders(this.getMarket(), page, limit);
	}

	public JSONArray getExecutedOrders() throws ClientException {
		return this.getExecutedOrders(0, 0);
	}

	public JSONArray getExecutedOrders(int page) throws ClientException {
		return this.getExecutedOrders(page, 0);
	}

	public JSONArray getExecutedOrders(int page, int limit) throws ClientException {
		return this.getClient().getExecutedOrders(this.getMarket(), page, limit);
	}

	public JSONObject createBuyOrder(double amount, double price) throws ClientException {
		return this.getClient().createOrder(this.getMarket(), "buy", amount, price);
	}

	public JSONObject createSellOrder(double amount, double price) throws ClientException {
		return this.getClient().createOrder(this.getMarket(), "sell", amount, price);
	}

	private String getMarket() {
		return this.market;
	}

	private Client getClient() {
		return this.client;
	}

}
