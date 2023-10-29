package io.github.wesleyosantos91.service;

import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.request.CustomerRequest;
import io.github.wesleyosantos91.domain.response.CustomerResponse;
import io.github.wesleyosantos91.exception.core.DatabaseException;
import io.github.wesleyosantos91.exception.core.ObjectNotFoundException;
import io.github.wesleyosantos91.mapper.CustomerMapper;
import io.github.wesleyosantos91.metric.annotations.CountExecution;
import io.github.wesleyosantos91.repository.CustomerRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @CountExecution(successCounter = "customers_saved_success",
                    successTags = {"action:save", "entity:customer"},
                    errorCounter = "customers_not_saved_for_error",
                    errorTags = {"action:save", "entity:customer"})
    @Transactional
    public CustomerResponse save(CustomerRequest request) {
        try {
            log.info("Saving customer with request: {}", request);
            CustomerEntity entity = mapper.parseEntity(request);
            entity = this.repository.save(entity);
            return mapper.parseResponse(entity);
        } catch (DataIntegrityViolationException e) {
            log.error("error: {}", ExceptionUtils.getRootCauseMessage(e));
            throw new DatabaseException(ExceptionUtils.getRootCause(e).getMessage());
        }
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
