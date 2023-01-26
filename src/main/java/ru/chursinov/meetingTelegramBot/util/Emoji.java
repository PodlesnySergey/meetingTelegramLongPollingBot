package ru.chursinov.meetingTelegramBot.util;

import com.vdurmont.emoji.EmojiParser;

/**
 * Small emoji library for interaction with user.
 */
public enum Emoji {

    CALENDAR(EmojiParser.parseToUnicode(":spiral_calendar_pad:")),
    TOOLS(EmojiParser.parseToUnicode(":hammer_and_wrench:")),
    REQUIREMENTS(EmojiParser.parseToUnicode(":no_mobile_phones:")),
    CLOSE(EmojiParser.parseToUnicode(":see_no_evil:")),
    MENU(EmojiParser.parseToUnicode(":point_down:")),
    EYES(EmojiParser.parseToUnicode(":eyes:")),
    DOCUMENT(EmojiParser.parseToUnicode(":page_facing_up:")),
    URL(EmojiParser.parseToUnicode(":earth_asia:")),
    SMILE(EmojiParser.parseToUnicode(":grin:")),
    POINT_UP(EmojiParser.parseToUnicode(":point_up:")),
    CONFUSED(EmojiParser.parseToUnicode(":confused:"));

    private final String emojiName;

    Emoji(String emojiName) {
        this.emojiName = emojiName;
    }

    @Override
    public String toString() {
        return emojiName;
    }

}
