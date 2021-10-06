package com.sds.chocomuffin.mollymvc.survey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@With
@Getter
@RequiredArgsConstructor
public class ManagedInfo {

    private final boolean isDeleted;

    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final LocalDateTime startedDate;
    private final LocalDateTime closedDate;

    private final ManagedBy managedBy;

    public static ManagedInfo initBy(String username, String displayName){
        return new ManagedInfo(false, LocalDateTime.now(), null, null, null, new ManagedBy(username, username, displayName));
    }
}
