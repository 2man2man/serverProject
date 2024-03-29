package com.thumann.server.web.controller.employee;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.web.controller.warehouse.WarehouseControllerFactory;
import com.thumann.server.web.controller.warehouse.WarehouseShortResponseDTO;
import com.thumann.server.web.response.APIResponseBuilderHelper;
import com.thumann.server.web.response.CreateJsonInterface;

public class EmployeeResponseDTO extends APIResponseBuilderHelper implements CreateJsonInterface
{
    private long                      id;

    private String                    firstName;

    private String                    lastName;

    private String                    userName;

    private String                    dateOfBirth;

    private boolean                   systemConfigurationPrivilege   = false;

    private boolean                   logisticConfigurationPrivilege = false;

    private List<String>              tenants                        = new ArrayList<>();

    private WarehouseShortResponseDTO warehouse;

    public void initValues( Employee employee, WarehouseControllerFactory warehouseFactory )
    {
        setId( employee.getId() );
        setFirstName( employee.getFirstName() );
        setLastName( employee.getLastName() );
        setUserName( employee.getCredentials().getUsername() );
        setSystemConfigurationPrivilege( employee.getPrivilege().isSystemConfiguration() );
        setLogisticConfigurationPrivilege( employee.getPrivilege().isLogisticConfiguration() );

        for ( Tenant tenant : employee.getTenants() ) {
            tenants.add( tenant.getNumber() );
        }
        this.warehouse = warehouseFactory.createShortResponseDTO( employee.getWarehouse() );
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "id", getId() );
        objectNode.put( "firstName", getFirstName() );
        objectNode.put( "lastName", getLastName() );
        objectNode.put( "userName", getUserName() );
        objectNode.put( "dateOfBirth", getDateOfBirth() );
        objectNode.put( "systemConfigurationPrivilege", isSystemConfigurationPrivilege() );
        objectNode.put( "logisticConfigurationPrivilege", isLogisticConfigurationPrivilege() );

        addValue( objectNode, "warehouse", this.warehouse );

        JsonUtil.putStringArray( objectNode, "tenants", tenants );

        return objectNode;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( String dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public boolean isSystemConfigurationPrivilege()
    {
        return systemConfigurationPrivilege;
    }

    public void setSystemConfigurationPrivilege( boolean systemConfigurationPrivilege )
    {
        this.systemConfigurationPrivilege = systemConfigurationPrivilege;
    }

    public boolean isLogisticConfigurationPrivilege()
    {
        return logisticConfigurationPrivilege;
    }

    public void setLogisticConfigurationPrivilege( boolean logisticConfigurationPrivilege )
    {
        this.logisticConfigurationPrivilege = logisticConfigurationPrivilege;
    }

}
