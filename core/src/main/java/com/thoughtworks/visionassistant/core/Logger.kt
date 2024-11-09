package com.thoughtworks.visionassistant.core

interface Logger {
    /**
     * Verbose
     *
     * @param message log message
     */
    fun verbose(message: String)

    /**
     * Debug
     *
     * @param message log message
     */
    fun debug(message: String)

    /**
     * Info
     *
     * @param message log message
     */
    fun info(message: String)

    /**
     * Warn
     *
     * @param message log message
     */
    fun warn(message: String)

    /**
     * Error
     *
     * @param message log message
     */
    fun error(message: String)

    /**
     * Wtf
     *
     * @param message log message
     */
    fun wtf(message: String)
}