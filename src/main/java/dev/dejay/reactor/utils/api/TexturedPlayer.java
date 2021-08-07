package dev.dejay.reactor.utils.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record TexturedPlayer(String displayName, String texture,
                             String signature) {}