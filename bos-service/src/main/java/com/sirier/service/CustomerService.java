package com.sirier.service;


import com.sirier.domain.Customer;

import java.util.List;

//@Produces("*/*")
public interface CustomerService {

    /**
     * 查询所有未与定区关联的customer
     * @return
     */
    // @GET
    // @Path("/customer")
    // @Produces({ "application/xml", "application/json" })
    List<Customer> getListNotAssociation();

    /**
     * 查询所有的已于定区管理的customer
     * @param decidezoneId
     * @return
     */
    // @GET
    // @Path("/customer/{decidezoneId}")
    // @Consumes({ "application/xml", "application/json" })
    // @Produces({ "application/xml", "application/json" })
    // List<Customer> getListHasAssociation(@PathParam("decidezoneId") String decidezoneId);
    List<Customer> getListHasAssociation(String decidezoneId);

    /**
     * 关联customer到定区,包含两个逻辑
     * @param customerIds
     * @param decidezoneId
     */
    // @PUT
    // @Path("/customer/{decidezoneId}/{cIds}")
    // @Consumes({ "application/xml", "application/json" })
    // public void assignedCustomerToDecidedzone(@PathParam("decidezoneId") String decidezoneId,
    //                                           @PathParam("cIds") String customerIds);
    public void assignedCustomerToDecidedzone(String decidezoneId, String customerIds);


    Customer getCustomerByAddress(String allAddress);

}
