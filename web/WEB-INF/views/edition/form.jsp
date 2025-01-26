<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.*" %>
<%@ page import="java.util.List" %>

<%
  String message = (String) request.getAttribute("message");
  List<House> houses = (List<House>) request.getAttribute("houses");
%>

<%@ include file="../../templates/header.jsp" %>
<div class="content-wrapper" style="min-height:680px;">
  <h1>Edition</h1>
  <form class="container" action="EditionController" method="post">
    <div class="row">
      <div class="col-md-3"></div>
      <div class="col-md-6">
        <% if (message != null && !message.isEmpty()) { %>
          <div class="alert alert-danger">
            <%= message %>
          </div>
        <% } %>

        <div class="box-insert-amadia">
          <div class="box">
            <div class="box-body">
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <th>
                      <label for="year">Year: </label>
                    </th>
                    <td>
                      <input type="number" class="form-control" id="year" name="year" min="2000" max="2100" value="2025" required>
                    </td>
                  </tr>
                  <!-- Month Select -->
                  <tr>
                    <th>
                      <label for="month_field">Month</label>
                    </th>
                    <td>
                      <select class="form-control" id="month" name="month" required>
                        <option value="">Select a month</option>
                        <option value="1">January</option>
                        <option value="2">February</option>
                        <option value="3">March</option>
                        <option value="4">April</option>
                        <option value="5">May</option>
                        <option value="6">June</option>
                        <option value="7">July</option>
                        <option value="8">August</option>
                        <option value="9">September</option>
                        <option value="10">October</option>
                        <option value="11">November</option>
                        <option value="12">December</option>
                      </select>
                    </td>
                  </tr>

                  <!--- Houses -->
                  <tr>
                    <th>
                      <label for="house">House</label>
                    </th>
                    <td>
                      <select class="form-control" id="house" name="house" required>
                        <option value="">Select a house</option>
                        <% for(House house : houses) { %>
                          <option value="<%= house.getId() %>"><%= house.getLabel() %></option>
                        <% } %> 
                      </select>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="box-footer">
              <button type="submit" class="btn btn-success">
                Submit
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </form>
</div>

<%@ include file="../../templates/footer.jsp" %> 