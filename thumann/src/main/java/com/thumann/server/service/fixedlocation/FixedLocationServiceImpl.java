package com.thumann.server.service.fixedlocation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeCreateDTO;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeUpdateDTO;

@Service( "fixedLocationService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class FixedLocationServiceImpl implements FixedLocationService
{
    @PersistenceContext
    private EntityManager entityManager;

    public FixedLocationServiceImpl()
    {
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