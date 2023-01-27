package com.gaaji.chat.statusmanagement.global.config.redis.converter;

import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.List;

public class RoomIdConversion {

    public static List<Converter> conversions() {
        return List.of(new BytesToRoomIdConverter(), new RoomIdToBytesConverter());
    }

    @Component
    @ReadingConverter
    private static class BytesToRoomIdConverter implements Converter<byte[], RoomId> {
        @Override
        public RoomId convert(final byte[] source) {
            return RoomId.of(new String(source));
        }
    }
    @Component
    @WritingConverter
    private static class RoomIdToBytesConverter implements Converter<RoomId, byte[]> {
        @Override
        public byte[] convert(RoomId source) {
            return source.getId().getBytes();
        }
    }
}
