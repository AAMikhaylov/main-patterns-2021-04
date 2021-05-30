package ru.otus.mainPatternsHW.hw02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DisplayName("Тестирование выполнения команд в многопоточном режиме через очередь")
public class CommandExecutorTest {
    private static BlockingQueue<Command> commandQueue;
    private static CommandExecutor commandExecutor;

    @BeforeEach
    public void beforeEach() {
        commandQueue = new LinkedBlockingQueue<>();
        commandExecutor = mock(CommandExecutor.class, withSettings().useConstructor(commandQueue).defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    @DisplayName("После команды старт поток запущен")
    public void startCommandTest() throws InterruptedException {
        StartCommand startCommand = new StartCommand(commandExecutor);
        synchronized (commandExecutor) {
            startCommand.execute();
            commandExecutor.wait(5000);
        }
        verify(commandExecutor, atLeast(1)).start();
        assertTrue(commandExecutor.isRunning());
    }

    @Test
    @DisplayName("После команды hard stop, поток завершается")
    public void hardStopCommandTest() throws InterruptedException {
        startCommandTest();
        synchronized (commandExecutor) {
            commandQueue.add(new StopHardCommand(commandExecutor));
            commandExecutor.wait(5000);
        }
        verify(commandExecutor, atLeast(1)).hardStop();
        assertFalse(commandExecutor.isRunning());
    }

    @Test
    @DisplayName("После команды soft stop, поток завершается только после того, как все задачи закончились")
    public void softStopCommandTest() throws InterruptedException {
        startCommandTest();
        Movable movableMock = Mockito.mock(Movable.class);
        MoveCommand moveCommand = new MoveCommand(movableMock);
        when(movableMock.getPosition()).thenReturn(new Vector(1, 1));
        when(movableMock.getVelocity()).thenReturn(new Vector(2, 2));
        int commandCount = 50;
        synchronized (commandExecutor) {
            for (int i = 0; i < commandCount; i++)
                commandQueue.add(moveCommand);
            commandQueue.add(new StopSoftCommand(commandExecutor));
            for (int i = 0; i < commandCount; i++)
                commandQueue.add(moveCommand);
            commandExecutor.wait();
        }
        verify(commandExecutor, atLeast(1)).softStop();
        verify(movableMock, times(commandCount * 2)).setPosition(any());
        assertFalse(commandExecutor.isRunning());
    }
}
