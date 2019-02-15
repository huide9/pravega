/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.client.tables.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Version of a Key in a Table.
 */
public interface KeyVersion extends Serializable {

    /**
     * A special KeyVersion which indicates the Key must not exist when performing Conditional Updates.
     */

    KeyVersion NOT_EXISTS = new KeyVersion() {
        private static final long serialVersionUID = 1L;

        @Override
        public long getSegmentVersion() {
            return -1L;
        }

        @Override
        public ByteBuffer toBytes() {
            return ByteBuffer.allocate(0);
        }

        @Override
        public String toString() {
            return "NOT_EXISTS";
        }

        private Object readResolve() {
            return NOT_EXISTS;
        }
    };

    /**
     * Gets a value representing the internal version inside the Table Segment for this Key.
     * @return The internal version inside the Table Segment for this Key.
     */
    long getSegmentVersion();

    /**
     * Serializes the KeyVersion instance to a compact byte array.
     * @return The compact byte array of serialized KeyVersion instance.
     */
    ByteBuffer toBytes();

    /**
     * Deserializes the KeyVersion from its serialized form obtained from calling {@link #toBytes()}.
     *
     * @param serializedKeyVersion A serialized KeyVersion.
     * @return The KeyVersion object.
     */
    static KeyVersion fromBytes(ByteBuffer serializedKeyVersion) {
        if (!serializedKeyVersion.hasRemaining()) {
            return NOT_EXISTS;
        }
        return KeyVersionImpl.fromBytes(serializedKeyVersion);
    }
}
