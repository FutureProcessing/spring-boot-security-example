package com.futureprocessing.spring.infrastructure;

import com.futureprocessing.spring.domain.DomainUser;
import com.futureprocessing.spring.domain.Stuff;

import java.util.List;

public interface ServiceGateway {
    List<Stuff> getSomeStuff();

    void createStuff(Stuff newStuff, DomainUser domainUser);
}
