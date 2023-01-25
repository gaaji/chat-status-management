package com.gaaji.chat.statusmanagement.domain.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@RequiredArgsConstructor(staticName = "of")
public class RoomId implements Serializable {

    @NonNull
    private String id;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        RoomId r = (RoomId) o;
        return Objects.equals(this.id, r.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}