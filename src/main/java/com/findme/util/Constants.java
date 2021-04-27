package com.findme.util;

public class Constants {

    // - минимальное количество дней в статусе друга
    public static final Integer MIN_DAYS_AS_FRIEND = 3;

    // - максимальное количество ожидающих подтверждения на статус
    public static final Integer MAX_QUANTITY_OF_STATUS_WAITING_FOR_ACCEPT = 10;

    // - максимальное количество друзей
    public static final Integer LIMIT_OF_FRIENDS = 100;

    // - максимальная длина поста
    public static final Integer MAX_LENGTH_OF_POST = 200;

    public static final String URL_REGEX = "^((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";

    // - максимальное колличество постов от друзей
    public static final Integer MAX_FEED_LIST = 10;

    // - максимальная длина сообщения
    public static final Integer MAX_SYMBOL_QUANTITY = 140;

    // - максимальное количество соообщений в списке
    public static final Integer MESSAGE_LIST_SIZE = 10;
}
