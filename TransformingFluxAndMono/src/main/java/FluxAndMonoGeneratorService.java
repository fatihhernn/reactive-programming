import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Delayed;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFlux_immutibility() {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> namesFlux_filter(int length) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .map(p -> p.length() + "-" + p)
                .log();
    }


    public Flux<String> namesFlux_flatMap(int length) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMap(s -> splitString(s))
                .log();
    }

    //ALEX : Flux(A,L,E,X)
    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> namesFlux_flatMap_asyn(int length) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMap(s -> splitString_asyn(s))
                .log();
    }

    public Flux<String> namesFlux_concat_map(int length) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .concatMap(s -> splitString_asyn(s))
                .log();
    }

    public Flux<String> namesFlux_transform(int length) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                                                                        .filter(s -> s.length() > length);

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .concatMap(s -> splitString(s))
                .log();
    }

    public Flux<String> namesFlux_transform_defaultIfEmpty(int length) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > length);
        //Flux.empty();
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .concatMap(s -> splitString(s))
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int length) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > length)
                .flatMap(s->splitString(s));

        var defaultFlux=Flux.just("default").transform(filterMap);


        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .concatMap(s -> splitString(s))
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> splitString_asyn(String name) {
        var charArray = name.split("");
        int duration = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(duration));
    }

    public Mono<String> namesMono_map_filter(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength);
    }

    public Mono<String> namesMono_map_filter_defaultIfEmpty(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .defaultIfEmpty("default").log();
    }
    public Mono<String> namesMono_map_filter_switchIfEmpty(int stringLength) {

        Function<Mono<String>, Mono<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        var defaultvalue = Mono.just("default");

        return Mono.just("alex")
                .transform(filterMap)
                .switchIfEmpty(defaultvalue).log();
    }

    public Mono<String> nameMono() {
        return Mono.just("fatih").log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux_immutibility()
                .subscribe(p ->
                        System.out.println("name is " + p)
                );
        System.out.println("---------------------");
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name ->
                        System.out.println("name is " + name)
                );
    }

}
