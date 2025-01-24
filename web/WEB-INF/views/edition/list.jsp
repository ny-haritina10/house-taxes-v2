<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.Facture" %>
<%@ page import="java.util.List" %>

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
                                  <th style="background-color:#103a8e; color:white">Coefficient</th>
                                  <th style="background-color:#103a8e; color:white">House ID</th>
                                  <th style="background-color:#103a8e; color:white">Details</th> 
                              </tr>
                          </thead>
                          <tbody>
                              <%
                              List<Facture> factures = (List<Facture>) request.getAttribute("factures");
                                  if (factures != null) {
                                      for (Facture facture : factures) {
                              %>
                                      <tr>
                                          <td><%= facture.getId() %></td>
                                          <td><%= facture.getTotalSurface() %></td>
                                          <td><%= facture.getYear() %></td>
                                          <td><%= facture.getMonth() %></td>
                                          <td><%= facture.getUnitPrice() %></td>
                                          <td><%= facture.getCoefficient() %></td>
                                          <td><%= facture.getIdHouse() %></td>
                                          <td>
                                              <a href="EditionController?action=details&id=<%= facture.getId() %>" class="btn btn-primary btn-sm">Details</a>
                                          </td>
                                      </tr>
                              <%
                                      }
                                  } else {
                              %>
                                      <tr>
                                          <td colspan="8" style="text-align:center;">No data available</td>
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