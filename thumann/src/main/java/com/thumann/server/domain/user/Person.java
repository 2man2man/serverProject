package com.thumann.server.domain.user;

import java.io.Serializable;

import com.thumann.server.domain.Domain;

public abstract class Person extends Domain implements Serializable
{
    private static final long serialVersionUID = -7318430226362976826L;

    public abstract String getName();
}
