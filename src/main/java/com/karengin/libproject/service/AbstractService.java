package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AbstractEntity;
import com.karengin.libproject.converter.AbstractConverter;
import com.karengin.libproject.dto.AbstractDto;
import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public abstract class AbstractService<DTO extends AbstractDto,
        Entity extends AbstractEntity, Repository extends JpaRepository> {

    private Repository repository;

    private AbstractConverter<DTO, Entity> converter;

    protected abstract boolean beforeSave(DTO dto);

    public ResponseEntity<List<DTO>> findAll() {
        return ResponseEntity.status(200).body(converter.convertToDto(repository.findAll()));
    }

    public ResponseEntity<String> deleteAll(final Collection<DTO> toDelete) {
        List<AbstractEntity> deleted = new ArrayList<>(
                toDelete.stream().map(getConverter()::convertToEntity)
                .collect(Collectors.toList())
        );
        getRepository().deleteAll(deleted);
        return ResponseEntity.status(200).body("Entity was deleted");
    }

    private PageRequest preparePageRequest(final Query<DTO, String> query){
        final int pageNumber = query.getOffset() / query.getLimit();
        final List<Sort.Order> sortOrders = new ArrayList<>();
        for (QuerySortOrder sortOrder : query.getSortOrders()){
            Sort.Order order = new Sort.Order(sortOrder.getDirection() == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortOrder.getSorted());
            sortOrders.add(order);
        }
        return PageRequest.of(pageNumber, query.getLimit(), sortOrders.isEmpty() ? Sort.unsorted() : Sort.by(sortOrders));
    }

    public Stream<DTO> findByFilterQueryWithPagination(final Query<DTO, String> query){
        final PageRequest pageRequest = preparePageRequest(query);
        final Optional<String> filterParameter = query.getFilter();
        List<DTO> dtoList = null;
        if (query.getFilter().isPresent() && !query.getFilter().get().isEmpty()){
            dtoList = findByFilterParameter(filterParameter.get());
        } else {
            dtoList = converter.convertToDto(repository.findAll(pageRequest).getContent());
        }
        return dtoList.stream();
    }

    public abstract List<DTO> findByFilterParameter(final String filterParameter);

    @Transactional
    public ResponseEntity<String> save (final DTO toSaveDto){
        if (beforeSave(toSaveDto)){
            repository.save(converter.convertToEntity(toSaveDto));
            return ResponseEntity.status(200).body("Entity saved");
        }
        return ResponseEntity.status(400).body("Wrong value");
    }

    public long count(){
        return repository.count();
    }

    public Repository getRepository() {
        return repository;
    }

    public AbstractConverter<DTO, Entity> getConverter() {
        return converter;
    }

}
