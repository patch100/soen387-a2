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
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by pyoung on 2016-11-28.
 */
@WebServlet("/RevenueBeforeDateController")
public class RevenueBeforeDateController extends ActionServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevenueBeforeDateController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContractMapper contractMapper = new ContractMapper();
        Long id = Long.parseLong(request.getParameter("id"));
        LocalDate date = LocalDate.parse(request.getParameter("beforeDate"));
        Contract contract = contractMapper.find(id);

        double recognizedRevenue = contract.recognizedRevenue(date);

        if (contract == null)
            forward("/MissingDataView.jsp", request, response);
        else {
            request.setAttribute("recognizedRevenue", recognizedRevenue);
            forward("/WEB-INF/RevenueBeforeDateView.jsp", request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
