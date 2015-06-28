package com.eshop;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import NET.webserviceX.www.CurrencyConvertor;
import NET.webserviceX.www.CurrencyConvertorLocator;
import NET.webserviceX.www.CurrencyConvertorSoap;
import NET.webserviceX.www.Currency;

/**
 * Servlet implementation class CurrencyConverter
 */
@WebServlet("/CurrencyConverter")
public class CurrencyConverter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrencyConverter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getParameter("ToCurrency");
		CurrencyConvertor cc = new CurrencyConvertorLocator();
		CurrencyConvertorSoap ccs = null;
		try {
			ccs = cc.getCurrencyConvertorSoap();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Currency from = Currency.getTable().get(request.getParameter("FromCurrency"));
		Currency to = (Currency) Currency.getTable().get(request.getParameter("ToCurrency"));
		// Send the SOAP request to the server and get the result from the web service
		double conversionRate = ccs.conversionRate(from, to);
		response.getWriter().print(conversionRate);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
