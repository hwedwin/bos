package com.sirier.crm.service;

import com.sirier.crm.domain.Customer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Produces("*/*")
public interface CustomerService {

    /**
     * 查询所有未与定区关联的customer
     * @return
     */
    @GET
    @Path("/customer/getListNotAssociation")
    @Produces({ "application/xml", "application/json" })
    List<Customer> getListNotAssociation();

    /**
     * 查询所有的已于定区管理的customer
     * @param decidezoneId
     * @return
     */
    @GET
    @Path("/customer/getListHasAssociation/{decidezoneId}")
    @Consumes({ "application/xml", "application/json" })
    @Produces({ "application/xml", "application/json" })
    List<Customer> getListHasAssociation(@PathParam("decidezoneId") String decidezoneId);

    /**
     * 关联customer到定区,包含两个逻辑
     * @param customerIds
     * @param decidezoneId
     */
    @PUT
    @Path("/customer/assignedCustomerToDecidedzone/{decidezoneId}/{cIds}")
    @Consumes({ "application/xml", "application/json" })
    public void assignedCustomerToDecidedzone(@PathParam("decidezoneId") String decidezoneId,
                                              @PathParam("cIds") String customerIds);

    @GET
    @Path("/customer/getCustomerByAddress/{address}")
    @Consumes({ "application/xml", "application/json" })
    @Produces({ "application/xml", "application/json" })
    public List<Customer> getCustomerByAddress(@PathParam("address") String address);

}
