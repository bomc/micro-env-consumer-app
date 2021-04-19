package de.bomc.poc.consumer.application.core;

import org.springframework.stereotype.Service;

import de.bomc.poc.consumer.domain.model.PublishMetaData;
import de.bomc.poc.consumer.infrastructure.dao.InMemoryPublishMetaDataRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InMemoryPublishMetaDataService {

	private static final String LOG_PREFIX = InMemoryPublishMetaDataService.class.getName() + "#";
	
	private final InMemoryPublishMetaDataRepository inMemoryPublishMetaDataRepository;
	
	public InMemoryPublishMetaDataService(final InMemoryPublishMetaDataRepository inMemoryPublishMetaDataRepository) {
		this.inMemoryPublishMetaDataRepository = inMemoryPublishMetaDataRepository;
	}
	
	public Mono<PublishMetaData> create(final PublishMetaData publishMetaData) {
		log.debug(LOG_PREFIX + "create [publishMetaData=" + publishMetaData + "]");
		
		return this.inMemoryPublishMetaDataRepository.create(publishMetaData);
	}
	
	public Mono<PublishMetaData> get(final String name) {
		log.debug(LOG_PREFIX + "get [name=" + name + "]");
		
		return this.inMemoryPublishMetaDataRepository.findById(name);
	}
	
	public Mono<String> list() {
		log.debug(LOG_PREFIX + "list");

		return this.inMemoryPublishMetaDataRepository.findAll();
	}
	
	public Mono<PublishMetaData> delete(final String name) {
		log.debug(LOG_PREFIX + "delete [name=" + name + "]");
	
		return this.inMemoryPublishMetaDataRepository.delete(name);
	}

	public Mono<PublishMetaData> update(final PublishMetaData publishMetaData) {
		log.debug(LOG_PREFIX + "update [publishMetaData=" + publishMetaData + "]");
	
		return this.inMemoryPublishMetaDataRepository.update(publishMetaData);
	}
}
