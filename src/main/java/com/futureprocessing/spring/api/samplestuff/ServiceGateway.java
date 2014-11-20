package com.futureprocessing.spring.api.samplestuff;

import com.futureprocessing.spring.domain.DomainUser;
import com.futureprocessing.spring.domain.Stuff;

import java.util.List;

public interface ServiceGateway {
    List<Stuff> getSomeStuff();

    void createStuff(Stuff newStuff, DomainUser domainUser);
}
