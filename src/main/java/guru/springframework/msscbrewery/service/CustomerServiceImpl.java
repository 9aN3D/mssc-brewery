package guru.springframework.msscbrewery.service;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getCustomer(UUID id) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Petro")
                .build();
    }

    @Override
    public CustomerDto save(CustomerDto customer) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void update(UUID id, CustomerDto customer) {
        //TODO impl - would add real impl to update beer
    }

    @Override
    public void delete(UUID id) {
        log.trace("Deleting beer {id: {}}", id);
    }

}
