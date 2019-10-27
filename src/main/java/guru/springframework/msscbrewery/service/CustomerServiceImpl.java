package guru.springframework.msscbrewery.service;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getCustomer(UUID id) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Petro")
                .build();
    }

}
