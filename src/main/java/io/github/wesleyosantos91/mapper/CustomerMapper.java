package io.github.wesleyosantos91.mapper;

import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.request.CustomerRequest;
import io.github.wesleyosantos91.domain.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse parseResponse(CustomerEntity entity);
    CustomerEntity parseEntity(CustomerRequest request);

    default List<CustomerResponse> parserToListResponse(List<CustomerEntity> domains){
        List<CustomerResponse> list = new ArrayList<>();
        domains.forEach(d-> list.add(parseResponse(d)));
        return list;
    }

    default Page<CustomerResponse> parserToPageResponse(Page<CustomerEntity> pages){
        List<CustomerResponse> list = parserToListResponse(pages.getContent());
        return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());
    }
}
