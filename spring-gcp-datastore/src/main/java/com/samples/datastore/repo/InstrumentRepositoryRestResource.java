package com.samples.datastore.repo;

import com.google.cloud.datastore.Key;

import com.samples.datastore.model.Instrument;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "instruments", path = "instruments")
public interface InstrumentRepositoryRestResource extends DatastoreRepository<Instrument, Key> {
}