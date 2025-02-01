select 
    id_facture,
    total_prix,
    sum(amount_pay) as total_paye,
    total_prix - sum(amount_pay) as reste_a_paye
from 
    v_facture_payement
 WHERE 1=1 
 and date_payment >= TO_DATE('2024-01-27', 'YYYY-MM-DD') 
 and date_payment <= TO_DATE('2024-03-27', 'YYYY-MM-DD')
group by 
    id_facture ,
    total_prix;