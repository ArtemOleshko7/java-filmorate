package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaDbStorage mpaDbStorage; // Хранилище данных для объектов MPA

    public List<Mpa> getMpaList() {
        return mpaDbStorage.getMpaList();
    }

    public Mpa getMpa(int id) {
        return mpaDbStorage.getMpa(id);
    }
}