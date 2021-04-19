package de.bomc.poc.consumer.application.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.bomc.poc.consumer.domain.model.PublishMetaData;
import de.bomc.poc.consumer.infrastructure.dao.InMemoryPublishMetaDataRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * A test case using {@link MockitoExtension}.
 *
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class InMemoryPublishMetaDataServiceTest {

	private static final String LOG_PREFIX = InMemoryPublishMetaDataServiceTest.class.getName() + "#";
	
	private static final String PUBLISH_META_DATA_ID_EMPTY = "";
	private static final String PUBLISH_META_DATA_ID = "42";
	private static final String PUBLISH_META_DATA_NAME = "bomc";
	
	@InjectMocks
	private InMemoryPublishMetaDataService sut;
	@Mock
	private InMemoryPublishMetaDataRepository inMemoryPublishMetaDataRepositoryMock;
	
	@Test
	@DisplayName("Creates a PublishMetaData instance in inmemory db.")
	public void test010_createPublishMetaData_pass() {
		log.debug(LOG_PREFIX + "test010_simpleInvocation_pass");
		
		// GIVEN
		final PublishMetaData publishMetaDataRequest = new PublishMetaData(PUBLISH_META_DATA_ID_EMPTY, PUBLISH_META_DATA_NAME);
		final PublishMetaData publishMetaDataReturn = new PublishMetaData(PUBLISH_META_DATA_ID, PUBLISH_META_DATA_NAME);
		
		when(this.inMemoryPublishMetaDataRepositoryMock.create(publishMetaDataRequest)).thenReturn(Mono.just(publishMetaDataReturn));
		
		// WHEN 
		final Mono<PublishMetaData> publishMetaDataMono = this.sut.create(publishMetaDataRequest);
		
		// THEN
		StepVerifier.create(publishMetaDataMono)
			.expectNextMatches(retVal -> {
				assertThat(retVal).isNotNull();
				assertThat(retVal.getId()).isEqualTo(PUBLISH_META_DATA_ID);
				assertThat(retVal.getName()).isEqualTo(PUBLISH_META_DATA_NAME);
				return true;
			})
			.expectComplete()
			.verify();
		
		verify(this.inMemoryPublishMetaDataRepositoryMock, Mockito.times(1)).create(any());
	}
	
}
