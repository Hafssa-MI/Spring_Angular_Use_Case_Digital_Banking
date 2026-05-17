package hafssamiftahidrissi.example.mcpserver.tools;

import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class McpTools {
    @McpTool(name = "getEmployee", description="Get information qbout a given employee ")
    public Employee getEmployee(@McpArg(description = "The name of employee") String name){
        return new Employee(name,12300,4);
    }
    @McpTool(description="Get All Employees")
    public List<Employee> getAllEmployees(){
        return List.of(
                new Employee("Hassan",123000,4),
                new Employee("Mohamed",34000,1),
                new Employee ("Imane",23000,10)

        );
    }
}
record Employee(String name, double salary, int seniority){}

