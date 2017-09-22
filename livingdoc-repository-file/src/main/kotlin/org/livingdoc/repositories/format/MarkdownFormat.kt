package org.livingdoc.repositories.format

import com.vladsch.flexmark.ast.Node
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import org.livingdoc.repositories.DocumentNode
import java.io.File
import java.util.Arrays.asList
import com.vladsch.flexmark.ast.BulletList as MDBulletList
import com.vladsch.flexmark.ast.FencedCodeBlock as MDFencedCodeBlock
import com.vladsch.flexmark.ast.Heading as MDHeading
import com.vladsch.flexmark.ast.IndentedCodeBlock as MDIndentedCodeBlock
import com.vladsch.flexmark.ast.ListItem as MDListItem
import com.vladsch.flexmark.ast.OrderedList as MDOrderedList
import com.vladsch.flexmark.ast.Paragraph as MDParagraph
import com.vladsch.flexmark.ast.Text as MDText


//fun show(seq: BasedSequence) {
//    println("Showing Segment $seq")
//}
//
//fun show(node: Node) {
//    println("Showing ${node.javaClass} Node ${node.nodeName}")
//    node.segments.forEach { show(it) }
//    node.children.forEach {
//        show(it)
//    }
//}

fun map(nodes: Iterable<Node>): List<DocumentNode> {
    return nodes.map { node ->
        when (node) {
            is MDHeading ->
                DocumentNode.Heading(node.text.toString(), node.level, map(node.children))
            is MDParagraph ->
                DocumentNode.Paragraph(node.contentChars.toString(), map(node.children))
            is MDBulletList ->
                DocumentNode.BulletList(node.openingMarker.toString(), map(node.children))
            is MDOrderedList ->
                DocumentNode.OrderedList(node.startNumber, node.delimiter.toString(), map(node.children))
            is MDListItem ->
                DocumentNode.ListItem(node.openingMarker.toString(), node.markerSuffix.toString(), map(node.children))
//            is MDText ->
//                DocumentNode.Text(node.chars.toString(), map(node.children))
            is MDIndentedCodeBlock ->
                DocumentNode.IndentedCodeBlock(node.chars.toString(), map(node.children))
            is MDFencedCodeBlock ->
                DocumentNode.CodeBlock(node.contentChars.toString(), node.info.toString(), map(node.children))
            else ->
                DocumentNode.Noop(node.toString(), map(node.children))
        }
    }.filter { it !is DocumentNode.Noop }
}


fun main(args: Array<String>) {
    val options = MutableDataSet()
    options.set(Parser.EXTENSIONS, asList(TablesExtension.create()))
    val parser = Parser.builder(options).build()
    val text = File("/home/am/Documents/TEST.MD").readText()
    val document = parser.parse(text)

    map(document.children.toList()).forEach {
        show(it)
    }
}

fun show(item: DocumentNode, depth: Int = 0): Unit {
    var prefix = " "
    (1..depth).forEach { prefix += " " }
    println("${prefix}${item}")
    item.children.forEach {
        show(it, depth + 1)
    }

}