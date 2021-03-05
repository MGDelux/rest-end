package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import facades.IPersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class RenameMeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final IPersonFacade FACADE =  IPersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
      
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String all(){
        List<PersonDTO> p = FACADE.getAll();
        return GSON.toJson(p);
    }
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String byid(@PathParam("id")int id) throws Exception{
        PersonDTO p = FACADE.getPerson(id);
        return GSON.toJson(p);
    }
    
    @Path("/edit/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonDTO edit(@PathParam("id") int id, String person) throws Exception{
    PersonDTO DTO = GSON.fromJson(person, PersonDTO.class);
    FACADE.editPerson(id, DTO.getFirstName(), DTO.getLastName(), DTO.getPhone());
     return DTO;

    }

     @Path("/delete/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String delete(@PathParam("id")int id) throws Exception{
        PersonDTO p = FACADE.deletePerson(id);
        return GSON.toJson(p);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
     @Consumes(MediaType.APPLICATION_JSON)
    public PersonDTO test(Person p){
      return FACADE.addPerson(p.getFirstName(), p.getLastName(), p.getPhone());
        
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"API ONLINE\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
       
       //  long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+"4"+"}";  //Done manually so no need for a DTO
    }
}
