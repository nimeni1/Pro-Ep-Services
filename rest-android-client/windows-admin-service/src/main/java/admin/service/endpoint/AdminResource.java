package admin.service.endpoint;

import admin.service.models.*;
import admin.service.mongo.db.Database;
import admin.service.serializer.JsonSerializer;
import com.google.gson.Gson;
import com.mongodb.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("admin")
public class AdminResource {

    private Gson gson = new Gson();
    private Database database = new Database();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String check() {
        return "Hello check!";
    }

    /**
     * Method Creates driver in database
     * @param jsonClient
     * @return
     */
    @Path("createDriver")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDriverAccount(String jsonClient) {
        try {
            database.insertDocumentInCollection(database.getDriverCollection(),jsonClient);
            return Response.ok().build();
        } catch (MongoException e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

    /**
     * Method creates a car in database
     * @param jsonCar
     * @return
     */
    @Path("createCar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDriverCar(String jsonCar) {
        try {
            database.insertDocumentInCollection(database.getCarsCollection(),jsonCar);
            return Response.ok().build();
        } catch (MongoException e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

    /**
     * Method Updates Driver
     * @param driverObject
     * @return
     */
    @Path("updateDriver")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDriverAccount(String driverObject) {
        try {
            Map<String, Object> map = JsonSerializer.jsonToMap(driverObject);
            Map<String, Object> license = new HashMap<>();
            license.put("license_number", map.get("license_number"));
            database.updateDocumentInCollection(database.getDriverCollection(), license, map);
            return Response.accepted().build();
        } catch (MongoException ex) {
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

    /**
     * Method Updates Car
     * @param car
     * @return
     */
    @PUT
    @Path("updateCar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDriverCar(String car) {
        try {
            Map<String, Object> map = JsonSerializer.jsonToMap(car);
            Map<String, Object> plate = new HashMap<>();
            plate.put("license_plate", map.get("license_plate"));
            database.updateDocumentInCollection(database.getCarsCollection(), plate, map);
            return Response.accepted().build();
        } catch (MongoException ex) {
            return Response.serverError().build();
        } finally {
            database.closeConnection();
        }
    }

    /**
     * Methods gets details about driver
     * @param license
     * @return
     */
    @GET
    @Path("getDriver/{license}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriver(@PathParam("license") String license) {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("license_number",license);
            String result = database.getDocumentFromCollection(database.getDriverCollection(),map);
            return Response.ok(result).build();
        } catch (MongoException ex) {
            return Response.status(404).build();
        } finally {
            database.closeConnection();
        }
    }

    /**
     * Method gets details about car
     * @param license
     * @return
     */
    @GET
    @Path("getCar/{license}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("license") String license) {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("license_plate",license);
            String result = database.getDocumentFromCollection(database.getCarsCollection(),map);
            return Response.ok(result).build();
        } catch (MongoException ex) {
            return Response.status(404).build();
        } finally {
            database.closeConnection();
        }
    }

    @Path("deletion/driver/{license}")
    @DELETE
    public Response deleteDriverAccount(@PathParam("license") String license) {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("license_number",license);
            database.deleteDocumentFromCollection(database.getDriverCollection(),map);
            return Response.ok().build();
        } catch (MongoException ex) {
            return Response.status(404).build();
        } finally {
            database.closeConnection();
        }
    }

    @Path("deletion/car/{license}")
    @DELETE
    public Response deleteCarAccount(@PathParam("license") String license) {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("license_plate",license);
            database.deleteDocumentFromCollection(database.getCarsCollection(),map);
            return Response.ok().build();
        } catch (MongoException ex) {
            return Response.status(404).build();
        } finally {
            database.closeConnection();
        }
    }

    @Path("payments/{license}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentsOverview(@PathParam("license") String license) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("license_number", license);
            String result = database.getDocumentsFromCollection(database.getFaresCollection(), map);
            return Response.ok(result).build();
        } catch (MongoException ex) {
            return Response.status(404).build();
        } finally {
            database.closeConnection();
        }
    }

    // Ask Not Done
//    @Path("payments/price_km/{price_km}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getFareBasedPerKilometer(@PathParam("price_km") double price_km) {
//        try {
//            Map<String, Object> document = new HashMap<>();
//            document.put("distance_km", price_km);
//
//            String json = database.getDocumentsFromCollection(database.getFaresCollection(), document);
//            return Response.ok(json).build();
//        } catch (MongoException ex) {
//            return Response.status(404).build();
//        } finally {
//            database.closeConnection();
//        }
//    }

    @Path("payments/date/{date}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFareBasedDate(@PathParam("date") String dateTime) {
        try {
            String[] array = dateTime.split("\\.");
            DateTime dateTimeObj = new DateTime(array[0], array[1], array[2]);
            String result = database.getDocumentsBasedOnDate(dateTimeObj);
            return Response.ok(result).build();
        } catch (MongoException ex) {
            return Response.status(404).build();
        } finally {
            database.closeConnection();
        }
    }
}
