package com.example.esalab3.messaging.utils;

import com.example.esalab3.entity.ChangeLog;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLogWithTypeInfo {
    private ChangeLog changeLog;
    private String changeLogTypeInfo;
}
