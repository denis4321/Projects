/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sample.server.caller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.restasyncclient.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import ui.sample.Employee;

/**
 *
 * @author Администратор
 */
public class ServerCaller {

    public List<Employee> getRemoteData(String url) throws Exception {
        ClientBuilder.newClient();
        WebTarget webTarget = ClientBuilder.newClient().target(url);
        final AsyncInvoker asyncInvoker = webTarget.request().async();
        Future<String> response = asyncInvoker.get(String.class);
        while (response.isDone()) {
        }
        return transforme(response.get());
    }

    private List<Employee> transforme(String responseData) {
        //{"Employees":[{"name":"value","age":"21","department":"value"},{"name":"value","age":"22","department":"value"}]}
        List<Employee> list = new ArrayList();
        JsonElement root = new JsonParser().parse(responseData);
        JsonObject obj = root.getAsJsonObject();
        JsonElement elem = obj.get("Employees");
        JsonArray array = elem.getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonElement item = array.get(i);
            JsonObject jo = item.getAsJsonObject();
            String name = jo.get("name").getAsString();
            String age = jo.get("age").getAsString();
            String department = jo.get("department").getAsString();
            Employee emp = new Employee(name, Integer.parseInt(age), department);
            list.add(emp);
        }
        return list;
    }

}
