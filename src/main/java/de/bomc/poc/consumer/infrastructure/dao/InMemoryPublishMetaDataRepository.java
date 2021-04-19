package de.bomc.poc.consumer.infrastructure.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.bomc.poc.consumer.application.exception.common.ApplicationException;
import de.bomc.poc.consumer.domain.model.PublishMetaData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class InMemoryPublishMetaDataRepository {

	private static final String LOG_PREFIX = InMemoryPublishMetaDataRepository.class.getName() + "#";
	
	private final Map<String, PublishMetaData> publishMetaDataMap;
	
	private final ObjectMapper objectMapper;
	
	public InMemoryPublishMetaDataRepository(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		
		this.publishMetaDataMap = new ConcurrentHashMap<String, PublishMetaData>();
	}
	
	public Mono<PublishMetaData> findById(final String id) {
		log.debug(LOG_PREFIX + "findById [id=" + id + "]");
		
		return Mono.justOrEmpty(this.publishMetaDataMap.get(id));
	}

	public Mono<PublishMetaData> create(final PublishMetaData publishMetaData) {
		log.debug(LOG_PREFIX + "create [publishMetaData=" + publishMetaData + "]");
		
		this.publishMetaDataMap.put(publishMetaData.getId(), publishMetaData);

		return Mono.just(publishMetaData);
	}

	public Mono<String> findAll() {
		log.debug(LOG_PREFIX + "findAll - return list as string.");
		
		final List<PublishMetaData> publishMetaDataList = this.publishMetaDataMap
																.entrySet()
																.stream()
																.sorted(Map.Entry.<String, PublishMetaData>comparingByKey().reversed())
																.map(Map.Entry::getValue)
																.collect(Collectors.toList());
		
		// Form List<PublishMetaData> to string to transport to client.
	    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    try {
			this.objectMapper.writeValue(byteArrayOutputStream, publishMetaDataList);
			
		    final byte[] data = byteArrayOutputStream.toByteArray();
		    
			return Mono.just(new String(data));
		} catch (final IOException iOException) {
			log.error(LOG_PREFIX + "findAll - jsonhandling failed!" + iOException.getMessage());
			
			throw new ApplicationException(LOG_PREFIX + "findAll - json handling failed", iOException);
		}
	}
	
	public Mono<PublishMetaData> delete(final String key) {
		log.debug(LOG_PREFIX + "delete [key=" + key + "]");
		
		final PublishMetaData deletedPublishMetaData = this.publishMetaDataMap.remove(key);
		
		return Mono.just(deletedPublishMetaData);
	}

	public Mono<PublishMetaData> update(final PublishMetaData publishMetaData) {
		log.debug(LOG_PREFIX + "update [publishMetaData=" + publishMetaData + "]");

		if(this.publishMetaDataMap.containsKey(publishMetaData.getId())) {
			final PublishMetaData retPublishMetaData = this.publishMetaDataMap.put(publishMetaData.getId(), publishMetaData);
		
			return Mono.just(retPublishMetaData);
		} else {
			//
			// No object available to the given id.
			return Mono.empty();
		}
	}
}
