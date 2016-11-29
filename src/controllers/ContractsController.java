package controllers;

import mappers.ContractMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by pyoung on 2016-11-28.
 */
@WebServlet("/ContractsController")
public class ContractsController extends ActionServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractsController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractMapper contractMapper = new ContractMapper();
        List contracts = contractMapper.findAll();

        if (contracts == null)
            forward("/MissingDataView.jsp", request, response);
        else {
            request.setAttribute("contracts", contracts);
            forward("/WEB-INF/ContractsView.jsp", request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
