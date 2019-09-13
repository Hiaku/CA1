package facades;

import dto.MembersDTO;
import entities.Members;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MembersFacade {

    private static MembersFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MembersFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MembersFacade getMembersFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MembersFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // To see how many members are in the database
    public long getAllMembersCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long allMembersCount = (long)em.createQuery("SELECT COUNT(m) FROM Members m").getSingleResult();
            return allMembersCount;
        }finally{  
            em.close();
        }
    }
    
    // Getting a list of all the members
    public List<MembersDTO> getAllMembers(){
        EntityManager em = emf.createEntityManager();
        try {
            List<Members> members = em.createNamedQuery("Members.getAll").getResultList();
            List<MembersDTO> result = new ArrayList<>();
            members.forEach((member) -> {
                result.add(new MembersDTO(member));
            });
            return result;
        } finally {
            em.close();
        }
    }
    
    public MembersDTO getMemberByID(Long id){
        EntityManager em = getEntityManager();
        try{
            Members member = em.find(Members.class, id);
            return new MembersDTO(member);
        } finally{
            em.close();
        }
    }
    
    public List<MembersDTO> getMembersByFirstname(String firstname){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MembersDTO> membersByFirstname
                    = em.createQuery("SELECT new dto.MembersDTO(m) FROM Members m WHERE m.firstname = :firstname", MembersDTO.class).setParameter("firstname", firstname);
            return membersByFirstname.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<MembersDTO> getMembersByLastname(String lastname){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MembersDTO> membersByLastname
                    = em.createQuery("SELECT new dto.MembersDTO(m) FROM Members m WHERE m.lastname = :lastname", MembersDTO.class).setParameter("lastname", lastname);
            return membersByLastname.getResultList();
        } finally {
            em.close();
        }
    }
    
    public MembersDTO getMemberByEmail(String email){
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new dto.MembersDTO(m) FROM Members m WHERE m.email = :email", MembersDTO.class).setParameter("email", email).getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<MembersDTO> getMembersByColor(String color){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MembersDTO> membersByColor
                    = em.createQuery("SELECT new dto.MembersDTO(m) FROM Members m WHERE m.color = :color", MembersDTO.class).setParameter("color", color);
            return membersByColor.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Members getChangeByID(Long id){
        EntityManager em = getEntityManager();
        try{
            Members member = em.find(Members.class, id);
            return member;
        } finally{
            em.close();
        }
    }
    
    public Members removeAndAddColor(Members m, String color){
        EntityManager em = emf.createEntityManager();
        m.setColor(color);
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            return m;
        } finally {
            em.close();
        }
    }
    
    // For Adding new member
    public Members addMember(Members m){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            return m;
        } finally {
            em.close();
        }
    }

}
