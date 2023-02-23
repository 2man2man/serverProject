package com.thumann.server.web.controller.fixedlocation.helper.print;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.web.controller.fixedlocation.FixedLocationBatchActionDTO;
import com.thumann.server.web.controller.fixedlocation.helper.FixedLocationBatchActionAbstractHelper;
import com.thumann.server.web.response.CreateJsonInterface;
import com.thumann.server.web.response.pdf.PdfResponseSimple;

public class FixedLocationBatchActionPrintHelper extends FixedLocationBatchActionAbstractHelper
{
    private String pdfString;

    public FixedLocationBatchActionPrintHelper( FixedLocationBatchActionDTO dto, FixedLocationService service )
    {
        super( dto, service );
        initRanges();
    }

    @Override
    public CreateJsonInterface getResponse()
    {
        PdfResponseSimple result = new PdfResponseSimple();
        result.setBase64String( pdfString );
        return result;
    }

    @Override
    public void execute()
    {
        List<String> fragments = new ArrayList<>( this.fragments );
        if ( fragments.isEmpty() ) {
            fragments.add( null );
        }
        List<Long> locationsToPrint = new ArrayList<>();

        for ( String column : columns ) {
            for ( String row : rows ) {
                for ( String level : levels ) {
                    for ( String fragment : fragments ) {
                        FixedLocation location = fixedLocationService.getLocation( row, column, level, fragment );
                        if ( location == null ) {
                            continue;
                        }
                        locationsToPrint.add( location.getId() );
                    }
                }
            }
        }

        IFixedLocationPrinter printer = getPrinter();
        byte[] result = printer.print( locationsToPrint );
        this.pdfString = Base64.getEncoder().encodeToString( result );
    }

    private IFixedLocationPrinter getPrinter()
    {
        return new FixedLocationStandardPrinter( fixedLocationService );
    }

}
