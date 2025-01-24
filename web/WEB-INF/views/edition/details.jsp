<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.Facture" %>

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
                          Facture facture = (Facture) request.getAttribute("facture");
                          if (facture != null) {
                      %>
                      <div class="card">
                          <div class="card-body">
                              <p><strong>ID:</strong> <%= facture.getId() %></p>
                              <p><strong>Total Surface:</strong> <%= facture.getTotalSurface() %></p>
                              <p><strong>Year:</strong> <%= facture.getYear() %></p>
                              <p><strong>Month:</strong> <%= facture.getMonth() %></p>
                              <p><strong>Unit Price:</strong> <%= facture.getUnitPrice() %></p>
                              <p><strong>Coefficient:</strong> <%= facture.getCoefficient() %></p>
                              <p><strong>House ID:</strong> <%= facture.getIdHouse() %></p>
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