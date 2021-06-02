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
    private static IQueueThread queueThread;

    @BeforeEach
    public void beforeEach() {
        commandQueue = new LinkedBlockingQueue<>();
        queueThread = mock(QueueProcessingThread.class, withSettings().useConstructor(commandQueue).defaultAnswer(CALLS_REAL_METHODS));
        commandExecutor = mock(CommandExecutor.class, withSettings().useConstructor(queueThread, commandQueue).defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    @DisplayName("После команды старт поток запущен")
    public void startCommandTest() {
        StartCommand startCommand = new StartCommand(commandExecutor);
        startCommand.execute();
        verify(commandExecutor, atLeast(1)).start();
        verify(queueThread, atLeast(1)).start();
        assertTrue(("'" + Thread.State.RUNNABLE + "','" + Thread.State.TIMED_WAITING + "','" + Thread.State.WAITING + "'").contains("'" + queueThread.getState() + "'"));
    }

    @Test
    @DisplayName("После команды hard stop, поток завершается")
    public void hardStopCommandTest() throws InterruptedException {
        startCommandTest();
        commandQueue.add(new StopHardCommand(commandExecutor));
        queueThread.join(5000);
        verify(commandExecutor, atLeast(1)).hardStop();
        verify(queueThread, atLeast(1)).stop();
        assertEquals(queueThread.getState(), Thread.State.TERMINATED);


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
        for (int i = 0; i < commandCount; i++)
            commandQueue.add(moveCommand);
        commandQueue.add(new StopSoftCommand(commandExecutor));
        for (int i = 0; i < commandCount; i++)
            commandQueue.add(moveCommand);
        queueThread.join(5000);
        verify(commandExecutor, atLeast(1)).softStop();
        verify(queueThread, atLeast(1)).stop();
        verify(movableMock, times(commandCount * 2)).setPosition(any());
        assertEquals(queueThread.getState(), Thread.State.TERMINATED);
    }

    @Test
    @DisplayName("После исключения, поток обработки сообщений продолжает работу")
    public void commmandExceptionTest() throws InterruptedException {
        startCommandTest();
        Movable movableMock = Mockito.mock(Movable.class);
        MoveCommand moveCommand = new MoveCommand(movableMock);
        when(movableMock.getPosition()).thenReturn(new Vector(1, 1));
        when(movableMock.getVelocity()).thenReturn(new Vector(2, 2));
        doThrow(new RuntimeException("Can't get position")).when(movableMock).setPosition(any());
        commandQueue.add(moveCommand);
        commandQueue.add(new StopSoftCommand(commandExecutor));
        queueThread.join(5000);
        verify(commandExecutor, atLeast(1)).softStop();
        verify(queueThread, atLeast(1)).stop();
        assertEquals(queueThread.getState(), Thread.State.TERMINATED);
    }
}
