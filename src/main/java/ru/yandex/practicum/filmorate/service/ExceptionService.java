package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service; // Импорт аннотации для объявления сервисного компонента Spring
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Service // Заявка класса как сервиса Spring, который будет управляться контейнером
public class ExceptionService {

    public void throwNotFound() {
        throw new NotFoundException("Not found");
    }

    public void throwBadRequest(String message) {
        throw new BadRequestException(message);
    }
}