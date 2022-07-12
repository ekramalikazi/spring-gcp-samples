package com.samples.datastore.repo;

import java.util.List;
import java.util.Set;

import com.samples.datastore.model.Band;
import com.samples.datastore.model.Instrument;
import com.samples.datastore.model.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * A transactional service that provides methods run as transactions.
 *
 * @author Chengyuan Zhao
 */
@Component
public class TransactionalRepositoryService {

	@Autowired
	private SingerRepository singerRepository;

	@Transactional
	public void createAndSaveSingerRelationshipsInTransaction(Singer singer,
															  Band firstBand,
															  List<Band> bands,
															  Set<Instrument> instruments) {

		singer.setFirstBand(firstBand);
		singer.setBands(bands);
		singer.setPersonalInstruments(instruments);

		this.singerRepository.save(singer);

		System.out.println(
				"Relationship links were saved between a singer, bands, and instruments"
						+ " in a single transaction: " + singer);
	}

}