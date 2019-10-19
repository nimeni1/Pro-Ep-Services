package client.service.endpoint;

import client.service.db.Database;
import client.service.models.Fare;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("client")
public class ClientResource {

    private Database database = new Database();
    private ArrayList<Fare> notDoneFares = new ArrayList<Fare>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        return "Welcome!";
    }

    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(String jsonClient) {
        try {
            System.out.println("Register successful");
            database.insertDocumentInCollection(database.getClientsCollection(), jsonClient);
            return Response.ok().build();
        } catch (MongoWriteException e) {
            System.out.println("Register not successful");
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

    @Path("askForPrice/{driverEmail}")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response askForPrice(@PathParam("driverEmail") String driverEmail){
        System.out.println("Asking for price");
        Map<String, Object> map = new HashMap<>();
        map.put("email", driverEmail);
        String json = database.findDocument(database.getDriverCollection(), map);
        return Response.ok(json).build();
    }

    @Path("updateFare")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFare(String inforFromClient) {
        try {
            System.out.println("Update Fare successful");
            database.insertDocumentInCollection(database.getFaresCollection(), inforFromClient);
            return Response.ok().build();
        }catch (MongoException e){
            System.out.println("Update fate failed");
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

    @Path("acceptFare")
    @POST
    public Response acceptFare() {
        return Response.ok().build();
    }

    @Path("logIn/{email}/{pass}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(@QueryParam("email") String email, @QueryParam("pass") String password) {
        try {
            System.out.println("LogIn successful");
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            String json = database.findDocument(database.getClientsCollection(), map);
            System.out.println(json);
            return Response.ok(json).build();
        } catch (MongoException ex) {
            System.out.println("LogIn not successful");
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

//    @Path("createFare")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createFare(String inforFromClient) {
//        // Receive JSON + Parse it
//        Timer timer = new Timer();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        Gson gson = new Gson();
//        RequestParse requestParse = gson.fromJson(inforFromClient,RequestParse.class);
//
//        Client infoAboutClient = new Client();
//        infoAboutClient = requestParse.getClient();
//
//        Address startAddress = new Address();
//        startAddress = requestParse.getDestination();
//        Address destinationAddress = new Address();
//        destinationAddress = requestParse.getDestination();
//
//        // Construct Fare
//        Fare fare = new Fare();
//        fare.setClientEmail(infoAboutClient.getEmail());
//        fare.setStart_address(startAddress);
//        fare.setDestination_address(destinationAddress);
//        fare.setDateTime(dateFormat.format(date));
//        notDoneFares.add(fare);
//        long n = System.currentTimeMillis();
//        while ((System.currentTimeMillis()-n)/1000 < 50) {
//            if ((System.currentTimeMillis()-n)/1000 > 5) {
//                String licence = fare.getLicense_number();
//                String toSend = gson.toJson(licence);
//                return Response.ok("armani").build();
//            }
//        }
//        return Response.status(404).build();
//
//    }


}
