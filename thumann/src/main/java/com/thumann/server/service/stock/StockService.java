package com.thumann.server.service.stock;

import com.thumann.server.service.stock.dto.StockFixedLocationCreateDto;
import com.thumann.server.service.stock.dto.StockFixedLocationCreateResultDto;

public interface StockService
{

    StockFixedLocationCreateResultDto create( StockFixedLocationCreateDto createDto );

}
