<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.*" %>

<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>


<%
    Facture facture = (Facture) request.getAttribute("facture");
    House house = (House) request.getAttribute("house");
    Arrondissement arrondissement = (Arrondissement) request.getAttribute("arrondissement");
    Commune commune = (Commune) request.getAttribute("commune");
    HouseOwner owner = (HouseOwner) request.getAttribute("owner");

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.FRANCE); 
    currencyFormatter.setMaximumFractionDigits(0); 

    String formattedUnitPrice = currencyFormatter.format(facture.getUnitPrice());
    String formattedTotalAmount = currencyFormatter.format(facture.getMonthlyMonthlyAmountToPay());
%>

<%@ include file="../../templates/header.jsp" %>
<div class="content-wrapper" style="min-height:680px;">
  <section class="content">
      <div class="row">
          <div class="col-md-12">
              <div class="box box-solid">
                  <div class="box-header">
                      <h3 class="box-title">Facture Details</h3>
                  </div>
                  <div class="box-body">
                      <%
                        if (facture != null) {
                      %>
                      <div class="card">
                          <div class="card-body">
                              <h3>Period: <%= facture.getMonth() + "/" + facture.getYear() %></h3>
                              <% if (facture.getIsPayed().equals("N")) { %>
                                <h4>Unpayed facture</h4>
                              <% } else { %>
                                <h4>Payed facture</h4>
                              <% } %>

                              <h4>Date payment: <%= facture.getDatePaymentFacture() %></h4>

                              <p><strong>ID:</strong> FACT-00<%= facture.getId() %></p>
                              <p><strong>Total Surface:</strong> <%= facture.getTotalSurface() %></p>
                              <p><strong>Price per m2:</strong> <%= formattedUnitPrice %></p>
                              <p><strong>Total Coefficient:</strong> <%= facture.getCoefficient() %> </p>
                              <p><strong>Total Amount To Pay:</strong> <b><%= formattedTotalAmount %></b></p>
                              <br>
                              <h3>House details</h3>
                              <p><strong>Owner:</strong> <%= owner.getName() %></p>
                              <p><strong>House:</strong> <%= house.getLabel() %></p>
                              <p><strong>Arrondissement: </strong> <%= arrondissement.getLabel() %></p>
                              <p><strong>Commune: </strong> <%= commune.getLabel() %></p>
                          </div>
                      </div>
                      <%
                          } else {
                      %>
                      <div class="alert alert-warning">
                          No details found for this Facture.
                      </div>
                      <%
                          }
                      %>
                  </div>
              </div>
          </div>
      </div>
  </section>
</div>
<%@ include file="../../templates/footer.jsp" %>