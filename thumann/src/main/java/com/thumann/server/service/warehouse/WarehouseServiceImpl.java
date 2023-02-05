package com.thumann.server.service.warehouse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.controller.warehouse.area.WarehouseAreaCreateDTO;
import com.thumann.server.web.controller.warehouse.area.WarehouseAreaUpdateDTO;

@Service( "warehouseService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class WarehouseServiceImpl implements WarehouseService
{
    @PersistenceContext
    private EntityManager entityManager;

    public WarehouseServiceImpl()
    {
    }

    @Override
    public Warehouse update( WarehouseUpdateDto dto )
    {
        Warehouse warehouse = entityManager.find( Warehouse.class, dto.getWarehouseId() );
        if ( warehouse == null ) {
            throw new IllegalArgumentException( "Warehouse with id [" + dto.getWarehouseId() + "] cant be found!" );
        }
        if ( !StringUtil.isEmpty( dto.getName() ) ) {
            warehouse.setName( dto.getName() );
        }
        final String number = dto.getNumber();
        if ( !StringUtil.isEmpty( number ) ) {
            Warehouse byNumber = getByNumber( number );
            if ( byNumber != null && byNumber.getId() != warehouse.getId() ) {
                throw new IllegalArgumentException( "There already is a warehouse with number [" + number + "]. This existing warehouse has id [" + byNumber.getId() + "]" );
            }
            warehouse.setNumber( number );
        }

        return entityManager.merge( warehouse );
    }

    @Override
    public Warehouse initNewWarehouse()
    {
        Warehouse warehouse = new Warehouse();
        warehouse.setNumber( getNextWarehouseNumber() );
        warehouse.setName( "New" );
        return entityManager.merge( warehouse );
    }

    private String getNextWarehouseNumber()
    {
        Set<String> existingNumbers = new HashSet<>();
        getAll().forEach( ( Warehouse warehouse ) -> existingNumbers.add( warehouse.getNumber() ) );

        int count = 1;
        String whNumber = "WH" + count;
        while ( existingNumbers.contains( whNumber ) ) {
            count = count + 1;
            whNumber = "WH" + count;
        }
        return whNumber;
    }

    /**
     * called on start up initializer to create a warehouse if there is none.
     */
    @Override
    public void createMainWarehouse()
    {
        if ( !getAll().isEmpty() ) {
            return;
        }
        initNewWarehouse();
    }

    private List<Warehouse> getAll()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT domain " )
          .append( "   FROM Warehouse domain " )
          .append( " ORDER BY domain.id asc" );

        return entityManager.createQuery( sb.toString(), Warehouse.class ).getResultList();
    }

    @Override
    public Warehouse getByNumber( String number )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT domain " )
          .append( "   FROM Warehouse domain " )
          .append( "  WHERE domain.number = :number " );

        List<Warehouse> result = entityManager.createQuery( sb.toString(), Warehouse.class )
                                              .setParameter( "number", number )
                                              .getResultList();
        if ( result.isEmpty() ) {
            return null;
        }
        else if ( result.size() == 1 ) {
            return result.get( 0 );
        }
        else {
            throw new IllegalStateException( "There are multiple warehouses with number [" + number + "]. This cannot happen!" );
        }
    }

    @Override
    public WarehouseArea create( WarehouseAreaCreateDTO dto )
    {
        WarehouseArea warehouseArea = new WarehouseArea();

        warehouseArea.setNumber( dto.getNumber() );
        warehouseArea.setName( dto.getName() );
        warehouseArea.setWarehouse( entityManager.find( Warehouse.class, dto.getWarehouseId() ) );

        return entityManager.merge( warehouseArea );
    }

    @Override
    public WarehouseArea update( WarehouseAreaUpdateDTO dto )
    {
        WarehouseArea warehouseArea = entityManager.find( WarehouseArea.class, dto.getWarehouseAreaId() );

        if ( !StringUtil.isEmpty( dto.getNumber() ) ) {
            warehouseArea.setNumber( dto.getNumber() );
        }
        if ( !StringUtil.isEmpty( dto.getName() ) ) {
            warehouseArea.setName( dto.getName() );
        }

        return entityManager.merge( warehouseArea );
    }

}