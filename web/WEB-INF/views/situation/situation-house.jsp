<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.HouseSituationPayment" %>
<%@ page import="mg.itu.model.House" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<%
    List<HouseSituationPayment> situations = (List<HouseSituationPayment>) request.getAttribute("situations");
    List<House> houses = (List<House>) request.getAttribute("houses");
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
                      <h3 class="box-title">Filter by Year and House</h3>
                  </div>
                  <div class="box-body">
                      <form action="SituationPaymentController" method="get">
                          <input type="hidden" name="action" value="view">
                          <div class="form-group">
                              <div class="col-md-4">
                                  <label for="year">Year</label>
                                  <input type="number" value="2024" class="form-control" id="year" name="year" value="<%= year != null ? year : "" %>" required>
                              </div>
                              <div class="col-md-4">
                                  <label for="house">House</label>
                                  <select class="form-control" id="house" name="house">
                                      <option value="">Select a House</option>
                                      <%
                                          if (houses != null && !houses.isEmpty()) {
                                              for (House house : houses) {
                                      %>
                                              <option value="<%= house.getId() %>"><%= house.getLabel() %></option>
                                      <%
                                              }
                                          }
                                      %>
                                  </select>
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
                      <h3 class="box-title">House Payment Situations for Year <%= year != null ? year : "" %></h3>
                  </div>
                  <div class="box-body table-responsive no-padding">
                      <table class="table table-hover table-bordered">
                          <thead>
                              <tr>
                                  <th style="background-color:#103a8e; color:white">House ID</th>
                                  <th style="background-color:#103a8e; color:white">Label</th>
                                  <th style="background-color:#103a8e; color:white">Total to Pay</th>
                                  <th style="background-color:#103a8e; color:white">Total Paid</th>
                                  <th style="background-color:#103a8e; color:white">Total Unpaid</th>
                              </tr>
                          </thead>
                          <tbody>
                              <%
                                  if (situations != null && !situations.isEmpty()) {
                                      for (HouseSituationPayment situation : situations) {
                              %>
                                      <tr>
                                          <td>HOUS-00<%= situation.getHouse().getId() %></td>
                                          <td><%= situation.getHouse().getLabel() %></td>
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