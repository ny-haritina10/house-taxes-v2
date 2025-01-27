<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../templates/header.jsp" %>
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>

<div class="content-wrapper" style="min-height:680px;">
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <div class="box box-solid">
          <div class="box-header">
              <h3 class="box-title">Map</h3>
          </div>
          <div class="box-body">
              <div id="map" style="height: 500px;"></div>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
<%@ include file="../../templates/footer.jsp" %>

<script>
  $(document).ready(function() {
    var map = L.map('map').setView([-18.91, 47.51], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    $.getJSON("MapController", function(data) {
        data.houses.forEach(function(house) {
            var latitude = house.latitude;
            var longitude = house.longitude;
            var label = house.label;
            var houseId = house.id;

            if (latitude && longitude) {
                var marker = L.marker([latitude, longitude])
                    .addTo(map)
                    .bindPopup(label);

                marker.on('click', function() {
                    window.location.href = "http://localhost:8080/house-taxes-v2/controller/SituationPaymentController?action=view&year=2024&house=" + houseId;
                });
            }
        });

        for (var idArrondissement in data.arrondissementPositions) {
            if (data.arrondissementPositions.hasOwnProperty(idArrondissement)) {
              var positions = data.arrondissementPositions[idArrondissement];
              var polygonPoints = positions.map(function(position) {
                  return [position.longitude, position.latitude];
              });

              polygonPoints.push(polygonPoints[0]);

              var polygon = L.polygon(polygonPoints, { color: 'red' })
                  .bindPopup("Arrondissement " + idArrondissement)
                  .addTo(map);

              (function(idArrondissement) {
                  polygon.on('click', function() {
                      console.log("Clicked Arrondissement ID:", idArrondissement); 
                      window.location.href = "http://localhost:8080/house-taxes-v2/controller/ArrondissementSituationController?action=view&year=2024&arrondissement=" + idArrondissement;
                  });
              })(idArrondissement); 
            }
        }
    });
  });
</script>