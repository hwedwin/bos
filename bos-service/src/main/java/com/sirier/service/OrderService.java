package com.sirier.service;

import com.sirier.domain.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Produces("*/*")
public interface OrderService {

    @POST
    @Path("/order/save")
    @Consumes({ "application/xml", "application/json" })
    public void Save(Order order);
}

