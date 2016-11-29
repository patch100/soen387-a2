package controllers;

import mappers.ContractMapper;
import mappers.RevenueRecognitionMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by pyoung on 2016-11-28.
 */
@WebServlet("/RevenueRecognitionController")
public class RevenueRecognitionController extends ActionServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevenueRecognitionController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RevenueRecognitionMapper revenueRecognitionMapper = new RevenueRecognitionMapper();
        List revenues = revenueRecognitionMapper.findAll();

        if (revenues == null)
            forward("/MissingDataView.jsp", request, response);
        else {
            request.setAttribute("revenues", revenues);
            forward("/WEB-INF/RevenuesView.jsp", request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
