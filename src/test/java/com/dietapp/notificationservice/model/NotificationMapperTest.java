package com.dietapp.notificationservice.model;

import com.dietapp.notificationservice.DataProvider;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class NotificationMapperTest {

    private final NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    void shouldMapDtoToEntity() {
        var id = UUID.randomUUID();
        var dto = DataProvider.createDto(id);
        var result = mapper.toEntity(dto);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getCreatedDate()).isEqualTo(DataProvider.CREATED_DATE);
        assertThat(result.getLastUpdatedDate()).isEqualTo(DataProvider.LAST_UPDATED_DATE);
        assertThat(result.getVersion()).isEqualTo(DataProvider.VERSION);
        assertThat(result.getMessage()).isEqualTo(DataProvider.MESSAGE);
        assertThat(result.getCode()).isEqualTo(DataProvider.CODE);
        assertThat(result.getProperties().size()).isEqualTo(1);

        var property = result.getProperties().stream().toList().get(0);
        assertThat(property.getNotification()).isEqualTo(result);
        assertThat(property.getKey()).isEqualTo(DataProvider.KEY);
        assertThat(property.getValue()).isEqualTo(DataProvider.VALUE);
        assertThat(property.getId()).isNull();
    }

    @Test
    void shouldMapEntityToDto() {
        var id = UUID.randomUUID();
        var entity = DataProvider.createEntity(id, UUID.randomUUID());
        var result = mapper.toDto(entity);

        assertThat(result).isEqualTo(DataProvider.createDto(id));
    }

    @Test
    void shouldMapDtoToHttpResponse() {
        var id = UUID.randomUUID();
        var dto = DataProvider.createDto(id);
        var result = mapper.toHttpResponse(dto);

        assertThat(result).isEqualTo(DataProvider.createHttpResponse(id));
    }
}