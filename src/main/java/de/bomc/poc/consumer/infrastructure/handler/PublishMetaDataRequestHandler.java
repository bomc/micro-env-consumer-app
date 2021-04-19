package de.bomc.poc.consumer.infrastructure.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import de.bomc.poc.consumer.application.core.InMemoryPublishMetaDataService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
//@Log4j2
@Service
public class PublishMetaDataRequestHandler {

	private static final String LOG_PREFIX = PublishMetaDataRequestHandler.class.getName() + "#";

	private static final String PATH_VARIABLE_NAME = "id";
	
	private final InMemoryPublishMetaDataService inMemoryPublishMetaDataService;
	
	public PublishMetaDataRequestHandler(final InMemoryPublishMetaDataService inMemoryPublishMetaDataService) {
		this.inMemoryPublishMetaDataService = inMemoryPublishMetaDataService;
	}

	public Mono<ServerResponse> get(final ServerRequest serverRequest) {
		log.debug(LOG_PREFIX + "get");

		return this.inMemoryPublishMetaDataService.get(serverRequest.pathVariable(PATH_VARIABLE_NAME))
				.flatMap(publishMetaData -> ServerResponse.ok().bodyValue(publishMetaData)).log()
				.switchIfEmpty(ServerResponse.notFound().build()).log();
	}

	public Mono<ServerResponse> list(final ServerRequest serverRequest) {
		log.debug(LOG_PREFIX + "list");

		return this.inMemoryPublishMetaDataService.list().flatMap(list -> ServerResponse.ok().bodyValue(list)).log();
	}

	public Mono<ServerResponse> delete(final ServerRequest serverRequest) {
		log.debug(LOG_PREFIX + "delete");

		return this.inMemoryPublishMetaDataService.delete(serverRequest.pathVariable(PATH_VARIABLE_NAME))
				.flatMap(deletedPublishMetaData -> ServerResponse.ok().bodyValue(deletedPublishMetaData)).log()
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	/**
	 * Outsourced to {@link AnnotatedPublishMetaDataHandler} to handle parameter validation by annotations.
	 */
//	public Mono<ServerResponse> create(final ServerRequest serverRequest) {
//	log.debug(LOG_PREFIX + "create");
//	
//	return serverRequest
//			.bodyToMono(PublishMetaData.class)
//			.flatMap(this.inMemoryPublishMetaDataService::create)
//			.log()
//			.flatMap(publishMetaData -> ServerResponse.created(URI.create("/metadata/" + publishMetaData.getName())).build())
//			.log();
//	}

}
