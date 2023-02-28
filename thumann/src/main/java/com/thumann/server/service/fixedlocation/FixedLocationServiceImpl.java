package com.thumann.server.service.fixedlocation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeCreateDTO;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeUpdateDTO;

@Service( "fixedLocationService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class FixedLocationServiceImpl implements FixedLocationService
{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BaseService   baseService;

    public FixedLocationServiceImpl()
    {
    }

    @Override
    public FixedLocation load( long id )
    {
        return load( id, new HashSet<>() );
    }

    @Override
    public FixedLocation load( long id, Set<String> eager )
    {
        return baseService.getById( id, FixedLocation.class, eager );
    }

    @Override
    public FixedLocation unArchive( FixedLocation fixedLocation )
    {
        fixedLocation = entityManager.find( FixedLocation.class, fixedLocation.getId() );
        fixedLocation.setArchived( false );
        return entityManager.merge( fixedLocation );
    }

    @Override
    public void deleteOrArchive( FixedLocation fixedLocation )
    {
        // TODO: handle stocks on location

        fixedLocation = entityManager.find( FixedLocation.class, fixedLocation.getId() );
        if ( canDelete( fixedLocation ) ) {
            entityManager.remove( fixedLocation );
        }
        else {
            fixedLocation.setArchived( true );
            entityManager.merge( fixedLocation );
        }
    }

    // TODO check actions on this location
    private boolean canDelete( FixedLocation fixedLocation )
    {
        return true;
    }

    @Override
    public FixedLocation create( FixedLocationCreateDto dto )
    {
        FixedLocation existingLocation = getLocation( dto.getRow(), dto.getColumn(), dto.getLevel(), dto.getFragment() );
        if ( existingLocation != null ) {
            throw new IllegalArgumentException( "There already is a location for the given input!" );
        }

        FixedLocation fixedLocation = new FixedLocation();

        fixedLocation.setRow( dto.getRow() );
        fixedLocation.setColumn( dto.getColumn() );
        fixedLocation.setLevel( dto.getLevel() );
        fixedLocation.setFragment( dto.getFragment() );
        fixedLocation.initNumberAndBarcode();

        WarehouseArea area = entityManager.find( WarehouseArea.class, dto.getWarehouseAreaId() );
        fixedLocation.setWarehouseArea( area );
        fixedLocation.setWarehouse( area.getWarehouse() );

        if ( dto.getLocationTypeId() != Domain.UNKOWN_ID ) {
            fixedLocation.setLocationType( entityManager.find( FixedLocationType.class, dto.getLocationTypeId() ) );
        }

        return entityManager.merge( fixedLocation );
    }

    @Override
    public FixedLocation update( FixedLocationUpdateDto dto )
    {
        FixedLocation existingLocation = entityManager.find( FixedLocation.class, dto.getFixedLocationId() );
        if ( existingLocation == null ) {
            throw new IllegalArgumentException( "No location found for id [" + dto.getFixedLocationId() + "]!" );
        }

        if ( dto.getWarehouseAreaId() != Domain.UNKOWN_ID ) {
            WarehouseArea area = entityManager.find( WarehouseArea.class, dto.getWarehouseAreaId() );
            existingLocation.setWarehouseArea( area );
            existingLocation.setWarehouse( area.getWarehouse() );
        }

        if ( dto.getLocationTypeId() != Domain.UNKOWN_ID ) {
            existingLocation.setLocationType( entityManager.find( FixedLocationType.class, dto.getLocationTypeId() ) );
        }

        return entityManager.merge( existingLocation );
    }

    @Override
    public FixedLocation getLocation( String row, String column, String level, String fragment )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT domain " )
          .append( " FROM FixedLocation domain " )
          .append( " WHERE domain.rrow = :row " )
          .append( " AND domain.ccolumn = :column " )
          .append( " AND domain.level = :level " );
        if ( !StringUtil.isEmpty( fragment ) ) {
            sb.append( " AND domain.fragment = :fragment " );
        }
        else {
            sb.append( " AND domain.fragment IS NULL " );
        }

        TypedQuery<FixedLocation> query = entityManager.createQuery( sb.toString(), FixedLocation.class )
                                                       .setParameter( "row", row )
                                                       .setParameter( "column", column )
                                                       .setParameter( "level", level );
        if ( !StringUtil.isEmpty( fragment ) ) {
            query.setParameter( "fragment", fragment );
        }

        List<FixedLocation> result = query.getResultList();
        if ( result.isEmpty() ) {
            return null;
        }
        else if ( result.size() == 1 ) {
            return result.get( 0 );
        }
        else {
            throw new IllegalStateException( "Multiple location for " + row + "-" + column + "-" + level + "-" + fragment + " found!" );
        }
    }

    @Override
    public FixedLocationType create( FixedLocationTypeCreateDTO createDto )
    {
        FixedLocationType existing = new FixedLocationType();
        existing.setNumber( createDto.getNumber() );
        existing.setName( createDto.getName() );
        return entityManager.merge( existing );
    }

    @Override
    public FixedLocationType update( FixedLocationTypeUpdateDTO updateDto )
    {
        FixedLocationType existing = entityManager.find( FixedLocationType.class, updateDto.getLocationTypeId() );
        if ( !StringUtil.isEmpty( updateDto.getNumber() ) ) {
            existing.setNumber( updateDto.getNumber() );
        }
        if ( !StringUtil.isEmpty( updateDto.getName() ) ) {
            existing.setName( updateDto.getName() );
        }
        return entityManager.merge( existing );
    }

    @Override
    public FixedLocationType getLocationTypeByNumber( String number )
    {
        if ( StringUtil.isEmpty( number ) ) {
            throw new IllegalArgumentException( "Given number cannot be emtpy" );
        }

        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT domain " )
          .append( "   FROM FixedLocationType domain " )
          .append( "  WHERE domain.number = :number " );

        List<FixedLocationType> domains = entityManager.createQuery( sb.toString(), FixedLocationType.class )
                                                       .setParameter( "number", number )
                                                       .getResultList();

        if ( domains.isEmpty() ) {
            return null;
        }
        else if ( domains.size() == 1 ) {
            return domains.get( 0 );
        }
        else {
            throw new IllegalStateException( "There are multiple FixedLocationTypes with number [" + number + "]" );
        }
    }

}