package com.thumann.server.domain.stock;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thumann.server.domain.Domain;
import com.thumann.server.helper.string.StringUtil;

@Entity
public class StockDetail extends Domain
{
    private static final long serialVersionUID = 6047438750266724315L;

    @Temporal( TemporalType.DATE )
    private Date              expirationDate;

    private String            lot;

    public static boolean match( StockDetail detail1, StockDetail detail2 )
    {
        long expirationDate1 = detail1.getExpirationDate() != null ? detail1.getExpirationDate().getTime() : 0;
        long expirationDate2 = detail2.getExpirationDate() != null ? detail2.getExpirationDate().getTime() : 0;
        if ( expirationDate1 != expirationDate2 ) {
            return false;
        }

        if ( StringUtil.isDifferent( detail1.getLot(), detail2.getLot() ) ) {
            return false;
        }
        return true;
    }

    public StockDetail copy()
    {
        StockDetail result = new StockDetail();
        result.setLot( getLot() );
        if ( this.expirationDate != null ) {
            result.setExpirationDate( new Date( this.expirationDate.getTime() ) );
        }
        return result;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate( Date expirationDate )
    {
        this.expirationDate = expirationDate;
    }

    public String getLot()
    {
        return lot;
    }

    public void setLot( String lot )
    {
        this.lot = lot;
    }

}
