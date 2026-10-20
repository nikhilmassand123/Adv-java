package com.j2ee.enterprise.web;

import com.j2ee.enterprise.model.Account;
import com.j2ee.enterprise.service.BankingService;

import java.util.Map;

/**
 * Web Tier Component (Servlet):
 * Interprets client HTTP requests, delegates to Business Tier, and renders output.
 */
public class BankingServlet implements MiniServlet {
    private BankingService bankingService;

    @Override
    public void init() {
        System.out.println("[Web Container] Initializing BankingServlet lifecycle (init)...");
        this.bankingService = new BankingService();
    }

    @Override
    public void service(HttpRequest req, HttpResponse res) {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        StringBuilder body = new StringBuilder();
        body.append("<!DOCTYPE html><html><head><title>J2EE Enterprise Banking</title>");
        body.append("<style>body{font-family:sans-serif;margin:40px;background:#f4f6f9;color:#333;}")
            .append("table{border-collapse:collapse;width:100%;margin-top:20px;background:#fff;}")
            .append("th,td{border:1px solid #ddd;padding:12px;text-align:left;}th{background:#004085;color:white;}")
            .append(".banner{background:#004085;color:white;padding:20px;border-radius:4px;}")
            .append(".badge{padding:4px 8px;border-radius:3px;background:#28a745;color:white;font-size:12px;}</style></head><body>");

        body.append("<div class='banner'><h1>J2EE Enterprise Web Application</h1>")
            .append("<p>N-Tier Architecture Demo: Client Tier &rarr; Web Tier &rarr; Business Tier &rarr; EIS Tier</p></div>");

        if ("transfer".equalsIgnoreCase(action)) {
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            String amtStr = req.getParameter("amount");

            if (from != null && to != null && amtStr != null) {
                try {
                    double amt = Double.parseDouble(amtStr);
                    boolean success = bankingService.transfer(from, to, amt);
                    if (success) {
                        body.append("<div style='margin:20px 0;padding:15px;background:#d4edda;color:#155724;border-radius:4px;'>")
                            .append("<strong>Success:</strong> Transferred $").append(amt).append(" from ").append(from).append(" to ").append(to)
                            .append("</div>");
                    } else {
                        body.append("<div style='margin:20px 0;padding:15px;background:#f8d7da;color:#721c24;border-radius:4px;'>")
                            .append("<strong>Error:</strong> Transfer failed. Verify account balances.</div>");
                    }
                } catch (NumberFormatException e) {
                    body.append("<p style='color:red;'>Invalid amount specified.</p>");
                }
            }
        }

        // Display Account Ledger
        body.append("<h2>Active Enterprise Accounts <span class='badge'>Live EIS Tier</span></h2>");
        body.append("<table><tr><th>Account ID</th><th>Account Holder</th><th>Balance (USD)</th></tr>");

        for (Map.Entry<String, Account> entry : bankingService.listAccounts().entrySet()) {
            Account acc = entry.getValue();
            body.append("<tr>")
                .append("<td>").append(acc.getAccountNumber()).append("</td>")
                .append("<td>").append(acc.getOwnerName()).append("</td>")
                .append("<td>$").append(String.format("%.2f", acc.getBalance())).append("</td>")
                .append("</tr>");
        }
        body.append("</table>");

        body.append("<h3>Simulate Transaction:</h3>");
        body.append("<form method='GET' action='/app'>")
            .append("<input type='hidden' name='action' value='transfer'/>")
            .append("From: <input type='text' name='from' value='ACC1001'/> ")
            .append("To: <input type='text' name='to' value='ACC1002'/> ")
            .append("Amount: <input type='text' name='amount' value='500'/> ")
            .append("<input type='submit' value='Execute Transfer'/>")
            .append("</form>");

        body.append("</body></html>");

        res.sendHtml(body.toString());
    }

    @Override
    public void destroy() {
        System.out.println("[Web Container] Destroying BankingServlet (destroy)...");
    }
}
