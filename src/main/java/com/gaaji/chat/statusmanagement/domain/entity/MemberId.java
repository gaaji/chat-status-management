package com.gaaji.chat.statusmanagement.domain.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@RequiredArgsConstructor(staticName = "of")
public class MemberId implements Serializable {

    private final String id;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        MemberId m = (MemberId) o;
        return Objects.equals(this.id, m.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
