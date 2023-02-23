package com.thumann.server.web.controller.fixedlocation.helper.print;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.helper.io.IOUtill;
import com.thumann.server.service.fixedlocation.FixedLocationService;

public class FixedLocationStandardPrinter implements IFixedLocationPrinter
{
    private Document              document;

    private ByteArrayOutputStream out;

    private FixedLocationService  fixedLocationService;

    private PdfContentByte        pdfContentByte;

    public FixedLocationStandardPrinter( FixedLocationService fixedLocationService )
    {
        this.fixedLocationService = fixedLocationService;
    }

    @Override
    public byte[] print( List<Long> locationsToPrint )
    {
        try {
            init();

            for ( Long locationId : locationsToPrint ) {
                newPage();
                print( locationId );
            }
            this.document.close();
            return out.toByteArray();
        }
        catch ( DocumentException e ) {
            throw new IllegalStateException( e.getMessage() );
        }
        finally {
            close();
        }
    }

    private void print( long locationId ) throws DocumentException
    {
        FixedLocation fixedLocation = loadLocation( locationId );

        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode( FixedLocation.BARCODE_PREFIX + fixedLocation.getBarcode() );
        barcode128.setAltText( " " );
        Image barcodeImage = barcode128.createImageWithBarcode( pdfContentByte, null, null );
        barcodeImage.setLeft( 10 );
        barcodeImage.setBottom( 10 );
        barcodeImage.setRight( document.getPageSize().getRight() - 10 );
        barcodeImage.setTop( document.getPageSize().getHeight() - 10 );
        document.add( barcodeImage );

        add( fixedLocation.getNumber(), 0, 0, document.getPageSize().getRight(), document.getPageSize().getHeight() );

    }

    private FixedLocation loadLocation( long locationId )
    {
        return fixedLocationService.load( locationId );
    }

    private void close()
    {
        IOUtill.safeClose( out );
    }

    private void init() throws DocumentException
    {
        this.document = new Document( PageSize.A7.rotate() );

        out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance( document, out );
        document.open();
        this.pdfContentByte = writer.getDirectContent();
    }

    private boolean firstTimeCalled = true;

    private void newPage()
    {
        if ( firstTimeCalled ) {
            firstTimeCalled = false;
            return;
        }
        document.newPage();
    }

    private void add( String text, float left, float bottom, float right, float top ) throws DocumentException
    {
        ColumnText ct = new ColumnText( pdfContentByte );
        ct.setAlignment( Element.ALIGN_CENTER );
        ct.setSimpleColumn( left, bottom, right, top );
        ct.setText( new Phrase( text ) );
        ct.go();
    }

}
