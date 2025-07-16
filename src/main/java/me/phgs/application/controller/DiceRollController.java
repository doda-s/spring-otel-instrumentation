package me.phgs.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import me.phgs.application.util.Random;

@RestController
public class DiceRollController {
    @GetMapping("/dice")
    @WithSpan(kind = SpanKind.CLIENT, inheritContext = false, value = "dice roll")
    public String diceRoll() {
        int result = Random.getRandomNumber(1, 7);
        return Integer.toString(result);
    }
}