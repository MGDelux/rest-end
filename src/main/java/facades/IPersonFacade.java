package facades;

import dtos.PersonDTO;
import dtos.PersonsDTI;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class IPersonFacade {

    private static IPersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private IPersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static IPersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new IPersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO deletePerson(int id) throws Exception{
        
         EntityManager em = emf.createEntityManager();
         Person p;
         try{
         em.getTransaction().begin();
         p = (em.find(Person.class, id));
      
         em.remove(p);
         em.getTransaction().commit();
         
         }catch(Exception e){
          throw new WebApplicationException("Could not delete, provided id does not exist",404);
         }
         return new PersonDTO(p);
            
         
   


    }
    public PersonDTO editPerson(int id, String fname, String lname, String phonenr) throws Exception{
    EntityManager em = emf.createEntityManager();
     Person persontoEdit;
    try{
    persontoEdit = (em.find(Person.class, id));
    persontoEdit.setFirstName(fname);
    persontoEdit.setLastName(lname);
    persontoEdit.setPhone(phonenr);
     em.getTransaction().begin();
     
     em.merge(persontoEdit);
     em.getTransaction().commit();
    }catch(Exception e){
     throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience",500);
    }
     
     return new PersonDTO(persontoEdit);


    }
    
    
    public PersonDTO addPerson(String fname, String lName, String phone){
              EntityManager em = emf.createEntityManager();
              Person p = new Person(fname,lName,phone);
            try{
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
               
            }catch(Exception e){
                  throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience",500);
            }finally{
                em.close();
            }
       return new PersonDTO(p);
    }
    
    public PersonDTO getPerson(int id) throws Exception{
          EntityManager em = emf.createEntityManager();
          PersonDTO pDTO;
        try{
         pDTO = new PersonDTO(em.find(Person.class, id));
        }catch(Exception e){
         throw new WebApplicationException("No person with provided id found",404);
        }
      return  pDTO;
    }
    
    public List<PersonDTO> getAll(){
        
        EntityManager em = emf.createEntityManager();
          List<Person> rms;
        try{
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        rms = query.getResultList();
        PersonsDTI p = new PersonsDTI();
        }catch(Exception e){
     throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience",500);
    }
        return PersonDTO.getDtos(rms);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        IPersonFacade fe = getFacadeExample(emf);
        fe.getAll().forEach(dto->System.out.println(dto));
    }

}
