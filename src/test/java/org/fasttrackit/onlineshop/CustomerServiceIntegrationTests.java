package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.exeption.ResourceNotFoundExeption;
import org.fasttrackit.onlineshop.service.CustomerService;
import org.fasttrackit.onlineshop.steps.CustomerSteps;
import org.fasttrackit.onlineshop.transfer.SaveCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerSteps customerSteps;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_whenValidRequest_thenCustomerIsSaved() {
        customerSteps.createCustomer();
    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateCustomer_whenInvalidRequest_thenThrowException() {
        SaveCustomerRequest request = new SaveCustomerRequest();
//		leaving request properties with default null values
//		to validate the negative flow
        customerService.createCustomer(request);
    }

    @Test
    public void testGetCustomer_whenExistingCustomer_thenReturnCustomer() {
        Customer createdCustomer = customerSteps.createCustomer();
        Customer retrieve = customerService.getCustomer(createdCustomer.getId());

        assertThat(retrieve, notNullValue());
        assertThat(retrieve.getId(), is(createdCustomer.getId()));
        assertThat(retrieve.getFirstName(), is(createdCustomer.getFirstName()));
        assertThat(retrieve.getLastName(), is(createdCustomer.getLastName()));
    }

    @Test(expected = ResourceNotFoundExeption.class)
    public void testGetCustomer_whenNonExistingCustomer_thenThrowResourcesNotFoundException() {
        customerService.getCustomer(999999999);
    }

    @Test
    public void testUpdateCustomer_whenValidRequest_thenReturnUpdatedCustomer() {
        Customer createdCustomer = customerSteps.createCustomer();

        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName(createdCustomer.getFirstName() + " updated ");
        request.setLastName(createdCustomer.getLastName() + " updated ");

        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), request);

        assertThat(updatedCustomer, notNullValue());
        assertThat(updatedCustomer.getId(), is(createdCustomer.getId()));
        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));
    }

    @Test(expected = ResourceNotFoundExeption.class)
    public void testDeleteCustomer_whenExistingCustomer_thenCustomerIsDeleted() {
        Customer customer = customerSteps.createCustomer();
        customerService.deleteCustomer(customer.getId());
        customerService.getCustomer(customer.getId());
    }
}