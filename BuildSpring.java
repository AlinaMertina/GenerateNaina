package buildclasse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.TreeUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class BuildSpring{
    java.util.List<String> templateclass = new java.util.ArrayList<String>();
    java.util.List<String> templatecontrolleur = new java.util.ArrayList<String>();
    java.util.List<String> templateservice = new java.util.ArrayList<String>();
    java.util.List<String> templaterepositorie = new java.util.ArrayList<String>();
    String nombase;
    String jdbc ;
    String nomtable;
    java.util.List<String[]> colonne =new java.util.ArrayList<String[]>();
    String[] primarykey;

    public BuildSpring(String cheminclasse,String cheminconlleur,String cheminservice,String cheminrepositorie,String nomb,String jd ,String nomt){
        templateclass = geLigneHtlm(cheminclasse);
        templatecontrolleur = geLigneHtlm(cheminconlleur);
        templateservice = geLigneHtlm(cheminservice);
        templaterepositorie = geLigneHtlm(cheminrepositorie);
        nomtable=nomt;
        nombase=nomb;
        jdbc=jd;
        colonne= new Description(nomb,jd).getDescriptionTable(nomt);
        primarykey= colonne.get(0);
        buildClassefile();
        buildRepositoryfile();
        buildControlleurfile();
        buildServicefile();
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
    public java.util.List<String> getSyntaxetemplate(String debut,String fin,java.util.List<String> template){
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
    public String modifLigne(String[][] parame,String ligne){
        for(int i=0;i<parame.length;i++){
            //indice parame[0] mot a remplacer indice parame[1] 
            if(ligne.contains("GenerationType.SEQUENCE")==true) continue;
            ligne = ligne.replace(parame[i][0], parame[i][1]);
        }
        return ligne;
    }

    public java.util.List<String> buildSyntaxe(java.util.List<String> syntaxe,String[][] value,int annoter){
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
    public void DropdSyntaxe(String debut,String fin,java.util.List<String> template){
        int a=1;
        for(int i=0;i<template.size();i++){
            if(template.get(i).contains(debut)==true)  a=0;
            if(a==0){
                template.remove(i);
            if(template.get(i).contains(fin)==true)  break;
        }
        }
    }
    public String mettrePremiereLettreEnMajuscule(String chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return chaine; // Si la chaîne est vide ou null, retourne la chaîne inchangée
        }
        return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
    }
    public java.util.List<String> buildSyntaxeSetteursGetteurs(java.util.List<String> syntaxe,String colonne,String type,int annoter){
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
    public void printLigne(java.util.List<String> template){
        for(int i=0;i<template.size();i++){
                System.out.println(template.get(i));
        }
    }

    // build class 
    public void buildClassefile(){
        String nomFichier = mettrePremiereLettreEnMajuscule(nomtable);
        java.util.List<String> syntaxeprimarykey = getSyntaxetemplate("primaryKey","finprimaryKey",templateclass);
        // DropdSyntaxe("primaryKey","finprimaryKey",templateclass);
        // System.out.println("size "+templateclass.size());
        // printLigne(templateclass);
        java.util.List<String> syntaxegetset = getSyntaxetemplate("debutC","finC",templateclass);
        try {
            File fichier = new File(templateclass.get(0)+"/"+nomFichier+".java");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            templateclass.remove(0);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true)); // 'true' pour écrire à la fin du fichier
            int a=1;
           for (String string : templateclass) {

                if(string.contains("attribu")==true){
                    for(String ligneprimarykey :buildSyntaxe(syntaxeprimarykey ,new String[][]{{"Type",primarykey[0]},{"Colonne",primarykey[1]},{"nomtable",nomtable}},0) ){
                        // System.out.println("primary :"+ligneprimarykey);
                        writer.write(ligneprimarykey);
                        writer.newLine();
                    }
                    for (String[] string1 :  colonne ) {
                        writer.write("          "+string1[0]+" "+string1[1]+";");
                        writer.newLine();
                    }  
                }
                else if(string.contains("debutC")==true){
                    System.out.println("setget");
                    for (String[] stringa :  colonne ) {
                        for (String string1 :  buildSyntaxeSetteursGetteurs(syntaxegetset,stringa[1],stringa[0],1) ) {
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
                    if(string.contains("primaryKey")==true){
                        a=0;
                    }
                    else if(string.contains("finprimaryKey")==true){
                        a=1;
                    }
                    if(a!=0){
                        writer.write(string.replace("nomtable", nomFichier));
                        writer.newLine();
                      
                    }
                   
                }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fin build class

    //build repository
    public void buildRepositoryfile(){
        try {
            String nomFichier = mettrePremiereLettreEnMajuscule(nomtable);
            File fichier = new File(templaterepositorie.get(0)+"/"+nomFichier+"Repository.java");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            templaterepositorie.remove(0);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true)); // 'true' pour écrire à la fin du fichier
        int a=1;
            for (String string : templaterepositorie) {
                System.out.println("repository : "+string);
                writer.write(string.replace("nomtable", nomFichier));
                writer.newLine();
            }
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //fin repository

    //build service
    public void buildServicefile(){
        try {
            String mintable= nomtable;
            String nomFichier = mettrePremiereLettreEnMajuscule(nomtable);
            File fichier = new File(templateservice.get(0)+"/"+nomFichier+"Service.java");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            templateservice.remove(0);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true)); // 'true' pour écrire à la fin du fichier
        int a=1;
            for (String string : templateservice) {
                System.out.println("service : "+string);
                string = string.replace("nomtable", nomFichier);
                string = string.replace("mintable", mintable);
                writer.write(string);
                writer.newLine();
            }
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //find service

     public void buildControlleurfile(){
        try {
            String mintable= nomtable;
            String nomFichier = mettrePremiereLettreEnMajuscule(nomtable);
            File fichier = new File(templatecontrolleur.get(0)+"/"+nomFichier+"Controller.java");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            templatecontrolleur.remove(0);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true)); // 'true' pour écrire à la fin du fichier
        int a=1;
            for (String string : templatecontrolleur) {
                System.out.println("service : "+string);
                string = string.replace("nomtable", nomFichier);
                string = string.replace("mintable", mintable);
                writer.write(string);
                writer.newLine();
            }
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //find service
}