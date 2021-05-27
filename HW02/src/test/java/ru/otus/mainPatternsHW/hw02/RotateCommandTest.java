package ru.otus.mainPatternsHW.hw02;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование поворота объекта")
public class RotateCommandTest {
    private static Rotable rotable;
    private static RotateCommand rotateCommand;

    @BeforeAll
    public static void beforeAll() {
        rotable = Mockito.mock(Rotable.class);
        rotateCommand = new RotateCommand(rotable);
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(rotable);
    }

    @Test
    @DisplayName("Для объекта, повернутого на угол 300гр, с угловой скоростью 70гр, поворот выполняется на угол 10гр от нулевого положения")
    public void testRotate() {
        when(rotable.getMaxDirections()).thenReturn(360);
        when(rotable.getDirection()).thenReturn(300);
        when(rotable.getAngularVelocity()).thenReturn(70);
        rotateCommand.execute();
        Mockito.verify(rotable, Mockito.times(1)).setDirection(Mockito.eq(10));
    }
    @Test
    @DisplayName("Для объекта, повернутого на угол 100гр, с угловой скоростью 0гр, поворот не выполняется")
    public void testZeroVelocity() {
        when(rotable.getMaxDirections()).thenReturn(360);
        when(rotable.getDirection()).thenReturn(100);
        when(rotable.getAngularVelocity()).thenReturn(0);
        rotateCommand.execute();
        Mockito.verify(rotable, Mockito.times(1)).setDirection(Mockito.eq(100));
    }

    @Test
    @DisplayName("Поворот с нулевым шагом приводит к ошибке")
    public void testFailMaxDirections() {
        when(rotable.getMaxDirections()).thenReturn(0);
        when(rotable.getDirection()).thenReturn(300);
        when(rotable.getAngularVelocity()).thenReturn(70);
        Throwable runtimeException = assertThrows(RuntimeException.class, rotateCommand::execute);
        assertEquals("/ by zero", runtimeException.getMessage());
    }
    @Test
    @DisplayName("Поворот объекта, у которого невозможно прочитать начальный угол, приводит к ошибке")
    public void testFailGetDirection() {
        when(rotable.getMaxDirections()).thenReturn(0);
        when(rotable.getDirection()).thenThrow(new RuntimeException("Can't get direction"));
        when(rotable.getAngularVelocity()).thenReturn(70);
        Throwable runtimeException = assertThrows(RuntimeException.class, rotateCommand::execute);
        assertEquals("Can't get direction", runtimeException.getMessage());
    }

}
