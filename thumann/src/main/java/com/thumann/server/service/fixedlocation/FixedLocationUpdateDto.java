package com.thumann.server.service.fixedlocation;

public class FixedLocationUpdateDto extends FixedLocationCreateUpdateDto
{
    private long fixedLocationId;

    public long getFixedLocationId()
    {
        return fixedLocationId;
    }

    public void setFixedLocationId( long fixedLocationId )
    {
        this.fixedLocationId = fixedLocationId;
    }

}
