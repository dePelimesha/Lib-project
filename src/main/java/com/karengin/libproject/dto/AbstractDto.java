package com.karengin.libproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public abstract class AbstractDto implements Serializable {
    private Long id;
}
