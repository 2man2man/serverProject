package com.thumann.server.service.warehouse;

import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.web.controller.warehouse.area.WarehouseAreaCreateDTO;
import com.thumann.server.web.controller.warehouse.area.WarehouseAreaUpdateDTO;

public interface WarehouseService
{

    Warehouse initNewWarehouse();

    void createMainWarehouse();

    Warehouse getByNumber( String number );

    Warehouse update( WarehouseUpdateDto dto );

    WarehouseArea update( WarehouseAreaUpdateDTO dto );

    WarehouseArea create( WarehouseAreaCreateDTO dto );

}
