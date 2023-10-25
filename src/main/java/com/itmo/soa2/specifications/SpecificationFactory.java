package com.itmo.soa2.specifications;

import com.itmo.soa2.entities.SpaceMarine;
import com.itmo.soa2.filters.Filter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class SpecificationFactory {
//    private Specification<SpaceMarine> createSpecification(Filter filter){
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
//                root.get(filter.getField()),
//                castToType(root.get(filter.getField()).getJavaType(), filter.getValue()));
//    }
//
//    private Object castToType(Class type, String value){
//        if (type.isAssignableFrom(Double.class)) {
//            return Double.valueOf(value);
//        } else if (type.isAssignableFrom(Integer.class)) {
//            return Integer.valueOf(value);
//        } else if (Enum.class.isAssignableFrom(type)){
//            return Enum.valueOf(type, value);
//        }
//        return null;
//    }
//
//    public Specification<SpaceMarine> createSpecificationOfFilters(List<Filter> filters){
//        if (filters.size() == 0){
//            return null;
//        }
//        Specification<SpaceMarine> specification = Specification.where(createSpecification(filters.get(0)));
//        for (int i = 1; i < filters.size(); i++){
//            specification = specification.and(createSpecification(filters.get(i)));
//        }
//        return specification;
//    }
}
