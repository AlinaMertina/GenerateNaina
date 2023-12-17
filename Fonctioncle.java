package buildclasse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.TreeUI;

import buildclasse.BuildSpring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fonctioncle {
    public Fonctioncle(){}

    public static java.util.List<String> getSyntaxetemplate(String debut,String fin,java.util.List<String> template){
        java.util.List<String> resulta = new java.util.ArrayList<String>();
        int a=-1;
        for(int i=0;i<template.size();i++){
            if(template.get(i).contains(debut)==true)  a=0;
            if(a==0){
                // System.out.println(template.get(i));
                resulta.add(template.get(i));
            }
            if(template.get(i).contains(fin)==true)  break;
        }
        resulta.remove(0);
        resulta.remove(resulta.size()-1);
        return resulta;
    }
    public  static String mettrePremiereLettreEnMajuscule(String chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return chaine; // Si la chaîne est vide ou null, retourne la chaîne inchangée
        }
        return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
    }

    public  static java.util.List<String> removeSection(String debut,String fin,java.util.List<String> templateclass){
        java.util.List<String>  value = new java.util.ArrayList<String>();
        int a=1;
             for(int i=0;i<templateclass.size();i++){
                     if(templateclass.get(i).contains(debut)==true){
                         a=0;
                     }
                     else if (templateclass.get(i).contains(fin)==true){
                         a=1;
                     }
                     if(a==1){
                         value.add(templateclass.get(i));
                     }
             }
             return value;
     }

     public  static java.util.List<String> buildSyntaxeSetteursGetteurs(java.util.List<String> syntaxe,String colonne,String type,int annoter){
        java.util.List<String> resulta = new java.util.ArrayList<String>();
        for(int i=0;i<syntaxe.size();i++){
            String ligne = syntaxe.get(i).replace("Colonne", colonne);
            ligne = ligne.replace("Type", type);
            ligne = ligne.replace("maxCol", mettrePremiereLettreEnMajuscule(colonne));
            if(ligne.contains("@")==true && annoter==1){
                continue;
            }
            resulta.add(ligne);
        }
        return resulta;
    }
    public static String modifLigne(String[][] parame,String ligne){
        for(int i=0;i<parame.length;i++){
            //indice parame[0] mot a remplacer indice parame[1] 
            if(ligne.contains("GenerationType.SEQUENCE")==true) continue;
            ligne = ligne.replace(parame[i][0], parame[i][1]);
        }
        return ligne;
    }

    public static java.util.List<String> buildSyntaxe(java.util.List<String> syntaxe,String[][] value,int annoter){
        java.util.List<String> resulta = new java.util.ArrayList<String>();
        for(int i=0;i<syntaxe.size();i++){
            String ligne = modifLigne(value,syntaxe.get(i));
            if(ligne.contains("@")==true && annoter==1){
                continue;
            }
            resulta.add(ligne);
        }
        return resulta;
    }
    public static String doParametrestring(java.util.List<String[]> colonne){
        String resulta = new String();
        for(int i=0;i<colonne.size();i++){
                resulta=resulta+"\""+colonne.get(i)[1]+"\"";
                if(i<colonne.size()-1){
                    resulta=resulta+",";
                }
        }
        return resulta;
    }

    public static String doParametreliste(java.util.List<String[]> colonne){
        String resulta = new String();
        for(int i=0;i<colonne.size();i++){
                resulta=resulta+colonne.get(i)[0]+" "+colonne.get(i)[1];
                if(i<colonne.size()-1){
                    resulta=resulta+",";
                }
        }
        return resulta;
    }

    public static String doParametrevalue(java.util.List<String[]> colonne){
        String resulta = new String();
        for(int i=0;i<colonne.size();i++){
                resulta=resulta+colonne.get(i)[1];
                if(i<colonne.size()-1){
                    resulta=resulta+",";
                }
        }
        return resulta;
    }

    public static  java.util.List<String> doConstructeur(java.util.List<String[]> colonne,String nomtable){
        java.util.List<String> resulta =  new java.util.ArrayList<String>();
        resulta.add("public "+nomtable+" ("+doParametreliste(colonne)+"){");
        resulta.add("       super();");
        resulta.add("       super.setchild(this);");


          
          
        for(int i =0;i<colonne.size();i++){
            resulta.add("   this."+colonne.get(i)[1]+"="+colonne.get(i)[1]+";");
        }
        resulta.add("}");
        return resulta;
    }

}
