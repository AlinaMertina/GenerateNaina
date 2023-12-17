package buildclasse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.TreeUI;

import buildclasse.BuildSpring;
import buildclasse.Fonctioncle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class BuildFramework{
    java.util.List<String> templateclassORM = new java.util.ArrayList<String>();
    String nombase;
    String nomutilisateur;
    String jb;
    String port;
    String password;
    String nomtable;
    java.util.List<String[]> colonne =new java.util.ArrayList<String[]>();
    public BuildFramework(String nombase,String nomutilisateur,String jb,String port,String password,String nomtable){
        this.nombase=nombase;
        this.nomutilisateur=nomutilisateur;
        this.jb=jb;
        this.port=port;
        this.password=password;
        this.nomtable=nomtable;
        colonne= new Description(nombase,jb).getDescriptionTable(nomtable);
    }
    public void buildController(){
        String nomFichier = Fonctioncle.mettrePremiereLettreEnMajuscule(nomtable);
        java.util.List<String> templateclass = geLigneHtlm("/home/mertina/Bureau/L3/S5/Naina/templateFramewoek/Controller.templ");
       
        try {
            File fichier = new File(templateclass.get(0)+"/"+nomFichier+"Controller.java");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            templateclass.remove(0);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true));
            String primary =Fonctioncle.mettrePremiereLettreEnMajuscule(colonne.get(0)[1]) ;
            for(int i=0;i<templateclass.size();i++){
                
                String string = templateclass.get(i);
                string = string.replace("nomClasse", nomFichier );
                string = string.replace("maxCol", primary );
                string = string.replace("entrepar", Fonctioncle.doParametrestring(colonne) );
                string = string.replace("parametreliste", Fonctioncle.doParametreliste(colonne) );
                string = string.replace("test", Fonctioncle.doParametrevalue(colonne) );
                writer.write(string);
                writer.newLine();
                
            }
            writer.close();
           
        } catch (IOException e) {
            System.out.println(e);

        }
    }
    public void buildClasseORM(){
        String nomFichier = Fonctioncle.mettrePremiereLettreEnMajuscule(nomtable);
        java.util.List<String> templateclass = geLigneHtlm("/home/mertina/Bureau/L3/S5/Naina/templateFramewoek/classes.templ");
        java.util.List<String> syntaxeprimarykey= Fonctioncle.getSyntaxetemplate("primaryKey", "finprimaryKey", templateclass);
        try {
            File fichier = new File(templateclass.get(0)+"/"+nomFichier+".java");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            templateclass.remove(0);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true));
            for(int i=0;i<templateclass.size();i++){
                String string = templateclass.get(i);
                 if(string.contains("@ANomTable")==true){
                    string = string.replace("nomT", this.nomtable);
                    string = string.replace("nbrC", Integer.toString(colonne.size()) );
                    string=string.replace("nomS",nomtable+"sequence");
                    writer.write(string);
                    writer.newLine();
                }else if(string.contains("@Adatabase")==true){
                    string = string.replace("nomB", this.nombase);
                    string = string.replace("baseT", this.jb);
                    string = string.replace("nomU", this.nomutilisateur);
                    string = string.replace("passW", this.password);
                    string = string.replace("porT", this.port);
                    writer.write(string);
                    writer.newLine();
                }else if(string.contains("nomClasse")==true){
                    string = string.replace("nomClasse", nomFichier );
                    writer.write(string);
                    writer.newLine();
                }
                else if(string.contains("primaryKey")==true){
                    for(String constructor:Fonctioncle.doConstructeur(colonne,nomFichier)){
                        writer.write(constructor);
                        writer.newLine();
                    }
                    String[] primarykey = colonne.get(0);
                    for(int a=i+1;a<templateclass.size();a++){
                        String stringm= templateclass.get(a);
                        if(stringm.contains("finprimaryKey")==true){
                            i=i+1;
                            break;
                        }
                        stringm=stringm.replace("Colonne", primarykey[1]);
                        stringm=stringm.replace("Type", primarykey[0]);
                        stringm=stringm.replace("maxCol", Fonctioncle.mettrePremiereLettreEnMajuscule(primarykey[1]));
                        writer.write(stringm);
                        writer.newLine();
                        i=i+1;
                    }
                    colonne.remove(0);
                }
                else if(string.contains("attribu")==true){
                    for (String[] string1 :  colonne ) {
                        writer.write("      "+string1[0]+" "+string1[1]+";");
                        writer.newLine();
                    }  
                  
                }
                else if(string.contains("debutC")==true){
                    System.out.println("setget");
                    for (String[] stringa :  colonne ) {
                        for (String string1 :  Fonctioncle.buildSyntaxeSetteursGetteurs(Fonctioncle.getSyntaxetemplate("debutC", "finC", templateclass),stringa[1],stringa[0],0) ) {
                            writer.write(string1);
                            writer.newLine();
                        }
                    }
                    writer.write("}");
                    writer.newLine();
                    writer.close();
                    break;
                }else{
                    writer.write(string);
                    writer.newLine();
                }
            }
           
        } catch (IOException e) {
            System.out.println(e);

        }
    }
    public java.util.List<String> geLigneHtlm(String chemintemplate){
        java.util.List<String> template = new java.util.ArrayList<String>();
        try {
            FileReader fichierReader = new FileReader(chemintemplate);
            BufferedReader bufferedReader = new BufferedReader(fichierReader);
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null) {
                // System.out.println(ligne);
                template.add(ligne);
            }
            bufferedReader.close();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }


}