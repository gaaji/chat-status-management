package com.gaaji.chat.statusmanagement.global.config.redis.converter;

import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.List;

public class MemberIdConversion {

    public static List<Converter> conversions() {
        return List.of(new BytesToMemberIdConverter(), new MemberIdToBytesConverter());
    }

    @Component
    @ReadingConverter
    private static class BytesToMemberIdConverter implements Converter<byte[], MemberId> {
        @Override
        public MemberId convert(final byte[] source) {
            return MemberId.of(new String(source));
        }
    }
    @Component
    @WritingConverter
    private static class MemberIdToBytesConverter implements Converter<MemberId, byte[]> {
        @Override
        public byte[] convert(MemberId source) {
            return source.getId().getBytes();
        }
    }
}
