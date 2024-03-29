package com.thumann.server.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.thumann.server.domain.Domain;

@Entity
public class UserPrivilege extends Domain implements Serializable
{
    private static final long serialVersionUID      = 1456182454791795173L;

    @OneToOne( fetch = FetchType.LAZY, mappedBy = "credentials" )
    private Employee          employee;

    @Column( columnDefinition = "BOOLEAN DEFAULT FALSE" )
    private boolean           systemConfiguration   = false;

    @Column( columnDefinition = "BOOLEAN DEFAULT FALSE" )
    private boolean           logisticConfiguration = false;

    public boolean isSystemConfiguration()
    {
        return systemConfiguration;
    }

    public void setSystemConfiguration( boolean systemConfiguration )
    {
        this.systemConfiguration = systemConfiguration;
    }

    public boolean isLogisticConfiguration()
    {
        return logisticConfiguration;
    }

    public void setLogisticConfiguration( boolean logisticConfiguration )
    {
        this.logisticConfiguration = logisticConfiguration;
    }

}