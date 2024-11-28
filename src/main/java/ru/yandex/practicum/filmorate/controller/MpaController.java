package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

// Аннотация для логирования
@Slf4j
// Аннотация для REST-контроллера, который будет обрабатывать запросы о рейтингах MPA
@RestController
// Генерация конструктора с обязательными параметрами
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    // для работы с рейтингами MPA
    private final MpaService mpaService;

    // Получение списка всех рейтингов MPA
    @GetMapping
    public List<Mpa> getMpaList() {
        log.info("Получен запрос на получение списка рейтингов MPA");
        return mpaService.getMpaList();
    }

    // Получение рейтинга MPA по идентификатору
    @GetMapping("/{id}")
    public Mpa getMpa(@PathVariable int id) {
        log.info("Получен запрос на получения рейтинга MPA по id: {}", id);
        return mpaService.getMpa(id);
    }
}