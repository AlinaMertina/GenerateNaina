package buildclasse;

import buildclasse.*;

public class Main {
    public static void main(String[] args) {
        // for (String[] string :  new Description("varotra","postgres").getDescriptionTable("stock") ) {
        //         System.out.println(string[0]+" "+string[1]);
        // }   
        // new BuildClasse("/home/mertina/Bureau/L3/S5/Naina/template/ccharp.templ","varotra","postgres","stock",".cs").buildClassefile();
        // new BuildSpring("/home/mertina/Bureau/L3/S5/Naina/templateSpring/classe.templ",
        // "/home/mertina/Bureau/L3/S5/Naina/templateSpring/controlleur.templ",
        // "/home/mertina/Bureau/L3/S5/Naina/templateSpring/service.templ",
        // "/home/mertina/Bureau/L3/S5/Naina/templateSpring/repositorie.templ","systemecommercialle","postgres","sortiestock");
    
        BuildFramework buildFramework = new BuildFramework("systemecommercialle", "mertina", "postgres", "5432", "root", "personnev");
        buildFramework.buildController();
        buildFramework.buildClasseORM();
    }
}



