package com.kaz4.composemessanger.ui.chat.chatrow.chatlayout

data class ChatRowData(
    var text: String = "",
    // Width of the text without padding
    var textWidth: Int = 0,
    var lastLineWidth: Float = 0f,
    var lineCount: Int = 0,
    var rowWidth: Int = 0,
    var rowHeight: Int = 0,
    var parentWidth: Int = 0,
    var measuredType: Int = 0,
) {

    override fun toString(): String =
        "ChatRowData text: $text, " +
                "lastLineWidth: $lastLineWidth, lineCount: $lineCount, " +
                "textWidth: ${textWidth}, rowWidth: $rowWidth, height: $rowHeight, " +
                "parentWidth: $parentWidth, measuredType: $measuredType"

}
