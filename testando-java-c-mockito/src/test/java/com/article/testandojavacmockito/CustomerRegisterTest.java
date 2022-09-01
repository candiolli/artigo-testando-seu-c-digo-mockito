package com.article.testandojavacmockito;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRegisterTest {

    @Spy
    @InjectMocks
    private CustomerRegister customerRegister;

    @Mock
    private CustomerRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_success() throws InvalidValueException {
        Customer objectSaved = new Customer("83607056072", "Silas", 123456L);

        when(customerRegister.validateRealCpf(anyString())).thenReturn(true);
        when(repository.save(any(Customer.class))).thenReturn(objectSaved);

        CustomerVO vo = new CustomerVO("83607056072", "Silas", 123456L);

        CustomerVO register = customerRegister.register(vo);
        assertEquals(objectSaved.getId(), register.getId());
        verify(customerRegister).register(vo);
    }

    @Test
    public void register_success_verify() throws InvalidValueException {
        CustomerRegister customerRegister2 = mock(CustomerRegister.class);

        when(customerRegister2.validateRealCpf(anyString())).thenCallRealMethod();

        CustomerVO vo = new CustomerVO("33408777004", "Silas", 123456L);

        customerRegister2.register(vo);

        verify(customerRegister2).register(vo);
        verifyNoMoreInteractions(customerRegister2);
    }
}