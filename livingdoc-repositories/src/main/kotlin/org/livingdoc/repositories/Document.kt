package org.livingdoc.repositories

class Document(val content: List<DocumentNode>)

sealed class DocumentNode {
    abstract val children: List<DocumentNode>

    data class Heading(
            val text: String,
            val depth: Int,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class Paragraph(
            val text: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class BulletList(
            val bulletChar: String? = null,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class OrderedList(
            val startItem: String,
            val delimiter: String? = null,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class IndentedCodeBlock(
            val code: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class ListItem(
            val openingMarker: String? = null,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class CodeBlock(
            val code: String,
            val info: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class Table(
            val rows: List<TableRow>,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class TableRow(
            val cells: List<TableCell>,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class TableCell(
            val text: String,
            override val children: List<DocumentNode>
    ) : DocumentNode()

    data class Other(
            override val children: List<DocumentNode>
    ) : DocumentNode()

}

