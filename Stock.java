package varotra;

public class Stock{
          Long idstock;
          Long idfourniseur;
          Long idproduit;
          Long idaction;
          Long iduniter;
          Double quantiter;
          Double prixunitaireachat;
          Double prixunitairevente;
          java.util.Date date;
          Long idfstock;
          Long iddemandesociete;
    public Stock(){}

              public void setidstock( Long a){
                  idstock=a;
              }
          
              public Long getidstock(){
                  return idstock;
              }
              public void setidfourniseur( Long a){
                  idfourniseur=a;
              }
          
              public Long getidfourniseur(){
                  return idfourniseur;
              }
              public void setidproduit( Long a){
                  idproduit=a;
              }
          
              public Long getidproduit(){
                  return idproduit;
              }
              public void setidaction( Long a){
                  idaction=a;
              }
          
              public Long getidaction(){
                  return idaction;
              }
              public void setiduniter( Long a){
                  iduniter=a;
              }
          
              public Long getiduniter(){
                  return iduniter;
              }
              public void setquantiter( Double a){
                  quantiter=a;
              }
          
              public Double getquantiter(){
                  return quantiter;
              }
              public void setprixunitaireachat( Double a){
                  prixunitaireachat=a;
              }
          
              public Double getprixunitaireachat(){
                  return prixunitaireachat;
              }
              public void setprixunitairevente( Double a){
                  prixunitairevente=a;
              }
          
              public Double getprixunitairevente(){
                  return prixunitairevente;
              }
              public void setdate( java.util.Date a){
                  date=a;
              }
          
              public java.util.Date getdate(){
                  return date;
              }
              public void setidfstock( Long a){
                  idfstock=a;
              }
          
              public Long getidfstock(){
                  return idfstock;
              }
              public void setiddemandesociete( Long a){
                  iddemandesociete=a;
              }
          
              public Long getiddemandesociete(){
                  return iddemandesociete;
              }
}
