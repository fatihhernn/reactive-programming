import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService=new FluxAndMonoGeneratorService();


    @Test
    void nameMono() {
    }

    @Test
    void namesFlux_map(){
        //given

        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX","BEN","CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutibility(){
        //given

        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_immutibility();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX","BEN","CHLOE")
                .verifyComplete();
    }
    @Test
    void namesFlux_filter(){
        //given

        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_filter(3);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("4-ALEX","5-CHLOE")
                .verifyComplete();
    }
    @Test
    void namesMono_filter_map(){
        //given

        //when
        var namesFlux= fluxAndMonoGeneratorService.namesMono_map_filter(3);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX")
                .verifyComplete();
    }
    @Test
    void names_flatMap(){
        //given
        int word_length=3;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_flatMap(word_length);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void names_flatMap_asyc(){
        //given
        int word_length=3;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_flatMap_asyn(word_length);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void names_concat_asyc(){
        //given
        int word_length=3;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_concat_map(word_length);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void names_concat_transform(){
        //given
        int word_length=3;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_transform(word_length);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                //.expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void names_concat_transform_defaultIfEmpty(){
        //given
        int word_length=6;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_transform_defaultIfEmpty(word_length);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("default")
                .verifyComplete();
    }
    @Test
    void names_concat_transform_switchIfEmpty(){
        //given
        int word_length=6;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(word_length);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void name_concat_transform_defaultIfEmpty (){
        //given
        int word_length=4;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesMono_map_filter_defaultIfEmpty(word_length);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesMono_map_filter_switchIfEmpty (){
        //given
        int word_length=4;
        //when
        var namesFlux= fluxAndMonoGeneratorService.namesMono_map_filter_switchIfEmpty(word_length);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void explore_concat_test (){
        //given

        //when
        var concatFlux= fluxAndMonoGeneratorService.explore_concat();

        //then
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }
    @Test
    void explore_concat_test_concat_with (){
        //given

        //when
        var concatFlux= fluxAndMonoGeneratorService.explore_concat_mono();

        //then
        StepVerifier.create(concatFlux)
                .expectNext("D","A")
                .verifyComplete();
    }

    @Test
    void explore_concat_test_merge (){
        //given

        //when
        var value= fluxAndMonoGeneratorService.explore_merge();

        //then
        StepVerifier.create(value)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }
    @Test
    void explore_mergewith_mono (){
        //given

        //when
        var value= fluxAndMonoGeneratorService.explore_mergeSequential();

        //then
        StepVerifier.create(value)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }
    @Test
    void explore_zip (){
        //given

        //when
        var value= fluxAndMonoGeneratorService.explore_zip();

        //then
        StepVerifier.create(value)
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }
    @Test
    void explore_zip4 (){
        //given

        //when
        var value= fluxAndMonoGeneratorService.explore_zipWith();

        //then
        StepVerifier.create(value)
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }


}