/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
     private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final IPersonFacade FACADE =  IPersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static void populate() throws Exception{
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        IPersonFacade fe = IPersonFacade.getFacadeExample(emf);
        fe.addPerson("Mathias Baumann", "Touboel","3123123");
         fe.addPerson("Janus ", "Autist","2311323");
          fe.addPerson("Ali ", "Meme","123123");
          FACADE.editPerson(2,"FICK","YOU","LOL");
    }
    
    public static void main(String[] args) throws Exception {
        populate();
    }
}
