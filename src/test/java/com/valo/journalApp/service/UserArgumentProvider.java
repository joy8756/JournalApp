package com.valo.journalApp.service;

import com.valo.journalApp.entity.UserEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(UserEntity.builder().username("Jeff").password("jeff").build()),
                Arguments.of(UserEntity.builder().username("Suraj").password("suraj").build())
        );
    }
}
