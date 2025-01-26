<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<%
    List<ArrondissementSituationPayment> situations = (List<ArrondissementSituationPayment>) request.getAttribute("situations");
    Integer year = (Integer) request.getAttribute("year");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);
%>

<%@ include file="../../templates/header.jsp" %>
<div class="content-wrapper" style="min-height:680px;">
  <section class="content">
      <!-- Filter Section -->
      <div class="row">
          <div class="col-md-12">
              <div class="box box-solid">
                  <div style="background:#103a8e; color:white;" class="box-header with-border">
                      <h3 class="box-title">Filter by Year</h3>
                  </div>
                  <div class="box-body">
                      <form action="ArrondissementSituationController" method="get">
                          <input type="hidden" name="action" value="view">
                          <div class="form-group">
                              <div class="col-md-4">
                                  <label for="year">Year</label>
                                  <input type="number" value="2025" class="form-control" id="year" name="year" value="<%= year != null ? year : "" %>" required>
                              </div>
                          </div>
                          <div class="form-group">
                            <div class="col-md-4">
                                <button type="submit" class="btn btn-success" style="margin-top: 25px;">Filter</button>
                            </div>
                          </div>
                      </form>
                  </div>
              </div>
          </div>
      </div>

      <!-- List Section -->
      <div class="row">
          <div class="col-md-12">
              <div class="box box-solid">
                  <div class="box-header">
                      <h3 class="box-title">Arrondissement Payment Situations for Year <%= year != null ? year : "" %></h3>
                  </div>
                  <div class="box-body table-responsive no-padding">
                    <table class="table table-hover table-bordered">
                      <thead>
                          <tr>
                              <th style="background-color:#103a8e; color:white">Arrondissement ID</th>
                              <th style="background-color:#103a8e; color:white">Label</th>
                              <th style="background-color:#103a8e; color:white">Total to Pay</th>
                              <th style="background-color:#103a8e; color:white">Total Paid</th>
                              <th style="background-color:#103a8e; color:white">Total Unpaid</th>
                          </tr>
                      </thead>
                      <tbody>
                          <%
                              if (situations != null && !situations.isEmpty()) {
                                  for (ArrondissementSituationPayment situation : situations) {
                          %>
                                      <tr>
                                          <td>ARR-00<%= situation.getArrondissement().getId() %></td>
                                          <td><%= situation.getArrondissement().getLabel() %></td>
                                          <td><%= currencyFormatter.format(situation.getTotalToPay()) %></td>
                                          <td><%= currencyFormatter.format(situation.getTotalPayed()) %></td>
                                          <td><%= currencyFormatter.format(situation.getTotalUnpayed()) %></td>
                                      </tr>
                          <%
                                  }
                              } else {
                          %>
                                  <tr>
                                      <td colspan="5" style="text-align:center;">No data available for the selected year.</td>
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