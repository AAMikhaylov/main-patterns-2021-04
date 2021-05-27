package ru.otus.mainPatternsHW.hw01;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование движения объекта")
public class MoveTest {
    private static Movable movable;
    private static Move move;

    @BeforeAll
    public static void beforeAll() {
        movable = Mockito.mock(Movable.class);
        move = new Move(movable);
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(movable);
    }

    @Test
    @DisplayName("Для объекта, находящегося в точке (12, 5) и движущегося со скоростью (-7, 3) движение меняет положение объекта на (5, 8)")
    public void testMove() {
        when(movable.getPosition()).thenReturn(new Vector(12, 5));
        when(movable.getVelocity()).thenReturn(new Vector(-7, 3));
        move.execute();
        Mockito.verify(movable, Mockito.times(1)).setPosition(Mockito.eq(new Vector(5, 8)));
    }

    @Test
    @DisplayName("Попытка сдвинуть объект, у которого невозможно прочитать положение в пространстве, приводит к ошибке")
    public void testFailGetPosition() {
        when(movable.getPosition()).thenThrow(new RuntimeException("Can't get position"));
        when(movable.getVelocity()).thenReturn(new Vector(-7, 3));
        Throwable runtimeException = assertThrows(RuntimeException.class, move::execute);
        assertEquals("Can't get position", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Попытка сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости, приводит к ошибке")
    public void testFailGetVelocity() {
        when(movable.getPosition()).thenReturn(new Vector(12, 5));
        when(movable.getVelocity()).thenThrow(new RuntimeException("Can't get velocity"));
        Throwable runtimeException = assertThrows(RuntimeException.class, move::execute);
        assertEquals("Can't get velocity", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Попытка сдвинуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке")
    public void testFailSetPosition() {
        when(movable.getPosition()).thenReturn(new Vector(12, 5));
        when(movable.getVelocity()).thenReturn(new Vector(-7, 3));
        doThrow(new RuntimeException("Can't set new position")).when(movable).setPosition(Mockito.any());
        Throwable runtimeException = assertThrows(RuntimeException.class, move::execute);
        assertEquals("Can't set new position", runtimeException.getMessage());
    }
}
