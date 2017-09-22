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
            val bulletChar: String? = null,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "BulletList[bulletChar = $bulletChar]"
    }

    data class OrderedMDList(
            val startItem: String,
            val delimiter: String? = null,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "OrderedMDList[startNumber = $startItem, delimiter = $delimiter]"
    }

    data class OrderedHtmlList(
            val type: String = "1",
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "OrderedHtmlList[type = $type]"
    }

    data class IndentedCodeBlock(
            val code: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class ListItem(
            val openingMarker: String? = null,
            override val children: List<DocumentNode>
    ) : DocumentNode() {
        override fun toString() = "ListItem[openingMarker = $openingMarker]"
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