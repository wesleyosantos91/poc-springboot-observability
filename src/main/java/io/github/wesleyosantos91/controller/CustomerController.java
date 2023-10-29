package io.github.wesleyosantos91.controller;

import io.github.wesleyosantos91.domain.request.CustomerRequest;
import io.github.wesleyosantos91.domain.response.CustomerResponse;
import io.github.wesleyosantos91.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest request) throws Exception {
        log.info("Creating customer with request: {}", request);
        var response = service.save(request);
        log.info("Customer created with response: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> findByCode(@PathVariable Long id) {
        log.info("Finding customer with ID: {}", id);
        var response = service.findById(id);
        log.info("Customer found with response: {}", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>>findAllPage(
            @PageableDefault(sort = "id",
                    direction = Sort.Direction.ASC,
                    page = 0,
                    size = 10) Pageable page) {

        log.info("Finding all customers with page: {}", page);
        var pages = service.findAllPage(page);
        log.info("Customers found with pages: {}", pages);

        return ResponseEntity.ok().body(pages);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerRequest request) throws Exception {
        log.info("Updating customer with ID: {} and request: {}", id, request);
        var response = service.update(id, request);
        log.info("Customer updated with response: {}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting customer with ID: {}", id);
        service.delete(id);
        log.info("Customer deleted");

        return ResponseEntity.noContent().build();
    }
}
