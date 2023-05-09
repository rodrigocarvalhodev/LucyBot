package com.duckdeveloper.lucy.model.interfaces;

import com.duckdeveloper.lucy.type.InteractionType;

public interface Interaction {

    String getMessageId();
    InteractionType getType();

}