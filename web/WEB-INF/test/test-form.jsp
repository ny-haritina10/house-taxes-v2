<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String message = (String) request.getAttribute("message");
%>

<%@ include file="../templates/header.jsp" %>
<div class="content-wrapper" style="min-height:680px;">
  <h1>Sample Form</h1>
  <form class="container" action="FormActionServlet" method="post">
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
                                          <label for="date_field">Date</label>
                                      </th>
                                      <td>
                                          <input type="date" class="form-control" id="date_field" name="date_field" required>
                                      </td>
                                  </tr>
                                  <tr>
                                      <th>
                                          <label for="text_field">Text</label>
                                      </th>
                                      <td>
                                          <input type="text" class="form-control" id="text_field" name="text_field" required>
                                      </td>
                                  </tr>
                                  <tr>
                                      <th>
                                          <label for="number_field">Number</label>
                                      </th>
                                      <td>
                                          <input type="number" class="form-control" id="number_field" name="number_field" required>
                                      </td>
                                  </tr>
                                  <tr>
                                      <th>
                                          <label for="select_field">Select</label>
                                      </th>
                                      <td>
                                          <select class="form-control" id="select_field" name="select_field" required>
                                              <option value="">Choose an option</option>
                                              <option value="option1">Option 1</option>
                                              <option value="option2">Option 2</option>
                                              <option value="option3">Option 3</option>
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
<%@ include file="../templates/footer.jsp" %>