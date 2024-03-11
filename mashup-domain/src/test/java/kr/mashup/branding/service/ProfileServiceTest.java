package kr.mashup.branding.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @ParameterizedTest
    @MethodSource("provideForIsLocalTest")
    @DisplayName("프로파일이 local 이 포함될 경우에만 isLocal 의 응답값은 true 이다.")
    void isLocal(String[] activeProfiles, boolean expected) {
        // given
        Environment environment = Mockito.mock(Environment.class);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);
        ProfileService sut = new ProfileService(environment);

        // when
        boolean actual = sut.isLocal();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
    private static Stream<Arguments> provideForIsLocalTest(){
        return Stream.of(
            Arguments.of(new String[]{"local"}, true),
            Arguments.of(new String[]{"local", "swagger"}, true),
            Arguments.of(new String[]{"develop"}, false),
            Arguments.of(new String[]{"production"}, false)
        );
    }


    @ParameterizedTest
    @MethodSource("provideForIsDevelopTest")
    @DisplayName("프로파일이 develop 이 포함될 경우에만 isDevelop 의 응답값은 true 이다.")
    void isDevelop(String[] activeProfiles, boolean expected) {
        // given
        Environment environment = Mockito.mock(Environment.class);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);
        ProfileService sut = new ProfileService(environment);

        // when
        boolean actual = sut.isDevelop();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsDevelopTest(){
        return Stream.of(
            Arguments.of(new String[]{"local"}, false),
            Arguments.of(new String[]{"local", "swagger"}, false),
            Arguments.of(new String[]{"develop"}, true),
            Arguments.of(new String[]{"develop","swagger"}, true),
            Arguments.of(new String[]{"production"}, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideForIsProductionTest")
    @DisplayName("프로파일이 production 이 포함될 경우에만 isProduction 의 응답값은 true 이다.")
    void isProduction(String[] activeProfiles, boolean expected) {
        // given
        Environment environment = Mockito.mock(Environment.class);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);
        ProfileService sut = new ProfileService(environment);

        // when
        boolean actual = sut.isProduction();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsProductionTest(){
        return Stream.of(
            Arguments.of(new String[]{"local"}, false),
            Arguments.of(new String[]{"local", "swagger"}, false),
            Arguments.of(new String[]{"develop"}, false),
            Arguments.of(new String[]{"develop","swagger"}, false),
            Arguments.of(new String[]{"production"}, true),
            Arguments.of(new String[]{"production","swagger"}, true)
        );
    }
}