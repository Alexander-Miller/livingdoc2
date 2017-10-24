package org.livingdoc.repositories.format

import com.vladsch.flexmark.ast.Node
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import org.livingdoc.repositories.Document
import org.livingdoc.repositories.DocumentFormat
import org.livingdoc.repositories.DocumentNode
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*
import java.util.Arrays.asList
import com.vladsch.flexmark.ast.BulletList as MDBulletList
import com.vladsch.flexmark.ast.FencedCodeBlock as MDFencedCodeBlock
import com.vladsch.flexmark.ast.Heading as MDHeading
import com.vladsch.flexmark.ast.IndentedCodeBlock as MDIndentedCodeBlock
import com.vladsch.flexmark.ast.ListItem as MDListItem
import com.vladsch.flexmark.ast.OrderedList as MDOrderedList
import com.vladsch.flexmark.ast.Paragraph as MDParagraph
import com.vladsch.flexmark.ast.Text as MDText
import com.vladsch.flexmark.ext.tables.TableBlock as MDTableBlock
import com.vladsch.flexmark.ext.tables.TableBody as MDTableBody
import com.vladsch.flexmark.ext.tables.TableCell as MDTableCell
import com.vladsch.flexmark.ext.tables.TableHead as MDTableHead
import com.vladsch.flexmark.ext.tables.TableRow as MDTableRow
import com.vladsch.flexmark.ext.tables.TableSeparator as MDTableSeparator

class MarkdownFormat : DocumentFormat {

    override fun parse(stream: InputStream): Document {
        val streamContent = stream.readBytes().toString(Charset.defaultCharset())
        val options = MutableDataSet().apply { set(Parser.EXTENSIONS, asList(TablesExtension.create())) }
        val parser = Parser.builder(options).build()
        val document = parser.parse(streamContent)

        return Document(map(document.children.toList()))
    }

    private fun map(nodes: Iterable<Node>): List<DocumentNode> {
        return nodes.map { node ->
            when (node) {
                is MDHeading ->
                    DocumentNode.Heading(node.text.toString(), node.level, map(node.children))

                is MDParagraph ->
                    DocumentNode.Paragraph(node.contentChars.toString(), map(node.children))

                is MDBulletList ->
                    DocumentNode.BulletList(node.openingMarker.toString(), map(node.children))

                is MDOrderedList ->
                    DocumentNode.OrderedList(node.startNumber.toString(), node.delimiter.toString(), map(node.children))

                is MDListItem ->
                    DocumentNode.ListItem(node.openingMarker.toString(), map(node.children))

                is MDIndentedCodeBlock ->
                    DocumentNode.IndentedCodeBlock(node.chars.toString(), map(node.children))

                is MDFencedCodeBlock ->
                    DocumentNode.CodeBlock(node.contentChars.toString(), node.info.toString(), map(node.children))

                is MDTableBlock ->
                    parseTable(node)

                else ->
                    DocumentNode.Other(map(node.children))
            }
        }.filter { it !is DocumentNode.Other }
    }

    private fun parseTable(markdownTable: MDTableBlock): DocumentNode.Table {
        val resultRows = ArrayList<DocumentNode.TableRow>()
        markdownTable.children.forEach { node ->
            when (node) {
                is MDTableHead -> {
                    val headsFirstChild = node.children.first()
                    if (headsFirstChild is MDTableRow) {
                        resultRows += parseTableRow(headsFirstChild)
                    }
                }
                is MDTableBody ->
                    node.children
                            .filterIsInstance<MDTableRow>()
                            .forEach { resultRows += parseTableRow(it) }
            }
        }

        return DocumentNode.Table(resultRows, emptyList())
    }

    private fun parseTableCell(mdTableCell: MDTableCell) = DocumentNode.TableCell(mdTableCell.text.toString(), ArrayList())

    private fun parseTableRow(mdTableRow: MDTableRow): DocumentNode.TableRow {
        val cells = mdTableRow.children
                .filterIsInstance<MDTableCell>()
                .map(this::parseTableCell)
        return DocumentNode.TableRow(cells, emptyList())
    }

}

fun main(args: Array<String>) {
    val format = MarkdownFormat()
    val stream = File("/home/am/Documents/TEST.MD").inputStream()
    val document = format.parse(stream)
}

fun show(item: DocumentNode, depth: Int = 0): Unit {
    var prefix = " "
    (1..depth).forEach { prefix += " " }
    println("${prefix}${item}")
    item.children.forEach {
        show(it, depth + 1)
    }
}