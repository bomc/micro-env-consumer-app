package de.bomc.poc.consumer.infrastructure.handler;

import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import de.bomc.poc.consumer.application.core.InMemoryPublishMetaDataService;
import de.bomc.poc.consumer.application.validation.AbstractValidationHandler;
import de.bomc.poc.consumer.domain.model.PublishMetaData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * A handler that handles requests validation by annotation. 
 */
@Slf4j
@Service
public class AnnotatedPublishMetaDataRequestHandler extends AbstractValidationHandler<PublishMetaData, Validator> {

	private static final String LOG_PREFIX = AnnotatedPublishMetaDataRequestHandler.class.getName() + "#";
	
	private final InMemoryPublishMetaDataService inMemoryPublishMetaDataService;
	
	/**
	 * The constructor is private, spring boot uses reflection during autowiring.
	 */
	private AnnotatedPublishMetaDataRequestHandler(final Validator validator, final InMemoryPublishMetaDataService inMemoryPublishMetaDataService) {
        super(PublishMetaData.class, validator);

        this.inMemoryPublishMetaDataService = inMemoryPublishMetaDataService;
    }

	@Override
	public Mono<ServerResponse> processBody(final PublishMetaData publishMetaData, final ServerRequest serverRequest) {
		log.debug(LOG_PREFIX + "processBody [publishMetaData=" + publishMetaData + ", serverRequest]");
		
		return this.inMemoryPublishMetaDataService
				.create((PublishMetaData)publishMetaData)
				.flatMap(retPublishMetaData -> ServerResponse.ok().bodyValue(retPublishMetaData))
				.log();
	}
	
	/**
	 * This method is only overwritten because OpenAPI does not call the handler method in a super class -> issue by OpenAPI.
	 *   
	 * @param serverRequest
	 * @return
	 */
	@Override
	public Mono<ServerResponse> handleRequest(final ServerRequest serverRequest) {
		log.debug(LOG_PREFIX + "handleRequest [serverRequest=" + serverRequest + "]");
		
		return super.handleRequest(serverRequest);
	}
	
}
