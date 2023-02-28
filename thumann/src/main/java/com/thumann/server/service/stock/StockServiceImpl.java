package com.thumann.server.service.stock;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.logisticexecution.LogisticExecutionType;
import com.thumann.server.domain.stock.StockDetail;
import com.thumann.server.domain.stock.fixedlocation.StockFixedLocation;
import com.thumann.server.domain.stock.fixedlocation.StockFixedLocationCreateAction;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.user.Person;
import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.stock.dto.StockFixedLocationCreateDto;
import com.thumann.server.service.stock.dto.StockFixedLocationCreateResultDto;

@Service( "stockService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class StockServiceImpl implements StockService
{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BaseService   baseService;

    @Override
    public StockFixedLocationCreateResultDto create( StockFixedLocationCreateDto createDto )
    {
        Article managedArticle = entityManager.find( Article.class, createDto.getArticle().getId() );
        FixedLocation managedLocation = entityManager.find( FixedLocation.class, createDto.getFixedLocation().getId() );

        baseService.checkTenantAndThrow( managedArticle );

        Person caller = baseService.getCaller();

        StockDetail stockDetail = new StockDetail();
        stockDetail.setLot( createDto.getLot() );
        stockDetail.setExpirationDate( createDto.getExpirationDate() );

        StockFixedLocation stock = findStock( managedLocation, managedArticle, stockDetail );
        if ( stock == null ) {
            stock = new StockFixedLocation();
            stock.initCreation( (Employee) caller );
            stock.setArticle( managedArticle );
            stock.setFixedLocation( managedLocation );
            stock.setStockDetail( stockDetail.copy() );
            stock.setTenant( managedArticle.getTenant() );
        }
        stock.setQuantity( stock.getQuantity() + createDto.getQuantity() );
        stock = entityManager.merge( stock );

        StockFixedLocationCreateAction createAction = new StockFixedLocationCreateAction();
        createAction.initCreator( (Employee) caller );
        createAction.setArticle( managedArticle );
        createAction.setFixedLocation( managedLocation );
        createAction.setLogisticExecutionType( LogisticExecutionType.STORAGE );
        createAction.setQuantity( createDto.getQuantity() );
        createAction = entityManager.merge( createAction );

        return new StockFixedLocationCreateResultDto( stock, createAction );
    }

    public StockFixedLocation findStock( FixedLocation fixedLocation, Article article, StockDetail detail )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT domain " )
          .append( "   FROM StockFixedLocation domain " )
          .append( "  WHERE domain.fixedLocation.id = :fixedLocationId " )
          .append( "    AND domain.article.id = :articleId " );

        List<StockFixedLocation> stocks = entityManager.createQuery( sb.toString(), StockFixedLocation.class )
                                                       .setParameter( "fixedLocationId", fixedLocation.getId() )
                                                       .setParameter( "articleId", article.getId() )
                                                       .getResultList();

        for ( StockFixedLocation stock : stocks ) {
            if ( !StockDetail.match( stock.getStockDetail(), detail ) ) {
                continue;
            }
            return stock;
        }

        return null;
    }

}