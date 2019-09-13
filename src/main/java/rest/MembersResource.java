package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MembersDTO;
import entities.Members;
import utils.EMF_Creator;
import facades.MembersFacade;
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
@Path("groupmembers")
public class MembersResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final MembersFacade FACADE =  MembersFacade.getMembersFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @Path("/test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"hello\":\"world\"}";
    }
    
    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMemberCount() {
        long count = FACADE.getAllMembersCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allMembers(){
        List<MembersDTO> members = FACADE.getAllMembers();
        return GSON.toJson(members);
    }
    
    @Path("/member/{color}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allColorMembers(@PathParam("color") String color){
        List<MembersDTO> members = FACADE.getMembersByColor(color);
        return GSON.toJson(members);
    }
    
    @Path("/member/email/{mail}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String memberByEmail(@PathParam("mail") String email){
        MembersDTO member = FACADE.getMemberByEmail(email);
        return GSON.toJson(member);
    }
    
//    @Path("/member/{id}/change/{color}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String changeMemberColor(@PathParam("id") int id, @PathParam("color") String color ){
////        Members old = FACADE.getMemberByID(new Long(id));
//        Members old = new Members(FACADE.getChangeByID(new Long(id)));
//        MembersDTO changedMember = FACADE.removeAndAddColor(old, color);
//        return GSON.toJson(changedMember);
//    }
    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMemberById(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getMemberByID(new Long(id)));
    }
}
