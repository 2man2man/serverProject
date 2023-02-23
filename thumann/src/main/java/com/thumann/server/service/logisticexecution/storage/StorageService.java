package com.thumann.server.service.logisticexecution.storage;

import com.thumann.server.domain.logisticexecution.storage.StorageExecution;
import com.thumann.server.domain.logisticexecution.storage.StorageExecutionPosition;
import com.thumann.server.service.logisticexecution.storage.dto.StorageExecutionAddPositionDto;
import com.thumann.server.service.logisticexecution.storage.dto.StorageExecutionCreateDto;

public interface StorageService
{
    StorageExecution createExecution( StorageExecutionCreateDto dto );

    StorageExecutionPosition addPosition( StorageExecutionAddPositionDto dto );

}
