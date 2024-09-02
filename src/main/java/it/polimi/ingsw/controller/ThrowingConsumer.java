package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.EarlyTerminationException;

/**
 * Represents an operation that accepts a single input argument and may throw an exception.
 *
 * @param <T> the type of the input to the operation
 * @param <E> the type of exception that may be thrown by the operation
 * @author Luca Gritti
 */
public interface ThrowingConsumer <T, E extends Exception>{

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws EarlyTerminationException if an exception occurs during the operation
     */
    void accept(T t) throws EarlyTerminationException;
}
