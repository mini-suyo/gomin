package com.ssafy.sushi.domain.notification.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NotificationTypeConverter implements AttributeConverter<NotificationType, Character> {

    @Override
    public Character convertToDatabaseColumn(NotificationType attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public NotificationType convertToEntityAttribute(Character dbData) {
        return dbData != null ? NotificationType.fromCode(dbData) : null;
    }

}
