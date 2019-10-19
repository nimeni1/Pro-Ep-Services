package driver.service.endpoint;

import com.mongodb.MongoException;
import driver.service.db.Database;
import driver.service.models.Driver;
import driver.service.serializer.JsonSerializer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("driver")
public class DriverResource {
    private Database database = new Database();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello, my friend!!!";
    }

    @Path("logIn/{email}/{pass}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(@QueryParam("email") String email, @QueryParam("pass") String password) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            String json = database.findDocument(database.getDriverCollection(), map);
            System.out.println(json);
            return Response.ok(json).build();
        } catch (MongoException ex) {
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

    @Path("createFare")
    @POST
    public Response createFare() {
        return Response.ok().build();
    }

    @Path("askForCarAndPrice/{driverEmail}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response askForCarAndPrice(@PathParam("driverEmail") String driverEmail){
        try {
            System.out.println("Asking for price");
            Map<String, Object> map = new HashMap<>();
            map.put("email", driverEmail);
            String json = database.findDocument(database.getDriverCollection(), map);
            Driver driver = JsonSerializer.driverFromJson(json);
            String driver_lincense = driver.getLicenseNumber();
            map = new HashMap<>();
            map.put("driver_license", driver_lincense);
            json = database.findDocument(database.getCarsCollection(), map);
            return Response.ok(json).build();
        } catch (MongoException ex) {
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }
}
