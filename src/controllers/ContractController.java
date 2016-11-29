package controllers;

import mappers.ContractMapper;
import mappers.RevenueRecognitionMapper;
import models.Contract;
import models.RevenueRecognition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pyoung on 2016-11-28.
 */
@WebServlet("/ContractController")
public class ContractController extends ActionServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractMapper contractMapper = new ContractMapper();
        Long id = Long.parseLong(request.getParameter("id"));
        Contract contract = contractMapper.find(id);

        if (contract == null)
            forward("/MissingDataView.jsp", request, response);
        else {
            request.setAttribute("contract", contract);
            forward("/WEB-INF/ContractView.jsp", request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractMapper contractMapper = new ContractMapper();
        Long id = Long.parseLong(request.getParameter("id"));
        Contract contract = contractMapper.find(id);

        if (contract == null)
            forward("/MissingDataView.jsp", request, response);
        else {
            contract.calculateRecognitions();
            ArrayList<RevenueRecognition> recognitions = contract.getRevenueRecognitions();
            RevenueRecognitionMapper revenueRecognitionMapper = new RevenueRecognitionMapper();

            for (RevenueRecognition r:recognitions) {
                revenueRecognitionMapper.insertRevenueRecognition(r);
            }
            request.setAttribute("revenues", recognitions);
            forward("/WEB-INF/RevenuesView.jsp", request, response);
        }
    }
}
