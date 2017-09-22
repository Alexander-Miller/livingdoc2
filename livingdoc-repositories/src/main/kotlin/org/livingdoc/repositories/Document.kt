package org.livingdoc.repositories

sealed class DocumentNode() {
    abstract val children: List<DocumentNode>


    data class Heading(
            val text: String,
            val depth: Int,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "Heading[text = $text, depth = $depth]"
    }


    data class Paragraph(
            val text: String,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "Paragraph[text = $text]"
    }

    data class Text(
            val text: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class BulletList(
            val bulletChar: String,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "BulletList[bulletChar = $bulletChar]"
    }

    data class OrderedList(
            val startNumber: Int,
            val delimiter: String,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "OrderedList[startNumber = $startNumber, delimiter = $delimiter]"
    }

    data class IndentedCodeBlock(
            val code: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class ListItem(
            val openingMarker: String,
            val markerSuffix: String,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "ListItem[openingMarker = $openingMarker, markerSuffix = $markerSuffix]"
    }

    data class CodeBlock(
            val code: String,
            val info: String,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "CodeBlock[code = $code, info = $info]"
    }


    data class Noop(
            val name: String,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "Noop $name"
    }

//data class ListItem(val text: String) : DocumentNode()
}

class Document(val content: List<DocumentNode>)