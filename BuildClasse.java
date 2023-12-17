package buildclasse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.TreeUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BuildClasse {
    String chemintemplate ;
    String nombase;
    String jdbc;
    String nomtable;
    String extentionfile;
    java.util.List<String> template = new java.util.ArrayList<String>();
    public BuildClasse(String chemin,String nom,String j,String nomT,String ext){
        chemintemplate=chemin;
        nombase=nom;
        jdbc=j;
        nomtable=nomT;
        extentionfile=ext;
        geLigneHtlm();
    }
    public void geLigneHtlm(){
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
    }
    public void buildClassefile(){
        String nomFichier = mettrePremiereLettreEnMajuscule(nomtable);
        java.util.List<String> syntaxeGetSet = getSyntaxeSetteursGetteurs();
        java.util.List<String[]> attribu = new Description(nombase,jdbc).getDescriptionTable(nomtable);
        try {
            File fichier = new File(nomFichier+extentionfile);
            if (!fichier.exists()) {
                fichier.createNewFile();
                // System.out.println("cree");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true)); // 'true' pour écrire à la fin du fichier
           for (String string : template) {
                if(string.contains("nomtable")==true){
                    writer.write(string.replace("nomtable", nomFichier));
                    writer.newLine();
                }
                else if(string.contains("attribu")==true){
                    for (String[] string1 :  attribu ) {
                        writer.write("          "+string1[0]+" "+string1[1]+";");
                        writer.newLine();
                    }  
                }
                else if(string.contains("debutC")==true){
                    System.out.println("setget");
                    for (String[] stringa :  attribu ) {
                        for (String string1 :  buildSyntaxeSetteursGetteurs(syntaxeGetSet,stringa[1],stringa[0],1) ) {
                            writer.write("          "+string1);
                            writer.newLine();
                        }
                    }
                    writer.write("}");
                    writer.newLine();
                    writer.close();
                    break;
                }else{
                    // System.out.println("eto ay"+string);
                    writer.write(string);
                    writer.newLine();
                }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String mettrePremiereLettreEnMajuscule(String chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return chaine; // Si la chaîne est vide ou null, retourne la chaîne inchangée
        }
        return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
    }
    public String ajoutNom(String ancienClasse,String nomclasse){
        int p=0;
        for(int i=0;i<ancienClasse.length();i++){
                if(ancienClasse.charAt(i)=='#'){
                    p=i;
                }
        }
        String premierePartie = ancienClasse.substring(0, p); // Extraction de la première partie jusqu'à l'index
        String deuxiemePartie = ancienClasse.substring(p); // Extraction de la deuxième partie à partir de l'index
        return premierePartie+" " +nomclasse+" " +deuxiemePartie;
    }
    public java.util.List<String> getSyntaxeSetteursGetteurs(){
            java.util.List<String> resulta = new java.util.ArrayList<String>();
            int a=-1;
            for(int i=0;i<template.size();i++){
                if(template.get(i).contains("debutC")==true)  a=0;
                if(a==0){
                    resulta.add(template.get(i));
                }
                if(template.get(i).contains("finC")==true)  break;
            }
            resulta.remove(0);
            resulta.remove(resulta.size()-1);
            return resulta;
    }
    public String modifLigne(String[] parame,String ligne){
        for(int i=0;i<parame.length;i++){
            //indice parame[0] mot a remplacer indice parame[1] value
            ligne = ligne.replace(parame[0], parame[1]);
        }
        return ligne;
    }
    public java.util.List<String> buildSyntaxeSetteursGetteurs(java.util.List<String> syntaxe,String colonne,String type,int annoter){
            java.util.List<String> resulta = new java.util.ArrayList<String>();
            for(int i=0;i<syntaxe.size();i++){
                String ligne = syntaxe.get(i).replace("Colonne", colonne);
                ligne = ligne.replace("Type", type);
                if(ligne.contains("@")==true && annoter==1){
                    continue;
                }
                resulta.add(ligne);
            }
            return resulta;
    }
}
