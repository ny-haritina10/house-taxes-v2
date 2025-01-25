<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.Facture" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<%
    List<Facture> factures = (List<Facture>) request.getAttribute("factures");    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.FRANCE); 
%>

<%@ include file="../../templates/header.jsp" %>
<div class="content-wrapper" style="min-height:680px;">
  <section class="content">
      <!-- List Section -->
      <div class="row">
          <div class="col-md-12">
              <div class="box box-solid">
                  <div class="box-header">
                      <h3 class="box-title">List of Factures</h3>
                  </div>
                  <div class="box-body table-responsive no-padding">
                      <table class="table table-hover table-bordered">
                          <thead>
                              <tr>
                                  <th style="background-color:#103a8e; color:white">ID</th>
                                  <th style="background-color:#103a8e; color:white">Total Surface</th>
                                  <th style="background-color:#103a8e; color:white">Year</th>
                                  <th style="background-color:#103a8e; color:white">Month</th>
                                  <th style="background-color:#103a8e; color:white">Unit Price</th>
                                  <th style="background-color:#103a8e; color:white">Monthly Amount</th>
                                  <th style="background-color:#103a8e; color:white">Coefficient</th>
                                  <th style="background-color:#103a8e; color:white">Payed</th>
                                  <th style="background-color:#103a8e; color:white">Details</th>
                                  <th style="background-color:#103a8e; color:white">Actions</th> <!-- New column for Pay button -->
                              </tr>
                          </thead>
                          <tbody>
                              <%
                                  if (factures != null) {
                                      for (Facture facture : factures) {
                                          // Format prices for each facture
                                          String formattedUnitPrice = currencyFormatter.format(facture.getUnitPrice());
                                          String formattedMonthlyAmount = currencyFormatter.format(facture.getMonthlyMonthlyAmountToPay());
                              %>
                                      <tr>
                                          <td>FACT-00<%= facture.getId() %></td>
                                          <td><%= facture.getTotalSurface() %></td>
                                          <td><%= facture.getYear() %></td>
                                          <td><%= facture.getMonth() %></td>
                                          <td><%= formattedUnitPrice %></td>
                                          <td><%= formattedMonthlyAmount %></td>
                                          <td><%= facture.getCoefficient() %></td>
                                          <td><%= facture.getIsPayed().equals("Y") ? "Payed" : "Unpayed" %></td>
                                          <td>
                                              <a href="EditionController?action=details&id=<%= facture.getId() %>" class="btn btn-primary btn-sm">Details</a>
                                          </td>
                                          <td>
                                              <% if (facture.getIsPayed().equals("N")) { %>
                                                  <a href="EditionController?action=pay&id=<%= facture.getId() %>" class="btn btn-success btn-sm">Pay Facture</a>
                                              <% } else { %>
                                                  <span class="text-success">Already Paid</span>
                                              <% } %>
                                          </td>
                                      </tr>
                              <%
                                      }
                                  } else {
                              %>
                                      <tr>
                                          <td colspan="10" style="text-align:center;">No data available</td>
                                      </tr>
                              <%
                                  }
                              %>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
      </div>
  </section>
</div>
<%@ include file="../../templates/footer.jsp" %>