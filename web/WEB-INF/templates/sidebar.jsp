<aside class="main-sidebar">
  <section class="sidebar" style="height: auto;">

    <ul class="sidebar-menu" id="menuslider">
      <li class="header">Menu</li>

      <div class="dropdown">
        <li>
          <a style="display: block; padding: 8px;" href="#" onclick="window.location.href='#'">
              <i style="margin-right: 3.6px;" class="fa fa-edit"></i> 
              <span>Edition</span>
              <i class="fa fa-angle-left pull-right"></i>
          </a>

          <ul class="treeview-menu">
              <li><a href="EditionController"><i class="fa fa-file-text"></i> Formulaire facture</a></li>
              <li><a href="EditionController?action=list"><i class="fa fa-list"></i> Liste facture</a></li>
          </ul>
        </li>
      </div>

      <div class="dropdown">
        <li>
          <a style="display: block; padding: 8px;" href="#" onclick="window.location.href='#'">
              <i style="margin-right: 3.6px;" class="fa fa-credit-card"></i> 
              <span>Situation Payment</span>
              <i class="fa fa-angle-left pull-right"></i>
          </a>

          <ul class="treeview-menu">
              <li><a href="SituationPaymentController"><i class="fa fa-home"></i> Houses</a></li>
              <li><a href="ArrondissementSituationController"><i class="fa fa-map-marker"></i> Arrondissements</a></li>
          </ul>
        </li>
      </div>

      <div class="dropdown">
        <li>
          <a style="display: block; padding: 8px;" href="#" onclick="window.location.href='#'">
              <i style="margin-right: 3.6px;" class="fa fa-map"></i> 
              <span>Open Street Map</span>
              <i class="fa fa-angle-left pull-right"></i>
          </a>

          <ul class="treeview-menu">
              <li><a href="MapController?action=map"><i class="fa fa-globe"></i> Map</a></li>
          </ul>
        </li>
      </div>

    </ul>
  </section>
</aside>