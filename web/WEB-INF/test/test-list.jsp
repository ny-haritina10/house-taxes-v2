<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../templates/header.jsp" %>
<div class="content-wrapper" style="min-height:680px;">
  <section class="content">
      <!-- Form Section -->
      <form action="FormActionServlet" method="post">
          <div class="row">
              <div class="col-md-12">
                  <div class="box box-solid">
                      <div style="background:#103a8e; color:white;" class="box-header with-border">
                          <h3 class="box-title">Sample Form</h3>
                      </div>
                      <div class="box-body">
                          <div class="form-group">
                              <div class="col-md-4">
                                  <label for="date_field">Date</label>
                                  <input type="date" class="form-control" id="date_field" name="date_field" required>
                              </div>
                              <div class="col-md-4">
                                  <label for="text_field">Text</label>
                                  <input type="text" class="form-control" id="text_field" name="text_field" required>
                              </div>
                              <div class="col-md-4">
                                  <label for="number_field">Number</label>
                                  <input type="number" class="form-control" id="number_field" name="number_field" required>
                              </div>
                              <div class="col-md-4">
                                  <label for="select_field">Select</label>
                                  <select class="form-control" id="select_field" name="select_field" required>
                                      <option value="">Choose an option</option>
                                      <option value="option1">Option 1</option>
                                      <option value="option2">Option 2</option>
                                      <option value="option3">Option 3</option>
                                  </select>
                              </div>
                          </div>
                      </div>
                      <div class="box-footer">
                          <button type="submit" class="btn btn-success">Submit</button>
                      </div>
                  </div>
              </div>
          </div>
      </form>

      <!-- List Section -->
      <div class="row">
          <div class="col-md-12">
              <div class="box box-solid">
                  <div class="box-header">
                      <h3 class="box-title">List of Items</h3>
                  </div>
                  <div class="box-body table-responsive no-padding">
                      <table class="table table-hover table-bordered">
                          <thead>
                              <tr>
                                  <th style="background-color:#103a8e; color:white">ID</th>
                                  <th style="background-color:#103a8e; color:white">Name</th>
                                  <th style="background-color:#103a8e; color:white">Quantity</th>
                                  <th style="background-color:#103a8e; color:white">Date</th>
                                  <th style="background-color:#103a8e; color:white">Category</th>
                                  <th style="background-color:#103a8e; color:white">Price</th>
                              </tr>
                          </thead>
                          <tbody>
                              <!-- Static Data -->
                              <tr>
                                  <td>1</td>
                                  <td>Product A</td>
                                  <td>10</td>
                                  <td>2025-01-01</td>
                                  <td>Category 1</td>
                                  <td>$100</td>
                              </tr>
                              <tr>
                                  <td>2</td>
                                  <td>Product B</td>
                                  <td>5</td>
                                  <td>2025-01-10</td>
                                  <td>Category 2</td>
                                  <td>$50</td>
                              </tr>
                              <tr>
                                  <td>3</td>
                                  <td>Product C</td>
                                  <td>8</td>
                                  <td>2025-02-15</td>
                                  <td>Category 1</td>
                                  <td>$80</td>
                              </tr>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
      </div>
  </section>
</div>
<%@ include file="../templates/footer.jsp" %>