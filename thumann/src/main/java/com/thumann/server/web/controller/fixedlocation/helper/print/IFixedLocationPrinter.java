package com.thumann.server.web.controller.fixedlocation.helper.print;

import java.util.List;

public interface IFixedLocationPrinter
{

    byte[] print( List<Long> locationsToPrint );
}
