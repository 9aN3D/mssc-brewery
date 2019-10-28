package guru.springframework.msscbrewery.service;

import guru.springframework.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomer(UUID id);

    CustomerDto save(CustomerDto customer);

    void update(UUID id, CustomerDto customer);

    void delete(UUID id);

}
