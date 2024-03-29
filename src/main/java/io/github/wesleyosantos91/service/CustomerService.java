package io.github.wesleyosantos91.service;

import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.request.CustomerRequest;
import io.github.wesleyosantos91.domain.response.CustomerResponse;
import io.github.wesleyosantos91.exception.core.DatabaseException;
import io.github.wesleyosantos91.exception.core.ObjectNotFoundException;
import io.github.wesleyosantos91.mapper.CustomerMapper;
import io.github.wesleyosantos91.metric.annotation.CounterExecution;
import io.github.wesleyosantos91.metric.annotation.TimerExecution;
import io.github.wesleyosantos91.repository.CustomerRepository;
import io.micrometer.tracing.annotation.ContinueSpan;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
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

    @NewSpan
    public Page<CustomerResponse> findAllPage(Pageable pageable) {
        try {
            log.info("Finding all customers with page: {}", pageable);
            final Page<CustomerEntity> pages = repository.findAll(pageable);

            return mapper.parserToPageResponse(pages);
        } catch (DataIntegrityViolationException e) {
            log.error("error: {}", ExceptionUtils.getRootCauseMessage(e));
            throw new DatabaseException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }

    public CustomerResponse findById(Long id) {
        try {
            log.info("Finding customer with ID: {}", id);
            return mapper.parseResponse(exist(id));
        } catch (DataIntegrityViolationException e) {
            log.error("error: {}", ExceptionUtils.getRootCauseMessage(e));
            throw new DatabaseException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }

    @CounterExecution(name = "customers_counter_saved")
    @TimerExecution(name =   "customers_timer_saved")
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
        try {
            log.info("Updating customer with ID: {} and request: {}", id, request);
            CustomerEntity customer = exist(id);
            BeanUtils.copyProperties(request, customer, "id");
            customer = this.repository.save(customer);
            return mapper.parseResponse(customer);
        } catch (DataIntegrityViolationException e) {
            log.error("error: {}", ExceptionUtils.getRootCauseMessage(e));
            throw new DatabaseException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            log.info("Deleting customer with ID: {}", id);
            CustomerEntity customer = exist(id);
            repository.delete(customer);
        } catch (DataIntegrityViolationException e) {
            log.error("error: {}", ExceptionUtils.getRootCauseMessage(e));
            throw new DatabaseException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }

    private CustomerEntity exist(Long id) {
        try {
            return repository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format("Not found Customer with code {0}", id)));
        } catch (DataIntegrityViolationException e) {
            log.error("error: {}", ExceptionUtils.getRootCauseMessage(e));
            throw new DatabaseException(ExceptionUtils.getRootCause(e).getMessage());
        }
    }
}
