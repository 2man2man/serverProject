package com.thumann.server.web.controller.logisticexecution.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.logisticexecution.storage.StorageExecution;
import com.thumann.server.web.controller.warehouse.WarehouseControllerFactory;
import com.thumann.server.web.controller.warehouse.WarehouseShortResponseDTO;
import com.thumann.server.web.response.APIResponseBuilderHelper;
import com.thumann.server.web.response.CreateJsonInterface;

public class StorageExecutionResponseDTO extends APIResponseBuilderHelper implements CreateJsonInterface
{
    private long                      id;

    private WarehouseShortResponseDTO warehouseShort;

    public void initValues( StorageExecution domain, WarehouseControllerFactory warehouseFactory )
    {
        setId( domain.getId() );
        if ( isFieldincluded( "warehouse" ) ) {
            setWarehouseShort( warehouseFactory.createShortResponseDTO( domain.getWarehouse() ) );
        }
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        addValue( objectNode, "id", getId() );
        addValue( objectNode, "warehouse", getWarehouseShort() );
        return objectNode;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public WarehouseShortResponseDTO getWarehouseShort()
    {
        return warehouseShort;
    }

    public void setWarehouseShort( WarehouseShortResponseDTO warehouseShort )
    {
        this.warehouseShort = warehouseShort;
    }

}
