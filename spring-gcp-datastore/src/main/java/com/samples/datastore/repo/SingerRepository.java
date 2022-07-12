package com.samples.datastore.repo;

import com.samples.datastore.model.Band;
import com.samples.datastore.model.Singer;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SingerRepository extends DatastoreRepository<Singer, String> {

    @Query("select * from  |com.samples.datastore.model.Singer| where last_name = @name")
    Slice<Singer> findSingersByLastName(@Param("name") String name, Pageable pageable);

    @Query("select * from  |com.samples.datastore.model.Singer| where firstBand = @band")
    List<Singer> findSingersByFirstBand(@Param("band") Band band);

    List<Singer> findByFirstBand(Band band);
}