package io.github.wesleyosantos91.service;

import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.request.CustomerRequest;
import io.github.wesleyosantos91.domain.response.CustomerResponse;
import io.github.wesleyosantos91.exception.core.ObjectNotFoundException;
import io.github.wesleyosantos91.mapper.CustomerMapper;
import io.github.wesleyosantos91.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public Page<CustomerResponse> findAllPage(Pageable pageable) {
        log.info("Finding all customers with page: {}", pageable);
        final Page<CustomerEntity> pages = repository.findAll(pageable);

        return mapper.parserToPageResponse(pages);
    }

    public CustomerResponse findById(Long id){
        log.info("Finding customer with ID: {}", id);
        return mapper.parseResponse(exist(id));
    }

    @Transactional
    public CustomerResponse save(CustomerRequest request) {
        log.info("Saving customer with request: {}", request);
        CustomerEntity entity = mapper.parseEntity(request);
        entity = this.repository.save(entity);
        return mapper.parseResponse(entity);
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        log.info("Updating customer with ID: {} and request: {}", id, request);
        CustomerEntity customer = exist(id);
        BeanUtils.copyProperties(request, customer, "id");
        customer = this.repository.save(customer);
        return mapper.parseResponse(customer);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting customer with ID: {}", id);
        CustomerEntity customer = exist(id);
        repository.delete(customer);
    }

    private CustomerEntity exist(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format("Not found Customer with code {0}", id)));
    }
}
