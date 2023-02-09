package com.thumann.server.service.fixedlocation;

public class FixedLocationCreateDto extends FixedLocationCreateUpdateDto
{
    private String row;

    private String column;

    private String level;

    private String fragment;

    public String getRow()
    {
        return row;
    }

    public void setRow( String row )
    {
        this.row = row;
    }

    public String getColumn()
    {
        return column;
    }

    public void setColumn( String column )
    {
        this.column = column;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel( String level )
    {
        this.level = level;
    }

    public String getFragment()
    {
        return fragment;
    }

    public void setFragment( String fragment )
    {
        this.fragment = fragment;
    }

}
